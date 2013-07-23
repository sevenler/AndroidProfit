
package com.androidprofit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;

public class AccountTab extends Fragment  implements IFragment{
	private FrameLayout mFrameLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mFrameLayout = new FrameLayout(getActivity());
		mFrameLayout.setLayoutParams(params);

		AccountTab.setAccountList(getActivity(), mFrameLayout);

		return mFrameLayout;
	}
	
	@Override
	public void onReflush() {
		//控件初始化之前也可能调用
		if(mFrameLayout != null) AccountTab.setData(mFrameLayout);
	}

	public static ViewGroup setAccountList(final Context ctx, ViewGroup root) {
		View.inflate(ctx, R.layout.view_account, root);
		ViewGroup view = (ViewGroup)root.findViewById(R.id.account);

		setData(view);

		return view;
	}

	public static void setData(ViewGroup view) {
		Account account = AccountManager.instance().getAccount();

		TextView id = (TextView)view.findViewById(R.id.id);
		id.setText(account.getId());
		TextView name = (TextView)view.findViewById(R.id.name);
		name.setText(account.getName());
		TextView email = (TextView)view.findViewById(R.id.email);
		email.setText(account.getMail());
		TextView money = (TextView)view.findViewById(R.id.money);
		money.setText(account.getMoney() + "");

		System.out.println(String.format("account.getMoney():%s", account.getMoney()));
	}
}
