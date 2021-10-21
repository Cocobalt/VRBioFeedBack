package com.example.vrbiofeedback.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.vrbiofeedback.Base.MyApplication;
import com.example.vrbiofeedback.R;
import com.example.vrbiofeedback.Utils.CheckString;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private View mView;
    private Context c;

    private Button btn_save;
    private Button btn_clear;
    private EditText text_uid;
    private TextView text_br;
    private Spinner sp_br;

    private ArrayAdapter<String> br_adapter;
    List<String> data_list = new ArrayList<String>();

    public AccountFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        this.c=getActivity();
        findElement();
        data_list.add("5.5");
        data_list.add("6");
        data_list.add("6.5");
        data_list.add("7");
        data_list.add("7.5");
        data_list.add("8");

        br_adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        br_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sp_br.setAdapter(br_adapter);

        listenElement();
        return mView;
    }

    private void listenElement() {
        sp_br.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                text_br.setText(data_list.get(pos));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MyApplication.setUserID("");
                MyApplication.setUserBR("");
                text_uid.setText("");
                text_br.setText("");
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!CheckString.isEmpty(text_br.getText().toString()) &&!CheckString.isEmpty(text_uid.getText().toString())){
                    MyApplication.setUserID(text_uid.getText().toString());
                    MyApplication.setUserBR(text_br.getText().toString());
                    Toast.makeText(c, "Account saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(c, "Please complete your account information", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void findElement() {
        btn_save = mView.findViewById(R.id.btn_save);
        btn_clear = mView.findViewById(R.id.btn_clear);
        text_uid = mView.findViewById(R.id.text_uid);
        text_br = mView.findViewById(R.id.text_br);
        sp_br = mView.findViewById(R.id.sp_br);
    }
}
