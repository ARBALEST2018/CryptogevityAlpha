package com.example.cryptogevityalpha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loginButton).setOnClickListener(v-> {
            loginWindow();
        });
        findViewById(R.id.chatbotButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatbotActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.campusResourceButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, CampusResourceActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.newsButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        });
    }
    private void loginWindow() {
        final View container = getLayoutInflater().inflate(R.layout.login_window_chunk, null, false);
        ((EditText) container.findViewById(R.id.enterUsername)).setText("");
        ((EditText) container.findViewById(R.id.enterPassword)).setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(container).setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                testTemp();
            }
        });
        builder.show();
    }
    private void testTemp() {
        final View tempContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(MainActivity.this);
        ((TextView)tempContainer.findViewById(R.id.onlyTextView)).setText("This is where the system supposed to send the message to the database, and get a response about whether the username and password combo are valid. For testing purpose now, click VALID to simulate if it is, and INVALID otherwise.");
        tempBuilder.setView(tempContainer);
        tempBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                upvalid();
            }
        }).setNegativeButton("INVALID", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                upinvalid();
            }
        });
        tempBuilder.show();
    }
    private void upvalid() {
        final View validContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        AlertDialog.Builder validBuilder = new AlertDialog.Builder(MainActivity.this);

        ((TextView)validContainer.findViewById(R.id.onlyTextView)).setText("Welcome!");
        validBuilder.setView(validContainer);
        validBuilder.show();
    }
    private void upinvalid() {
        final View invalidContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        AlertDialog.Builder invalidBuilder = new AlertDialog.Builder(MainActivity.this);

        ((TextView)invalidContainer.findViewById(R.id.onlyTextView)).setText("Your password does not match your user name. Try again. Click FORGOT PASSWORD if you don't remember.");
        invalidBuilder.setView(invalidContainer);
        invalidBuilder.show();
    }
}

