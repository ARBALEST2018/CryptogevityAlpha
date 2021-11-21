package com.example.cryptogevityalpha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChatbotActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        findViewById(R.id.chatbotBackButton).setOnClickListener(v -> {
            onBackPressed();
        });
        findViewById(R.id.send).setOnClickListener(v -> {
            send();
        });
        final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Hello there, I am Cryptogevity Chatbot. Say something to me!");
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(19, 41, 75));
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
        ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
        ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setPadding(20, 20, 200, 20);
        ((LinearLayout)findViewById(R.id.chat_scroll_container)).addView(botTextContainer);
        //((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
        findViewById(R.id.chat_scroll).post(new Runnable() {
            public void run() {
                ((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
            }
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
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setText(entered);
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(232, 74, 39));

            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.RIGHT);
            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setPadding(200, 20, 20, 20);
            ((LinearLayout)findViewById(R.id.chat_scroll_container)).addView(myTextContainer);
            ((EditText)findViewById(R.id.enter_chat)).setText("");

            final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Just a minute, looking that up");
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(19, 41, 75));
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
            respond(entered, ((TextView)botTextContainer.findViewById(R.id.onlyTextView)));
            ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
            ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setPadding(20, 20, 200, 20);
            ((LinearLayout)findViewById(R.id.chat_scroll_container)).addView(botTextContainer);
            //((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
            findViewById(R.id.chat_scroll).post(new Runnable() {
                public void run() {
                    ((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }
    private void respond(String input, TextView toSet) {
        String url = "http://34.231.40.162:8000/api/chatterbot/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("text", input);
        JSONObject toSend = new JSONObject(params);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                toSend,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Web Api called");
                            toSet.setText(response.getString("text"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toSet.setText("Sorry, I lost connection now, please try again later.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            toSet.setText("Sorry, I lost connection now, please try again later.");
                        }
                        //((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
                        findViewById(R.id.chat_scroll).post(new Runnable() {
                            public void run() {
                                ((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject temp = new JSONObject("{"+error.toString().split("\\{")[1]+"}");
                            if (temp.length() == 1) {
                                toSet.setText("Sorry, can you repeat that again?");
                            }
                            toSet.setText(temp.getString("text"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
                        findViewById(R.id.chat_scroll).post(new Runnable() {
                            public void run() {
                                ((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                }
        );
        requestQueue.add(objectRequest);
    }
//    public static void scrollToBottom(final View scroll, final View inner) {
//
//        Handler mHandler = new Handler();
//
//        mHandler.post(new Runnable() {
//            public void run() {
//                if (scroll == null || inner == null) {
//                    return;
//                }
//
//                int offset = inner.getMeasuredHeight() - scroll.getHeight();
//                if (offset < 0) {
//                    offset = 0;
//                }
//
//                scroll.scrollTo(0, offset);
//            }
//        });
//    }
}
