package com.starmelon.lovelife.view.activity;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.starmelon.lovelife.ActivityManager;
import com.starmelon.lovelife.presenter.BasePresenter;

/**
 * 1.该基类解决了Activity间跳转动画失效的问题
 * Created by starmelon on 2016/11/21 0021.
 */

public abstract  class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAnime();

        //创建presenter
        mPresenter = createPresenter();

        if (mPresenter != null){
            mPresenter.attachView((V) this);
        }


        ActivityManager.addActivity(this);
}

    //初始化进入和退出动画
    private void initAnime() {


        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});

        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);

        activityStyle.recycle();

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

        activityStyle.recycle();
    }


    @Override
    public void finish() {
        super.finish();
        ActivityManager.finishActivity(this);
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    protected abstract  T createPresenter();

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }

    }

}
