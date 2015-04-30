package com.stenbergroom.goodlock.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_activity);

        Thread loadThread = new Thread() {
            @Override
            public void run() {
                try {
                    int loadScreenTime = 0;
                    while (loadScreenTime < 4000) {
                        sleep(100);
                        loadScreenTime += 100;
                    }
                    Intent intent = new Intent(getApplicationContext(), MainPreferences.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    //
                } finally {
                    finish();
                }
            }
        };
        loadThread.start();
    }
}
