package com.starmelon.lovelife.presenter;

import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.source.UserRepository;
import com.starmelon.lovelife.data.source.local.UserLocalDataSource;
import com.starmelon.lovelife.data.source.remote.UserRemoteDataSource;

/**
 * Created by starmelon on 2016/11/24 0024.
 */

public class UserCenterPresenter extends BasePresenter<UserCenterContact.View> implements UserCenterContact.Presenter {

    private UserCenterContact.View mView;
    private UserRepository mUserRps;

    public UserCenterPresenter (){
        //this.mView = view;
        this.mUserRps = UserRepository.getInstance(UserLocalDataSource.getInstance(), UserRemoteDataSource.getInstance());
    }

    @Override
    public void showUser() {
        User user = mUserRps.getUser();
        if (user!= null){
            //getView().showUserSignIned(user);
            //mView.showUserSignIned(user);
        }else{
            //getView().showUserSignOuted();
            //mView.showUserSignOuted();
        }
    }
}
