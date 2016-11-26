package com.starmelon.lovelife.data.source;

import android.support.annotation.NonNull;

import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.source.remote.UserRemoteDataSource;

import cn.sharesdk.framework.Platform;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;

    private final UserDataSource mUserRemoteDateSource;

    private final UserDataSource mUserLocalDataSource;

    private User mUser;

    private UserRepository(@NonNull UserDataSource userLocalDataSource,
                           @NonNull UserDataSource userRemoteDataSource){
        mUserRemoteDateSource = userRemoteDataSource;
        mUserLocalDataSource = userLocalDataSource;
    }

    public static UserRepository getInstance(UserDataSource userLocalDataSource,
                                             UserDataSource userRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(userLocalDataSource, userRemoteDataSource);
        }
        return INSTANCE;
    }

    public User getUser(){
        return mUser;
    }

    public boolean isUserExist(){
        return mUser == null ? false : true;
    }

    public void clearUser(){
        mUser = null;
    }

    @Override
    public void signIn(String platform, final SignInCallback callback) {
        mUserRemoteDateSource.signIn(platform, new SignInCallback() {
            @Override
            public void onSuccess(User user) {
                mUser = user;
                callback.onSuccess(user);
            }

            @Override
            public void onError(String msg) {
                callback.onError(msg);
            }
        });
    }

    @Override
    public void signUp() {

    }
}
