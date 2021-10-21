package com.example.vrbiofeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vrbiofeedback.R;

public class SetUpActivity extends AppCompatActivity {

    private Button btn_connect;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        findElement();
        listenElement();
    }

    public void listenElement() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetUpActivity.this,BlueToothConnect.class);
                intent.putExtra("id",1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void findElement() {
        btn_back = findViewById(R.id.btn_back1);
        btn_connect = findViewById(R.id.btn_connect);
    }
}