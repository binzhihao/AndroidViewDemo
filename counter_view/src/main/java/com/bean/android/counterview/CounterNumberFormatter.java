package com.bean.android.counterview;

import java.text.NumberFormat;

public class CounterNumberFormatter implements CounterFormatter {

    private final NumberFormat formatter;

    public CounterNumberFormatter(NumberFormat format) {
        this.formatter = format;
    }

    @Override
    public String format(float value) {
        return formatter.format(value);
    }
}
