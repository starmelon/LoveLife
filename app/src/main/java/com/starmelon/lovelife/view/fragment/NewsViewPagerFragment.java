package com.starmelon.lovelife.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.mvc.MVCUltraHelper;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.adapter.BannerAdapter;
import com.starmelon.lovelife.adapter.NewsListViewAdapter;
import com.starmelon.lovelife.adapter.AlphaAnimatorAdapterWithIData;
import com.starmelon.lovelife.bean.enties.HotNews;
import com.starmelon.lovelife.db.net.News_DataSource;
import com.starmelon.lovelife.view.custom.DividerItemDecoration;
import com.starmelon.lovelife.view.activity.NewsDetailActivity;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import it.gmariotti.recyclerview.itemanimator.SlideScaleInOutRightItemAnimator;


public class NewsViewPagerFragment extends LazyFragment {

    public static final String INTENT_STRING_TABNAME = "intent_String_tabName";
    public static final String INTENT_INT_POSITION = "intent_int_position";
    private String tabName;
    private int position;
    private TextView textView;
    private ProgressBar progressBar;

    private MVCHelper<List<HotNews>> mvcHelper;
    private BannerComponent bannerComponent;

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
//        tabName = getArguments().getString(INTENT_STRING_TABNAME);
        position = getArguments().getInt(INTENT_INT_POSITION);
        setContentView(R.layout.fragment_view_pager);

        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setItemAnimator(new SlideScaleInOutRightItemAnimator(recyclerView));



//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //Toast.makeText(getContext(), "你按到了" + position, Toast.LENGTH_SHORT).show();
//
//                	HotNews news = mAdapter.getItem(position);
//                	final int newsid = news.id;
//                	Intent intent = new Intent(getContext(),NewsDetailActivity.class);
//                	intent.putExtra("id", newsid);
//
//                    startActivity(intent);
////                	if (position >= 0) {
////                    	HotNews ht = mAdapter.getItem(position);
////                        final String url = ht!=null?ht.img:null;
////                        if (!TextUtils.isEmpty(url)) {
////                            getContext().pushFragmentToBackStack(MaterialStyleFragment.class, url);
////                        }
////                    }
//                }
//            });

        //初始化轮播控件
        View view = inflater.inflate(R.layout.ad_banner,recyclerView,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.banner_viewPager);
        Indicator indicator = (Indicator) view.findViewById(R.id.banner_indicator);
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.WHITE, 0, ScrollBar.Gravity.CENTENT_BACKGROUND));
        viewPager.setOffscreenPageLimit(2);

        bannerComponent = new BannerComponent(indicator, viewPager, false);
        bannerComponent.setAdapter(new BannerAdapter());
        bannerComponent.setAutoPlayTime(2500);


        mvcHelper = new MVCUltraHelper<List<HotNews>>(mPtrFrameLayout);

        // 设置数据源
        mvcHelper.setDataSource(new News_DataSource(position));

        NewsListViewAdapter newsListViewAdapter = new NewsListViewAdapter(this.getContext());
        newsListViewAdapter.setHeaderView(view);
        newsListViewAdapter.setOnItemClickLitener(new NewsListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,HotNews hotNews) {

                //Toast.makeText(getContext(), "你按到了" + position, Toast.LENGTH_SHORT).show();

                if (hotNews == null){
                    return;
                }

                Intent intent = new Intent(getContext(),NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hotNews", hotNews);
                intent.putExtras(bundle);
                //intent.putExtra("id", hotNews.getId());
                //intent.putExtra("news",hotNews.s);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        // 设置适配器
        AlphaAnimatorAdapterWithIData animatorAdapter = new AlphaAnimatorAdapterWithIData(newsListViewAdapter, recyclerView);

        mvcHelper.setAdapter(animatorAdapter);


        // 加载数据
        mvcHelper.refresh();


    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        bannerComponent.startAutoPlay();
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        bannerComponent.stopAutoPlay();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        handler.removeMessages(1);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        ;
    };


}
