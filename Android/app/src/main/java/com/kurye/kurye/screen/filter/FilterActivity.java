package com.kurye.kurye.screen.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kurye.kurye.R;

public class FilterActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, FilterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

}
