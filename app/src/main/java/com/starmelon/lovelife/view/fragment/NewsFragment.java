package com.starmelon.lovelife.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.adapter.NewsFragmentViewPagerAdapter;

public class NewsFragment extends LazyFragment
{
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    private String tabName;
    private int index;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);


        setContentView(R.layout.fragment_tab_news);


        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        viewPager.setOffscreenPageLimit(4);

        Indicator indicator = (Indicator) findViewById(R.id.fragment_tabmain_indicator);

        //设置tab下标
        indicator.setScrollBar(new ColorBar(getApplicationContext(),getResources().getColor(R.color.tab_top_scrollbar),5));

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.2f;
        int selectColor = getResources().getColor(R.color.tab_top_text_2);
        int unSelectColor = getResources().getColor(R.color.tab_top_text_1);

        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));



        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new NewsFragmentViewPagerAdapter(getChildFragmentManager()));

        inflate = LayoutInflater.from(getActivity());

//         注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
//         而在activity里面用FragmentManager 是 getSupportFragmentManager()


        Log.d("cccc", "Fragment 将要创建View " + this);

    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        Log.d("cccc", "Fragment所在的Activity onResume, onResumeLazy " + this);
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        Log.d("cccc", "Fragment 显示 " + this);
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Log.d("cccc", "Fragment 掩藏 " + this);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        Log.d("cccc", "Fragment所在的Activity onPause, onPauseLazy " + this);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        Log.d("cccc", "Fragment View将被销毁 " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }


}
