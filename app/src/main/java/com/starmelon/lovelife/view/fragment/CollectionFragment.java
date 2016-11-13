package com.starmelon.lovelife.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shizhefei.fragment.LazyFragment;
import com.starmelon.lovelife.R;

public class CollectionFragment extends LazyFragment
{
	//inflater.inflate(R.layout.fragment_collection, container, false);

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_collection);

	}
}
