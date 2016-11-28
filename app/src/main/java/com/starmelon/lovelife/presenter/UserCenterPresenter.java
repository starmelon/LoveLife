package com.starmelon.lovelife.presenter;

import android.app.UiModeManager;
import android.content.Context;

import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.source.UserRepository;
import com.starmelon.lovelife.data.source.local.UserLocalDataSource;
import com.starmelon.lovelife.data.source.remote.UserRemoteDataSource;
import com.starmelon.lovelife.util.SPutils;

/**
 * Created by starmelon on 2016/11/24 0024.
 */

public class UserCenterPresenter extends BasePresenter<UserCenterContact.View> implements UserCenterContact.Presenter {

    private UserRepository mUserRps;

    public UserCenterPresenter (){
        //this.mView = view;
        this.mUserRps = UserRepository.getInstance(UserLocalDataSource.getInstance(), UserRemoteDataSource.getInstance());
    }

    @Override
    public void showNightMode() {
        getView().showNightModeSwitchState((Boolean) SPutils.get(MyApplication.getContext(),"NigthMode",false));

    }

    @Override
    public void showUser() {
        User user = mUserRps.getUser();
        if (user!= null){
            getView().showUserSignIned(user);
            //mView.showUserSignIned(user);
        }else{
            getView().showUserSignOuted();
            //mView.showUserSignOuted();
        }
    }

    @Override
    public void switchNightMode(boolean isNight) {
        UiModeManager mUiModeManager = (UiModeManager) MyApplication.getContext().getSystemService(Context.UI_MODE_SERVICE);
        mUiModeManager.setNightMode(isNight == true ? UiModeManager.MODE_NIGHT_YES : UiModeManager.MODE_NIGHT_NO);
        SPutils.put(MyApplication.getContext(),"NigthMode",isNight);
        getView().showNightModeSwitchState(isNight);
    }

    @Override
    public void showMessageBook() {
        User user = UserRepository.getInstance(null,null).getUser();
        if (user == null){
            getView().showJoinMessageBookFail();
        }else{
            getView().showMessageBookActivity();
        }
    }
}
