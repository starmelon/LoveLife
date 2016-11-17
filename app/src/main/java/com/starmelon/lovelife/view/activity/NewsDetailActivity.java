package com.starmelon.lovelife.view.activity;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.bean.NewsDetail;
import com.starmelon.lovelife.bean.enties.Collection;
import com.starmelon.lovelife.bean.enties.HotNews;
import com.starmelon.lovelife.db.local.CollectionDaoLHelper;
import com.starmelon.lovelife.db.net.API;
import com.starmelon.lovelife.db.net.ApiManager;
import com.starmelon.lovelife.db.net.GenericsCallback;
import com.starmelon.lovelife.db.net.JsonGenericsSerializator;


import cn.sharesdk.framework.ShareSDK;

import cn.sharesdk.onekeyshare.OnekeyShare;

import okhttp3.Call;

public class NewsDetailActivity extends Activity {

	private LinearLayout ll_tab;
	private ImageView mImg;
	private TextView mTv_title;
	private TextView mTv_time;
	private TextView mTv_provenance;
	private TextView mTv_count;
	private TextView mTv_content;

	private ImageButton mBtn_collect;
	private ImageButton mBtn_share;

	private boolean isCollected;
	private HotNews mHotNews;
	NewsDetail mNewsDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsdetail);
		
		iniView();
		initEvent();
		initData();


		
	}

	private void initData() {

		Intent intent = getIntent();
		mHotNews = (HotNews) intent.getSerializableExtra("hotNews");


		mTv_title.setText(mHotNews.getTitle());
		mTv_time.setText(mHotNews.getTime());
		mTv_provenance.setText(mHotNews.getFromname());
		mTv_count.setText(mHotNews.getCount() + "");

		getData(mHotNews.getId());

		//判断是否已收藏过
		Collection collection = new CollectionDaoLHelper().getCollecionByID(mHotNews.getId());
		isCollected = collection == null ? false : true;
	}

	private void iniView() {
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
				if (mNewsDetail != null){
					CollectionDaoLHelper cdl = new CollectionDaoLHelper();

					if (isCollected){
						Toast.makeText(NewsDetailActivity.this,"已收藏过",Toast.LENGTH_SHORT).show();
					}else {
						new CollectionDaoLHelper().addCollection(mHotNews.getId(),mHotNews.getTitle());
						isCollected = true;
						Toast.makeText(NewsDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();

					}


				}

			}
		});


		mBtn_share = (ImageButton) findViewById(R.id.btn_share);
		mBtn_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showShare();
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
	
	private void initEvent()
	{

	}
	
	/**
	 * 获取文章详细信息
	 * @param id
	 */
	private void getData(int id) {

		ApiManager.getNewsDetailById(new GenericsCallback<NewsDetail>(new JsonGenericsSerializator()) {

			@Override
			public void onError(Call call, Exception e, int id) {

			}

			@Override
			public void onResponse(NewsDetail response, int id) {

				if (response == null) {
					Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
					return;
				}

				mNewsDetail = response;

				if (mNewsDetail.img.equals("/top/default.jpg")){
					mImg.setVisibility(View.GONE);
					//Picasso.with(MyApplication.getContext()).load(R.drawable.img_default).into(mImg);
				}else
				{
					Picasso.with(MyApplication.getContext()).load(API.API_IMAGE+ mNewsDetail.img).into(mImg);
				}
				//Picasso.with(MyApplication.getContext()).load(mNewsDetail..getImg()).into(mImg);
				String content = Html.fromHtml(mNewsDetail.message).toString();
				mTv_content.setText(content.replace((char)65532,' '));

			}
		},id);


		
	}

	
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(mNewsDetail.fromurl);
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

		// 启动分享GUI
		oks.show(this);
	}
	



	

	
	
	
	
}
