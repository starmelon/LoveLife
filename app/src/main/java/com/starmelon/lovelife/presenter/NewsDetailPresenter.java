package com.starmelon.lovelife.presenter;

import android.util.Log;

import com.starmelon.lovelife.data.Collection;
import com.starmelon.lovelife.data.ifeng.newsdetail.NewsDetail;
import com.starmelon.lovelife.data.source.CollectionDataSource;
import com.starmelon.lovelife.data.source.CollectionRepository;
import com.starmelon.lovelife.data.source.local.CollectionLocalDataSource;
import com.starmelon.lovelife.util.net.ApiManager;
import com.starmelon.lovelife.util.net.GenericsCallback;
import com.starmelon.lovelife.util.net.JsonGenericsSerializator;

import java.util.Date;

import okhttp3.Call;

/**
 * Created by starmelon on 2016/11/27 0027.
 */

public class NewsDetailPresenter extends BasePresenter<NewsDetailContact.View> implements NewsDetailContact.Presenter {


    private final CollectionRepository mCollectionRepository;

    private boolean isCollected;
    //private int mNewsId = -1;
    private String mNewsId;
    private NewsDetail mNewsDetail;

    public NewsDetailPresenter() {
        mCollectionRepository = CollectionRepository.getInstance(CollectionLocalDataSource.getInstance());
    }

    @Override
    public void loadNewsDetail(String id) {

        if (mNewsId == null){
            mNewsId = id;
        }

        getNewsDetail();


    }

//    @Override
//    public void loadNewsDetail(int id) {
//
//        if (mNewsId == -1){
//            mNewsId = id;
//        }
//
//        getNewsDetail();
//
//
//    }

    @Override
    public void reloadNewsDetail() {
        getView().showLoading();
        getNewsDetail();
    }

    private void getNewsDetail() {
        ApiManager.getIfengNewsDetailById(new GenericsCallback<NewsDetail>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                getView().loadNewsDetailFailed("数据获取失败");
            }

            @Override
            public void onResponse(NewsDetail response, int id) {

                mNewsDetail = response;

                Log.v("clc",response.getBody().getText() + "");
                getView().loadNewsDetailSucceed(response);
                //mNewsDetail.img = API.API_IMAGE + mNewsDetail.img;

            }
        },mNewsId);


    }



    @Override
    public void collectNews() {

        if (mNewsDetail != null){

            if (isCollected == false){
                long time = new Date().getTime();
                Collection collection = new Collection(null,mNewsId,mNewsDetail.getBody().getTitle(),time);
                mCollectionRepository.addCollection(collection);
                //new CollectionDaoLHelper().addCollection(mNewsId,mNewsDetail.getBody().getTitle());
                isCollected = true;
            }else {
                mCollectionRepository.deleteCollection(mNewsId);
                //new CollectionDaoLHelper().deleteCollecion(mNewsId);
                isCollected = false;
            }
            getView().showCollecResult(isCollected);
            getView().setCollectState(isCollected);

        }

    }

    @Override
    public void showCollectState() {
        mCollectionRepository.getCollectionById(mNewsId, new CollectionDataSource.GetCollectionCallBack() {
            @Override
            public void onCollectionLoaded(Collection collection) {
                isCollected = collection == null ? false : true;
                getView().setCollectState(isCollected);
            }

            @Override
            public void onDataNotAvailable() {
                isCollected = false;
            }
        });
        //Collection collection = new CollectionDaoLHelper().getCollecionByID(mNewsId);


    }

    @Override
    public void shareNews() {
        getView().shareNews(mNewsDetail);
    }
}
