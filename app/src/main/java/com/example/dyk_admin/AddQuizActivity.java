package com.example.dyk_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
    }

    public void addTechnologyQuiz(View view) {
        Intent intent = new Intent(this, AddQuizTwoActivity.class);
        intent.putExtra("category","Technology");
        startActivity(intent);
    }

    public void addScienceQuiz(View view) {
        Intent intent = new Intent(this, AddQuizTwoActivity.class);
        intent.putExtra("category","Science");
        startActivity(intent);
    }

    public void addHistoryQuiz(View view) {
        Intent intent = new Intent(this, AddQuizTwoActivity.class);
        intent.putExtra("category","History");
        startActivity(intent);
    }

    public void addFinanceQuiz(View view) {
        Intent intent = new Intent(this, AddQuizTwoActivity.class);
        intent.putExtra("category","Finance");
        startActivity(intent);
    }
}