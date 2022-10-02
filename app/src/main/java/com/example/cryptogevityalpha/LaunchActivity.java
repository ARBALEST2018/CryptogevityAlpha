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
import android.util.Log;
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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.AmplifyConfiguration;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaunchActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView GiesNews;
    private TextView ActivityNews;
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
    private ArrayList<String> newsSearchHistory;
    private ArrayList<ForumPost> forumPosts;
    private ArrayList<NewsBrief> newsBriefs;
    private ArrayList<NewsBrief> activitynewsBriefs;
    private ArrayList<TrendingCourse> trendingCourses;
    private LinearLayout homepage;
    private LinearLayout alternative;
    private String username = "oscartest";
    private String password = "";
    private boolean login = false;
    private final String Split = "!#!#OscarChen#!#!";
    private final String loginFilename = "CryptogevityLogin";
    private final String courseFilename = "CryptogevityCourseSearchHistory";
    private final String newsFilename = "CryptogevityNewsSearchHistory";


    private int current; //1 for News, 2 for Course, 3 for Chatbot, 4 for Forum, 5 for Me

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
        requestQueue = Volley.newRequestQueue(this);

//        try {
//            Amplify.configure(getApplicationContext());
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
//        }
//        Amplify.addPlugin(new AWSCognitoAuthPlugin());
//        Amplify.configure(getApplicationContext());

        courseSearchHistory = new ArrayList<>();
        newsSearchHistory = new ArrayList<>();
        forumPosts = new ArrayList<>();
        newsBriefs = new ArrayList<>();
        activitynewsBriefs = new ArrayList<>();
        trendingCourses = new ArrayList<>();
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
        try {
            String[] saved = read(newsFilename).split(Split);
            for (String content:saved) {
                if (!content.equals("")) {
                    newsSearchHistory.add(content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        findViewById(R.id.UsernameSet).setVisibility(View.GONE);
        findViewById(R.id.LoginSet).setVisibility(View.VISIBLE);
        try {
            String[] saved = read(loginFilename).split(Split);
            if (saved.length >= 2) {
                if (username.length() > 0 && password.length() > 0) {
//                    login(saved[0], saved[1]);
                    login = true;
                    ((TextView)findViewById(R.id.MyUsername)).setText(username);
                    findViewById(R.id.UsernameSet).setVisibility(View.VISIBLE);
                    findViewById(R.id.LoginSet).setVisibility(View.GONE);

//                    findViewById(R.id.MyUsername).setVisibility(View.VISIBLE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(courseSearchHistory);
        Collections.reverse(newsSearchHistory);
        updateCourseSearchHistory();
        updateNewsSearchHistory();

//        forumPosts.add(new ForumPost("What is Ethereum", 3, 16, 10, "Ethereum, which launched in 2015, is the second-biggest", "Tim Schiesser", 20211207));
//        forumPosts.add(new ForumPost("Graphics Card Ethereum Hashrate", 4, 30, 25, "Bitcoin and Ethereum mining have been making headlines again, as prices and mining profitability were way up compared to the last couple of years. Everyone who didn't start mining last time is kicking themselves for their lack of foresight.", "Sami Haj-Assaad", 20211118));
//        forumPosts.add(new ForumPost("Nvidia 4000 Series Release Date", 5, 21, 13, "The RTX 4000 series GPUs are rumored to hit mass production in mid-2022. Assuming there's enough time to build up enough inventory, a retail launch around October or November 2022 is likely", "Tim Schiesser", 20211117));
//        forumPosts.add(new ForumPost("Coleco: Gone But Not Forgotten", 6, 12, 45, "#ThrowBackThursday For those growing up in the 1980s, the name \"Coleco\" stirs up nostalgic memories of a gaming era long past. The ColecoVision competed with the likes of Atari and Intellivision, leaving its mark in gaming history.", "William Gayde", 20211019));
//        forumPosts.add(new ForumPost("Cyrix 5x86 and Cyrix 6x86: Gone But Not Forgotten", 7, 50, 34, "Precursor chip maker Cyrix brought the world of personal computing to millions in the form of attainable budget PCs, only to be killed by its best product and its inability to run a popular game.", "Mike Jennings", 20210831));
//        forumPosts.add(new ForumPost("Nokia: The Story of the Once-Legendary Phone Maker", 8, 17, 28, "Most people who hear the word \"Nokia\" associate it with mobile phones, but there's a convoluted history to tell since the company's humble beginnings over 150 years ago and many reinventions.", "Tim Schiesser", 20210817));
//        forumPosts.add(new ForumPost("3dfx: Gone But Not Forgotten", 9, 45, 74, "", "Tim Schiesser", 20210730));
//        forumPosts.add(new ForumPost("S3 Graphics: Gone But Not Forgotten", 10, 12, 10, "These days it's rare to see a new hardware company break ground in the world of PCs, but 30 years ago, they were popping up all over the place. Join us as we pay tribute to S3 and see how its remarkable story unfolded over the years.", "TechSpot Staff", 20210728));
//        forumPosts.add(new ForumPost("Intellivision: Gone But Not Forgotten", 11, 24, 11, "Mattel developed a gaming system called Intellivision in the late 1970s, around the same time that the Atari 2600 launched. However, it made such an impression that the iconic console was never forgotten and is, in fact, making a comeback.", "TechSpot Staff", 20210712));
//        forumPosts.add(new ForumPost("Silicon Graphics: Gone But Not Forgotten", 12, 25, 45, "At its peak in the 1990s, Silicon Graphics had legendary status among 3D and graphic designers who leveraged the unique power of these workstations that were at cutting edge of visual computing.", "Tim Schiesser", 20210621));
//        forumPosts.add(new ForumPost("Compaq: Gone But Not Forgotten", 13, 25, 82, "Computers had finally made the jump from taking up an entire room to fitting on a desk, but they were still far from portable. In 1982, three entrepreneurs decided to change that. Their first entry into the market was the Compaq Portable in 1983.", "Tim Schiesser", 20210604));
//        forumPosts.add(new ForumPost("Sinclair Computers: Gone But Not Forgotten", 14, 21, 32, "For many, the 1980s was the golden era in home computing. Fighting among new companies was Sinclair who made cheap and basic computers, but helped give rise to the world of bedroom programming and game developers like Rare, Codemasters and Rockstar North.", "Mike Jennings", 20210528));
//        forumPosts.add(new ForumPost("The Commodore Story: Gone but Not Forgotten", 15, 37, 13, "A lot of people over 30 will probably name a Commodore as the first computer they ever used. Whether it was your first computer game or first program in BASIC, Commodore led an entire generation to a life-long career in the tech industry.", "Tim Schiesser", 20210526));
//        forumPosts.add(new ForumPost("Gateway 2000: Gone But Not Forgotten", 16, 20, 14, "What does a cattle ranch have in common with computers? Admittedly not much, but that didn't stop a couple of college dropouts from capitalizing on the concept to create a lucrative business that would reshape how consumers perceive and purchase personal computers.", "Tim Schiesser", 20210514));
//        forumPosts.add(new ForumPost("Palm: Gone But Not Forgotten", 17, 20, 52, "Palm, the inventor of the Palm Pilot, is one of the earliest and most successful personal digital assistants which made the name \"Palm\" synonymous with PDAs, a leading handheld computing form factor for nearly a decade and the precursor to the modern smartphone.", "Tim Schiesser", 20210513));
//        forumPosts.add(new ForumPost("OCZ Technology: Gone But Not Forgotten", 18, 47, 22, "OCZ Technology was founded in 2000 by Ryan Petersen as \"The Overclockerz Store,\" an online hardware reseller that catered to computer enthusiasts. The company started out selling binned processors and memory kits capable of running faster than their rated speeds - items which overclockers were willing to pay a premium for.", "Tim Schiesser", 20210503));
        updateForumBrief();

        newsBriefs.add(new NewsBrief(R.mipmap.news001, "Choi's $100K Bitcoin gift to fund blockchain education at Gies", getDrawable(R.mipmap.news001)));
        newsBriefs.add(new NewsBrief(R.mipmap.news002, "Hecht charts new ways to engage learners with micro-credentials", getDrawable(R.mipmap.news002)));
        newsBriefs.add(new NewsBrief(R.mipmap.news003, "New research explores implicit biases of creativity", getDrawable(R.mipmap.news003)));
        newsBriefs.add(new NewsBrief(R.mipmap.news004, "Eight Chicago alumni honored by top business publication", getDrawable(R.mipmap.news004)));
        newsBriefs.add(new NewsBrief(R.mipmap.news005, "Demand up for Master of Accounting and Master of Finance graduates", getDrawable(R.mipmap.news005)));
        newsBriefs.add(new NewsBrief(R.mipmap.news006, "Elliott, Davis recognized for contributions to educational diversity", getDrawable(R.mipmap.news006)));
        newsBriefs.add(new NewsBrief(R.mipmap.news007, "Activating a collectivistic orientation conducive to curbing COVID‐19", getDrawable(R.mipmap.news007)));
        newsBriefs.add(new NewsBrief(R.mipmap.news008, "Jeffrey R. Brown named Dean of the Year by Poets&Quants", getDrawable(R.mipmap.news008)));
        newsBriefs.add(new NewsBrief(R.mipmap.news009, "Retail credit exec discovers new opportunities with Gies iMBA", getDrawable(R.mipmap.news009)));
        newsBriefs.add(new NewsBrief(R.mipmap.news010, "Why our MBA Program doesn’t require the GMAT", getDrawable(R.mipmap.news010)));
        newsBriefs.add(new NewsBrief(R.mipmap.news011, "How can a Master’s in Technology Management help bridge the gap between STEM and business?", getDrawable(R.mipmap.news011)));
        newsBriefs.add(new NewsBrief(R.mipmap.news012, "'Paradox brands' hold strong appeal for bicultural consumers", getDrawable(R.mipmap.news012)));
        newsBriefs.add(new NewsBrief(R.mipmap.news013, "How can a Gies online MBA help you stand out in the workplace?", getDrawable(R.mipmap.news013)));
        newsBriefs.add(new NewsBrief(R.mipmap.news014, "Choosing the graduate program that’s right for you", getDrawable(R.mipmap.news014)));
        newsBriefs.add(new NewsBrief(R.mipmap.news015, "Two paths. One Destination. The Gies Accounting Master’s Degree Program", getDrawable(R.mipmap.news015)));
        newsBriefs.add(new NewsBrief(R.mipmap.news016, "PODCAST: Gies marketing professor Tiffany White", getDrawable(R.mipmap.news016)));
        newsBriefs.add(new NewsBrief(R.mipmap.news017, "Engaging donors in creative acts can boost charitable fundraising", getDrawable(R.mipmap.news017)));
        newsBriefs.add(new NewsBrief(R.mipmap.news018, "Gies honors Matthew Kraatz with the Merle H. and Virginia Downs Boren Professorship", getDrawable(R.mipmap.news018)));
        newsBriefs.add(new NewsBrief(R.mipmap.news019, "New DSRS director offers Gies researchers the moon and the stars", getDrawable(R.mipmap.news019)));
        newsBriefs.add(new NewsBrief(R.mipmap.news020, "Celebrating 25 years of Illinois Business Consulting", getDrawable(R.mipmap.news020)));

        activitynewsBriefs.add(new NewsBrief(R.mipmap.activitynews_1_0, "", getDrawable(R.mipmap.activitynews_1_0)));
        activitynewsBriefs.add(new NewsBrief(R.mipmap.activitynews_2_1, "", getDrawable(R.mipmap.activitynews_2_1)));
        updateNewsBrief();


        trendingCourses.add(new TrendingCourse(R.mipmap.trendingcourse1, "Object-Oriented Data Structures in C++", getDrawable(R.mipmap.trendingcourse1), "Comprehensive CS"));
        trendingCourses.add(new TrendingCourse(R.mipmap.trendingcourse2, "Data Mining", getDrawable(R.mipmap.trendingcourse2), "Comprehensive CS"));
        trendingCourses.add(new TrendingCourse(R.mipmap.trendingcourse3, "Introduction to Computer Science I", getDrawable(R.mipmap.trendingcourse3), "Comprehensive CS"));
        trendingCourses.add(new TrendingCourse(R.mipmap.trendingcourse4, "Introduction to Computer Science II", getDrawable(R.mipmap.trendingcourse4), "Comprehensive CS"));
        trendingCourses.add(new TrendingCourse(R.mipmap.trendingcourse5, "Discrete Structures and Algorithms", getDrawable(R.mipmap.trendingcourse5), "Comprehensive CS"));
        trendingCourses.add(new TrendingCourse(R.mipmap.trendingcourse6, "Introduction to Data Mining", getDrawable(R.mipmap.trendingcourse6), "Comprehensive CS"));
        updateTrendingCourse();


        GiesNews = findViewById(R.id.GiesNews);
        ActivityNews = findViewById(R.id.ActivityNews);
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
        homepage = findViewById(R.id.homepageContainer);
        alternative = findViewById(R.id.alternativeContainer);

        chatbot.setVisibility(View.GONE);
        campusResource.setVisibility(View.GONE);
        news.setVisibility(View.VISIBLE);
        me.setVisibility(View.GONE);
        forum.setVisibility(View.GONE);

        chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_42));
        toCampusResource.setTextColor(Color.WHITE);
        toNews.setTextColor(0xFF4472C4);
        toMe.setTextColor(Color.WHITE);
        toForum.setTextColor(Color.WHITE);

        current = 3;
        homepage.setVisibility(View.VISIBLE);
        alternative.setVisibility(View.GONE);
        //toChatbot.setTextColor(0xFF4472C4);
        //chatbotNav.setBackground(getDrawable(R.mipmap.bot_avatar_simple_selected_42));

        findViewById(R.id.news_scroll_container).setVisibility(View.GONE);
        findViewById(R.id.activitynews_scroll_container).setVisibility(View.VISIBLE);
        ActivityNews.setTextColor(0xFF02358F);
        GiesNews.setTextColor(0xFF4472C4);
        AWSCognitoAuthPlugin toAdd = new AWSCognitoAuthPlugin();

        String temp = "{\"CognitoUserPool\": {\n\"Default\": {\n\"PoolId\": \"us-east-1_IodB1PTOo\",\n\"AppClientId\": \"3huft27r3nsee0akin005k2b7\",\n\"Region\": \"us-east-1\"\n}\n}\n}";
        JSONObject toConfigure = null;
        try {
            toConfigure = new JSONObject(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            toConfigure.put("aws_cognito_region", "us-east-1");
//            toConfigure.put("aws_user_pools_id", "us-east-1_IodB1PTOo");
//            toConfigure.put("aws_user_pools_web_client_id", "3huft27r3nsee0akin005k2b7");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            toAdd.configure(toConfigure, getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
        try {
            Amplify.addPlugin(toAdd);
            Amplify.configure(AmplifyConfiguration.fromJson(toConfigure),getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
//        try {
//            Amplify.configure(getApplicationContext());
//        } catch (AmplifyException e) {
//            System.out.println("\n\n\n\n\n\n\n\n\n\n");
//            e.printStackTrace();
//        }
//        try {
//            Amplify.Auth.fetchAuthSession(
//                    result -> Log.i("AmplifyQuickstart", result.toString()),
//                    error -> Log.e("AmplifyQuickstart", error.toString())
//            );
//        } catch (AmplifyException e) {
//            e.printStackTrace();
//        }


        new KeyBoardShowListener(this).setKeyboardListener(
                new KeyBoardShowListener.OnKeyboardVisibilityListener() {
                    @Override
                    public void onVisibilityChanged(boolean visible) {
                        if (visible) {
                            findViewById(R.id.bottom_button_container).setVisibility(View.GONE);
                            //软键盘已弹出
                            if (current == 1) {
                                findViewById(R.id.NewsWhenNotSearch).setVisibility(View.GONE);
                                findViewById(R.id.NewsWhenSearch).setVisibility(View.VISIBLE);
                            }
                        } else {
                            //软键盘未弹出
                            findViewById(R.id.bottom_button_container).setVisibility(View.VISIBLE);
                            if (current == 1) {
                                findViewById(R.id.NewsWhenSearch).setVisibility(View.GONE);
                                findViewById(R.id.NewsWhenNotSearch).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }, this);

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
            current = 3;
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
            current = 2;
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
            current = 1;
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
            current = 5;
        });
        toForum.setOnClickListener(v -> {
            updateForumBrief();
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
            current = 4;
        });




        /** Forum Component */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.forum_sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.ForumSortedBySpinner)).setAdapter(adapter);
        findViewById(R.id.NewForumPost1).setOnClickListener(v -> {
            if (true/*login*/) {
                homepage.setVisibility(View.GONE);
                alternative.removeAllViews();
                final View newPost = getLayoutInflater().inflate(R.layout.forum_new_post_layout, null, false);
                newPost.findViewById(R.id.backContainer).setOnClickListener(v1 -> {
                    updateForumBrief();
                    alternative.removeAllViews();
                    alternative.setVisibility(View.GONE);
                    homepage.setVisibility(View.VISIBLE);
                });
                newPost.findViewById(R.id.postButton).setOnClickListener(v2 -> {
                    String title = ((EditText)findViewById(R.id.enterTitle)).getText().toString().trim();
                    String content = ((EditText)findViewById(R.id.enterContent)).getText().toString().trim();
                    if (title.length() == 0 || content.length() == 0) {
                        final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
                        AlertDialog.Builder nothingBuilder = new AlertDialog.Builder(this);
                        ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("Come on, at least share something!");
                        nothingBuilder.setView(nothingContainer);
                        nothingBuilder.show();
                        ((EditText)findViewById(R.id.enterTitle)).setText(title);
                        ((EditText)findViewById(R.id.enterContent)).setText(content);
                    } else {
                        JSONObject toPost = new JSONObject();
                        try {
                            toPost.put("user_id", username);
                            toPost.put("title", title);
                            toPost.put("desc", content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                                "https://be.cryptogevity.com/api/posts",
                                toPost,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        alternative.removeAllViews();
                                        alternative.setVisibility(View.GONE);
                                        homepage.setVisibility(View.VISIBLE);
                                        hideKeyboard();
                                        updateForumBrief();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        alternative.removeAllViews();
                                        alternative.setVisibility(View.GONE);
                                        homepage.setVisibility(View.VISIBLE);
                                    }
                                }
                        );
                        requestQueue.add(objectRequest);
                    }
                });
                alternative.addView(newPost);
                alternative.setVisibility(View.VISIBLE);
                ((EditText)findViewById(R.id.enterTitle)).setText("");
                ((EditText)findViewById(R.id.enterContent)).setText("");
            } else {
                final View tempContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
                AlertDialog.Builder tempBuilder = new AlertDialog.Builder(LaunchActivity.this);
                ((TextView)tempContainer.findViewById(R.id.onlyTextView)).setText("You have to login before creating a post!");
                tempBuilder.setView(tempContainer);
                tempBuilder.show();
            }
        });



        /** Chatbot component */
        findViewById(R.id.send).setOnClickListener(v -> {
            send();
        });
        final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Hello there, I am Cryptogevity Chatbot. Say something to me!");
        //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(19, 41, 75));
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.incoming_speech_bubble));
//        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.incoming_speech_bubble));
        ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);
        //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
        ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
        (botTextContainer.findViewById(R.id.ChatTextPlaceHolderRight)).setVisibility(View.INVISIBLE);
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
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.news_sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.NewsSortedBySpinner)).setAdapter(adapter1);

        RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.alma_mater_20211126));
        roundedBitmapDrawable1.setCornerRadius(144);
        findViewById(R.id.NewsSearch).setOnClickListener(v -> {
            newsSearch();
        });
        findViewById(R.id.delete_all_news_history).setOnClickListener(v -> {
            if (newsSearchHistory.size() > 0) {
                hideKeyboard();
                final View container1 = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
                container1.setPadding(42, 42, 42, 12);
                ((TextView) container1.findViewById(R.id.onlyTextView)).setText("Do you wish to delete ALL search history?");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(container1).setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newsSearchHistory = new ArrayList<>();
                        updateNewsSearchHistory();
                    }
                });
                builder.show();
            }
        });
        ActivityNews.setOnClickListener(v -> {
            ActivityNews.setTextColor(0xFF02358F);
            GiesNews.setTextColor(0xFF4472C4);
            findViewById(R.id.news_scroll_container).setVisibility(View.GONE);
            findViewById(R.id.activitynews_scroll_container).setVisibility(View.VISIBLE);
        });
        GiesNews.setOnClickListener(v -> {
            ActivityNews.setTextColor(0xFF4472C4);
            GiesNews.setTextColor(0xFF02358F);
            findViewById(R.id.activitynews_scroll_container).setVisibility(View.GONE);
            findViewById(R.id.news_scroll_container).setVisibility(View.VISIBLE);
        });


        /** Me Component */
        findViewById(R.id.loginButton).setOnClickListener(v-> {
            loginWindow();
        });
        findViewById(R.id.ToSetting).setOnClickListener(v -> {
            homepage.setVisibility(View.GONE);
            alternative.removeAllViews();
            final View settings = getLayoutInflater().inflate(R.layout.settings, alternative, true);
            settings.findViewById(R.id.settingBack).setOnClickListener(v1 -> {
                alternative.removeAllViews();
                alternative.setVisibility(View.GONE);
                homepage.setVisibility(View.VISIBLE);
            });
            //alternative.addView(settings);
            alternative.setVisibility(View.VISIBLE);
        });
        findViewById(R.id.UsernameSet).setOnClickListener(v -> {
            logout();
        });
        findViewById(R.id.AvatarMe).setOnClickListener(v -> {
            if (login) {
//                ((TextView)findViewById(R.id.MyUsername)).setText(username);
//                findViewById(R.id.UsernameSet).setVisibility(View.VISIBLE);
//                findViewById(R.id.MyUsername).setVisibility(View.VISIBLE);
                logout();
            }
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
                        updateCourseSearchHistory();
                    }
                });
                builder.show();
            }
        });

    }


    /** Forum functions*/
    private void updateForumBrief() {

        String url = "https://be.cryptogevity.com/api/posts";
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Web Api called");
                        forumPosts.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject temp = response.getJSONObject(i);
                                List<Comment>comments = new ArrayList<>();
                                JSONArray c = temp.getJSONArray("comments");
                                for (int k = 0; k < c.length(); k++) {
                                    JSONObject j = c.getJSONObject(k);
                                    comments.add(new Comment(j.getInt("id"), j.getString("user_id"),
                                            j.getString("desc"), j.getString("create_time")));
                                }
                                ForumPost toAdd = new ForumPost(temp.getInt("id"), temp.getString("user_id"),
                                        temp.getString("title"), temp.getString("desc"),
                                        temp.getString("create_time"), comments);
                                forumPosts.add(toAdd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        ((LinearLayout)findViewById(R.id.ForumBriefContainer)).removeAllViews();
                        for (int i = forumPosts.size() - 1; i >= 0; i--) {
                            ForumPost f = forumPosts.get(i);
                            final View forumtest = getLayoutInflater().inflate(R.layout.forum_brief_post_layout, null, false);
                            ((TextView) forumtest.findViewById(R.id.Username)).setText(f.getUserId());
                            ((TextView) forumtest.findViewById(R.id.ForumTime)).setText(""+f.getCreateTime());
                            ((TextView) forumtest.findViewById(R.id.ForumPosiTitle)).setText(f.getTitle());
                            ((TextView) forumtest.findViewById(R.id.ForumPostContent)).setText(f.getDesc());
                            ((TextView) forumtest.findViewById(R.id.ThumbNumber)).setText(""+0);
                            ((TextView) forumtest.findViewById(R.id.CommentNumber)).setText(""+f.getComments().size());
                            ((LinearLayout) findViewById(R.id.ForumBriefContainer)).addView(forumtest);
                            forumtest.setOnClickListener(v -> {
                                final View viewPost = getLayoutInflater().inflate(R.layout.forum_view_post_layout, alternative, true);
                                ((TextView)viewPost.findViewById(R.id.postUser)).setText(f.getUserId());
                                ((TextView)viewPost.findViewById(R.id.postContent)).setText(f.getDesc());
                                ((TextView)viewPost.findViewById(R.id.postTitle)).setText(f.getTitle());
                                ((TextView)viewPost.findViewById(R.id.postTime)).setText(f.getCreateTime());
                                final LinearLayout commentsContainer = viewPost.findViewById(R.id.commentsContainer);
                                updateComments(commentsContainer, f);
                                viewPost.findViewById(R.id.backContainer).setOnClickListener(v1 -> {
                                    alternative.removeAllViews();
                                    alternative.setVisibility(View.GONE);
                                    homepage.setVisibility(View.VISIBLE);
                                    updateForumBrief();
                                });
                                viewPost.findViewById(R.id.sendComment).setOnClickListener(v2 -> {
                                    if (login) {
                                        String typed = ((EditText) findViewById(R.id.enterComment)).getText().toString().trim();
                                        if (typed.length() > 0) {
                                            JSONObject toSend = new JSONObject();
                                            try {
                                                toSend.put("user_id", username);
                                                toSend.put("post_id", f.getId());
                                                toSend.put("desc", typed);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            requestQueue.add(new JsonObjectRequest(Request.Method.POST, "https://be.cryptogevity.com/api/comments", toSend, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    f.removeAllComments();
                                                    try {
                                                        response.getJSONArray("comments");
                                                        JSONArray c = response.getJSONArray("comments");
                                                        for (int k = 0; k < c.length(); k++) {
                                                            JSONObject j = c.getJSONObject(k);
                                                            f.addComment(new Comment(j.getInt("id"), j.getString("user_id"),
                                                                    j.getString("desc"), j.getString("create_time")));
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    ((EditText) findViewById(R.id.enterComment)).setText("");
                                                    updateComments(commentsContainer, f);
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            }));
                                        }
                                    }  else {
                                        final View tempContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
                                        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(LaunchActivity.this);
                                        ((TextView)tempContainer.findViewById(R.id.onlyTextView)).setText("You have to login before making a comment!");
                                        tempBuilder.setView(tempContainer);
                                        tempBuilder.show();
                                    }
                                });
                                homepage.setVisibility(View.GONE);
                                alternative.setVisibility(View.VISIBLE);
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Web Api called");
                    }
                });
        requestQueue.add(arrayRequest);
    }

    public void updateComments(LinearLayout commentsContainer, ForumPost f) {
        commentsContainer.removeAllViews();
        System.out.println("comment length: "+f.getComments().size());
        for (Comment c:f.getComments()) {
            final View comment = getLayoutInflater().inflate(R.layout.forum_comment_layout, null);
            ((TextView) comment.findViewById(R.id.commentUser)).setText(c.getUserId());
            ((TextView) comment.findViewById(R.id.commentContent)).setText(c.getDesc());
            ((TextView) comment.findViewById(R.id.commentTime)).setText(c.getCreateTime());
            commentsContainer.addView(comment);
        }
        updateForumBrief();
    }






    /** News functions */
    private void updateNewsBrief() {
        ((LinearLayout) findViewById(R.id.news_scroll_container)).removeAllViews();
        for (NewsBrief n: newsBriefs) {
            //zoomDrawable(n.getPicture(), 1800, 1200);
            RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), n.getDrawableId()));
            roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), zoomToBitmap(n.getPicture(), 1800, 1200));
            roundedBitmapDrawable1.setCornerRadius(144);
            final View newstest = getLayoutInflater().inflate(R.layout.news_brief, null, false);
            //newstest.findViewById(R.id.NewsBriefContainer).setBackground(roundedBitmapDrawable1);
            ((ImageView) newstest.findViewById(R.id.NewsBackground)).setImageDrawable(roundedBitmapDrawable1);
