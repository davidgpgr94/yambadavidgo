package com.example.davidgo.yambadavidgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "StatusActivity";
    EditText et_status;
    Button button_tweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        et_status = (EditText) findViewById(R.id.et_status);
        button_tweet = (Button) findViewById(R.id.button_tweet);
        button_tweet.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String status = et_status.getText().toString();
        Log.d(TAG, "onClicked");
    }
}
