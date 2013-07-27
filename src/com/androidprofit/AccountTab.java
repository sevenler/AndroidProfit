
package com.androidprofit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;
import com.androidprofit.user.Record;

public class AccountTab extends Fragment implements IFragment {
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

		View.inflate(getActivity(), R.layout.view_account, mFrameLayout);
		
		return mFrameLayout;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		setData(getActivity(), mFrameLayout);
	}

	@Override
	public void onReflush() {
		// 控件初始化之前也可能调用
		if (mFrameLayout != null) AccountTab.setData(getActivity(), mFrameLayout);
	}

	public static void setData(Context ctx, ViewGroup view) {
		Account account = AccountManager.instance().getAccount();

		TextView id = (TextView)view.findViewById(R.id.id);
		id.setText(account.getId());
		TextView name = (TextView)view.findViewById(R.id.name);
		name.setText(account.getName());
		TextView email = (TextView)view.findViewById(R.id.email);
		email.setText(account.getMail());
		TextView money = (TextView)view.findViewById(R.id.money);
		money.setText(account.getMoney() + "");
		ListView list = (ListView)view.findViewById(R.id.experience);
		list.setAdapter(new ExperienceListAdapter(ctx, account.getExperience().getExperienceArray()));

		System.out.println(String.format("account.getMoney():%s", account.getMoney()));
	}
}

class ExperienceListAdapter extends ArrayAdapter<Record> {
	private final Context context;
	private final Record[] values;

	public ExperienceListAdapter(Context context, Record[] values) {
		super(context, R.layout.item_experience, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_experience, parent, false);
		TextView name = (TextView)rowView.findViewById(R.id.name);
		TextView time = (TextView)rowView.findViewById(R.id.time);
		TextView getted = (TextView)rowView.findViewById(R.id.getted);

		Record record = values[position];
		name.setText(record.getName());
		time.setText(record.getTime() + "");
		getted.setText(record.getCost() + "");

		return rowView;
	}
}
