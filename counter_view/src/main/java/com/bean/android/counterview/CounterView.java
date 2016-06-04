package com.bean.android.counterview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class CounterView extends TextView{

    private float startValue,endValue,increment;
    private int timeInterval;
    private boolean autoStart;
    private Counter counter;
    private CounterFormatter counterFormatter;

    public CounterView(Context context) {
        super(context);
        init(context,null,0,0);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0,0);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr,0);
    }

    @TargetApi(21)
    public CounterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr,defStyleRes);
    }

    private void init(Context context,AttributeSet attrs,int defStyleAttr,int defStyleRes){

        if(attrs==null){
            startValue=0f;
            endValue=0f;
            autoStart=true;
            timeInterval=500;
            increment=1f;
            return;
        }
        final TypedArray typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CounterView,
                defStyleAttr,
                defStyleRes);
        timeInterval = typedArray.getInteger(R.styleable.CounterView_timeInterval, 500);
        increment = typedArray.getFloat(R.styleable.CounterView_increment, 1f);
        startValue = typedArray.getFloat(R.styleable.CounterView_startValue, 0f);
        endValue = typedArray.getFloat(R.styleable.CounterView_endValue, 0f);
        autoStart = typedArray.getBoolean(R.styleable.CounterView_autoStart, true);
        typedArray.recycle();

        counter = new Counter(this);
        counterFormatter = new CounterDefaultFormatter();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (autoStart) {
            start();
        }
    }

    @Override
    protected void onDetachedFromWindow(){
        stop();
        super.onDetachedFromWindow();
    }

    public void start(){
        //View.removeCallbacks(Runnable)-Removes the specified Runnable from the message queue if there exists
        removeCallbacks(counter);
        //post the new Runnable
        post(counter);
    }

    public void stop(){
        removeCallbacks(counter);
    }

    public void setCurrentTextValue(float number) {
        setText(counterFormatter.format(number));
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public float getEndValue() {
        return endValue;
    }

    public void setEndValue(float endValue) {
        this.endValue = endValue;
    }

    public float getIncrement() {
        return increment;
    }

    public void setIncrement(float increment) {
        this.increment = increment;
    }

    public int getInterval() {
        return timeInterval;
    }

    public void setInterval(int interval) {
        this.timeInterval = interval;
    }

    public float getStartValue() {
        return startValue;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    public CounterFormatter getCounterFormatter() {
        return counterFormatter;
    }

    public void setCounterFormatter(CounterFormatter counterFormatter) {
        this.counterFormatter = counterFormatter;
    }
}
