package com.starmelon.lovelife.presenter;

import com.starmelon.lovelife.bean.NewsDetail;
import com.starmelon.lovelife.bean.enties.HotNews;
import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.util.BaseView;

/**
 * Created by starmelon on 2016/11/24 0024.
 */

public class NewsDetailContact {

    public interface View extends BaseView<Presenter> {

        void showLoading();
        void loadNewsDetailSucceed(NewsDetail newsDetail);
        void loadNewsDetailFailed(String error);
        void setCollectState(boolean isCollected);
        void showCollecResult(boolean isCollected);

        void shareNews(NewsDetail newsDetail);

    }

    public interface Presenter {

        void loadNewsDetail(int id);
        void reloadNewsDetail();
        void collectNews();
        void showCollectState();
        void shareNews();

    }

}
