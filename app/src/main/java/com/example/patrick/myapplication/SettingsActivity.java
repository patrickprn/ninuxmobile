package com.example.patrick.myapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Patrizio Perna on 10/01/15.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            //creo il fragment
            SettingsFragment fragment = new SettingsFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }


    }
}

