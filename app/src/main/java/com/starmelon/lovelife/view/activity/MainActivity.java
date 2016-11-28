package com.starmelon.lovelife.view.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starmelon.lovelife.R;
import com.starmelon.lovelife.util.ToastUtils;
import com.starmelon.lovelife.util.WinUtils;
import com.starmelon.lovelife.view.fragment.CollectionFragment;
import com.starmelon.lovelife.view.fragment.NewsFragment;
import com.starmelon.lovelife.view.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

	private NewsFragment mNewsFragment;
	private CollectionFragment mCollectionFragment;
	private UserFragment mUserFragment;

	private LinearLayout mTabBottom;

	/**
	 * 底部tab
	 */
	private LinearLayout mTabNews;
	private ImageView mTabNewsImg;
	private TextView mTabNewsTxt;
	private LinearLayout mTabColletion;
	private ImageView mTabColletionImg;
	private TextView mTabColletionTxt;
	private LinearLayout mTabUser;
	private ImageView mTabUserImg;
	private TextView mTabUserTxt;

	private int mTabTxtColorPressed;
	private int mTabTxtColorNormal;

	private int curTabIndex = 0;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initViews();

		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null){

			FragmentTransaction transition = fragmentManager.beginTransaction();
			mNewsFragment = new NewsFragment();
			mCollectionFragment = new CollectionFragment();
			mUserFragment = new UserFragment();
			transition.add(R.id.id_content,mNewsFragment,"mNewsFragment");
			transition.add(R.id.id_content,mCollectionFragment,"mCollectionFragment");
			transition.add(R.id.id_content,mUserFragment,"mUserFragment");
			transition.commit();

			setTabSelection(0);


		}else{

			SwitchNightMode(savedInstanceState);

			//某些情况下Activity重启时，fragmentManager中的fragment并不会被立即释放
			//此处的情况主要为使用UiModeManager设置夜间模式产生的重启问题

			mNewsFragment = (NewsFragment) fragmentManager.findFragmentByTag("mNewsFragment");
			mCollectionFragment = (CollectionFragment) fragmentManager.findFragmentByTag("mCollectionFragment");
			mUserFragment = (UserFragment) fragmentManager.findFragmentByTag("mUserFragment");

			curTabIndex = savedInstanceState.getInt("curTabIndex");
			setTabSelection(curTabIndex);
		}



	}

	private void initViews() {


		mTabNews = (LinearLayout) findViewById(R.id.main_tab_news);
		mTabNews.setOnClickListener(tabClickListener);
		mTabNewsImg = (ImageView) findViewById(R.id.main_tab_news_img);
		mTabNewsTxt = (TextView) findViewById(R.id.main_tab_news_txt);

		mTabColletion = (LinearLayout) findViewById(R.id.main_tab_collection);
		mTabColletion.setOnClickListener(tabClickListener);
		mTabColletionImg = (ImageView) findViewById(R.id.main_tab_collection_img);
		mTabColletionTxt = (TextView)findViewById(R.id.main_tab_collection_txt);

		mTabUser = (LinearLayout) findViewById(R.id.main_tab_user);
		mTabUser.setOnClickListener(tabClickListener);
		mTabUserImg = (ImageView) findViewById(R.id.tab_main_center_img);
		mTabUserTxt = (TextView) findViewById(R.id.tab_main_center_txt);

		/**
		 * 获取自定义的 ?/attr 值
		 */
		TypedValue colorPressed = new TypedValue();
		TypedValue colorNormal = new TypedValue();
		this.getTheme().resolveAttribute(R.attr.tab_bottom_text_pressed, colorPressed, true);
		this.getTheme().resolveAttribute(R.attr.tab_bottom_text_normal, colorNormal, true);

		mTabTxtColorPressed = getResources().getColor(colorPressed.resourceId);
		mTabTxtColorNormal = getResources().getColor(colorNormal.resourceId);



		//设置阴影
		ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {

				//outline.setOval(0, 0, view.getWidth(), view.getHeight());
				outline.setRect(new Rect(0, 0, view.getWidth(), view.getHeight() + 5));
			}
		};
		mTabBottom = (LinearLayout) findViewById(R.id.main_tab_bottom);
		mTabBottom.setOutlineProvider(viewOutlineProvider);
	}

	//region Tab处理相关

	/**
	 * tab事件监听
	 */
	View.OnClickListener tabClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId())
			{
				case R.id.main_tab_news:
					setTabSelection(0);
					break;
				case R.id.main_tab_collection:
					setTabSelection(1);
					break;
				case R.id.main_tab_user:
					setTabSelection(2);
					break;
				default:
					break;
			}
		}
	};


	private void setTabSelection(int index)
	{

		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index)
		{
			// 当点击了tab时，改变控件的图片和文字颜色
			case 0:
				mTabNewsImg.setImageResource(R.drawable.tab_news_pressed);
				mTabNewsTxt.setTextColor(mTabTxtColorPressed);
				transaction.show(mNewsFragment);
				break;
			case 1:
				mTabColletionImg.setImageResource(R.drawable.tab_collection_pressed);
				mTabColletionTxt.setTextColor(mTabTxtColorPressed);
				transaction.show(mCollectionFragment);
				break;
			case 2:
				mTabUserImg.setImageResource(R.drawable.tab_user_pressed);
				mTabUserTxt.setTextColor(mTabTxtColorPressed);
				transaction.show(mUserFragment);
				break;
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
		curTabIndex = index;
	}


	/**
	 * 清除掉所有的选中状态。
	 */
	private void resetBtn()
	{
		mTabNewsImg.setImageResource(R.drawable.tab_news_normal);
		mTabNewsTxt.setTextColor(mTabTxtColorNormal);
		mTabColletionImg.setImageResource(R.drawable.tab_collection_normal);
		mTabColletionTxt.setTextColor(mTabTxtColorNormal);
		mTabUserImg.setImageResource(R.drawable.tab_user_normal);
		mTabUserTxt.setTextColor(mTabTxtColorNormal);
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction)
	{
		if (mNewsFragment != null)
		{
			transaction.hide(mNewsFragment);
		}
		if (mCollectionFragment != null)
		{
			transaction.hide(mCollectionFragment);
		}
		if (mUserFragment != null)
		{
			transaction.hide(mUserFragment);
		}

	}

	//endregion


    //region 夜间模式相关


	private WindowManager mWindowManager;
	private ImageView cover;
	private Bitmap lastView;
	private ObjectAnimator oa;

	/**
	 * 切换夜间模式
	 */
	private void SwitchNightMode(Bundle savedInstanceState) {


		lastView = savedInstanceState.getParcelable("lastView");
		savedInstanceState.remove("lastView");

		if (lastView != null) {


			cover = new ImageView(this);
			cover.setImageBitmap(lastView);


			if (mWindowManager == null) {
				mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			}

			ViewGroup.LayoutParams mNightViewParam = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.TYPE_APPLICATION,
					WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSPARENT);

			mWindowManager.addView(cover, mNightViewParam);

			oa = ObjectAnimator.ofFloat(cover, "alpha", 1f, 0f);
			oa.setDuration(550);
			oa.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animator) {

				}

				@Override
				public void onAnimationEnd(Animator animator) {
					recycleCover();
				}

				@Override
				public void onAnimationCancel(Animator animator) {

				}

				@Override
				public void onAnimationRepeat(Animator animator) {

				}
			});
			oa.start();
		}
	}

	private void recycleCover(){

		if (cover != null){
			if (cover.getParent() != null){
				mWindowManager.removeViewImmediate(cover);
			}
			oa.setTarget(null);
			cover = null;
			oa = null;
			System.gc();

		}
	}

	//endregion

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		//当进行快速切换夜间模式时，有可能会造成Cover的溢出
		recycleCover();

		//保存当前页面截图
		outState.putParcelable("lastView",WinUtils.getScreenShot(this));
		//保存当前TabIndex
		outState.putInt("curTabIndex",curTabIndex);
	}


	//region 双击退出程序

	/**
	 * 以下为双击退出程序完整代码
	 */

	private long exitTime = 0;

	private void ExitApp() {

		if ((System.currentTimeMillis() - exitTime) > 2000) {
			ToastUtils.show(MainActivity.this,"再按一次退出程序");
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
