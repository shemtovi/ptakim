package com.example.ptakim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  Integer NumOfWords = 20;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView  = findViewById(R.id.NumOfWorlds);

    }

    public   void  minus(View view){
        if(NumOfWords>20){
            NumOfWords = NumOfWords - 5;
            textView.setText(NumOfWords.toString());

        }
    }
    public   void  plas(View view){
        if(NumOfWords<=35){
            NumOfWords = NumOfWords +5;
            textView.setText(NumOfWords.toString());
        }
    }
    public   void  Start(View view){
        Intent intent = new Intent(this,CategoriesMenu.class);
        intent.putExtra("NumOfWorlds",NumOfWords);

        startActivity(intent);
    }
}