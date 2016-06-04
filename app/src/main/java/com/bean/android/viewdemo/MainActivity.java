package com.bean.android.viewdemo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bean.android.counterview.CounterNumberFormatter;
import com.bean.android.counterview.CounterView;
import com.bean.android.counterview.CounterDecimalFormatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textbg=(TextView)findViewById(R.id.text_bg);
        CounterView text=(CounterView)findViewById(R.id.text);
        TextView textbg2=(TextView)findViewById(R.id.text_bg2);
        CounterView text2=(CounterView)findViewById(R.id.text2);

        final Typeface font = Typeface.createFromAsset(getAssets(),"segment-7.otf");
        textbg.setTypeface(font);
        text.setTypeface(font);
        textbg2.setTypeface(font);
        text2.setTypeface(font);
        text.setCounterFormatter(new CounterNumberFormatter(NumberFormat.getNumberInstance(Locale.US)));
        text2.setCounterFormatter(new CounterDecimalFormatter(new DecimalFormat("##.00")));
    }


}
