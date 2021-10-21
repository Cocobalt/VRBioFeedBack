package com.example.vrbiofeedback.Adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;

public class FixedPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> titles;
    private List<Fragment> fragments;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    private FragmentManager fm;

    public FixedPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            removeFragment(container, position);
            fragment = (Fragment)
                    super.instantiateItem(container, position);
        } catch (Exception ignored) {
        }
        return fragment;
    }

    private void removeFragment(ViewGroup container, int index) {
        String tag = getFragmentTag(container.getId(), index);
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
        ft = null;
        fm.executePendingTransactions();
    }

    private String getFragmentTag(int viewId, int index) {
        try {
            Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
            Class<?>[] parameterTypes = {int.class, long.class};
            Method method = cls.getDeclaredMethod("makeFragmentName", parameterTypes);
            method.setAccessible(true);
            return (String) method.invoke(this, viewId, index);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        return POSITION_NONE;
    }


}