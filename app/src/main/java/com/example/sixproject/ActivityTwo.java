package com.example.sixproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle; import android.widget.ArrayAdapter; import android.widget.GridView;
import android.widget.TextView;


public class ActivityTwo extends Activity {

    String[] data = {"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k"};

    GridView gvMain;
    ArrayAdapter<String> adapter;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Intent intent = new Intent(this,MainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        String name=intent.getStringExtra("username");
        String email=intent.getStringExtra("gift");
        data[0]=name+email;
        adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data);
        gvMain = (GridView) findViewById(R.id.gvMain);
        gvMain.setAdapter(adapter);
        adjustGridView();
    }

    private void adjustGridView() {
    }
}