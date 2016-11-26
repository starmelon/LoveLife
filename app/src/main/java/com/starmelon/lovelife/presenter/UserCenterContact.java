package com.starmelon.lovelife.presenter;

import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.util.BaseView;

/**
 * Created by starmelon on 2016/11/24 0024.
 */

public class UserCenterContact {

    public interface View extends BaseView<Presenter> {

        void showSignInUi();
        void showUserSignIned(User user);
        void showUserSignOuted();
    }

    public interface Presenter {

        void showUser();
    }

}
