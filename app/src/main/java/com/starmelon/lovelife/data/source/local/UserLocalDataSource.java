package com.starmelon.lovelife.data.source.local;

import com.starmelon.lovelife.data.source.UserDataSource;

import cn.sharesdk.framework.Platform;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class UserLocalDataSource implements UserDataSource {

    private static UserLocalDataSource INSTANCE;

    public static UserLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void signIn(String platform, SignInCallback callback) {

    }

    @Override
    public void signUp() {

    }
}
