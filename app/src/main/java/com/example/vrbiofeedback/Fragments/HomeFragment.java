package com.example.vrbiofeedback.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.vrbiofeedback.Activity.AccountMissActivity;
import com.example.vrbiofeedback.Activity.SetUpActivity;
import com.example.vrbiofeedback.Base.MyApplication;
import com.example.vrbiofeedback.R;
import com.example.vrbiofeedback.Utils.CheckString;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View mView;
    private Context c;

    private CheckBox check_outspace;
    private CheckBox check_forest;
    private CheckBox check_train;
    private CheckBox check_beach;
    private Button btn_start;
    private TextView text_help;

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        this.c=getActivity();
        findElement();

        listenElement();
        return mView;
    }

    public void listenElement() {
        check_outspace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    check_beach.setChecked(false);
                    check_forest.setChecked(false);
                    check_train.setChecked(false);
                    MyApplication.setScene("OuterSpace");
                }else{
                    MyApplication.setScene("");
                }
            }
        });
        check_train.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    check_beach.setChecked(false);
                    check_forest.setChecked(false);
                    check_outspace.setChecked(false);
                    MyApplication.setScene("Train");
                }else{
                    MyApplication.setScene("");
                }
            }
        });
        check_forest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    check_beach.setChecked(false);
                    check_outspace.setChecked(false);
                    check_train.setChecked(false);
                    MyApplication.setScene("Forest");
                }else{
                    MyApplication.setScene("");
                }
            }
        });
        check_beach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    check_outspace.setChecked(false);
                    check_forest.setChecked(false);
                    check_train.setChecked(false);
                    MyApplication.setScene("Beach");
                }else{
                    MyApplication.setScene("");
                }
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(CheckString.isEmpty(MyApplication.getUserBR())||CheckString.isEmpty(MyApplication.getUserID())){
                    Intent intent = new Intent(getActivity(), AccountMissActivity.class);
                    startActivity(intent);
                }
                else if(CheckString.isEmpty(MyApplication.getScene())){
                    Toast.makeText(c, "Please select a scene", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getActivity(), SetUpActivity.class);
                    startActivity(intent);
                }
            }

        });
    }

    public void findElement() {
        check_beach = mView.findViewById(R.id.check_beach);
        check_forest = mView.findViewById(R.id.check_forest);
        check_outspace = mView.findViewById(R.id.check_outer_space);
        check_train = mView.findViewById(R.id.check_train);
        btn_start = mView.findViewById(R.id.btn_start);
        text_help = mView.findViewById(R.id.text_help);
    }
}
