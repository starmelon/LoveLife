package com.starmelon.lovelife.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.presenter.BasePresenter;
import com.starmelon.lovelife.presenter.SignInContract;
import com.starmelon.lovelife.presenter.SignInPresenter;
import com.starmelon.lovelife.util.ToastUtils;

import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends BaseActivity implements SignInContract.View{



	private TextView mTv_desc;

	private RelativeLayout mRl_signin;
	private RoundedImageView mBtn_weibo_login;
	private RoundedImageView mBtn_wechat_login;
	private RoundedImageView mBtn_qq_login;

	private Button mBtn_signout;

	private SignInContract.Presenter mPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		iniView();

		//MVP
		this.mPresenter = new SignInPresenter(this);

	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	private void iniView() {

		mTv_desc = (TextView) findViewById(R.id.tv_desc);

		mRl_signin = (RelativeLayout) findViewById(R.id.rl_login);
		mBtn_weibo_login = (RoundedImageView) findViewById(R.id.btn_weibo_login);
		mBtn_weibo_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ToastUtils.show(LoginActivity.this,"暂未开放");
			}
		});
		mBtn_wechat_login = (RoundedImageView) findViewById(R.id.btn_wechat_login);
		mBtn_wechat_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ToastUtils.show(LoginActivity.this,"暂未开放");
			}
		});
		mBtn_qq_login = (RoundedImageView) findViewById(R.id.btn_qq_login);
		mBtn_qq_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPresenter.signIn(QQ.NAME);
			}

			
		});

		mBtn_signout = (Button) findViewById(R.id.btn_signup);
		mBtn_signout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mPresenter.signOut();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		mPresenter.showSignStatus();
	}


	@Override
	public void showSignIn() {
		mRl_signin.setVisibility(View.VISIBLE);
		mBtn_signout.setVisibility(View.GONE);
		mTv_desc.setVisibility(View.VISIBLE);

	}

	@Override
	public void showSingOut() {
		mRl_signin.setVisibility(View.GONE);
		mBtn_signout.setVisibility(View.VISIBLE);
		mTv_desc.setVisibility(View.GONE);
	}

	@Override
	public void signInSuccess() {

		finish();
	}

	@Override
	public void signInError(String str) {
		ToastUtils.show(this,str);
	}

	@Override
	public void signOutSuccess() {
		finish();
	}


	@Override
	public void setPresenter(SignInContract.Presenter presenter) {

	}
}
