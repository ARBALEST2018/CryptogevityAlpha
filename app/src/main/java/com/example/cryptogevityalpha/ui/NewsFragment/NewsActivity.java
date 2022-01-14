package com.example.cryptogevityalpha.ui.NewsFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptogevityalpha.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        findViewById(R.id.newsBackButton).setOnClickListener(v -> {
            onBackPressed();
        });
        findViewById(R.id.renewButton).setOnClickListener(v -> {
            try {
                renew();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
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

}
