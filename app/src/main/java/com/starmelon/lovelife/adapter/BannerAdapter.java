package com.starmelon.lovelife.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;

/**
 * Created by starmelon on 2016/11/9 0009.
 */

public class BannerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

    private int[] images = {R.drawable.banner, R.drawable.banner, R.drawable.banner, R.drawable.banner};

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = new View(container.getContext());
        }
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = new ImageView(MyApplication.getContext());
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        ImageView imageView = (ImageView) convertView;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(images[position]);
        return convertView;
    }

//        @Override
//        public int getItemPosition(Object object) {
//            return RecyclingPagerAdapter.POSITION_NONE;
//        }

    @Override
    public int getCount() {
        return images.length;
    }

}
