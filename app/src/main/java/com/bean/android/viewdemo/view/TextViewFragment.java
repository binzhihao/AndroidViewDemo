package com.bean.android.viewdemo.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bean.android.counterview.CounterDecimalFormatter;
import com.bean.android.counterview.CounterNumberFormatter;
import com.bean.android.counterview.CounterView;
import com.bean.android.viewdemo.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TextViewFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_text_view,null);

        TextView textbg=(TextView)view.findViewById(R.id.text_bg);
        CounterView text=(CounterView)view.findViewById(R.id.text);
        TextView textbg2=(TextView)view.findViewById(R.id.text_bg2);
        CounterView text2=(CounterView)view.findViewById(R.id.text2);

        final Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"segment-7.otf");
        textbg.setTypeface(font);
        text.setTypeface(font);
        textbg2.setTypeface(font);
        text2.setTypeface(font);
        text.setCounterFormatter(new CounterNumberFormatter(NumberFormat.getNumberInstance(Locale.US)));
        text2.setCounterFormatter(new CounterDecimalFormatter(new DecimalFormat("##.00")));

        return view;
    }
}
