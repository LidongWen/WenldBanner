package com.wenld.wenldbanner01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startCommonBannerActivity(View view) {
        goActivity(CommonBannerActivity.class);
    }

    public void startCustomUse(View view) {
        goActivity(CustomUseActivity.class);
    }


    public void goActivity(Class clz) {
        Intent intent=new Intent(this,clz);
        startActivity(intent);
    }
}
