package com.example.cryptogevityalpha.ui.CampusResourceFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.cryptogevityalpha.R;

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
        findViewById(R.id.AlmaMater).setVisibility(View.VISIBLE);
        findViewById(R.id.cs498Button).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.VISIBLE);
            findViewById(R.id.badm375text).setVisibility(View.GONE);
            findViewById(R.id.ace499text).setVisibility(View.GONE);
            findViewById(R.id.AlmaMater).setVisibility(View.GONE);
        });
        findViewById(R.id.badm375Button).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.GONE);
            findViewById(R.id.badm375text).setVisibility(View.VISIBLE);
            findViewById(R.id.ace499text).setVisibility(View.GONE);
            findViewById(R.id.AlmaMater).setVisibility(View.GONE);
        });
        findViewById(R.id.ace499Button).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.GONE);
            findViewById(R.id.badm375text).setVisibility(View.GONE);
            findViewById(R.id.ace499text).setVisibility(View.VISIBLE);
            findViewById(R.id.AlmaMater).setVisibility(View.GONE);
        });
        findViewById(R.id.Other).setOnClickListener(v -> {
            findViewById(R.id.cs498text).setVisibility(View.GONE);
            findViewById(R.id.badm375text).setVisibility(View.GONE);
            findViewById(R.id.ace499text).setVisibility(View.GONE);
            findViewById(R.id.AlmaMater).setVisibility(View.VISIBLE);
        });
    }
}
