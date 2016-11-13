package com.starmelon.lovelife.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.starmelon.lovelife.util.volley.RequestManager;

public class ViewHolder {

	private SparseArray<View> mView;
	private int mPosition;
	private ImageLoader mImageLoader;
	

	private View mConvertView;
	
	public ViewHolder(Context context, ViewGroup parent , int layoutId, int position){
		this.mPosition = position;
		this.mView = new SparseArray<>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		mImageLoader = RequestManager.getImageLoader();
		mConvertView.setTag(this);
	}
	
	
	public static ViewHolder get(Context context, View convertView , ViewGroup parent , int layoutId, int position){
		
		if(convertView == null){
			return new ViewHolder(context, parent, layoutId, position);
		}
		else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
		
		
	}
	
	
	public int getmPosition() {
		return mPosition;
	}
	
	/**
	 * 通过viewId获取控件
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view = mView.get(viewId);
		
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mView.put(viewId, view);
		}
		
		return (T)view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	
	
	/**
	 * 为TextView赋值
	 * @param viewId 
	 * @param text 
	 * @return
	 */
	public ViewHolder setText(int viewId, String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	/**
	 * 通过RID为ImageView赋值
	 * @param viewId
	 * @param resId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int resId){
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	/**
	 * 通过Bitmap为ImageView赋值
	 * @param viewId
	 * @param bitmap
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}
	
	/**
	 * 通过url为ImageView赋值
	 * @param viewId
	 * @param url
	 * @return
	 */
	public ViewHolder setImageURI(int viewId, String url){
		NetworkImageView view = getView(viewId);
		view.setTag(url); //标识，防止重复加载
		view.setImageUrl(url, mImageLoader);
		//new ImageLoader().showImageByThread(view, url);
		//ImageLoader.getInstance().showImageByAsyncTast(view,url);
		//Imageloader.getInstance().loadImag(view,url);
		return this;
	}
	
	/**
	 * 通过Drawable为ImageView赋值
	 * @param viewId
	 * @param drawable
	 * @return
	 */
	public ViewHolder setImageDrawable(int viewId, Drawable drawable){
		ImageView view = getView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}
}
