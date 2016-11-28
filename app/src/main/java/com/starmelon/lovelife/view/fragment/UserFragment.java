package com.starmelon.lovelife.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.presenter.UserCenterContact;
import com.starmelon.lovelife.presenter.UserCenterPresenter;
import com.starmelon.lovelife.util.ToastUtils;
import com.starmelon.lovelife.view.activity.AboutActivity;
import com.starmelon.lovelife.view.activity.LoginActivity;
import com.starmelon.lovelife.view.activity.MessageBookActivity;
import com.starmelon.switchbutton.SwitchView;



/**
 *
 */
public class UserFragment extends BaseFragment<UserCenterContact.View,UserCenterPresenter> implements UserCenterContact.View
{
	
	private RoundedImageView mBtn_login;
	private TextView mTv_nickname;

	//夜间模式
	private SwitchView mSwitch_daynight;

	//留言大厅
	private RelativeLayout mRl_messageBook;

	//关于
	private RelativeLayout mRl_about;


	@Override
	protected UserCenterPresenter createPresenter() {
		return new UserCenterPresenter();
	}

	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		Log.v("UserFragment","onCreateView");
		setContentView(R.layout.fragment_user);
		initView();

		mPresenter.showNightMode();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mPresenter == null) return;
		mPresenter.showUser();

	}


	private void initView() {


		mBtn_login = (RoundedImageView) findViewById(R.id.btn_login);
		mBtn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showSignInUi();
			}
		});

		mTv_nickname = (TextView) findViewById(R.id.tv_nickname);


		mSwitch_daynight = (SwitchView) findViewById(R.id.togglebtn_daynight);
		mSwitch_daynight.setColor(getResources().getColor(R.color.purple_blue), getResources().getColor(R.color.smoke_white));
		mSwitch_daynight.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
			@Override
			public void toggleToOn(SwitchView view) {
				mPresenter.switchNightMode(true);
			}

			@Override
			public void toggleToOff(SwitchView view) {
				mPresenter.switchNightMode(false);
			}

			@Override
			public void toggleComplete(Boolean isOpened) {

			}
		});

		mRl_messageBook = (RelativeLayout) findViewById(R.id.rl_messagebook);
		mRl_messageBook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mPresenter.showMessageBook();
			}
		});

		mRl_about = (RelativeLayout) findViewById(R.id.rl_about);
		mRl_about.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(),AboutActivity.class);
				startActivity(intent);
			}
		});
		
	}


	@Override
	public void showNightModeSwitchState(boolean isNight) {
		mSwitch_daynight.toggleSwitch(isNight);
	}

	@Override
	public void showSignInUi() {
		Intent intent = new Intent(getContext(), LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void showUserSignIned(User user) {

		if (user == null){
			return;
		}

		if (!TextUtils.isEmpty(user.getHeadPic()) ){
			Picasso.with(MyApplication.getContext()).load(user.getHeadPic()).into(mBtn_login);
		}

		if (!TextUtils.isEmpty(user.getNickname()) ){
			mTv_nickname.setText(user.getNickname());
		}


	}

	@Override
	public void showUserSignOuted() {

		mBtn_login.setImageResource(R.drawable.login_head_default);
		Picasso.with(this.getContext()).load(R.drawable.login_head_default).into(mBtn_login);
		mTv_nickname.setText("立即登录");

	}

	@Override
	public void showMessageBookActivity() {
		Intent intent = new Intent(getContext(),MessageBookActivity.class);
		startActivity(intent);
	}

	@Override
	public void showJoinMessageBookFail() {
		ToastUtils.show(getContext(),"请先登录");
	}

	@Override
	public void setPresenter(UserCenterContact.Presenter presenter) {

	}


}
