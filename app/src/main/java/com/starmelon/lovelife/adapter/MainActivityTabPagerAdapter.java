package com.starmelon.lovelife.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.view.fragment.CollectionFragment;
import com.starmelon.lovelife.view.fragment.NewsFragment;
import com.starmelon.lovelife.view.fragment.UserFragment;

public class MainActivityTabPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private String[] tabNames = {"资讯", "收藏", "我"};
    private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector};
    private LayoutInflater inflater;

    public MainActivityTabPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        inflater = LayoutInflater.from(MyApplication.getContext());
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_main_tab, container, false);
        }

        LinearLayout ll = (LinearLayout)convertView;

        ImageView icon = (ImageView) convertView.findViewById(R.id.tab_main_icon);
        TextView text = (TextView) convertView.findViewById(R.id.tab_main_text);

        icon.setImageResource(tabIcons[position]);
        text.setText(tabNames[position]);

//        TextView textView = (TextView) convertView;
//        textView.setText(tabNames[position]);
//        Drawable icon = MyApplication.getContext().getResources().getDrawable(tabIcons[position]);
//        textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
        return ll;
    }

    @Override
    public Fragment getFragmentForPage(int position) {

        LazyFragment fragment = null;

        switch (position){
            case 0: {
                fragment = new NewsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(NewsFragment.INTENT_STRING_TABNAME, tabNames[position]);
                bundle.putInt(NewsFragment.INTENT_INT_INDEX, position);
                fragment.setArguments(bundle);
            }break;
            case 1: {
                fragment = new CollectionFragment();
                }
                break;
            case 2:
                return new UserFragment();
                //break;
            default:

                break;
        }

        return fragment;
    }
}
