package com.starmelon.lovelife;

import android.animation.ObjectAnimator;
import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.starmelon.lovelife.db.local.GreenDaoManager;
import com.starmelon.lovelife.util.SPutils;
import com.starmelon.lovelife.util.volley.RequestManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

	public static Bitmap bitmap;
	public static ImageView cover;
	public static WindowManager.LayoutParams mNightViewParam;

	private static Context context; //定义全局Context

	private static WindowManager mWindowManager;


	private static Boolean isNight;

	private static ObjectAnimator oa;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();



		context = getApplicationContext();
		cover = new ImageView(context);
		mNightViewParam = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSPARENT);

//		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//		mWindowManager.addView(MyApplication.cover,MyApplication.mNightViewParam);
//		cover.setVisibility(View.GONE);
		oa = ObjectAnimator.ofFloat(MyApplication.cover, "alpha", 1f, 0f);
		oa.setDuration(600);

		//初始化请求管理器
		//RequestManager.init(this);


		//ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
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

		//读取夜间模式
		if ((Boolean) SPutils.get(getContext(),"NigthMode",false) == false){
			UiModeManager mUiModeManager = (UiModeManager) getContext().getSystemService(Context.UI_MODE_SERVICE);
			mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
		}else {
			UiModeManager mUiModeManager = (UiModeManager) getContext().getSystemService(Context.UI_MODE_SERVICE);
			mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
		}

		//初始化数据库
		GreenDaoManager.getInstance();

	}

	public static Context getContext(){
		return context;
	}

	public static void setCover(){
		oa.start();
	}

	public static void recreate(){

		WindowManager mWindowManager = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);

		WindowManager.LayoutParams mNightViewParam = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSPARENT);

		ImageView in = new ImageView(getContext());
		in.setImageResource(R.color.bg_blue);
		mWindowManager.addView(in,mNightViewParam);

		ObjectAnimator oa= ObjectAnimator.ofFloat(in, "alpha", 1f, 0f);
		oa.setDuration(3000);
		oa.start();

	}
}
