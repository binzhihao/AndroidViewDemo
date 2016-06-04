package com.bean.android.counterview;

import java.text.DecimalFormat;

public class CounterDecimalFormatter implements CounterFormatter {

    private final DecimalFormat format;

    public CounterDecimalFormatter(final DecimalFormat format) {
        this.format = format;
    }

    @Override
    public String format(float value) {
        return format.format(value);
    }
}
