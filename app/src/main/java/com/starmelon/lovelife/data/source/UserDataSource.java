package com.starmelon.lovelife.data.source;

import com.starmelon.lovelife.data.User;

import cn.sharesdk.framework.Platform;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public interface UserDataSource {

    interface SignInCallback {
        void onSuccess(User user);
        void onError(String msg);
    }

    void signIn (String platform,SignInCallback callback);
    void signUp ();
}
