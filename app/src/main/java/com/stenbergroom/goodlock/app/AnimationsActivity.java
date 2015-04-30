package com.stenbergroom.goodlock.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AnimationsActivity extends Activity{

    private final String CONST_ANIM_OFF = "animationOff";
    private final String CONST_NUM_OF_ANIM = "numberOfAnimation";
    private SharedPreferences sharedPreferences;
    private ActionBar actionBar;
    private LinearLayout lLayoutTwoParts;
    private ImageView imageViewTop;
    private ImageView imageViewBot;
    private LinearLayout lLayoutFullScreen;
    private ImageView imageViewFullScreen;
    private Animation animationFull;
    private Animation animationDouble;
    private int animationTempNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animations_activity);

        lLayoutTwoParts = (LinearLayout)findViewById(R.id.lLayoutTwoParts);
        imageViewTop = (ImageView)findViewById(R.id.imageViewTop);
        imageViewBot = (ImageView)findViewById(R.id.imageViewBot);
        lLayoutFullScreen = (LinearLayout)findViewById(R.id.lLayoutFullScreen);
        imageViewFullScreen = (ImageView)findViewById(R.id.imageViewFullScreen);

        layoutsGone();
    }

    public void layoutsGone(){
        lLayoutTwoParts.setVisibility(View.GONE);
        lLayoutFullScreen.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setIcon(android.R.color.transparent);
        }
        getMenuInflater().inflate(R.menu.menu_animations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.itemSave:
                if(animationTempNumber != 0) {
                    saveNumberOfAnimation(CONST_NUM_OF_ANIM, animationTempNumber);
                    saveAnimationState(CONST_ANIM_OFF, false);
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickImageView(View view){
        switch (view.getId()){
            case R.id.imageButton1:
                layoutsGone();
                imageViewTop.setBackgroundResource(R.drawable.texic_top);
                imageViewBot.setBackgroundResource(R.drawable.texic_bot);
                lLayoutTwoParts.setVisibility(View.VISIBLE);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                imageViewTop.startAnimation(animationDouble);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                imageViewBot.startAnimation(animationDouble);
                animationTempNumber = 1;
                break;
            case R.id.imageButton2:
                layoutsGone();
                imageViewTop.setBackgroundResource(R.drawable.fitch_top);
                imageViewBot.setBackgroundResource(R.drawable.fitch_bot);
                lLayoutTwoParts.setVisibility(View.VISIBLE);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                imageViewTop.startAnimation(animationDouble);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                imageViewBot.startAnimation(animationDouble);
                animationTempNumber = 2;
                break;
            case R.id.imageButton3:
                layoutsGone();
                imageViewTop.setBackgroundResource(R.drawable.rible_top);
                imageViewBot.setBackgroundResource(R.drawable.rible_bot);
                lLayoutTwoParts.setVisibility(View.VISIBLE);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                imageViewTop.startAnimation(animationDouble);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                imageViewBot.startAnimation(animationDouble);
                animationTempNumber = 3;
                break;
            case R.id.imageButton4:
                layoutsGone();
                imageViewTop.setBackgroundResource(R.drawable.metallic_top);
                imageViewBot.setBackgroundResource(R.drawable.metallic_bot);
                lLayoutTwoParts.setVisibility(View.VISIBLE);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                imageViewTop.startAnimation(animationDouble);
                animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                imageViewBot.startAnimation(animationDouble);
                animationTempNumber = 4;
                break;
            case R.id.imageButton5:
                layoutsGone();
                imageViewFullScreen.setBackgroundResource(R.drawable.cornel);
                lLayoutFullScreen.setVisibility(View.VISIBLE);
                animationFull = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot_full);
                imageViewFullScreen.startAnimation(animationFull);
                animationTempNumber = 5;
                break;
            case R.id.imageButton6:
                layoutsGone();
                imageViewFullScreen.setBackgroundResource(R.drawable.integrey);
                lLayoutFullScreen.setVisibility(View.VISIBLE);
                animationFull = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot_full);
                imageViewFullScreen.startAnimation(animationFull);
                animationTempNumber = 6;
                break;
            case R.id.imageButton7:
                layoutsGone();
                imageViewFullScreen.setBackgroundResource(R.drawable.hearts);
                lLayoutFullScreen.setVisibility(View.VISIBLE);
                animationFull = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot_full);
                imageViewFullScreen.startAnimation(animationFull);
                animationTempNumber = 7;
                break;
        }
    }

    public void saveNumberOfAnimation(String key, int value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void saveAnimationState(String key, boolean value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
