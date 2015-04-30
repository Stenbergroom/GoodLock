package com.stenbergroom.goodlock.app;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.*;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.Locale;

public class MainPreferences extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private final String DEBUG_LOGS = "DebugLogs";
    private final int REQUEST_CODE_ADMIN = 1;
    private final int REQUEST_CODE_ANIM = 2;
    private final String CONST_LANG = "language";
    private final String CONST_CHECK_BOX_STATE = "checkBoxState";
    private final String CONST_NUM_OF_SOUND = "numberOfSound";
    private final String CONST_ADM_STATE_OFF = "adminStateOff";
    private SoundPool sounds;
    private int soundOne;
    private int soundTwo;
    private int soundThree;
    private int soundFour;
    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName componentName;
    private ActionBar actionBar;
    private PreferenceScreen anim;
    private CheckBoxPreference chb_sound;
    private ListPreference list_sounds;
    private PreferenceScreen activate;
    private PreferenceScreen deactivate;
    private ListPreference language;
    private PreferenceScreen about;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //language
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String languageToLoad = sharedPreferences.getString(CONST_LANG, "");
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        addPreferencesFromResource(R.xml.preference_main);

        anim = (PreferenceScreen)findPreference("anim");
        anim.setOnPreferenceClickListener(this);
        chb_sound = (CheckBoxPreference)findPreference("chb_sound");
        chb_sound.setOnPreferenceClickListener(this);
        list_sounds = (ListPreference)findPreference("list_sounds");
        list_sounds.setOnPreferenceClickListener(this);
        activate = (PreferenceScreen)findPreference("activate");
        activate.setOnPreferenceClickListener(this);
        deactivate = (PreferenceScreen)findPreference("deactivate");
        deactivate.setOnPreferenceClickListener(this);
        language = (ListPreference)findPreference("language");
        language.setOnPreferenceClickListener(this);
        about = (PreferenceScreen)findPreference("about");
        about.setOnPreferenceClickListener(this);
        devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        componentName = new ComponentName(this, UserAdmin.class);
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundOne = sounds.load(getApplicationContext(), R.raw.click, 1);
        soundTwo = sounds.load(getApplicationContext(), R.raw.tick, 1);
        soundThree = sounds.load(getApplicationContext(), R.raw.tock, 1);
        soundFour = sounds.load(getApplicationContext(), R.raw.floop, 1);

        //Language
        language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                switch (language.findIndexOfValue(newValue.toString())){
                    case 0:
                        saveLocale(CONST_LANG, "ru");
                        break;
                    case 1:
                        saveLocale(CONST_LANG, "en");
                        break;
                    case 2:
                        saveLocale(CONST_LANG, "uk");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //Save chb_sound state
        chb_sound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                saveCheckBoxState(CONST_CHECK_BOX_STATE, Boolean.parseBoolean(String.valueOf(newValue)));
                return true;
            }
        });

        //ChangeSound
        list_sounds.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                switch (list_sounds.findIndexOfValue(newValue.toString())){
                    //first item in listPref is 0
                    case 0:
                        sounds.play(soundOne, 1.0f, 1.0f, 0, 0, 1.5f);
                        saveSoundNumber(CONST_NUM_OF_SOUND, 1);
                        break;
                    case 1:
                        sounds.play(soundTwo, 1.0f, 1.0f, 0, 0, 1.5f);
                        saveSoundNumber(CONST_NUM_OF_SOUND, 2);
                        break;
                    case 2:
                        sounds.play(soundThree, 1.0f, 1.0f, 0, 0, 1.5f);
                        saveSoundNumber(CONST_NUM_OF_SOUND, 3);
                        break;
                    case 3:
                        sounds.play(soundFour, 1.0f, 1.0f, 0, 0, 1.5f);
                        saveSoundNumber(CONST_NUM_OF_SOUND, 4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setIcon(android.R.color.transparent);
            actionBar.setTitle(R.string.action_bar_title_main);
        }
        getMenuInflater().inflate(R.menu.menu_main_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == anim){
            Intent intent = new Intent(this, AnimationsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANIM);

        }else if(preference == activate){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if(sharedPreferences.getBoolean(CONST_ADM_STATE_OFF, true)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Select (ACTIVATE) for activate application");
                startActivityForResult(intent, REQUEST_CODE_ADMIN);
            }
            if(!(sharedPreferences.getBoolean(CONST_ADM_STATE_OFF, true))){
                Toast.makeText(this, R.string.already_activate, Toast.LENGTH_SHORT).show();
            }

        }else if(preference == deactivate){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if(!(sharedPreferences.getBoolean(CONST_ADM_STATE_OFF, true))){
                saveAdminStateOff(CONST_ADM_STATE_OFF, true);
                showDialogDelete();
            }else{
                Toast.makeText(this, R.string.already_deactivated, Toast.LENGTH_SHORT).show();
            }
            devicePolicyManager.removeActiveAdmin(componentName);

        }else if(preference == about){
            //my debug
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Log.d(DEBUG_LOGS, "adminStateOff = "+String.valueOf(sharedPreferences.getBoolean(CONST_ADM_STATE_OFF, true)));

        }else if(preference == language){
            Toast.makeText(this, R.string.restart_app, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_ADMIN:
                if(resultCode == RESULT_OK) {
                    saveAdminStateOff(CONST_ADM_STATE_OFF, false);
                }else{
                    Toast.makeText(this, R.string.activation_failed, Toast.LENGTH_SHORT).show();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sounds.release();
    }

    public void showDialogDelete(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainPreferences.this)
                .setIcon(R.drawable.icon_setting)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        AlertDialog alert = dialogBuilder.create();
        alert.show();
        }

    public void saveAdminStateOff(String key, boolean value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public void saveSoundNumber(String key, int value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public void saveCheckBoxState(String key, boolean value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveLocale(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        System.exit(0);
    }
}