package com.asiainfo.chart.activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.asiainfo.chart.bean.MonthBean;
import com.asiainfo.chart.chartexample.R;
import com.asiainfo.chart.fragment.PieFragment;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private final String mJson = "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]},{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"苹果\",\"value\":16},{\"title\":\"栗子\",\"value\":4},{\"title\":\"其他\",\"value\":6}]},{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"小木箱\",\"value\":23},{\"title\":\"小铁箱\",\"value\":24},{\"title\":\"其他\",\"value\":27}]}]";
    private ArrayList<MonthBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        mViewPager = (ViewPager) findViewById(R.id.piechart_vp);
        initData();
        initView();

    }

    public void initData() {
        Gson gson = new Gson();
        mData = gson.fromJson(mJson, new TypeToken<ArrayList<MonthBean>>() {
        }.getType());
    }

    private void initView() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
    }
}
