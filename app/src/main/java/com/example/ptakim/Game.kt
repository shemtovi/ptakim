package com.example.ptakim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.PriorityQueue;


public class Game extends AppCompatActivity {
    private  TextView textView;

    private Integer player1_score = 0;
    private Integer player2_score = 0;
    private TextView player1_ScoreBoard;
    private TextView player2_ScoreBoard;
    private TextView word;
    private Integer WhoPlay = 1;
    private Integer stage = 1;



    protected PriorityQueue<Node> FirstMaxHeap;
    protected PriorityQueue<Node> SecondMaxHeap;
    protected PriorityQueue<Node> ThirdMaxHeap;
    protected PriorityQueue<Node> CurrentMaxHeap;
    protected int NumOfWords;
    protected int NumOfCategories;
    protected boolean[] Categories;
    private NodeComparator Comparator = new NodeComparator();
    protected WordBank bank;

    private ImageButton correct_button;
    private ImageButton error_button;

    //popup window elements
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView MainText;
    private Button dagim;

    //timer elements
    private TextView timer;
    private  static  final  long START_TIME_IN_MILLIS = 60000;
    private ImageButton stop_button;
    private  CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private  long mTimeleftinmillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        textView = findViewById(R.id.textView);

        NumOfWords = getIntent().getIntExtra("NumOfWorlds", 20);
        NumOfCategories = getIntent().getIntExtra("NumOfCategories", 1);
        Categories = getIntent().getBooleanArrayExtra("Categories");
        bank = new WordBank(NumOfWords,NumOfCategories,Categories);
        FirstMaxHeap = new PriorityQueue<Node>(NumOfWords,Comparator );
        SecondMaxHeap = new PriorityQueue<Node>(NumOfWords,Comparator );
        ThirdMaxHeap = new PriorityQueue<Node>(NumOfWords,Comparator );
        CurrentMaxHeap = FirstMaxHeap;




        timer = findViewById(R.id.timer);
        player1_ScoreBoard = findViewById(R.id.player1_ScoreBoard);
        player2_ScoreBoard = findViewById(R.id.player2_ScoreBoard);
        word = findViewById(R.id.word);

        correct_button = findViewById(R.id.correct_button);
        error_button = findViewById(R.id.error_button);
        stop_button = findViewById(R.id.stop_button);

