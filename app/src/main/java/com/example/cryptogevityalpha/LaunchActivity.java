package com.example.cryptogevityalpha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaunchActivity extends AppCompatActivity {

    private LinearLayout toChatbot;
    private ImageView chatbotNav;
    private IconTextView toCampusResource;
    private IconTextView toNews;
    private IconTextView toMe;
    private IconTextView toForum;
    private LinearLayout chatbot;
    private LinearLayout campusResource;
    private LinearLayout news;
    private LinearLayout me;
    private LinearLayout forum;
    private ArrayList<String> courseSearchHistory;
    private final String Split = "!#!#OscarChen#!#!";
    private final String courseFilename = "CryptogevityCourseSearchHistory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
        setContentView(R.layout.activity_launch);


//        try {
//            Amplify.configure(getApplicationContext());
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
//        }
//        Amplify.addPlugin(new AWSCognitoAuthPlugin());
//        Amplify.configure(getApplicationContext());

        courseSearchHistory = new ArrayList<>();
        try {
            String[] saved = read(courseFilename).split(Split);
            for (String content:saved) {
                if (!content.equals("")) {
                    courseSearchHistory.add(content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateSearchHistory();

        toChatbot = findViewById(R.id.ToChatbot);
        chatbotNav = findViewById(R.id.Chatbot_Nav);
        toCampusResource = findViewById(R.id.ToCampusResource);
        toNews = findViewById(R.id.ToNews);
        toMe = findViewById(R.id.ToMe);
        toForum = findViewById(R.id.ToForum);
        chatbot = findViewById(R.id.ChatbotContainer);
        campusResource = findViewById(R.id.CampusResourceContainer);
        news = findViewById(R.id.NewsContainer);
        me = findViewById(R.id.MeContainer);
        forum = findViewById(R.id.ForumContainer);

        chatbot.setVisibility(View.VISIBLE);
        campusResource.setVisibility(View.GONE);
        news.setVisibility(View.GONE);
        me.setVisibility(View.GONE);
        forum.setVisibility(View.GONE);
        //toChatbot.setTextColor(0xFF4472C4);
        chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_selected_42));

//        Iconify.

        toChatbot.setOnClickListener(v -> {
            chatbot.setVisibility(View.VISIBLE);
            campusResource.setVisibility(View.GONE);
            news.setVisibility(View.GONE);
            me.setVisibility(View.GONE);
            forum.setVisibility(View.GONE);

            //toChatbot.setTextColor(0xFF4472C4);
            chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_selected_42));
            toCampusResource.setTextColor(Color.WHITE);
            toNews.setTextColor(Color.WHITE);
            toMe.setTextColor(Color.WHITE);
            toForum.setTextColor(Color.WHITE);
            hideKeyboard();
        });
        toCampusResource.setOnClickListener(v -> {
            chatbot.setVisibility(View.GONE);
            campusResource.setVisibility(View.VISIBLE);
            news.setVisibility(View.GONE);
            me.setVisibility(View.GONE);
            forum.setVisibility(View.GONE);

            //toChatbot.setTextColor(Color.WHITE);
            chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_42));
            toCampusResource.setTextColor(0xFF4472C4);
            toNews.setTextColor(Color.WHITE);
            toMe.setTextColor(Color.WHITE);
            toForum.setTextColor(Color.WHITE);
            hideKeyboard();
        });
        toNews.setOnClickListener(v -> {
            chatbot.setVisibility(View.GONE);
            campusResource.setVisibility(View.GONE);
            news.setVisibility(View.VISIBLE);
            me.setVisibility(View.GONE);
            forum.setVisibility(View.GONE);

            //toChatbot.setTextColor(Color.WHITE);
            chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_42));
            toCampusResource.setTextColor(Color.WHITE);
            toNews.setTextColor(0xFF4472C4);
            toMe.setTextColor(Color.WHITE);
            toForum.setTextColor(Color.WHITE);
            hideKeyboard();
        });
        toMe.setOnClickListener(v -> {
            chatbot.setVisibility(View.GONE);
            campusResource.setVisibility(View.GONE);
            news.setVisibility(View.GONE);
            me.setVisibility(View.VISIBLE);
            forum.setVisibility(View.GONE);

            //toChatbot.setTextColor(Color.WHITE);
            chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_42));
            toCampusResource.setTextColor(Color.WHITE);
            toNews.setTextColor(Color.WHITE);
            toMe.setTextColor(0xFF4472C4);
            toForum.setTextColor(Color.WHITE);
            hideKeyboard();
        });
        toForum.setOnClickListener(v -> {
            chatbot.setVisibility(View.GONE);
            campusResource.setVisibility(View.GONE);
            news.setVisibility(View.GONE);
            me.setVisibility(View.GONE);
            forum.setVisibility(View.VISIBLE);

            //toChatbot.setTextColor(Color.WHITE);
            chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_42));
            toCampusResource.setTextColor(Color.WHITE);
            toNews.setTextColor(Color.WHITE);
            toMe.setTextColor(Color.WHITE);
            toForum.setTextColor(0xFF4472C4);
            hideKeyboard();
        });




        /** Forum Component */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.forum_sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.ForumSortedBySpinner)).setAdapter(adapter);
        //((Spinner) container.findViewById(R.id.length_unit)).setSelection(index);
        for (int i = 0; i < 20; i++) {
            final View forumtest = getLayoutInflater().inflate(R.layout.forum_post_brief, null, false);
            ((TextView) forumtest.findViewById(R.id.Username)).setText("User No."+i);
            ((TextView) forumtest.findViewById(R.id.ForumTime)).setText(""+i);
            ((TextView) forumtest.findViewById(R.id.ForumPosiTitle)).setText("This Is Title For Post #"+i);
            String temp = "This is test content for post by User #"+i+". ";
            for (int j = 0; j < i; j++) {
                temp = temp + "This is test content for post by User #"+i+". ";
            }
            ((TextView) forumtest.findViewById(R.id.ForumPostContent)).setText(temp);
            ((TextView) forumtest.findViewById(R.id.ThumbNumber)).setText(i+1+"");
            ((TextView) forumtest.findViewById(R.id.CommentNumber)).setText(i+2+"");
            ((LinearLayout) findViewById(R.id.ForumBriefContainer)).addView(forumtest);
        }



        /** Chatbot component */
        findViewById(R.id.send).setOnClickListener(v -> {
            send();
        });
        final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Hello there, I am Cryptogevity Chatbot. Say something to me!");
        //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(19, 41, 75));
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.shape_bot_text_chat));
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);
        //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
        ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
        ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setPadding(20, 20, 200, 20);
        ((ImageView)botTextContainer.findViewById(R.id.avatar_bot)).setVisibility(View.VISIBLE);
        ((ImageView)botTextContainer.findViewById(R.id.avatar_bot)).setPadding(0, 0, 24, 0);
        ((LinearLayout)findViewById(R.id.chat_scroll_container)).addView(botTextContainer);
        //((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
        findViewById(R.id.chat_scroll).post(new Runnable() {
            public void run() {
                ((ScrollView)findViewById(R.id.chat_scroll)).fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        /** News Component */
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//        findViewById(R.id.renewButton).setOnClickListener(v -> {
//            try {
//                renew();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.news_sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.NewsSortedBySpinner)).setAdapter(adapter1);

        RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.alma_mater_20211126));
        //roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), getDrawable(R.mipmap.alma_mater_20211126))
        roundedBitmapDrawable1.setCornerRadius(144);
        for (int i = 0; i < 20; i++) {
            final View newstest = getLayoutInflater().inflate(R.layout.news_brief, null, false);
            //newstest.findViewById(R.id.NewsBriefContainer).setBackground(roundedBitmapDrawable1);
            ((ImageView) newstest.findViewById(R.id.NewsBackground)).setImageDrawable(roundedBitmapDrawable1);
//            newstest.findViewById(R.id.NewsBriefContainer).setBackground();
//            newstest.findViewById(R.id.NewsBriefContainer).setBackground(getDrawable(R.mipmap.alma_mater_20211126));
            ((TextView) newstest.findViewById(R.id.NewsTitle)).setText("This Is Title For News #"+i);
            ((LinearLayout) findViewById(R.id.news_scroll_container)).addView(newstest);
        }


        /** Me Component */
        findViewById(R.id.loginButton).setOnClickListener(v-> {
            loginWindow();
        });

        /** Campus Resource Component*/
        findViewById(R.id.CourseSearch).setOnClickListener(v -> {
            courseSearch();
        });
        findViewById(R.id.delete_all_course_history).setOnClickListener(v -> {
            if (courseSearchHistory.size() > 0) {
                hideKeyboard();
                final View container1 = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
                container1.setPadding(42, 42, 42, 12);
                ((TextView) container1.findViewById(R.id.onlyTextView)).setText("Do you wish to delete ALL search history?");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(container1).setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseSearchHistory = new ArrayList<>();
                        updateSearchHistory();
                    }
                });
                builder.show();
            }
        });
    }


    /** Campus Resource Function*/
    private void courseSearch() {
        String entered = ((EditText)findViewById(R.id.CourseToSearch)).getText().toString().trim();
        if (entered.length() == 0) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            AlertDialog.Builder nothingBuilder = new AlertDialog.Builder(this);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("Come on, at least type something!");
            nothingBuilder.setView(nothingContainer);
            nothingBuilder.show();
            ((EditText)findViewById(R.id.enter_chat)).setText("");
        } else {
            /** go to search result*/
//            if (courseSearchHistory.contains(entered)) {
//                courseSearchHistory.remove(entered);
//            }
            if (entered.equals(Split)) {
                return;
            }
            courseSearchHistory.remove(entered);
            courseSearchHistory.add(entered);
            updateSearchHistory();
        }
    }

    private void updateSearchHistory() {
        LinearLayout container = findViewById(R.id.search_history_container);
        container.removeAllViews();
        String toSave = "";
        for (int i = courseSearchHistory.size() - 1; i >= 0; i--) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText(courseSearchHistory.get(i));
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.search_history));
            nothingContainer.setPadding(12, 12, 12, 24);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setOnClickListener(v -> {
                courseSearchHistory.remove(((TextView)nothingContainer.findViewById(R.id.onlyTextView)).getText().toString());
                courseSearchHistory.add(((TextView)nothingContainer.findViewById(R.id.onlyTextView)).getText().toString());
                updateSearchHistory();
            });
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setOnLongClickListener(l -> {
                hideKeyboard();
                final View container1 = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
                container1.setPadding(42, 42, 42, 12);
                ((TextView) container1.findViewById(R.id.onlyTextView)).setText("Do you wish to delete this search history?");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(container1).setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseSearchHistory.remove(((TextView)nothingContainer.findViewById(R.id.onlyTextView)).getText().toString());
                        updateSearchHistory();
                    }
                });
                builder.show();
                return true;
            });
            container.addView(nothingContainer);
            toSave = toSave + courseSearchHistory.get(i);
            toSave = toSave + Split;
