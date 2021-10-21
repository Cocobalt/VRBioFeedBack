package com.example.vrbiofeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.vrbiofeedback.Utils.PermissionHelper;
import com.example.vrbiofeedback.Utils.PermissionInterface;

import com.example.vrbiofeedback.R;

public class WelcomeActivity extends AppCompatActivity implements PermissionInterface {
    private PermissionHelper permissionHelper;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        permissionHelper = new PermissionHelper(this, this);
        permissionHelper.requestPermissions();
    }

    public int getPermissionsRequestCode() {
        return 0;
    }

    public String[] getPermissions() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.BLUETOOTH};
    }

    public void requestPermissionsSuccess() {
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    public void requestPermissionsFail() {
        this.finish();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

