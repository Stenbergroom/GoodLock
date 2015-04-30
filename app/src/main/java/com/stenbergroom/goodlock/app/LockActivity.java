package com.stenbergroom.goodlock.app;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.*;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LockActivity extends AnimationsActivity{

    private final int REQUEST_CODE_ADMIN_LOCK = 3;
    private final String CONST_CHECK_BOX_STATE = "checkBoxState";
    private final String CONST_ADM_STATE_OFF = "adminStateOff";
    private final String CONST_NUM_OF_ANIM = "numberOfAnimation";
    private final String CONST_NUM_OF_SOUND = "numberOfSound";
    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName componentName;
    private SoundPool soundPool;
    private int soundOneLock;
    private int soundTwoLock;
    private int soundThreeLock;
    private int soundFourLock;
    private LinearLayout lLayoutTwoPartsLock;
    private ImageView imageViewTopLock;
    private ImageView imageViewBotLock;
    private LinearLayout lLayoutFullScreenLock;
    private ImageView imageViewFullScreenLock;
    private Animation animationFull;
    private Animation animationDouble;
    private Handler h;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundOneLock = soundPool.load(getApplicationContext(), R.raw.click, 1);
        soundTwoLock = soundPool.load(getApplicationContext(), R.raw.tick, 1);
        soundThreeLock = soundPool.load(getApplicationContext(), R.raw.tock, 1);
        soundFourLock = soundPool.load(getApplicationContext(), R.raw.floop, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if(sharedPreferences.getBoolean(CONST_CHECK_BOX_STATE, true)
                        && savedNumberOfAnim() > 0
                        && devicePolicyManager.isAdminActive(componentName)){
                    switch (savedNumberOfSound()){
                        case 1:
                            if(sampleId == soundOneLock) { //to avoid repeat play sound
                                soundPool.play(soundOneLock, 1.0f, 1.0f, 0, 0, 1.5f);
                            }
                            break;
                        case 2:
                            if(sampleId == soundTwoLock) {
                                soundPool.play(soundTwoLock, 1.0f, 1.0f, 0, 0, 1.5f);
                            }
                            break;
                        case 3:
                            if(sampleId == soundThreeLock) {
                                soundPool.play(soundThreeLock, 1.0f, 1.0f, 0, 0, 1.5f);
                            }
                            break;
                        case 4:
                            if(sampleId == soundFourLock) {
                                soundPool.play(soundFourLock, 1.0f, 1.0f, 0, 0, 1.5f);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        h = new Handler();

        lLayoutTwoPartsLock = (LinearLayout)findViewById(R.id.lLayoutTwoPartsLock);
        imageViewTopLock = (ImageView)findViewById(R.id.imageViewTopLock);
        imageViewBotLock = (ImageView)findViewById(R.id.imageViewBotLock);
        lLayoutFullScreenLock = (LinearLayout)findViewById(R.id.lLayoutFullScreenLock);
        imageViewFullScreenLock = (ImageView)findViewById(R.id.imageViewFullScreenLock);

        devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        componentName = new ComponentName(this, UserAdmin.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(devicePolicyManager.isAdminActive(componentName)){
            switch (savedNumberOfAnim()) {
                case 1:
                    imageViewTopLock.setBackgroundResource(R.drawable.texic_top);
                    imageViewBotLock.setBackgroundResource(R.drawable.texic_bot);
                    lLayoutTwoPartsLock.setVisibility(View.VISIBLE);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                    imageViewTopLock.startAnimation(animationDouble);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                    imageViewBotLock.startAnimation(animationDouble);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                            //finishAndRemoveTask(); API 21 and above (uses android:excludeFromRecents="true")
                        }
                    }, 2200);
                    break;
                case 2:
                    imageViewTopLock.setBackgroundResource(R.drawable.fitch_top);
                    imageViewBotLock.setBackgroundResource(R.drawable.fitch_bot);
                    lLayoutTwoPartsLock.setVisibility(View.VISIBLE);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                    imageViewTopLock.startAnimation(animationDouble);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                    imageViewBotLock.startAnimation(animationDouble);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                        }
                    }, 2200);
                    break;
                case 3:
                    imageViewTopLock.setBackgroundResource(R.drawable.rible_top);
                    imageViewBotLock.setBackgroundResource(R.drawable.rible_bot);
                    lLayoutTwoPartsLock.setVisibility(View.VISIBLE);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                    imageViewTopLock.startAnimation(animationDouble);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                    imageViewBotLock.startAnimation(animationDouble);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                        }
                    }, 2200);
                    break;
                case 4:
                    imageViewTopLock.setBackgroundResource(R.drawable.metallic_top);
                    imageViewBotLock.setBackgroundResource(R.drawable.metallic_bot);
                    lLayoutTwoPartsLock.setVisibility(View.VISIBLE);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot);
                    imageViewTopLock.startAnimation(animationDouble);
                    animationDouble = AnimationUtils.loadAnimation(this, R.anim.translate_bot_top);
                    imageViewBotLock.startAnimation(animationDouble);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                        }
                    }, 2200);
                    break;
                case 5:
                    imageViewFullScreenLock.setBackgroundResource(R.drawable.cornel);
                    lLayoutFullScreenLock.setVisibility(View.VISIBLE);
                    animationFull = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot_full);
                    imageViewFullScreenLock.startAnimation(animationFull);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                        }
                    }, 2200);
                    break;
                case 6:
                    imageViewFullScreenLock.setBackgroundResource(R.drawable.integrey);
                    lLayoutFullScreenLock.setVisibility(View.VISIBLE);
                    animationFull = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot_full);
                    imageViewFullScreenLock.startAnimation(animationFull);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                        }
                    }, 2200);
                    break;
                case 7:
                    imageViewFullScreenLock.setBackgroundResource(R.drawable.hearts);
                    lLayoutFullScreenLock.setVisibility(View.VISIBLE);
                    animationFull = AnimationUtils.loadAnimation(this, R.anim.translate_top_bot_full);
                    imageViewFullScreenLock.startAnimation(animationFull);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            devicePolicyManager.lockNow();
                            finish();
                        }
                    }, 2200);
                    break;
                default: //if animation not selected but admin activated
                    Toast.makeText(this, R.string.select_anim, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }else{
            showDialogNeedAdmin();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showDialogNeedAdmin(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LockActivity.this)
                .setIcon(R.drawable.icon_setting)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.need_adm_dialog_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Select (ACTIVATE) for activate application");
                        startActivityForResult(intent, REQUEST_CODE_ADMIN_LOCK);
                    }
                });
        AlertDialog alert = dialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_ADMIN_LOCK:
                if(resultCode == RESULT_OK) {
                    saveAdminStateOff(CONST_ADM_STATE_OFF, false);
                }else{
                    Toast.makeText(this, R.string.activation_failed, Toast.LENGTH_SHORT).show();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveAdminStateOff(String key, boolean value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int savedNumberOfAnim(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getInt(CONST_NUM_OF_ANIM, 0);
    }

    public int savedNumberOfSound(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getInt(CONST_NUM_OF_SOUND, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*Interception button Back*/
    @Override
    public void onBackPressed() {
        return;
    }

    /*Interception volume buttons*/
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }
}
