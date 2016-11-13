package com.starmelon.lovelife.adapter;

import android.support.v7.widget.RecyclerView;

import com.shizhefei.mvc.IDataAdapter;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;


/**
 * 1.在MVCHelper库中，设置的Adapter要求实现IDataAdapter接口
 * 2.为了让RecyclerView使用RecyclerViewItemAnimators库中提供的动画，
 *   扩展实现了一个支持泛型的AlphaAnimatorAdapter并实现IDataAdapter接口
 * 3.并使用RecycleViewAdapterWithIData对构造函数中传入的类做类型限定和接口限定
 */
public class AlphaAnimatorAdapterWithIData<T> extends AlphaAnimatorAdapter implements IDataAdapter<T> {

	RecycleViewAdapterWithIData mNewsListViewAdapter;

	public AlphaAnimatorAdapterWithIData(RecycleViewAdapterWithIData newsListViewAdapter, RecyclerView recyclerView) {
		super(newsListViewAdapter, recyclerView);
		this.mNewsListViewAdapter = newsListViewAdapter;
	}


	@Override
	public void notifyDataChanged(T data, boolean isRefresh) {

		mNewsListViewAdapter.notifyDataChanged(data,isRefresh);

	}

	@Override
	public T getData() {
		return (T) mNewsListViewAdapter.getData();
	}

	@Override
	public boolean isEmpty() {
		return mNewsListViewAdapter.isEmpty();
	}

}
