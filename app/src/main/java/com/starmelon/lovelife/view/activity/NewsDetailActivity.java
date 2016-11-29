package com.starmelon.lovelife.view.activity;

import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.data.tngou.NewsDetail;
import com.starmelon.lovelife.data.tngou.API;
import com.starmelon.lovelife.presenter.NewsDetailContact;
import com.starmelon.lovelife.presenter.NewsDetailPresenter;
import com.starmelon.lovelife.util.TimeUtils;
import com.starmelon.lovelife.util.ToastUtils;


import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import cn.sharesdk.onekeyshare.OnekeyShare;

import pl.tajchert.sample.DotsTextView;

public class NewsDetailActivity extends BaseActivity<NewsDetailContact.View,NewsDetailPresenter> implements NewsDetailContact.View {

	private RelativeLayout mLoading;
	private DotsTextView mDots;

	private LinearLayout mFailed;
	private ImageView mBtn_Refresh;

	private ScrollView mContent;

	private LinearLayout ll_tab;
	private ImageView mImg;
	private TextView mTv_title;
	private TextView mTv_time;
	private TextView mTv_provenance;
	private TextView mTv_count;
	private TextView mTv_content;

	private ImageButton mBtn_collect;
	private ImageButton mBtn_share;

	NewsDetail mNewsDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsdetail);
		
		iniView();
		initData();


		
	}

	@Override
	protected NewsDetailPresenter createPresenter() {
		return new NewsDetailPresenter();
	}

	private void initData() {

		mDots.start();

		Intent intent = getIntent();
		int hotNewsId = intent.getIntExtra("hotNewsId",-1);
		//HotNews hotnews = (HotNews) intent.getSerializableExtra("hotNewsId");


		//获取详细新闻信息
		mPresenter.loadNewsDetail(hotNewsId);

		//判断是否已收藏过
		mPresenter.showCollectState();

	}

	private void iniView() {

		mLoading = (RelativeLayout) findViewById(R.id.rl_loading);
		mDots = (DotsTextView) findViewById(R.id.dots);

		mFailed = (LinearLayout) findViewById(R.id.rl_failed);
		mBtn_Refresh = (ImageView) findViewById(R.id.btn_refresh);
		mBtn_Refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mPresenter.reloadNewsDetail();
			}
		});

		mContent = (ScrollView) findViewById(R.id.content);

		mTv_title = (TextView) findViewById(R.id.tv_title);
		mTv_time = (TextView) findViewById(R.id.tv_time);
		mTv_provenance = (TextView) findViewById(R.id.tv_provenance);
		mTv_count = (TextView) findViewById(R.id.tv_count);
		mImg = (ImageView) findViewById(R.id.img_news);
		mTv_content = (TextView) findViewById(R.id.tv_content);



		mBtn_collect = (ImageButton) findViewById(R.id.btn_collect);
		mBtn_collect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				mPresenter.collectNews();

			}
		});


		mBtn_share = (ImageButton) findViewById(R.id.btn_share);
		mBtn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPresenter.shareNews();
			}
		});

		ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
		//设置阴影
		ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {

				//outline.setOval(0, 0, view.getWidth(), view.getHeight());
				outline.setRect(new Rect(0, 0, view.getWidth(), view.getHeight() + 5));
			}
		};
		ll_tab.setOutlineProvider(viewOutlineProvider);

	}


	@Override
	public void showLoading() {

		mDots.start();
		mLoading.setVisibility(View.VISIBLE);

		mContent.setVisibility(View.GONE);
		ll_tab.setVisibility(View.GONE);
		mFailed.setVisibility(View.GONE);

	}

	@Override
	public void loadNewsDetailSucceed(NewsDetail newsDetail) {


		if (newsDetail.img.equals(API.API_IMAGE + "/top/default.jpg")){
			mImg.setVisibility(View.GONE);
		}else
		{
			Picasso.with(MyApplication.getContext()).load(newsDetail.img).into(mImg);
		}

		mTv_title.setText(newsDetail.title);
		mTv_time.setText(TimeUtils.long2String(newsDetail.time));
		mTv_provenance.setText(newsDetail.fromname);
		mTv_count.setText(newsDetail.count + "");

		String content = Html.fromHtml(newsDetail.message).toString();
		mTv_content.setText(content.replace((char)65532,' '));

		mDots.stop();
		mLoading.setVisibility(View.GONE);
		mFailed.setVisibility(View.GONE);

		mContent.setVisibility(View.VISIBLE);
		ll_tab.setVisibility(View.VISIBLE);
	}

	@Override
	public void loadNewsDetailFailed(String error) {


		mDots.stop();
		mLoading.setVisibility(View.GONE);
		mContent.setVisibility(View.GONE);
		ll_tab.setVisibility(View.GONE);

		mFailed.setVisibility(View.VISIBLE);


	}

	@Override
	public void setCollectState(boolean isCollected) {

		if (isCollected){
			mBtn_collect.setImageResource(R.drawable.collected);
		}else{
			mBtn_collect.setImageResource(R.drawable.collect);
		}
	}

	@Override
	public void showCollecResult(boolean isCollected) {

		if (isCollected){
			ToastUtils.show(this,"收藏成功");
		}else{
			ToastUtils.show(this,"取消收藏");
		}

	}

	@Override
	public void shareNews(NewsDetail newsDetail) {

		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(newsDetail.fromurl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		oks.setCallback(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

			}

			@Override
			public void onError(Platform platform, int i, Throwable throwable) {

			}

			@Override
			public void onCancel(Platform platform, int i) {

			}
		});

		// 启动分享GUI
		oks.show(this);

	}

	@Override
	public void setPresenter(NewsDetailContact.Presenter presenter) {

	}
}
