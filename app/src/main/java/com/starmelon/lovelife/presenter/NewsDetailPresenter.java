package com.starmelon.lovelife.presenter;

import com.starmelon.lovelife.bean.NewsDetail;
import com.starmelon.lovelife.bean.enties.Collection;
import com.starmelon.lovelife.db.local.CollectionDaoLHelper;
import com.starmelon.lovelife.db.net.API;
import com.starmelon.lovelife.db.net.ApiManager;
import com.starmelon.lovelife.db.net.GenericsCallback;
import com.starmelon.lovelife.db.net.JsonGenericsSerializator;

import okhttp3.Call;

/**
 * Created by starmelon on 2016/11/27 0027.
 */

public class NewsDetailPresenter extends BasePresenter<NewsDetailContact.View> implements NewsDetailContact.Presenter {



    private boolean isCollected;
    private int mNewsId = -1;
    private NewsDetail mNewsDetail;



    @Override
    public void loadNewsDetail(int id) {

        if (mNewsId == -1){
            mNewsId = id;
        }

        getNewsDetail();


    }

    @Override
    public void reloadNewsDetail() {
        getView().showLoading();
        getNewsDetail();
    }

    private void getNewsDetail() {
        ApiManager.getNewsDetailById(new GenericsCallback<NewsDetail>(new JsonGenericsSerializator()) {

            @Override
            public void onError(Call call, Exception e, int id) {
                getView().loadNewsDetailFailed("数据获取失败");
            }

            @Override
            public void onResponse(NewsDetail response, int id) {
                mNewsDetail = response;
                mNewsDetail.img = API.API_IMAGE + mNewsDetail.img;
                getView().loadNewsDetailSucceed(response);
            }
        },mNewsId);
    }



    @Override
    public void collectNews() {

        if (mNewsDetail != null){

            if (isCollected == false){
                new CollectionDaoLHelper().addCollection(mNewsDetail.id,mNewsDetail.title);
                isCollected = true;
            }else {
                new CollectionDaoLHelper().deleteCollecion(mNewsId);
                isCollected = false;
            }
            getView().showCollecResult(isCollected);
            getView().setCollectState(isCollected);

        }

    }

    @Override
    public void showCollectState() {
        Collection collection = new CollectionDaoLHelper().getCollecionByID(mNewsId);
        isCollected = collection == null ? false : true;
        getView().setCollectState(isCollected);
    }

    @Override
    public void shareNews() {
        getView().shareNews(mNewsDetail);
    }
}
