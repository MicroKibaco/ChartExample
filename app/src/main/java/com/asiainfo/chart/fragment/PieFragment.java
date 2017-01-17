package com.asiainfo.chart.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asiainfo.chart.bean.MonthBean;
import com.asiainfo.chart.chartexample.R;
import com.asiainfo.chart.view.PieChartExample;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * 饼状图雏形
 */

public class PieFragment extends Fragment implements OnChartValueSelectedListener {

    private static String DATA_KEY = PieFragment.class.getSimpleName();
    private MonthBean mData;
    private PieChartExample mChart;
    private TextView tvDes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflter, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflter.inflate(R.layout.fragment_pie, null);
        mChart = (PieChartExample) inflate.findViewById(R.id.pc_chart);
        tvDes = (TextView) inflate.findViewById(R.id.tv_des);
        initView();
        return inflate;
    }

    private void initView() {
      setData();
    }

    private void setData() {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Entry> entrys = new ArrayList<>();

        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            titles.add(pieBean.title);
            entrys.add(new Entry(pieBean.value,i));
        }

        PieDataSet dataSet = new PieDataSet(entrys,"月度数据饼形图");
        dataSet.setColors(new int[]{Color.rgb(216, 77, 719), Color.rgb(183, 56, 63), Color.rgb(247, 85, 47)});
        PieData pieData = new PieData(titles,dataSet);
        pieData.setValueTextSize(22);
        mChart.setData(pieData);
        mChart.getLegend().setEnabled(false);
        mChart.setCenterText(handleCenterText());
        mChart.setRotationEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawSliceText(false);
        mChart.getData().getDataSet().setDrawValues(false);
        mChart.setDescription("");

    }

    public CharSequence handleCenterText() {
        String expendSum = "总支出\n" + mData.getSum() + "元";
        SpannableString spannedString = new SpannableString(expendSum);
        spannedString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannedString.setSpan(new AbsoluteSizeSpan(64,true),3,expendSum.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return expendSum;
    }

    public static PieFragment newInstance(MonthBean data) {

        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void upDateDesText(float xIndex) {
        tvDes.setText(mData.obj.get((int) xIndex).title + ": " + mData.obj.get((int) xIndex).value);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        float percent = 360f / mData.getSum();//百分比
        float angle = 90-mData.obj.get( e.getXIndex()).value*percent/2-(int) mData.getSum()*percent;//角度
        mChart.setRotationAngle(angle);
        upDateDesText(e.getXIndex());
    }

    @Override
    public void onNothingSelected() {

    }
}
