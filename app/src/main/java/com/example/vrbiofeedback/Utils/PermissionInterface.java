package com.example.vrbiofeedback.Utils;

public interface PermissionInterface {

    int getPermissionsRequestCode();

    String[] getPermissions();

    void requestPermissionsSuccess();

    void requestPermissionsFail();
}
