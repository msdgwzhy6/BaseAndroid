package com.che.carcheck.ui.home;

import android.os.Bundle;

import com.che.carcheck.R;
import com.che.carcheck.support.config.BaseActivity;
import com.che.carcheck.support.config.BaseApplication;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.getBgThreadHandler().post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
