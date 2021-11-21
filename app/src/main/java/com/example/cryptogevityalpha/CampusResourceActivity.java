package com.example.cryptogevityalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CampusResourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_resource);
        findViewById(R.id.campusResourceBackButton).setOnClickListener(v -> {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            onBackPressed();
        });
        findViewById(R.id.cs498text).setVisibility(View.GONE);
        findViewById(R.id.badm375text).setVisibility(View.GONE);
        findViewById(R.id.ace499text).setVisibility(View.GONE);
        findViewById(R.id.cs498Button).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.VISIBLE);
            findViewById(R.id.badm375text).setVisibility(View.GONE);
            findViewById(R.id.ace499text).setVisibility(View.GONE);
        });
        findViewById(R.id.badm375Button).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.GONE);
            findViewById(R.id.badm375text).setVisibility(View.VISIBLE);
            findViewById(R.id.ace499text).setVisibility(View.GONE);
        });
        findViewById(R.id.ace499Button).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.GONE);
            findViewById(R.id.badm375text).setVisibility(View.GONE);
            findViewById(R.id.ace499text).setVisibility(View.VISIBLE);
        });
    }
}
