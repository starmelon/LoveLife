package com.starmelon.lovelife.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.makeramen.roundedimageview.RoundedImageView;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.bean.User;

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
	private RoundedImageView mBtn_qq_login;
	
	
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
		mBtn_qq_login = (RoundedImageView) findViewById(R.id.btn_qq_login);
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
		String openId = qq.getDb().getUserId(); // 获取用户在此平台的ID
		String nickname = qq.getDb().getUserName();
		String headurl = qq.getDb().getUserIcon();
		//默认获取的为40*40的小头像
		if(!headurl.endsWith("/240")){
			headurl = headurl.substring(0,headurl.length()-3) + "/240";
		}

		Log.v("getdb", qq.getDb().getUserId());
		Log.v("getdb", qq.getDb().getUserName());
		Log.v("getdb", qq.getDb().getUserIcon());
		Log.v("getdb", qq.getDb().exportData());
		mEdt_id.setText(openId);

		User user = new User(nickname,openId,headurl);

		signIn2easemob(user);

	}


	/**
	 * 通过环信注册
	 * @param user
	 */
	private void signUp2easemob(final User user){

		final ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setMessage("注册中，请稍后...");
		mDialog.show();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					EMClient.getInstance().createAccount(user.getPwb(), user.getPwb());
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!LoginActivity.this.isFinishing()) {
								mDialog.dismiss();
							}
							Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_LONG).show();
							signIn2easemob(user);

						}
					});
				} catch (final HyphenateException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!LoginActivity.this.isFinishing()) {
								mDialog.dismiss();
							}
							/**
							 * 关于错误码可以参考官方api详细说明
							 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
							 */
							int errorCode = e.getErrorCode();
							String message = e.getMessage();
							Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
							switch (errorCode) {
								// 网络错误
								case EMError.NETWORK_ERROR:
									Toast.makeText(LoginActivity.this, "网络错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
									break;
								// 用户已存在
								case EMError.USER_ALREADY_EXIST:
									Toast.makeText(LoginActivity.this, "用户已存在 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
									break;
								// 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
								case EMError.USER_ILLEGAL_ARGUMENT:
									Toast.makeText(LoginActivity.this, "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
									break;
								// 服务器未知错误
								case EMError.SERVER_UNKNOWN_ERROR:
									Toast.makeText(LoginActivity.this, "服务器未知错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
									break;
								case EMError.USER_REG_FAILED:
									Toast.makeText(LoginActivity.this, "账户注册失败 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
									break;
								default:
									Toast.makeText(LoginActivity.this, "ml_sign_up_failed code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
									break;
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	/**
	 * 登录方法
	 */
	private void signIn2easemob(final User user) {

		final ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setMessage("正在登陆，请稍后...");
		mDialog.show();

		if (TextUtils.isEmpty(user.getPwb()) || TextUtils.isEmpty(user.getPwb())) {
			Toast.makeText(LoginActivity.this, "登录信息获取失败", Toast.LENGTH_LONG).show();
			return;
		}
		EMClient.getInstance().login(user.getPwb(), user.getPwb(), new EMCallBack() {
			/**
			 * 登陆成功的回调
			 */
			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mDialog.dismiss();

						// 加载所有会话到内存
						EMClient.getInstance().chatManager().loadAllConversations();
						// 加载所有群组到内存，如果使用了群组的话
						// EMClient.getInstance().groupManager().loadAllGroups();

						// 登录成功跳转界面
//						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//						startActivity(intent);
						setResult(1);
						MyApplication.setUser(user);
						finish();

					}
				});
			}

			/**
			 * 登陆错误的回调
			 * @param i
			 * @param s
			 */
			@Override
			public void onError(final int i, final String s) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mDialog.dismiss();
						Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
						/**
						 * 关于错误码可以参考官方api详细说明
						 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
						 */
						switch (i) {
							// 网络异常 2
							case EMError.NETWORK_ERROR:
								Toast.makeText(LoginActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 无效的用户名 101
							case EMError.INVALID_USER_NAME:
								Toast.makeText(LoginActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 无效的密码 102
							case EMError.INVALID_PASSWORD:
								Toast.makeText(LoginActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 用户认证失败，用户名或密码错误 202
							case EMError.USER_AUTHENTICATION_FAILED:
								Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 用户不存在 204
							case EMError.USER_NOT_FOUND:
								signUp2easemob(user);
								//Toast.makeText(LoginActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 无法访问到服务器 300
							case EMError.SERVER_NOT_REACHABLE:
								Toast.makeText(LoginActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 等待服务器响应超时 301
							case EMError.SERVER_TIMEOUT:
								Toast.makeText(LoginActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 服务器繁忙 302
							case EMError.SERVER_BUSY:
								Toast.makeText(LoginActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							// 未知 Server 异常 303 一般断网会出现这个错误
							case EMError.SERVER_UNKNOWN_ERROR:
								Toast.makeText(LoginActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
							default:
								Toast.makeText(LoginActivity.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
								break;
						}
					}
				});
			}


			@Override
			public void onProgress(int i, String s) {

			}
		});
	}

	
	
	
	
}
