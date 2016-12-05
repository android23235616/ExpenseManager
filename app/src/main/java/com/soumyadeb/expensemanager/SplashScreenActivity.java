package com.soumyadeb.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {
    public static int SPLASH_TIMEOUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                Intent home=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(home);
                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
