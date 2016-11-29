package com.starmelon.lovelife.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.mvc.MVCSwipeRefreshHelper;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.adapter.AlphaAnimatorAdapterWithIData;
import com.starmelon.lovelife.adapter.BannerAdapter;
import com.starmelon.lovelife.adapter.IfengNewsListViewAdapter;
import com.starmelon.lovelife.data.tngou.HotNews;
import com.starmelon.lovelife.data.ifeng.news.Item;
import com.starmelon.lovelife.data.ifeng.news.IfengNews_DataSource;
import com.starmelon.lovelife.view.activity.NewsDetailActivity;
import com.starmelon.lovelife.view.custom.DividerItemDecoration;

import java.util.List;

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

    private SwipeRefreshLayout mSwipeRefreshLayout;

//    @Override
//    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
//        return inflater.inflate(R.layout.layout_preview, container, false);
//    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
//        tabName = getArguments().getString(INTENT_STRING_TABNAME);
        position = getArguments().getInt(INTENT_INT_POSITION);
        setContentView(R.layout.fragment_view_pager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshLayout);


//        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
//
        recyclerView.setItemAnimator(new SlideScaleInOutRightItemAnimator(recyclerView));
//
//
//
        //初始化轮播控件
        View view = inflater.inflate(R.layout.ad_banner,recyclerView,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.banner_viewPager);
        Indicator indicator = (Indicator) view.findViewById(R.id.banner_indicator);
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.WHITE, 0, ScrollBar.Gravity.CENTENT_BACKGROUND));
        viewPager.setOffscreenPageLimit(2);

        bannerComponent = new BannerComponent(indicator, viewPager, false);
        bannerComponent.setAdapter(new BannerAdapter());
        bannerComponent.setAutoPlayTime(2500);
//
//

        //MVCHelper<List<HotNews>> mvcHelper = new MVCSwipeRefreshHelper<List<HotNews>>(mSwipeRefreshLayout);
        MVCHelper<List<Item>> mvcHelper = new MVCSwipeRefreshHelper<List<Item>>(mSwipeRefreshLayout);
//
        // 设置数据源
        //mvcHelper.setDataSource(new News_DataSource(position));

        //设置凤凰数据源
        mvcHelper.setDataSource(new IfengNews_DataSource());

//
        //NewsListViewAdapter newsListViewAdapter = new NewsListViewAdapter(this.getContext());
        IfengNewsListViewAdapter newsListViewAdapter = new IfengNewsListViewAdapter(this.getContext());
        newsListViewAdapter.setHeaderView(view);
        newsListViewAdapter.setOnItemClickLitener(new IfengNewsListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,Item hotNews) {

                //Toast.makeText(getContext(), "你按到了" + position, Toast.LENGTH_SHORT).show();

                if (hotNews == null){
                    return;
                }

                Intent intent = new Intent(getContext(),NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putInt("hotNewsId", hotNews.getId());
                bundle.putString("hotNewsId", hotNews.getId());
                intent.putExtras(bundle);
                startActivity(intent);

                //intent.putExtra("id", hotNews.getId());
                //intent.putExtra("news",hotNews.s);

                //使用元素共享
//                View img =  view.findViewById(R.id.img_news);
//                View time =  view.findViewById(R.id.tv_time);
//
//                Pair<View, String> imgPair = Pair.create(img, getString(R.string.news_pic));
//                Pair<View, String> timePair = Pair.create(time, getString(R.string.news_time));
//
//                ActivityOptionsCompat compat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                getActivity(), imgPair, timePair );

//                ActivityCompat.startActivity(getContext(),intent, compat.toBundle());


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        // 设置适配器
        AlphaAnimatorAdapterWithIData animatorAdapter = new AlphaAnimatorAdapterWithIData(newsListViewAdapter, recyclerView);

        mvcHelper.setAdapter(animatorAdapter);

//        MVCHelper.setLoadViewFractory(new ILoadViewFactory() {
//            @Override
//            public ILoadMoreView madeLoadMoreView() {
//                return null;
//            }
//
//            @Override
//            public ILoadView madeLoadView() {
//                return null;
//            }
//        });

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
    protected void onPauseLazy() {
        super.onPauseLazy();
        bannerComponent.stopAutoPlay();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        handler.removeMessages(1);
        if (mvcHelper!= null){
            mvcHelper.destory();
        }
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
