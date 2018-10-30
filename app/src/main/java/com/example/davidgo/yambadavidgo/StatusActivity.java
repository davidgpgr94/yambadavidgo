package com.example.davidgo.yambadavidgo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String CONSUMER_KEY = "CONSUMER KEY";
    private static final String CONSUMER_SECRET = "CONSUMER SECRET";
    private static final String ACCESS_TOKEN = "ACCESS TOKEN";
    private static final String ACCESS_TOKEN_SECRET = "ACCESS TOKEN SECRET";

    private static final String TAG = "StatusActivity";

    EditText et_status;
    Button button_tweet;
    Twitter twitter;
    TextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        et_status = (EditText) findViewById(R.id.et_status);
        et_status.addTextChangedListener(this);
        button_tweet = (Button) findViewById(R.id.button_tweet);
        button_tweet.setOnClickListener(this);

        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_count.setText(Integer.toString(280));
        tv_count.setTextColor(Color.GREEN);



        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();

    }


    @Override
    public void onClick(View v) {
        String status = et_status.getText().toString();
        Log.d(TAG, "onClicked");

        new PostTask().execute(status);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable statusText) {
        int count = 280 - statusText.length();
        tv_count.setText(Integer.toString(count));
        if (count > 70)
            tv_count.setTextColor(Color.GREEN);
        else if (count <= 70 && count > 0)
            tv_count.setTextColor(Color.YELLOW);
        else
            tv_count.setTextColor(Color.RED);
    }

    private final class PostTask extends AsyncTask<String, Void, String> {

        //Llamada al empezar
        @Override
        protected String doInBackground(String... strings) {
            try {
                twitter.updateStatus(strings[0]);
                Log.d(TAG, "Enviado correctamente: ");
                return "Tweet enviado correctamente";
            } catch (Exception e) {
                Log.e(TAG, "Fallo en el envio");
                e.printStackTrace();
                return "Fallo en el envío del tweet";
            }
        }

        //Llamada cuando la actividad en background ha terminado
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this, "Tweet enviado satisfactoriamente", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
