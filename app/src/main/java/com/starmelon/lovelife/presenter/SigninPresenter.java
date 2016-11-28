package com.starmelon.lovelife.presenter;

import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.source.UserDataSource;
import com.starmelon.lovelife.data.source.UserRepository;
import com.starmelon.lovelife.data.source.local.UserLocalDataSource;
import com.starmelon.lovelife.data.source.remote.UserRemoteDataSource;


/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class SignInPresenter implements SignInContract.Presenter{

    private SignInContract.View mView;
    private UserRepository mUserRps;

    public SignInPresenter(SignInContract.View view){
        this.mView = view;
        this.mUserRps = UserRepository.getInstance(UserLocalDataSource.getInstance(), UserRemoteDataSource.getInstance());
    }


    @Override
    public void showSignStatus() {
        if (mUserRps.isUserExist()){
            mView.showSingOut();
        }else {
            mView.showSignIn();
        }
    }

    @Override
    public void signIn(String platform) {
        mUserRps.signIn(platform, new UserDataSource.SignInCallback() {
            @Override
            public void onSuccess(User user) {

                mView.signInSuccess();
            }

            @Override
            public void onError(String msg) {
                mView.signInError(msg);
            }
        });
    }

    @Override
    public void signOut() {
        mUserRps.signOut();
        mView.signOutSuccess();
    }
}
