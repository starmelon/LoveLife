package com.starmelon.lovelife.data.ifeng.news.source;

import android.support.annotation.Nullable;

import com.starmelon.lovelife.data.ifeng.news.Item;
import com.starmelon.lovelife.data.ifeng.news.News;

import java.util.List;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public interface NewsDataSource {

    interface getNewsListCallback {
        void onSuccess(@Nullable News news, List<Item> items);
        void onError(String msg);
    }

    void getNewestNewsList(String id,getNewsListCallback callback);
    void getNextPageNewsList(String id,int page,getNewsListCallback callback);
}
