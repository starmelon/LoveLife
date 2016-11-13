package com.starmelon.lovelife.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.starmelon.lovelife.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends Activity {

	private EditText mEdt_id;
	private EditText mEdt_password;
	private Button mBtn_login;
	private ImageButton mBtn_weibo_login;
	private ImageButton mBtn_wechat_login;
	private ImageButton mBtn_qq_login;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		iniView();
		//initEvent();
		
//		Intent intent = getIntent();
//		int id = intent.getIntExtra("id", -1);
//		if (id != -1) {
//
//			getData(id);
//			
//		}else
//		{
//			Toast.makeText(this, "数据id出错", Toast.LENGTH_SHORT).show();
//		}
		
		
		
	}

	private void iniView() {
		mEdt_id = (EditText) findViewById(R.id.edt_id);
		mEdt_password = (EditText) findViewById(R.id.edt_password);
		mBtn_login = (Button) findViewById(R.id.btn_login);
		mBtn_weibo_login = (ImageButton) findViewById(R.id.btn_weibo_login);
		mBtn_wechat_login = (ImageButton) findViewById(R.id.btn_wechat_login);
		mBtn_qq_login = (ImageButton) findViewById(R.id.btn_qq_login);
		mBtn_qq_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginByQQ();
			}

			
		});
		
	}
	
	private void LoginByQQ() {
		Platform qq = ShareSDK.getPlatform(QQ.NAME);

		qq.SSOSetting(false);
		qq.setPlatformActionListener(new PlatformActionListener() {
			
			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		qq.showUser(null);
		//qq.authorize();
		
	
		String accessToken = qq.getDb().getToken(); // 获取授权token
		String openId = qq.getDb().get("uid"); // 获取用户在此平台的ID
		String nickname = qq.getDb().get("nickname");
		//String nickname = qq.getDb().get
		String headurl = qq.getDb().getUserIcon();

//		Log.v("getdb", qq.getDb().getToken());
//		Log.v("getdb", qq.getDb().getUserId());
//		Log.v("getdb", qq.getDb().getUserName());
//		Log.v("getdb", qq.getDb().get("uid"));
//		Log.v("getdb", qq.getDb().getUserGender());
//		Log.v("getdb", qq.getDb().getUserIcon());
//		Log.v("getdb", qq.getDb().get("nickname"));
		Log.v("getdb", qq.getDb().exportData());
		mEdt_id.setText(openId);
		
	}
	
	
	
	private void initEvent()
	{

	}
	
	



	

	
	
	
	
}
