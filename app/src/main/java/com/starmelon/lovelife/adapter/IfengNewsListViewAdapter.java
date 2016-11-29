package com.starmelon.lovelife.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.R;
import com.starmelon.lovelife.data.ifeng.news.Item;

import java.util.ArrayList;
import java.util.List;


public class IfengNewsListViewAdapter extends RecycleViewAdapterWithIData<IfengNewsListViewAdapter.MyViewHolder,List<Item>> {


	private LayoutInflater inflater;
	public List<Item> hotNewses = new ArrayList<>();


	public IfengNewsListViewAdapter(Context context) {
		super();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public IfengNewsListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		MyViewHolder holder = null;
		switch (viewType){
			case HEADER:
				holder = new MyViewHolder(headerView);
				break;
			case NORMAL:
				holder = new MyViewHolder(inflater.inflate(R.layout.lst_item_news,parent,false));
				break;
			default:
				holder = new MyViewHolder(inflater.inflate(R.layout.lst_item_news,parent,false));
				break;

		}
		return holder;
	}

	@Override
	public int getItemCount() {
		return hotNewses == null ? 0 : hotNewses.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {

		//添加Header后位置后移一位
		if (getItemViewType(position) == HEADER){
			return;
		}
		final int pos = getRealPosition(holder);


//		if (hotNewses.get(pos).getImg().equals(API.API_IMAGE + "/top/default.jpg")){
//			Picasso.with(MyApplication.getContext()).load(R.drawable.img_default).into(holder.img);
//		}else
		{
			Picasso.with(MyApplication.getContext()).load(hotNewses.get(pos).getThumbnail()).into(holder.img);
		}

		holder.title.setText(hotNewses.get(pos).getTitle());
		holder.time.setText(hotNewses.get(pos).getUpdateTime());
		holder.count.setText(hotNewses.get(pos).getComments());

		//region 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos,hotNewses.get(pos));
				}
			});

			holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					//int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					return false;
				}
			});
		}
		//endregion
	}


	class MyViewHolder extends RecyclerView.ViewHolder
	{

		//private ImageLoader mImageLoader;
		//private CubeImageView mImageView;
		private ImageView img;
		private TextView title;
		private TextView time;
		private TextView count;

		public MyViewHolder(View v)
		{
			super(v);
			if (v == headerView) return;
			//mImageView = (CubeImageView) v.findViewById(R.id.img_news);
			///mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			img = (ImageView) v.findViewById(R.id.img_news);
			title = (TextView)v.findViewById(R.id.tv_title);
			time = (TextView)v.findViewById(R.id.tv_time);
			count = (TextView) v.findViewById(R.id.tv_count);
		}


	}


	@Override
	public void notifyDataChanged(List<Item> data, boolean isRefresh) {
		if (isRefresh) {
			hotNewses.clear();
		}
		Log.v("Net","刷新：" + hotNewses.size() + "");
		hotNewses.addAll(data);
		notifyDataSetChanged();
	}

	@Override
	public List<Item> getData() {
		return hotNewses;
	}

	@Override
	public boolean isEmpty() {
		return hotNewses.isEmpty();
	}


	//region 添加头部Banner

	private View headerView;
	private static final int HEADER = 0;
	private static final int NORMAL = 1;


	@Override
	public int getItemViewType(int position) {
		if (headerView == null) return NORMAL;
		if (position == 0) return HEADER;
		return NORMAL;
	}

	public void setHeaderView(View headerView) {
		this.headerView = headerView;
		notifyItemInserted(0);
	}

	private int getRealPosition(RecyclerView.ViewHolder holder) {
		int position = holder.getLayoutPosition();
		return headerView == null ? position : position - 1;
	}

	//endregion

	//region 添加点击事件

	public interface OnItemClickListener
	{
		void onItemClick(View view, int position, Item hotNews);
		void onItemLongClick(View view, int position);
	}

	private OnItemClickListener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	//endregion
}
