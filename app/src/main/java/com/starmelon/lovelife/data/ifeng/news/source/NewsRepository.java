package com.starmelon.lovelife.data.ifeng.news.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.ifeng.news.Item;
import com.starmelon.lovelife.data.ifeng.news.News;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class NewsRepository implements NewsDataSource {

    private static NewsRepository INSTANCE = null;

    private final NewsDataSource mNewsRemoteDateSource;

    private final NewsDataSource mNewsLocalDataSource;

    private List<Item> mNewList;
    private int mCurrentPage;
    private int mTotalPage;

    private NewsRepository(@NonNull NewsDataSource newsLocalDataSource,
                           @NonNull NewsDataSource newsRemoteDataSource){
        mNewsRemoteDateSource = newsRemoteDataSource;
        mNewsLocalDataSource = newsLocalDataSource;
    }

    public static NewsRepository getInstance(NewsDataSource newsLocalDataSource,
                                             NewsDataSource newsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository(newsLocalDataSource, newsRemoteDataSource);

        }
        return INSTANCE;
    }


    @Override
    public void getNewestNewsList(String id, final getNewsListCallback callback) {
        mNewsRemoteDateSource.getNewestNewsList(id, new getNewsListCallback() {

            @Override
            public void onSuccess(News news,List<Item> newslist) {
                if (mNewList == null){
                    mNewList = new ArrayList<>();
                }

                List<Item> newest = new ArrayList<>();
                String theTopDocumentId = mNewList.get(0).getDocumentId();
                for (Item item : newslist){
                    if (!item.getDocumentId().equals(theTopDocumentId)){
                        newest.add(item);
                    }else{
                        break;
                    }
                }
                if (newest.size() == 0 ){
                    callback.onError("已经是最新数据");
                }else {
                    newest.addAll(mNewList);
                    mNewList = newest;
                    callback.onSuccess(null,mNewList);
                }

            }

            @Override
            public void onError(String msg) {
                callback.onError(msg);
            }
        });
    }

    @Override
    public void getNextPageNewsList(String id, @Nullable int page, final getNewsListCallback callback) {

        mNewsRemoteDateSource.getNextPageNewsList(id, this.mCurrentPage, new getNewsListCallback() {
            @Override
            public void onSuccess(News news,List<Item> newslist) {
                if (newslist != null && newslist.size() > 0 ){
                    mNewList.addAll(newslist);

                    callback.onSuccess(null,mNewList);
                }
            }

            @Override
            public void onError(String msg) {

            }
        });

    }
}