//            newstest.findViewById(R.id.NewsBriefContainer).setBackground();
//            newstest.findViewById(R.id.NewsBriefContainer).setBackground(getDrawable(R.mipmap.alma_mater_20211126));
            ((TextView) newstest.findViewById(R.id.NewsTitle)).setText(n.getTitle());
            ((LinearLayout) findViewById(R.id.news_scroll_container)).addView(newstest);
        }
        ((LinearLayout) findViewById(R.id.activitynews_scroll_container)).removeAllViews();
        for (NewsBrief n: activitynewsBriefs) {
            //zoomDrawable(n.getPicture(), 1800, 1200);
            RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), n.getDrawableId()));
            roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), zoomToBitmap(n.getPicture(), 1800, (int)(1800.0/((double)n.getPicture().getIntrinsicWidth())*((double)n.getPicture().getIntrinsicHeight()))));

            roundedBitmapDrawable1.setCornerRadius(144);
            final View newstest = getLayoutInflater().inflate(R.layout.news_brief, null, false);
            //newstest.findViewById(R.id.NewsBriefContainer).setBackground(roundedBitmapDrawable1);
            ((ImageView) newstest.findViewById(R.id.NewsBackground)).setImageDrawable(roundedBitmapDrawable1);
//            newstest.findViewById(R.id.NewsBriefContainer).setBackground();
//            newstest.findViewById(R.id.NewsBriefContainer).setBackground(getDrawable(R.mipmap.alma_mater_20211126));
            ((TextView) newstest.findViewById(R.id.NewsTitle)).setText(n.getTitle());
            ((LinearLayout) findViewById(R.id.activitynews_scroll_container)).addView(newstest);
        }
    }
    private void newsSearch() {
        String entered = ((EditText)findViewById(R.id.NewsToSearch)).getText().toString().trim();
        if (entered.length() == 0) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            AlertDialog.Builder nothingBuilder = new AlertDialog.Builder(this);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("Come on, at least type something!");
            nothingBuilder.setView(nothingContainer);
            nothingBuilder.show();
        } else {
            /** go to search result*/
            if (entered.equals(Split)) {
                return;
            }
            newsSearchHistory.remove(entered);
            newsSearchHistory.add(entered);
            updateNewsSearchHistory();
        }
    }
    private void updateNewsSearchHistory() {
        LinearLayout container = findViewById(R.id.news_search_history_container);
        container.removeAllViews();
        String toSave = "";
        for (int i = newsSearchHistory.size() - 1; i >= 0; i--) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText(newsSearchHistory.get(i));
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.search_history));
            nothingContainer.setPadding(12, 12, 12, 24);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setOnClickListener(v -> {
                newsSearchHistory.remove(((TextView)nothingContainer.findViewById(R.id.onlyTextView)).getText().toString());
                newsSearchHistory.add(((TextView)nothingContainer.findViewById(R.id.onlyTextView)).getText().toString());
                updateNewsSearchHistory();
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
                        newsSearchHistory.remove(((TextView)nothingContainer.findViewById(R.id.onlyTextView)).getText().toString());
                        updateNewsSearchHistory();
                    }
                });
                builder.show();
                return true;
            });
            container.addView(nothingContainer);
            toSave = toSave + newsSearchHistory.get(i) + Split ;
        }
        try {
            save(newsFilename, toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newsSearchHistory.size() == 0) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("OscarChen");
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.search_history));
            nothingContainer.setPadding(12, 12, 12, 24);
            nothingContainer.setVisibility(View.INVISIBLE);
            container.addView(nothingContainer);
        }
    }






    /** Campus Resource Function*/
    private void updateTrendingCourse() {
        ((LinearLayout) findViewById(R.id.TrendingContainer)).removeAllViews();
        for (TrendingCourse tc: trendingCourses) {
            final View coursetest = getLayoutInflater().inflate(R.layout.trending_course, null, false);
            coursetest.findViewById(R.id.CourseImage).setBackground(tc.getPicture());
            ((TextView)coursetest.findViewById(R.id.CourseTitle)).setText(tc.getTitle());
            ((TextView)coursetest.findViewById(R.id.CourseCategory)).setText(tc.getCategory());
            ((LinearLayout) findViewById(R.id.TrendingContainer)).addView(coursetest);
            coursetest.findViewById(R.id.trendingCourseContainer).setOnClickListener(v -> {
                alternative.removeAllViews();
                final View courseDetail = getLayoutInflater().inflate(R.layout.course_detail, alternative, true);
                courseDetail.findViewById(R.id.backContainerCourse).setOnClickListener(v1 -> {
                    alternative.removeAllViews();
                    alternative.setVisibility(View.GONE);
                    homepage.setVisibility(View.VISIBLE);
                });
                ((TextView) courseDetail.findViewById(R.id.courseTitle1)).setText(tc.getTitle1());
                ((TextView) courseDetail.findViewById(R.id.courseTitle2)).setText(tc.getTitle2());
                ((TextView) courseDetail.findViewById(R.id.courseCategory)).setText(tc.getCategory());
                //alternative.addView(courseDetail);
                homepage.setVisibility(View.GONE);
                alternative.setVisibility(View.VISIBLE);
            });
        }
    }
    private void courseSearch() {
        String entered = ((EditText)findViewById(R.id.CourseToSearch)).getText().toString().trim();
        if (entered.length() == 0) {
            final View nothingContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            AlertDialog.Builder nothingBuilder = new AlertDialog.Builder(this);
            ((TextView)nothingContainer.findViewById(R.id.onlyTextView)).setText("Come on, at least type something!");
            nothingBuilder.setView(nothingContainer);
            nothingBuilder.show();
        } else {
            /** go to search result*/
            if (entered.equals(Split)) {
                return;
            }
            courseSearchHistory.remove(entered);
            courseSearchHistory.add(entered);
            updateCourseSearchHistory();
        }
    }

    private void updateCourseSearchHistory() {
        LinearLayout container = findViewById(R.id.course_search_history_container);
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
                updateCourseSearchHistory();
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
                        updateCourseSearchHistory();
                    }
                });
                builder.show();
                return true;
            });
            container.addView(nothingContainer);
            toSave = toSave + courseSearchHistory.get(i) + Split;
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
            container.addView(nothingContainer);
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
//            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.shape_my_text_chat));
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.outgoing_speech_bubble));
            ((TextView)myTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);

            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setHorizontalGravity(Gravity.RIGHT);
            (myTextContainer.findViewById(R.id.ChatTextPlaceHolderLeft)).setVisibility(View.INVISIBLE);
            ((LinearLayout) myTextContainer.findViewById(R.id.textViewContainer)).setPadding(200, 20, 20, 20);
            ((ImageView)myTextContainer.findViewById(R.id.avatar_self)).setVisibility(View.VISIBLE);
            ((ImageView)myTextContainer.findViewById(R.id.avatar_self)).setPadding(24, 0, 0, 0);
            ((LinearLayout)findViewById(R.id.chat_scroll_container)).addView(myTextContainer);
            ((EditText)findViewById(R.id.enter_chat)).setText("");

            ChatbotQnA QA = new ChatbotQnA(entered);

            final View botTextContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setText("Just a minute, looking that up");
            //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackgroundColor(Color.rgb(19, 41, 75));
