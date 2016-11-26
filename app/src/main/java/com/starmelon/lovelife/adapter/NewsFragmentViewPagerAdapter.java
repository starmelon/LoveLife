package com.starmelon.lovelife.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.starmelon.lovelife.ActivityManager;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.view.fragment.CollectionFragment;
import com.starmelon.lovelife.view.fragment.NewsViewPagerFragment;

public class NewsFragmentViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {


    private LayoutInflater inflater;
    private String[] tabNames = {"民生", "娱乐", "财经", "体育", "教育" ,"社会"};

    public NewsFragmentViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {

        if (inflater == null){
            inflater = LayoutInflater.from(MyApplication.getContext());
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(tabNames[position]);
        return convertView;

    }

    @Override
    public Fragment getFragmentForPage(int position) {
        NewsViewPagerFragment mainFragment = new NewsViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NewsViewPagerFragment.INTENT_STRING_TABNAME, tabNames[position]);
        bundle.putInt(NewsViewPagerFragment.INTENT_INT_POSITION, position);
        mainFragment.setArguments(bundle);
        return mainFragment;

//        return new CollectionFragment();
    }
}
