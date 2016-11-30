package com.starmelon.lovelife.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starmelon.lovelife.R;
import com.starmelon.lovelife.adapter.NewsCollectionAdapter;
import com.starmelon.lovelife.data.Collection;
import com.starmelon.lovelife.presenter.CollectionContact;
import com.starmelon.lovelife.presenter.CollectionPresenter;
import com.starmelon.lovelife.util.ToastUtils;
import com.starmelon.lovelife.view.activity.NewsDetailActivity;
import com.starmelon.lovelife.view.custom.DividerItemDecoration;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionFragment extends BaseFragment<CollectionContact.View,CollectionPresenter> implements CollectionContact.View
{


	private Activity mContext;

	private NewsCollectionAdapter mNewsCollectionAdapter;

	private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

	private List<Collection> mCollections;

	private TextView mTxtEmpty;

	@Override
	protected CollectionPresenter createPresenter() {
		return new CollectionPresenter();
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNewsCollectionAdapter = new NewsCollectionAdapter(new ArrayList<Collection>(0),mItemListener);
	}

	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		super.onCreateView(savedInstanceState);
		setContentView(R.layout.fragment_collection);

		mContext = getActivity();

		mTxtEmpty = (TextView) findViewById(R.id.tv_empty);


		mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
		mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
		mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
		mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
		mSwipeMenuRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));// 添加分割线。

		// 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
		// 设置菜单创建器。
		mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
		// 设置菜单Item点击监听。
		mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

		mCollections = new ArrayList<>();

		//mNewsCollectionAdapter = new NewsCollectionAdapter(mCollections);
		//mNewsCollectionAdapter.setOnItemClickListener(onItemClickListener);
		mSwipeMenuRecyclerView.setAdapter(mNewsCollectionAdapter);

		initData();

	}

	@Override
	public void onResume() {
		super.onResume();
		refreshCollection();
	}

	private void initData() {
		long time = new Date().getTime();
		refreshCollection();
//		List<Collection> collections = new CollectionDaoLHelper().getCollectionByTime(time,10);
//		mCollections.addAll(collections);

	}


	/**
	 * 菜单创建器。在Item要创建菜单的时候调用。
	 */
	private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
		@Override
		public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
			int width = getResources().getDimensionPixelSize(R.dimen.item_height);

			// MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
			int height = RelativeLayout.LayoutParams.MATCH_PARENT;


			// 添加右侧的，如果不添加，则右侧不会出现菜单。
			{
				SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
						.setBackgroundDrawable(R.drawable.lst_item_collection_selector_red)
						//.setImage(R.drawable.ic_action_delete)
						.setText("删除") // 文字，还可以设置文字颜色，大小等。。
						.setTextColor(Color.WHITE)
						.setWidth(width)
						.setHeight(height);
				swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

			}
		}
	};

	/**
	 * 侧滑菜单点击监听。
	 */
	private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
		/**
		 * Item的菜单被点击的时候调用。
		 * @param closeable       closeable. 用来关闭菜单。
		 * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
		 * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
		 * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
		 */
		@Override
		public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
			closeable.smoothCloseMenu();// 关闭被点击的菜单。

			//如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
			if (menuPosition == 0) {// 删除按钮被点击。
				//mStrings.remove(adapterPosition);

				mNewsCollectionAdapter.notifyItemRemoved(adapterPosition);
				String newid = mCollections.get(adapterPosition).getNewsid();
				//mCollections.remove(adapterPosition);
				mPresenter.deleteCollection(newid);


			}
		}
	};


	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (!hidden){
			mPresenter.loadCollections();
			//refreshCollection();
		}

	}

	@Override
	public void showDeleteResult(String msg) {
		ToastUtils.show(getActivity(),msg);
	}

	@Override
	public void showEmpty() {
		mCollections.clear();
		mSwipeMenuRecyclerView.setVisibility(View.GONE);
		mTxtEmpty.setVisibility(View.VISIBLE);
	}

	@Override
	public void showCollections(List<Collection> collections) {

		mSwipeMenuRecyclerView.setVisibility(View.VISIBLE);
		mTxtEmpty.setVisibility(View.GONE);
		mNewsCollectionAdapter.replaceData(collections);
		mCollections = collections;

	}

	@Override
	public void refreshCollection(){

		mPresenter.loadCollections();

	}

	@Override
	public void showNewsDetailUi(String newsid) {
		Intent intent = new Intent(getContext(),NewsDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("hotNewsId", newsid);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void setPresenter(CollectionContact.Presenter presenter) {

	}

	/**
	 * Listener for clicks on tasks in the ListView.
	 */
	CollectionItemListener mItemListener = new CollectionItemListener() {
		@Override
		public void onCollectionClick(Collection clickedCollection) {
			mPresenter.openNewsDetail(clickedCollection);
		}

	};

	public interface CollectionItemListener{
		void onCollectionClick(Collection collection);
	}
}
