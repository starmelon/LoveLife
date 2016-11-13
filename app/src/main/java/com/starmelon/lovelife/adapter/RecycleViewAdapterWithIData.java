package com.starmelon.lovelife.adapter;

import android.support.v7.widget.RecyclerView;

import com.shizhefei.mvc.IDataAdapter;

/**
 * 1.该抽象类继承自RecyclerView.Adapter，且允许定义ViewHolder
 * 2.该抽象类要求实现MVCHelper中的IDataAdapter
 * 3.该抽象类的目的在于提供一个继承限定和接口限定的类
 * Created by starmelon on 2016/11/9 0009.
 */

public abstract class RecycleViewAdapterWithIData<VH extends RecyclerView.ViewHolder,T> extends RecyclerView.Adapter<VH> implements IDataAdapter<T> {


}

