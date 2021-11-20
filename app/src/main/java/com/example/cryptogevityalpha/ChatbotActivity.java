package com.example.cryptogevityalpha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        findViewById(R.id.chatbotBackButton).setOnClickListener(v -> {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            onBackPressed();
        });
        findViewById(R.id.send).setOnClickListener(v -> {
            send();
        });
    }
    private void send() {
        String entered = ((EditText)findViewById(R.id.enter_chat)).getText().toString().trim();
        if (entered.length() == 0) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            AlertDialog.Builder nothingBuilder = new AlertDialog.Builder(this);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("Come on, at least say something!");
            nothingBuilder.setView(nothingContainer);
            nothingBuilder.show();
            ((EditText)findViewById(R.id.enter_chat)).setText("");
        } else {
            final View myTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setText("I said: "+entered+"\n");
            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.RIGHT);
            ((LinearLayout)findViewById(R.id.scroll_container)).addView(myTextContainer);
            ((EditText)findViewById(R.id.enter_chat)).setText("");

            final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Just a minute, looking that up");
            respond(entered, ((TextView)botTextContainer.findViewById(R.id.onlyTextView)));
            ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
            ((LinearLayout)findViewById(R.id.scroll_container)).addView(botTextContainer);
        }
    }
    private void respond(String input, TextView toSet) {
        String url = "http://34.231.40.162:8000/api/chatterbot/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject toSend = new JSONObject();
        try {
            toSend.put("text", input);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("\n\n\n\n");
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                toSend,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Web Api called");
                            toSet.setText(response.getJSONObject("text").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toSet.setText("1: "+e.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            toSet.setText("2: "+e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toSet.setText("Sorry, I lost connection now, please try again later.");
                        toSet.setText("3: "+error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }
}
