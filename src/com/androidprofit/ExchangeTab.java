package com.androidprofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ExchangeTab extends Fragment implements IFragment, OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup group = (ViewGroup)View.inflate(getActivity(), R.layout.view_exchange, null);
		group.findViewById(R.id.exchange_alipay).setOnClickListener(this);
		group.findViewById(R.id.exchange_mobile).setOnClickListener(this);
		group.findViewById(R.id.exchange_tencent).setOnClickListener(this);
		
		return group;
	}

	@Override
	public void onReflush() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exchange_alipay:
			redirect();
			break;
		case R.id.exchange_mobile:
			redirect();
			break;
		case R.id.exchange_tencent:
			redirect();
			break;
		default:
			break;
		}
	}
	
	private void redirect(){
		Intent intent = new Intent(ExchangeSendActivity.ACTION_EXCHANGE_SEND);
		startActivity(intent);
	}
}