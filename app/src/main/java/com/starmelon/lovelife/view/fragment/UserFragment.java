package com.starmelon.lovelife.view.fragment;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.makeramen.roundedimageview.RoundedImageView;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.util.SPutils;
import com.starmelon.lovelife.util.WinUtils;
import com.starmelon.lovelife.view.activity.AboutActivity;
import com.starmelon.lovelife.view.activity.LoginActivity;
import com.zcw.togglebutton.ToggleButton;

/**
 *
 */
public class UserFragment extends Fragment
{
	
	private RoundedImageView mBtn_login;
	private ToggleButton mTogglebtn_daynight;

	private RelativeLayout mRl_about;

	//R.layout.fragment_user
	View view;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null){
			view = inflater.inflate(R.layout.fragment_user,container,false);
			initView();
		}

		return view;
	}

	private void initView() {

		mBtn_login = (RoundedImageView) view.findViewById(R.id.btn_login);
		mBtn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
		mTogglebtn_daynight = (ToggleButton) view.findViewById(R.id.togglebtn_daynight);


//		//切换
//		mTogglebtn_daynight.toggle();
//
//		//切换无动画
//		mTogglebtn_daynight.toggle(false);

//		mTogglebtn_daynight.setToggleOn();
//		mTogglebtn_daynight.setToggleOff();
//		//无动画切换
//		mTogglebtn_daynight.setToggleOn(false);
//		mTogglebtn_daynight.setToggleOff(false);
//
//		//禁用动画
//		mTogglebtn_daynight.setAnimate(false);

		if (MyApplication.bitmap != null){

			mTogglebtn_daynight.setOnToggleChanged(null);
			UiModeManager mUiModeManager = (UiModeManager) getContext().getSystemService(Context.UI_MODE_SERVICE);
			if (mUiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES){
				mTogglebtn_daynight.setToggleOn(true);
			}else{
				mTogglebtn_daynight.setToggleOn(false);
				mTogglebtn_daynight.setToggleOff(true);
			}

			MyApplication.bitmap = null;
		}else {
			Log.v("isNight",(Boolean) SPutils.get(getContext(),"NigthMode",false) + "");
			if ((Boolean) SPutils.get(getContext(),"NigthMode",false) == false){
				mTogglebtn_daynight.setToggleOff(false);
			}else {
				mTogglebtn_daynight.setToggleOn();
			}
		}

		//开关切换事件
		mTogglebtn_daynight.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
			@Override
			public void onToggle(boolean on) {
				Log.v("win","onToggle 触发");
				mTogglebtn_daynight.setOnToggleChanged(null);
				Log.v("Toggle",on + "");
				switchNightMode(on);
			}
		});


		mRl_about = (RelativeLayout) view.findViewById(R.id.rl_about);
		mRl_about.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(),AboutActivity.class);
				startActivity(intent);
			}
		});
		
	}



	/**
	 * 切换夜间模式
	 * @param on
	 */
	private void switchNightMode(boolean on){

		//缓存当前界面作为图片
		View v = this.getActivity().getWindow().getDecorView();
		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache();

		Context context = MyApplication.getContext();
		int statusBarHeight = WinUtils.getStatusHeight(context);
		int contentHeight = WinUtils.getContentHeight(context);
		int screenWidth = WinUtils.getScreenWidth(context);

		Bitmap curViewTemp = Bitmap.createBitmap(v.getDrawingCache(),0,statusBarHeight,screenWidth,contentHeight,null,false);

		MyApplication.bitmap = curViewTemp;


		if (on){

			UiModeManager mUiModeManager = (UiModeManager) getContext().getSystemService(Context.UI_MODE_SERVICE);
			mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
			SPutils.put(getContext(),"NigthMode",on);
		}else{

			UiModeManager mUiModeManager = (UiModeManager) getContext().getSystemService(Context.UI_MODE_SERVICE);
			mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
			SPutils.put(getContext(),"NigthMode",on);
		}

	}


	private void showAboutPopWin(){
//		// 一个自定义的布局，作为显示的内容
//		View contentView = LayoutInflater.from(getActivity()).inflate(
//				R.layout.popwin_about,null);
//
//		final PopupWindow popupWindow = new PopupWindow(contentView,
//				WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
//
//
//		popupWindow.setTouchable(true);
//
//		ColorDrawable dw = new ColorDrawable(0xb0000000);
//		popupWindow.setBackgroundDrawable(dw);
//
//		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//		// 我觉得这里是API的一个bug
////		popupWindow.setBackgroundDrawable();
////		popupWindow.setBackgroundDrawable(Color.TRANSPARENT);
//
//		// 设置好参数之后再show
//		popupWindow.showAtLocation(view,Gravity.CENTER,0,0 );


	}


}
