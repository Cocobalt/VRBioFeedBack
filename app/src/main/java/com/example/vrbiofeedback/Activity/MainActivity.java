package com.example.vrbiofeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import nl.joery.animatedbottombar.AnimatedBottomBar;

import android.os.Bundle;

import com.example.vrbiofeedback.Adapter.FixedPagerAdapter;
import com.example.vrbiofeedback.MyView.MainViewPager;
import com.example.vrbiofeedback.R;
import com.example.vrbiofeedback.Fragments.HomeFragment;
import com.example.vrbiofeedback.Fragments.AccountFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewPager viewPager;
    private AnimatedBottomBar bottomBar;
    private List<Fragment> fragments = new ArrayList<>();
    private FixedPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        bottomBar = findViewById(R.id.bottom_bar);
        fragments.add(new HomeFragment());
        fragments.add(new AccountFragment());
        adapter = new FixedPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        bottomBar.setupWithViewPager(viewPager);
    }
    protected void onResume() {
        int id=getIntent().getIntExtra("id", 0);
        System.out.println(id);
        if(id==1){
            viewPager.setCurrentItem(1);
        }
        if(id==0){
            viewPager.setCurrentItem(0);
        }
        super.onResume();
    }


    @Override
    public void onBackPressed() {
    }
}

