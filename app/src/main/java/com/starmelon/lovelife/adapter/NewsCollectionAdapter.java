/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.starmelon.lovelife.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starmelon.lovelife.R;
import com.starmelon.lovelife.data.Collection;
import com.starmelon.lovelife.view.fragment.CollectionFragment.CollectionItemListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by starmelon on 2016/11/28.
 */
public class NewsCollectionAdapter extends SwipeMenuAdapter<NewsCollectionAdapter.DefaultViewHolder> {

    private List<Collection> mCollections;
    //private OnItemClickListener mOnItemClickListener;
    private CollectionItemListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public NewsCollectionAdapter(List<Collection> collections, CollectionItemListener collectionItemListener) {
        setList(collections);
        mOnItemClickListener = collectionItemListener;

    }

    public void replaceData(List<Collection> collections) {
        setList(collections);
        notifyDataSetChanged();
    }

    private void setList(List<Collection> tasks) {
        mCollections = checkNotNull(tasks);
    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }

    @Override
    public int getItemCount() {
        return mCollections == null ? 0 : mCollections.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_item_collecion, parent, false);
    }

    @Override
    public NewsCollectionAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(NewsCollectionAdapter.DefaultViewHolder holder, int position) {
        final Collection collection = mCollections.get(position);
        holder.tvTitle.setText(collection.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        holder.tvTime.setText(sdf.format(new Timestamp(collection.getTime())));

        //holder.setData();
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mOnItemClickListener.onCollectionClick(collection);
            }
        });
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvTime;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
