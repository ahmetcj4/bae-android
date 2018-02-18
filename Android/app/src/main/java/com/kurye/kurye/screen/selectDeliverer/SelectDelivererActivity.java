package com.kurye.kurye.screen.selectDeliverer;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kurye.kurye.R;
import com.kurye.kurye.databinding.ActivitySelectDelivererBinding;

/**
 * Created by elifguler on 18.02.2018.
 */

public class SelectDelivererActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, SelectDelivererActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectDelivererBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_deliverer);
        binding.executePendingBindings();
    }
}
