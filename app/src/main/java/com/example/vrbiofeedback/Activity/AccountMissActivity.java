package com.example.vrbiofeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vrbiofeedback.Fragments.AccountFragment;
import com.example.vrbiofeedback.R;

import java.sql.SQLException;

public class AccountMissActivity extends AppCompatActivity {

    private Button btn_account;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_miss);
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
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountMissActivity.this, MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void findElement() {
        btn_account = findViewById(R.id.btn_account);
        btn_back = findViewById(R.id.btn_back);
    }
}