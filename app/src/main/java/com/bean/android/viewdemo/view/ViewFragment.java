package com.bean.android.viewdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bean.android.viewdemo.R;
import com.bean.android.viewdemo.adapter.MyFragmentPagerAdapter;

public class ViewFragment extends Fragment{

    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, null);

        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TextViewFragment(), getString(R.string.fragment_1));
        adapter.addFragment(new EditTextFragment(), getString(R.string.fragment_2));
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);

        mTablayout.addTab(mTablayout.newTab().setText(R.string.fragment_1));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.fragment_2));
        mTablayout.setupWithViewPager(mViewPager);  // 将两者关联起来

        return view;
    }
}
