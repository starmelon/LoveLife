package com.starmelon.lovelife.view.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.adapter.MainActivityTabPagerAdapter;

public class MainActivity extends AppCompatActivity {

	private IndicatorViewPager indicatorViewPager;
	private FixedIndicatorView indicator;

	WindowManager mWindowManager;
	ImageView cover;
	ObjectAnimator oa;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);



		//切换夜间模式
		if (MyApplication.bitmap != null) {

			cover = new ImageView(this);
			cover.setImageBitmap(MyApplication.bitmap);
			oa = ObjectAnimator.ofFloat(cover, "alpha", 1f, 0f);
			oa.setDuration(600);
			if (mWindowManager == null) {
				Log.v("win", "WindowManager 被创建了");
				mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			}

			try {

				mWindowManager.addView(cover, MyApplication.mNightViewParam);
			} catch (Exception e) {
				this.finish();
				//e.printStackTrace();
			}
			oa.start();
		}



		SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
		indicator = (FixedIndicatorView) findViewById(R.id.tabmain_indicator);

		//获取自定义的 ?/attr 值
		TypedValue colorPressed = new TypedValue();
		TypedValue colorNormal = new TypedValue();
		this.getTheme().resolveAttribute(R.attr.tab_bottom_text_pressed, colorPressed, true);
		this.getTheme().resolveAttribute(R.attr.tab_bottom_text_normal, colorNormal, true);
		indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(getBaseContext(),colorPressed.resourceId, colorNormal.resourceId));

		//设置阴影
		ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {

				//outline.setOval(0, 0, view.getWidth(), view.getHeight());
				outline.setRect(new Rect(0, 0, view.getWidth(), view.getHeight() + 5));
			}
		};
		indicator.setOutlineProvider(viewOutlineProvider);

		indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
		indicatorViewPager.setAdapter(new MainActivityTabPagerAdapter(getSupportFragmentManager()));
		// 禁止viewpager的滑动事件
		viewPager.setCanScroll(false);
		// 设置viewpager保留界面不重新加载的页面数量
		viewPager.setOffscreenPageLimit(2);


		//ShareSDK.initSDK(this);

	}

	private class OnTransitionTextListener extends com.shizhefei.view.indicator.transition.OnTransitionTextListener{

		@Override
		public TextView getTextView(View tabItemView, int position) {
			TextView tv = (TextView) tabItemView.findViewById(R.id.tab_main_text);
			return tv;
		}
	}

	@Override
	protected void onPause() {
		if (mWindowManager != null){
			try {
				cover.setImageBitmap(null);
				mWindowManager.removeView(cover);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onPause();
	}

	@Override
	public void finish() {
//		ViewGroup view = (ViewGroup) getWindow().getDecorView();
//		view.removeAllViews();
		super.finish();
	}

	@Override
	protected void onDestroy() {
//		if (mWindowManager != null){
//			mWindowManager.removeView(MyApplication.cover);
//		}
		super.onDestroy();


	}

	//endregion



	//region 双击退出程序

	/**
	 * 以下为双击退出程序完整代码
	 */

	private long exitTime = 0;

	private void ExitApp() {

		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
			this.finish();
			System.exit(0);

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		ExitApp();
		return true;
	}

	//endregion
	
	
	
}
