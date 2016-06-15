package com.bean.android.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener{

    private int timeDelay;
    private boolean autoPlay;
    private int indicatorColor;
    private float indicatorHeight;
    private int mCount=0,mCurrentItem=0;
    private ViewPager mPager;
    private TabLayout mIndicator;
    private OnBannerClickListener mListener;
    private Context mContext;
    private final Runnable mTask=new Runnable() {
        @Override
        public void run() {
            mCurrentItem = (mCurrentItem+1)%mCount;
            mPager.setCurrentItem(mCurrentItem);
            //removeCallbacks(mTask);
            postDelayed(mTask, timeDelay);
        }
    };
    private LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

    public Banner(Context context) {
        super(context);
        init(context,null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Banner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.banner, this, true);  //pay attention to true
        mPager=(ViewPager)findViewById(R.id.pager);
        mIndicator=(TabLayout)findViewById(R.id.indicator);
        mContext=context;
        timeDelay=5000;autoPlay=true;
        indicatorColor=getResources().getColor(R.color.colorBlue);
        indicatorHeight=5;
        if(attrs!=null){
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.Banner);
            timeDelay=attrsArray.getInteger(R.styleable.Banner_timeDelay,timeDelay);
            autoPlay=attrsArray.getBoolean(R.styleable.Banner_autoPlay,autoPlay);
            indicatorColor=attrsArray.getColor(R.styleable.Banner_indicatorColor,indicatorColor);
            indicatorHeight=attrsArray.getDimension(R.styleable.Banner_indicatorHeight,indicatorHeight);
            attrsArray.recycle();
        }
    }

    public void setImages(Object[] imagesUrl) {
        if (imagesUrl==null||imagesUrl.length<=0) return;

        mCount=imagesUrl.length; mCurrentItem=0;
        BannerPagerAdapter adapter=new BannerPagerAdapter();

        for (int i = 0; i < mCount; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(imagesUrl[i]).into(iv);    //加载图片
            adapter.addImageView(iv);
        }

        mPager.setAdapter(adapter); //mPager.setOffscreenPageLimit(3);
        mPager.setCurrentItem(mCurrentItem);
        mPager.addOnPageChangeListener(this);
        mIndicator.setSelectedTabIndicatorColor(indicatorColor);
        mIndicator.setSelectedTabIndicatorHeight((int)indicatorHeight);
        mIndicator.setupWithViewPager(mPager);

        if(mCount<=1){
            autoPlay=false;
            mIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {  //退到后台的时候停止轮播
        if(Constant.DEBUG)  Log.e("fuck","focus");
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            if(autoPlay && mCount>=2)
                postDelayed(mTask, timeDelay);
        }else{
            removeCallbacks(mTask);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if(Constant.DEBUG)  Log.e("fuck","detached");
        removeCallbacks(mTask);
        super.onDetachedFromWindow();
    }

    class BannerPagerAdapter extends PagerAdapter {

        private ArrayList<ImageView> imageViews=new ArrayList<>();

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final ImageView view=imageViews.get(position);
            container.addView(view,params);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.OnBannerClick(v,position);
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

        public void addImageView(ImageView iv){
            imageViews.add(iv);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {   //控制能否手动混动循环滚动
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentItem=position;
    }

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        this.mListener = listener;
    }

    public interface OnBannerClickListener {
        void OnBannerClick(View view, int position);
    }
}
