package com.asiainfo.chart.activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.asiainfo.chart.bean.MonthBean;
import com.asiainfo.chart.chartexample.R;
import com.asiainfo.chart.fragment.PieFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PieChartActivity extends AppCompatActivity {

    @BindView(R.id.piechart_vp)
    ViewPager mPiechartVp;
    @BindView(R.id.pre_btn)
    Button mPreBtn;
    @BindView(R.id.next_btn)
    Button mNextBtn;
    private ViewPager mViewPager;
    private final String mJson = "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]},{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"苹果\",\"value\":16},{\"title\":\"栗子\",\"value\":4},{\"title\":\"其他\",\"value\":6}]},{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"小木箱\",\"value\":23},{\"title\":\"小铁箱\",\"value\":24},{\"title\":\"其他\",\"value\":27}]}]";
    private ArrayList<MonthBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        ButterKnife.bind(this);
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
        upDateJumpText();
    }

    private void upDateJumpText() {
        CharSequence jumpNextText = mPiechartVp.getCurrentItem() != mPiechartVp.getAdapter().getCount() - 1 ? handleText(mData.get((mPiechartVp.getCurrentItem() + 1)).date) : "没有啦!";
        CharSequence jumpPreText = mPiechartVp.getCurrentItem() != 0 ? handleText(mData.get((mPiechartVp.getCurrentItem() - 1)).date) : "没有啦!";
        mNextBtn.setText(jumpNextText);
        mPreBtn.setText(jumpPreText);
    }

    private CharSequence handleText(String date) {
        return date.substring(date.indexOf("年") + 1);
    }

    @OnClick({R.id.piechart_vp, R.id.pre_btn, R.id.next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pre_btn:
                if (mViewPager.getCurrentItem() != 0) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
                break;
            case R.id.next_btn:
                if (mViewPager.getCurrentItem() != mPiechartVp.getAdapter().getCount()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                break;
        }
        upDateJumpText();
    }
}
