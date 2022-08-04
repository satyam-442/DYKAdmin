package com.example.dyk_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addFacts(View view) {
        Intent intent = new Intent(this, AddFactsActivity.class);
        startActivity(intent);
    }

    public void addBlogs(View view) {
        Intent intent = new Intent(this, AddBlogsActivity.class);
        startActivity(intent);
    }

    public void addQuotes(View view) {
        Intent intent = new Intent(this, AddQuotesActivity.class);
        startActivity(intent);
    }

    public void logoutAdmin(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void sendNotification(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void viewUsers(View view) {
        Intent intent = new Intent(this, ViewUsersActivity.class);
        startActivity(intent);
    }

    public void goToViewFacts(View view) {
        Intent intent = new Intent(this, FactCategory.class);
        startActivity(intent);
    }

    public void viewQuiz(View view) {
    }

    public void addQuiz(View view) {
        Intent intent = new Intent(this, AddQuizActivity.class);
        startActivity(intent);
    }
}