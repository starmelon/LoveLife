package com.starmelon.lovelife.presenter;

import android.view.View;

import com.starmelon.lovelife.util.BaseView;

import cn.sharesdk.framework.Platform;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class SignInContract {

    public interface View extends BaseView<Presenter> {

        void showSignIn();
        void showSingOut();
        void signInSuccess();
        void signInError(String str);
        void signOutSuccess();
    }

    public interface Presenter {

        void showSignStatus();
        void signIn(String platform);
        void signOut();
    }
}
