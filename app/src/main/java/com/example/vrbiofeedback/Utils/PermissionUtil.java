package com.example.vrbiofeedback.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;

public class PermissionUtil {

    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode);
        }
    }

    public static String[] getDeniedPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> deniedPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissionList.add(permission);
                }
            }
            int size = deniedPermissionList.size();
            if (size > 0) {
                return deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
            }
        }
        return null;
    }
}



