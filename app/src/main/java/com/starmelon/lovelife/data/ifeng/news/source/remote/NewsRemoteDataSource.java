package com.starmelon.lovelife.data.ifeng.news.source.remote;

import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.starmelon.lovelife.AppContext;
import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.ifeng.news.source.NewsDataSource;
import com.starmelon.lovelife.data.source.UserDataSource;
import com.starmelon.lovelife.util.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class NewsRemoteDataSource implements NewsDataSource{

    private static NewsRemoteDataSource INSTANCE;

    public static NewsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewsRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getNewestNewsList(String id, getNewsListCallback callback) {

    }

    @Override
    public void getNextPageNewsList(String id, int page, getNewsListCallback callback) {

    }
}
