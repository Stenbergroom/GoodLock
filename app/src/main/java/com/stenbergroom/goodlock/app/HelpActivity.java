package com.stenbergroom.goodlock.app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class HelpActivity extends Activity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);

        actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setIcon(android.R.color.transparent);
            actionBar.setTitle(R.string.action_bar_title_help);
        }
    }
}
