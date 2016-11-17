package com.starmelon.lovelife.view.fragment;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
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
	private TextView mTv_nickname;

	private ToggleButton mTogglebtn_daynight;

	private RelativeLayout mRl_about;

	//R.layout.fragment_user
	View view;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null){
			view = inflater.inflate(R.layout.fragment_user,container,false);
			initView();
			setUserInfo();
		}

		return view;
	}

	private void initView() {

		mBtn_login = (RoundedImageView) view.findViewById(R.id.btn_login);
		mBtn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), LoginActivity.class);
				startActivityForResult(intent,1);
				//startActivity(intent);
			}
		});

		mTv_nickname = (TextView) view.findViewById(R.id.tv_nickname);

		mTogglebtn_daynight = (ToggleButton) view.findViewById(R.id.togglebtn_daynight);


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



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == 1){
			setUserInfo();
		}

	}


	private void setUserInfo(){
		if (MyApplication.getUser() == null){
			return;
		}

		if (TextUtils.isEmpty(MyApplication.getUser().getHeadPic()) ){
			return;
		}

		Picasso.with(MyApplication.getContext()).load(MyApplication.getUser().getHeadPic()).into(mBtn_login);

		if (TextUtils.isEmpty(MyApplication.getUser().getNickname()) ){
			return;
		}
		mTv_nickname.setText(MyApplication.getUser().getNickname());

	}
}
