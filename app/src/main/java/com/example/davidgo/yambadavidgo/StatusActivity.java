
//David Gonzalez Perez

package com.example.davidgo.yambadavidgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StatusActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Inflar de forma dinamica el fragment
        if (savedInstanceState == null) {
            StatusFragment fragment = new StatusFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }

    }
}