//            if (i != 0) {
//                toSave = toSave + Split;
//            }

        }
        try {
            save(courseFilename, toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (courseSearchHistory.size() == 0) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("OscarChen");
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.search_history));
            nothingContainer.setPadding(12, 12, 12, 24);
            nothingContainer.setVisibility(View.INVISIBLE);
            ((LinearLayout) findViewById(R.id.search_history_container)).addView(nothingContainer);
        }
    }




    /** Chatbot functions */
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
            //((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
            //((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(0xFF2E9DFB);
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.shape_my_text_chat));
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);

            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setHorizontalGravity(Gravity.RIGHT);
            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setPadding(200, 20, 20, 20);
            ((ImageView)myTextContainer.findViewById(R.id.avatar_self)).setVisibility(View.VISIBLE);
            ((ImageView)myTextContainer.findViewById(R.id.avatar_self)).setPadding(24, 0, 0, 0);
            ((LinearLayout)findViewById(R.id.chat_scroll_container)).addView(myTextContainer);
            ((EditText)findViewById(R.id.enter_chat)).setText("");

            final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Just a minute, looking that up");
            //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(19, 41, 75));
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.shape_bot_text_chat));
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);
            //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
            respond(entered, ((TextView)botTextContainer.findViewById(R.id.onlyTextView)));
            ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
            ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setPadding(20, 20, 200, 20);
            ((ImageView)botTextContainer.findViewById(R.id.avatar_bot)).setVisibility(View.VISIBLE);
            ((ImageView)botTextContainer.findViewById(R.id.avatar_bot)).setPadding(0, 0, 24, 0);
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
                            //toSet.setText(response.toString());
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
                            toSet.setText("Sorry, can you repeat that again?");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            toSet.setText("Sorry, can you repeat that again?");
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






    /** News functions */
    private void renew() throws Exception {
        URL url = new URL("https://giesbusiness.illinois.edu/news");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5*1000);
        InputStream inStream= conn.getInputStream();
        byte[] data = readInputStream(inStream);
        String html = new String(data, "UTF-8");
        final View myTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setText(html);
        ((LinearLayout)findViewById(R.id.news_scroll_container)).addView(myTextContainer);
        System.out.println(html);
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }



    /** Me functions */
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
//    private void login(EditText username, EditText password) {
//        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(), "us-east-1_IodB1PTOo", "3huft27r3nsee0akin005k2b7", null, "us-east-1");
////        Amplify.Auth.signIn(username.getText().toString(),
////                password.getText().toString(),
////                result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
////                error -> Log.e("AuthQuickstart", error.toString()));
//    }
    private void testTemp() {
        final View tempContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(LaunchActivity.this);
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
        AlertDialog.Builder validBuilder = new AlertDialog.Builder(LaunchActivity.this);

        ((TextView)validContainer.findViewById(R.id.onlyTextView)).setText("Welcome!");
        validBuilder.setView(validContainer);
        validBuilder.show();
    }
    private void upinvalid() {
        final View invalidContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        AlertDialog.Builder invalidBuilder = new AlertDialog.Builder(LaunchActivity.this);

        ((TextView)invalidContainer.findViewById(R.id.onlyTextView)).setText("Your password does not match your user name. Try again. Click FORGOT PASSWORD if you don't remember.");
        invalidBuilder.setView(invalidContainer);
        invalidBuilder.show();
    }


    /**
     * This can zoom a drawable
     * @param drawable the drawable to zoom
     * @param w the target width
     * @param h the target height
     * @return
     */
    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }
    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
    public void save(String filename, String filecontent) throws Exception {
        FileOutputStream output = this.openFileOutput(filename, Context.MODE_PRIVATE);
        output.write(filecontent.getBytes());
        output.close();
    }
    public String read(String filename) throws IOException {
        FileInputStream input = this.openFileInput(filename);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        input.close();
        return sb.toString();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
