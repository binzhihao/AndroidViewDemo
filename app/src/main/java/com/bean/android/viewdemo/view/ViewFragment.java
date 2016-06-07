package com.bean.android.viewdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),inflater);
        adapter.addFragment(new TextViewFragment(), getString(R.string.fragment_1),R.drawable.tab_icon1);
        adapter.addFragment(new EditTextFragment(), getString(R.string.fragment_2),R.drawable.tab_icon2);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTablayout.setupWithViewPager(mViewPager);  // 将两者关联起来，必须在setAdapter之后调用
        //自定义Tab的样式
        for(int i=mTablayout.getTabCount()-1;i>=0;i--){
            //set icon only
            //mTablayout.getTabAt(i).setIcon(R.drawable.tab_icon1);
            //set custom view
            mTablayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
        mTablayout.getTabAt(0).getCustomView().setSelected(true);//初始化时候选中第一个标签，否则图标状态不匹配

        return view;
    }
}
