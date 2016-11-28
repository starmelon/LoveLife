package com.starmelon.lovelife.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starmelon.lovelife.presenter.BasePresenter;
import com.starmelon.lovelife.util.BaseView;

/**
 * Created by starmelon on 2016/11/25 0025.
 */

public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    protected LayoutInflater inflater;
    private ViewGroup container;
    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建presenter
        mPresenter = createPresenter();
        //内存泄露
        //关联View
        if (mPresenter != null){
            mPresenter.attachView((V) this);
        }

    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (view == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }

    protected void onCreateView(Bundle savedInstanceState) {

    }

    public void setContentView(int layoutResID){
        view = inflater.inflate(layoutResID, container, false);
    }

    public View findViewById(int ResourceId){
        return view == null ? null : view.findViewById(ResourceId);
    }

    protected abstract  T createPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }

    }


}
