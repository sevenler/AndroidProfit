package com.androidprofit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class ExchangeTab extends Fragment implements IFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout mFrameLayout = new FrameLayout(getActivity());
		mFrameLayout.setLayoutParams(params);
		mFrameLayout.setBackgroundColor(Color.parseColor("#e2e2e2e2"));

		return mFrameLayout;
	}

	@Override
	public void onReflush() {
	}
}