//            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.shape_bot_text_chat));
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setBackground(getDrawable(R.drawable.incoming_speech_bubble));
            ((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setTextColor(Color.WHITE);
            //((TextView)botTextContainer.findViewById(R.id.onlyTextView)).setPadding(20, 20, 20, 20);
            respond(entered, botTextContainer, QA);
            ((LinearLayout) botTextContainer.findViewById(R.id.textViewContainer)).setGravity(Gravity.LEFT);
            (botTextContainer.findViewById(R.id.ChatTextPlaceHolderRight)).setVisibility(View.INVISIBLE);
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
    private void respond(String input, View toAdd, ChatbotQnA QA) {
        TextView toSet = (TextView)toAdd.findViewById(R.id.onlyTextView);
        String url = "https://be.cryptogevity.com/api/chatterbot/";
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            QA.setAnswer(response.getString("text"));
                            toSet.setText(response.getString("text"));
                            toAdd.findViewById(R.id.ThumbsContainer).setVisibility(View.VISIBLE);
                            //toSet.setText(response.toString());
                            toAdd.findViewById(R.id.ThumbUp).setOnClickListener(v -> {
                                toAdd.findViewById(R.id.ThumbsContainer).setVisibility(View.INVISIBLE);
                                rateChatbotQnA(QA, "1");
                            });
                            toAdd.findViewById(R.id.ThumbDown).setOnClickListener(v -> {
                                toAdd.findViewById(R.id.ThumbsContainer).setVisibility(View.INVISIBLE);
                                rateChatbotQnA(QA, "-1");
                            });
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
    private void rateChatbotQnA(ChatbotQnA QA, String score) {
        JSONObject toPost = new JSONObject();
        System.out.println("Q : "+QA.getQuestion()+"\nA : "+QA.getAnswer());
        try {
            toPost.put("text", QA.getAnswer());
            toPost.put("in_response_to", QA.getQuestion());
            toPost.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(toPost.toString());
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://be.cryptogevity.com/api/rateanswer",
                toPost,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("RATE SENT & RECEIVED");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("RATE NOT SENT");
                        error.printStackTrace();
                    }
                }
        );
        System.out.println(objectRequest);
        requestQueue.add(objectRequest);
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
                loginViewR(container.findViewById(R.id.enterUsername),container.findViewById(R.id.enterPassword));
            }
        });
        builder.show();
    }
    private void loginViewR(EditText enterUsername, EditText enterPassword) {
//        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(), "us-east-1_IodB1PTOo", "3huft27r3nsee0akin005k2b7", null);
        final String tempUsername = enterUsername.getText().toString();
        final String tempPassword = enterPassword.getText().toString();
        login(tempUsername, tempPassword);
    }
    private void login(String tempUsername, String tempPassword) {
        ((TextView)findViewById(R.id.MyUsername)).setText(tempUsername);
        Amplify.Auth.signIn(tempUsername,
                tempPassword,
                result -> {
                    if (result.isSignInComplete()) {
                        username = tempUsername;
                        password = tempPassword;
                        login = true;
                        try {
                            save(loginFilename, username + Split + password);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        findViewById(R.id.UsernameSet).setVisibility(View.VISIBLE);
                        findViewById(R.id.LoginSet).setVisibility(View.GONE);

//                        findViewById(R.id.MyUsername).setVisibility(View.VISIBLE);
                        System.out.println(findViewById(R.id.UsernameSet).getVisibility() + " "
                                + findViewById(R.id.MyUsername).getVisibility() + " "
                                + ((TextView)findViewById(R.id.MyUsername)).getText());
//                        upvalid();
                    } else {
//                        upinvalid();
                    }
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
//                    upinvalid();
                });
    }
    private void logout() {
        final View tempContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(LaunchActivity.this);
        ((TextView)tempContainer.findViewById(R.id.onlyTextView)).setText("Are you sure to log out?");
        tempBuilder.setView(tempContainer);
        tempBuilder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = "";
                password = "";
                login = false;
                try {
                    save(loginFilename, username + Split + password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                findViewById(R.id.UsernameSet).setVisibility(View.GONE);
                findViewById(R.id.LoginSet).setVisibility(View.VISIBLE);
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        tempBuilder.show();
    }
//    private void testTemp() {
//        final View tempContainer = getLayoutInflater().inflate(R.layout.just_a_text_view, null, false);
//        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(LaunchActivity.this);
//        ((TextView)tempContainer.findViewById(R.id.onlyTextView)).setText("This is where the system supposed to send the message to the database, and get a response about whether the username and password combo are valid. For testing purpose now, click VALID to simulate if it is, and INVALID otherwise.");
//        tempBuilder.setView(tempContainer);
//        tempBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                upvalid();
//            }
//        }).setNegativeButton("INVALID", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                upinvalid();
//            }
//        });
//        tempBuilder.show();
//    }
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
    private Bitmap zoomToBitmap(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
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
