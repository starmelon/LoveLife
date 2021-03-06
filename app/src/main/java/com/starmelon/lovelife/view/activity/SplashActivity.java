package com.starmelon.lovelife.view.activity;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.starmelon.lovelife.AppContext;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.data.source.local.GreenDaoManager;
import com.starmelon.lovelife.presenter.BasePresenter;
import com.starmelon.lovelife.util.SPutils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;


public class SplashActivity extends BaseActivity {

	private final int showtime = 1500;//展示时间
	private boolean isInited = false;
	private Object lock;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 不显示标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setContentView(R.layout.activity_splash);

//		lock = new Object();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				initEnvir();
//			}
//		});

		//((MyApplication)getApplication()).inti();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();

			}

		}, 1500);


	}

	@Override
	protected BasePresenter createPresenter() {
		return null;
	}

	private void initEnvir() {

		//region 初始化OkHttp

		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				.addInterceptor(new LoggerInterceptor("TAG"))
				//.cookieJar(cookieJar1)
				.hostnameVerifier(new HostnameVerifier()
				{
					@Override
					public boolean verify(String hostname, SSLSession session)
					{
						return true;
					}
				})
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.build();
		OkHttpUtils.initClient(okHttpClient);

		//endregion

		//region 初始化ShareSDK

		ShareSDK.initSDK(AppContext.getInstance());

		//endregion

		//region 读取夜间模式

		if ((Boolean) SPutils.get(AppContext.getInstance(),"NigthMode",false) == false){
			UiModeManager mUiModeManager = (UiModeManager) AppContext.getInstance().getSystemService(Context.UI_MODE_SERVICE);
			mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
		}else {
			UiModeManager mUiModeManager = (UiModeManager) AppContext.getInstance().getSystemService(Context.UI_MODE_SERVICE);
			mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
		}

		//endregion

		//region 初始化GreenDao

		GreenDaoManager.getInstance();

		//endregion

		//region 初始化环信SDK

		initEasemob();

		//endregion
	}


	//region 环信初始化相关

	// 记录是否已经初始化
	private boolean isInit = false;

	/**
	 * 初始化环信
	 */
	private void initEasemob() {
		// 获取当前进程 id 并取得进程名
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		/**
		 * 如果app启用了远程的service，此application:onCreate会被调用2次
		 * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		 * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
		 */
		if (processAppName == null || !processAppName.equalsIgnoreCase(MyApplication.getContext().getPackageName())) {
			// 则此application的onCreate 是被service 调用的，直接返回
			return;
		}
		if (isInit) {
			return;
		}

		// 调用初始化方法初始化sdk
		EMClient.getInstance().init(MyApplication.getContext(), initOptions());

		// 设置开启debug模式
		EMClient.getInstance().setDebugMode(true);

		// 设置初始化已经完成
		isInit = true;
	}

	/**
	 * 环信SDK初始化的一些配置
	 * 关于 EMOptions 可以参考官方的 API 文档
	 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
	 */
	private EMOptions initOptions() {

		EMOptions options = new EMOptions();
		// 设置Appkey，如果配置文件已经配置，这里可以不用设置
		// options.setAppKey("lzan13#hxsdkdemo");
		// 设置自动登录
		options.setAutoLogin(true);
		// 设置是否需要发送已读回执
		options.setRequireAck(true);
		// 设置是否需要发送回执，
		options.setRequireDeliveryAck(true);
		// 设置是否需要服务器收到消息确认
		options.setRequireServerAck(true);
		// 设置是否根据服务器时间排序，默认是true
		options.setSortMessageByServerTime(false);
		// 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
		options.setAcceptInvitationAlways(false);
		// 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
		options.setAutoAcceptGroupInvitation(false);
		// 设置（主动或被动）退出群组时，是否删除群聊聊天记录
		options.setDeleteMessagesAsExitGroup(false);
		// 设置是否允许聊天室的Owner 离开并删除聊天室的会话
		options.allowChatroomOwnerLeave(true);
		// 设置google GCM推送id，国内可以不用设置
		// options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
		// 设置集成小米推送的appid和appkey
		// options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

		return options;
	}

	/**
	 * 根据Pid获取当前进程的名字，一般就是当前app的包名
	 *
	 * @param pid 进程的id
	 * @return 返回进程的名字
	 */
	private String getAppName(int pid) {
		String processName = null;
		ActivityManager activityManager = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		List list = activityManager.getRunningAppProcesses();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pid) {
					// 根据进程的信息获取当前进程的名字
					processName = info.processName;
					// 返回当前进程名
					return processName;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 没有匹配的项，返回为null
		return null;
	}

	//endregion

}
