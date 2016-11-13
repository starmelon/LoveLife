package com.starmelon.lovelife.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.bean.NewsDetail;
import com.starmelon.lovelife.bean.enties.HotNews;
import com.starmelon.lovelife.db.net.API;
import com.starmelon.lovelife.db.net.ApiManager;
import com.starmelon.lovelife.db.net.GenericsCallback;
import com.starmelon.lovelife.db.net.JsonGenericsSerializator;


import java.sql.Timestamp;

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
	
	private ImageButton mBtn_share;
	
	NewsDetail news;
	
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
		HotNews hotNews = (HotNews) intent.getSerializableExtra("hotNews");


		mTv_title.setText(hotNews.getTitle());
		mTv_time.setText(hotNews.getTime());
		mTv_provenance.setText(hotNews.getFromname());
		mTv_count.setText(hotNews.getCount() + "");

		getData(hotNews.getId());
	}

	private void iniView() {
		mTv_title = (TextView) findViewById(R.id.tv_title);
		mTv_time = (TextView) findViewById(R.id.tv_time);
		mTv_provenance = (TextView) findViewById(R.id.tv_provenance);
		mTv_count = (TextView) findViewById(R.id.tv_count);
		mImg = (ImageView) findViewById(R.id.img_news);
		mTv_content = (TextView) findViewById(R.id.tv_content);

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

				news = response;

				//mTv_title.setText(news.title);
				//mTv_time.setText(new Timestamp(news.time).toString());
				//mTv_provenance.setText(news.fromname);
				//mTv_count.setText(news.fcount+"");
				if (news.img.equals("/top/default.jpg")){
					mImg.setVisibility(View.GONE);
					//Picasso.with(MyApplication.getContext()).load(R.drawable.img_default).into(mImg);
				}else
				{
					Picasso.with(MyApplication.getContext()).load(API.API_IMAGE+news.img).into(mImg);
				}
				//Picasso.with(MyApplication.getContext()).load(news..getImg()).into(mImg);
				String content = Html.fromHtml(news.message).toString();
				mTv_content.setText(content.replace((char)65532,' '));

			}
		},id);

//		ApiManager.getNewsDetailById(new RequestHandler<NewsDetail>() {
//
//			@Override
//			public void onRequestFinish(NewsDetail data) {
//				if (data == null) {
//					Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//				news = data;
//
//				mTv_title.setText(news.title);
//				mTv_time.setText(new Timestamp(news.time).toString());
//				mTv_provenance.setText(news.fromname);
//				mTv_fcount.setText(news.fcount+"");
//				mTv_content.setText(Html.fromHtml(news.message));
//				//mTv_content.setText(data.message);
//			}
//
//			@Override
//			public NewsDetail processOriginData(JsonData jsonData) {
//				Gson gson = new Gson();
//				return gson.fromJson(jsonData.toString(),NewsDetail.class);
//			}
//
//			@Override
//			public void onRequestFail(FailData failData) {
//				// TODO Auto-generated method stub
//
//			}
//		}, id);
		
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
		oks.setTitleUrl(news.fromurl);
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
