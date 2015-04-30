package com.stenbergroom.goodlock.app;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UserAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context,R.string.activation_successful);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
    }
    public void showToast(Context context, int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
