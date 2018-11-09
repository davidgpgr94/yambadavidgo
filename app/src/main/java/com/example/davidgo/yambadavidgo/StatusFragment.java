
//David Gonzalez Perez

package com.example.davidgo.yambadavidgo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private static final String CONSUMER_KEY = "CONSUMER_KEY";
    private static final String CONSUMER_SECRET = "CONSUMER_SECRET";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET";
    private static final int TWEET_MAX_LENGTH = 280;

    private static final String TAG = "StatusActivity";

    EditText et_status;
    Button button_tweet;
    Twitter twitter;
    TextView tv_count;
    ProgressBar progressbar_status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);


        et_status = (EditText) view.findViewById(R.id.et_status);
        progressbar_status = (ProgressBar) view.findViewById(R.id.progressbar_status);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        button_tweet = (Button) view.findViewById(R.id.button_tweet);

        et_status.addTextChangedListener(this);
        tv_count.setText(Integer.toString(TWEET_MAX_LENGTH));
        tv_count.setTextColor(Color.GREEN);
        button_tweet.setOnClickListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();

        return view;
    }

    @Override
    public void onClick(View v) {
        String status = et_status.getText().toString();
        Log.d(TAG, "onClicked");

        new PostTask().execute(status);
        progressbar_status.setVisibility(View.VISIBLE);
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

        //Llamada cuando hay algun progreso
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //Llamada cuando la actividad en background ha terminado
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressbar_status.setVisibility(View.GONE);
            Snackbar.make(StatusFragment.this.getView(), result, Snackbar.LENGTH_LONG).show();
            et_status.setText("");
        }
    }

}
