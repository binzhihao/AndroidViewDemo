package com.bean.android.counterview;

import android.util.Log;

public class Counter implements Runnable{
    private CounterView view;
    private float currentValue;

    public Counter(CounterView view) {
        this.view = view;
    }

    @Override
    public void run() {
        if (currentValue < view.getEndValue()) {
            if(Constant.DEBUG){Log.e("fuck",""+currentValue);}
            if(currentValue + view.getIncrement() <= view.getEndValue()){
                currentValue+=view.getIncrement();
            }else{
                currentValue=view.getEndValue();
            }
            view.setCurrentTextValue(currentValue);
            //不断的移除和post Runnable可以防止主线程阻塞
            view.removeCallbacks(Counter.this);
            view.postDelayed(Counter.this, view.getInterval());
        } else if(currentValue == view.getEndValue()){
            if(Constant.DEBUG){Log.e("fuck",""+currentValue);}
            view.removeCallbacks(Counter.this);
        }
    }
}