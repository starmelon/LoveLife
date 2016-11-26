package com.starmelon.lovelife.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by starmelon on 2016/11/25 0025.
 */

public abstract class BasePresenter<T>{

    //当内存不足，释放内存
    protected WeakReference<T> mViewReference;

    //进行关联
    public void attachView(T view) {
        mViewReference = new WeakReference<T>(view);
    }

    //解除关联
    public void detachView() {
        if(mViewReference != null){
            mViewReference.clear();
            mViewReference = null;
        }
    }

    protected T getView() {

        return mViewReference.get();

    }
}
