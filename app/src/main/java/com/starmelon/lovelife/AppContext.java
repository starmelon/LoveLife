package com.starmelon.lovelife;

import android.app.Application;

/**
 * Created by starmelon on 2016/11/23 0023.
 */
public class AppContext extends Application{

    private static AppContext appContext = null;

    public static AppContext getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        //捕获异常
    }


}
