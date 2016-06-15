package com.bean.android.viewdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bean.android.banner.Banner;
import com.bean.android.banner.Constant;
import com.bean.android.viewdemo.R;

public class BannerFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_banner,null);
        Banner banner1=(Banner)view.findViewById(R.id.banner);
        Banner banner2=(Banner)view.findViewById(R.id.banner2);

        String[] images= new String[] {
                "http://img.zcool.cn/community/01ae5656e1427f6ac72531cb72bac5.jpg",
                "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
                "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
                "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
                "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg"};
        Integer[] localImages={R.drawable.test0,R.drawable.test1,R.drawable.test2};

        banner1.setImages(images);
        banner1.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                if(Constant.DEBUG) Log.e("fuck",""+position);
            }
        });
        banner2.setImages(localImages);

        return view;
    }
}
