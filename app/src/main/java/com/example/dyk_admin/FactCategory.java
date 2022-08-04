package com.example.dyk_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FactCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_category);
    }

    public void interesting(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","Interesting");
        startActivity(intent);
    }

    public void mostPopular(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","MostPopular");
        startActivity(intent);
    }

    public void animal(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","Animal");
        startActivity(intent);
    }

    public void nature(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","Nature");
        startActivity(intent);
    }

    public void worldCulture(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","WorldCulture");
        startActivity(intent);
    }

    public void science(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","Science");
        startActivity(intent);
    }

    public void technology(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","Technology");
        startActivity(intent);
    }

    public void funny(View view) {
        Intent intent = new Intent(FactCategory.this, ViewFactsActivity.class);
        intent.putExtra("category","Funny");
        startActivity(intent);
    }
}