        CreatingMaxHeaps();
        CreateNewPopUpWindow();




    }

    public void CreatingMaxHeaps(){

        String [][] selectedWords = WordBank.WordsOfCategory();
        for(int t = 0;t<8;t++) {
            if(Categories[t]){
                for(String str:selectedWords[t] ) {
                    FirstMaxHeap.add(new Node(str));
                    SecondMaxHeap.add(new Node(str));
                    ThirdMaxHeap.add(new Node(str));
                }
            }
        }
    }

    //popup window
    public void  CreateNewPopUpWindow(){
        dialogBuilder = new AlertDialog.Builder(this);
        final  View PopUpView = getLayoutInflater().inflate(R.layout.popup,null);
        MainText = PopUpView.findViewById(R.id.MainText);
        dagim = PopUpView.findViewById(R.id.dagim);



        dialogBuilder.setView(PopUpView);
        dialog = dialogBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /*
        final PopupWindow popUpReject = new PopupWindow(popuplayoutReject, WindowManager.LayoutParams.FILL_PARENT,
                            WindowManager.LayoutParams.FILL_PARENT, false);
                    popUpReject.setOutsideTouchable(false);
         */
    }

    public void dagim_start(View view){
        dialog.dismiss();
        startTimer();
        nextWord();

    }
    public void Home_Button(View view){
        //go back to home activity
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void stop_button(View view){
        //stop -> popup show
        CreateNewPopUpWindow();
        MainText.setText( "שלב מס':"+ stage.toString() +"             " +"והתור של קבוצה מס':" + WhoPlay.toString());
        stopTimer();
    }



    //timer function
    public void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeleftinmillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeleftinmillis = millisUntilFinished;
                updateCountDownTimer();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                resetTimer();
                CreateNewPopUpWindow();
                changeWhoIsPlaying();
                MainText.setText( "שלב מס':"+ stage.toString() +"             " +"והתור של קבוצה מס':" + WhoPlay.toString());

            }
        }.start();

        mTimerRunning =true;


    }

    public void stopTimer(){
        if(mTimerRunning){
            mCountDownTimer.cancel();
            mTimerRunning = false;
        }
    }

    public void resetTimer(){
        mTimeleftinmillis = START_TIME_IN_MILLIS;
        updateCountDownTimer();

    }

    private  void  updateCountDownTimer(){
        Integer seconds = (int)(mTimeleftinmillis/1000)%60;

        timer.setText(seconds.toString());
    }



    public void correct_ans(View view){
        if(WhoPlay ==1){
            player1_score = player1_score +2;
            player1_ScoreBoard.setText(player1_score.toString());
        }
        else {
            player2_score = player2_score + 2;
            player2_ScoreBoard.setText(player2_score.toString());
        }
        nextWord();
    }

    public void false_ans(View view){
        if(WhoPlay ==1){
            player1_score = player1_score - 1;
            player1_ScoreBoard.setText(player1_score.toString());
        }
        else {
            player2_score = player2_score - 1;
            player2_ScoreBoard.setText(player2_score.toString());
        }
        String false_word = word.getText().toString();
        if(!CurrentMaxHeap.isEmpty()){
            CurrentMaxHeap.add(new Node(false_word,CurrentMaxHeap.peek().getNum()));//insure that the word wont came out again immediately
        }
        else{
            CurrentMaxHeap.add(new Node(false_word));
        }
        nextWord();
    }


    private void nextWord(){
        String str = "";
        if(!CurrentMaxHeap.isEmpty()) {
            Node node = CurrentMaxHeap.poll();
            str = node.getStr();
            textView.setText(((Integer) node.getNum()).toString());

        }
        else {//if the part done
            changeMaxHeap();
        }

        word.setText(str);

    }

    private  void  changeMaxHeap(){
        if(!SecondMaxHeap.isEmpty()){//if stage 2 do not began it
            CurrentMaxHeap = SecondMaxHeap;
            stage = 2;
            stopTimer();
            resetTimer();
            changeWhoIsPlaying();
            CreateNewPopUpWindow();
            MainText.setText("שלב שני מתחיל:    מילה אחת בלבד לתאור.                 הקבוצה המתחילה היא קבוצה מס':"+ " " + WhoPlay.toString());



            //popup stage 2 begins

        }
        else if (!ThirdMaxHeap.isEmpty()) {//if stage 3 do not began it
            CurrentMaxHeap = ThirdMaxHeap;
            stage = 3;
            stopTimer();
            resetTimer();
            changeWhoIsPlaying();
            CreateNewPopUpWindow();
            MainText.setText("שלב שלישי מתחיל:                  פנטומימה בלבד לתאור.             הקבוצה המתחילה היא קבוצה מס':"+ " " + WhoPlay.toString());

            //popup stage 3 begins
        }
        else {//stage 3 finished
            //popup winner is...
            correct_button.setEnabled(false);
            error_button.setEnabled(false);
            stopTimer();
            resetTimer();
            CreateNewPopUpWindow();
            dagim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StartNewGame(view);
                }
            });
            dagim.setText("משחק חדש");
            if(player1_score-player2_score>0)
                MainText.setText( "הקבוצה המנצחת היא: קבוצה מס' 1!!!!");
            else if(player1_score-player2_score<0)
                MainText.setText( "הקבוצה המנצחת היא: קבוצה מס' 2!!!!");
            else
                MainText.setText("דבר כזה לא קרה מעולם המנצח הוא זה הנהנה מהדרך");


        }
    }

    public void  StartNewGame(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private  void  changeWhoIsPlaying(){
        if(WhoPlay == 1)
            WhoPlay = 2;
        else
            WhoPlay = 1;
    }







}

