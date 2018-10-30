package com.example.davidgo.yambadavidgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "StatusActivity";
    EditText et_status;
    Button button_tweet;
    Twitter twitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        et_status = (EditText) findViewById(R.id.et_status);
        button_tweet = (Button) findViewById(R.id.button_tweet);
        button_tweet.setOnClickListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("LA CONSUMER KEY")
                .setOAuthConsumerSecret("EL CONSUMER SECRET")
                .setOAuthAccessToken("EL ACCESS TOKEN")
                .setOAuthAccessTokenSecret("EL ACCESS TOKEN SECRET");
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();

    }


    @Override
    public void onClick(View v) {
        String status = et_status.getText().toString();
        Log.d(TAG, "onClicked");

        try {
            twitter.updateStatus(status);
            Log.d(TAG, "Enviado correctamente: ");
        } catch (Exception e) {
            Log.e(TAG, "Fallo en el envio");
            e.printStackTrace();
        }
    }
}
