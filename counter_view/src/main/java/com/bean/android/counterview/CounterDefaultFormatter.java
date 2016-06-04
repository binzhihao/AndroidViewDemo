package com.bean.android.counterview;

public class CounterDefaultFormatter implements CounterFormatter {
    @Override
    public String format(float value) {
        return String.valueOf(value);
    }
}
