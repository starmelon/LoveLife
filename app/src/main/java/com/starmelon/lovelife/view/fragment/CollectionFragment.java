package com.starmelon.lovelife.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shizhefei.fragment.LazyFragment;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.adapter.NewsCollecionAdapter;
import com.starmelon.lovelife.bean.enties.Collection;
import com.starmelon.lovelife.db.local.CollectionDaoLHelper;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionFragment extends LazyFragment
{
	//inflater.inflate(R.layout.fragment_collection, container, false);

	private Activity mContext;

	private NewsCollecionAdapter mNewCollecionAdapter;

	private List<Collection> mCollecions;

	private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_collection);

		mContext = getActivity();

		mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
		mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
		mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
		mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
		mSwipeMenuRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));// 添加分割线。

		// 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
		// 设置菜单创建器。
		mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
		// 设置菜单Item点击监听。
		mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

		mCollecions = new ArrayList<>();

		mNewCollecionAdapter = new NewsCollecionAdapter(mCollecions);
		mNewCollecionAdapter.setOnItemClickListener(onItemClickListener);
		mSwipeMenuRecyclerView.setAdapter(mNewCollecionAdapter);

		initData();
	}

	private void initData() {
//		long time = new Date().getTime();
//		List<Collection> getCollecions = new CollectionDaoLHelper().getCollectionByTime(time,10);
//		mCollecions.addAll(getCollecions);

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

	private NewsCollecionAdapter.OnItemClickListener onItemClickListener = new NewsCollecionAdapter.OnItemClickListener() {
		@Override
		public void onItemClick(int position) {
			Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
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

				new CollectionDaoLHelper().deleteCollecion(mCollecions.get(adapterPosition).getNewsid());
				mCollecions.remove(adapterPosition);
				mNewCollecionAdapter.notifyItemRemoved(adapterPosition);
			}
		}
	};


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser){
			//当收藏夹可见时，刷新收藏夹
			long time = new Date().getTime();
			mCollecions.clear();
			List<Collection> getCollecions = new CollectionDaoLHelper().getCollectionByTime(time,10);
			mCollecions.addAll(getCollecions);
			mNewCollecionAdapter.notifyDataSetChanged();
		}
	}
}
