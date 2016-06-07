package com.bean.android.viewdemo.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.android.viewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private final List<Integer> mFragmentIcons = new ArrayList<>();
    private LayoutInflater mInflater;

    public MyFragmentPagerAdapter(FragmentManager fm, LayoutInflater inflater) {
        super(fm);
        mInflater=inflater;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return mFragmentTitles.get(position);
        return null;
    }

    public View getTabView(int position){
        //可用ViewHolder进行优化
        View view=mInflater.inflate(R.layout.tab,null);
        ((TextView)view.findViewById(R.id.tab_text)).setText(mFragmentTitles.get(position));
        ((ImageView)view.findViewById(R.id.tab_img)).setImageResource(mFragmentIcons.get(position));
        return view;
    }

    public void addFragment(Fragment fragment, String title, int iconRes) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mFragmentIcons.add(iconRes);
    }
}
