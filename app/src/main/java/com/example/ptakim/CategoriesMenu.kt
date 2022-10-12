package com.example.ptakim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoriesMenu extends AppCompatActivity {
    protected  Integer NumOfWorld;
    protected int NumOfCategories = 0;
    protected boolean [] Categories = new boolean[8];
    protected Button place;
    protected Button movie;
    protected Button company;
    protected Button object;
    protected Button culture_leaders;
    protected Button sport;
    protected Button nature;
    protected Button scientists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_menu);
        NumOfWorld = getIntent().getIntExtra("NumOfWorlds",20);
        place = findViewById(R.id.place);
        movie = findViewById(R.id.movie);
        company = findViewById(R.id.company);
        object = findViewById(R.id.object);
        culture_leaders = findViewById(R.id.culture_leaders);
        sport = findViewById(R.id.sport);
        nature= findViewById(R.id.nature);
        scientists= findViewById(R.id.scientists);


        for(int i=0;i<8;i++)
            Categories[i] = false;
    }

    public void place(View view){
        if(Categories[0] == false){
            Categories[0] = true;
            place.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[0] = false;
            place.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void movie(View view){
        if(Categories[1] == false){
            Categories[1] = true;
            movie.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[1] = false;
            movie.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void company(View view){
        if(Categories[2] == false){
            Categories[2] = true;
            company.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[2] = false;
            company.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void object(View view){
        if(Categories[3] == false){
            Categories[3] = true;
            object.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[3] = false;
            object.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void culture_leaders(View view){
        if(Categories[4] == false){
            Categories[4] = true;
            culture_leaders.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[4] = false;
            culture_leaders.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void sport(View view){
        if(Categories[5] == false){
            Categories[5] = true;
            sport.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[5] = false;
            sport.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void nature(View view){
        if(Categories[6] == false){
            Categories[6] = true;
            nature.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[6] = false;
            nature.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }

    public void scientists(View view){
        if(Categories[7] == false){
            Categories[7] = true;
            scientists.setBackgroundColor(Color.parseColor("#ff669900"));
            NumOfCategories ++;
        }
        else {
            Categories[7] = false;
            scientists.setBackgroundColor(Color.parseColor("#B00020"));
            NumOfCategories --;
        }
    }


    public   void  Start(View view){
        if(NumOfCategories != 0){
            Intent intent = new Intent(this,Game.class);
            intent.putExtra("NumOfWorlds",NumOfWorld);
            intent.putExtra("NumOfCategories",NumOfCategories);
            intent.putExtra("Categories",Categories);

            startActivity(intent);
        }

    }

    public void  return_button(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}