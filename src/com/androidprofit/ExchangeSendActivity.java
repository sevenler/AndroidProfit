package com.androidprofit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;

public class ExchangeSendActivity extends Activity {
	public static final String ACTION_EXCHANGE_SEND = "com.androidprofit.action.exchange.send";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_exchange_send);
		
		Account account = AccountManager.instance().getAccount();
		
		TextView surplus = (TextView)findViewById(R.id.surplus);
		surplus.setText(account.getMoney() + "");
		
		Spinner type = (Spinner)findViewById(R.id.type);
		String[] choices = getResources().getStringArray(R.array.exchange_type_choices);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.view_exchange_type_spinner_item, choices);
		type.setAdapter(adapter);
		
		findViewById(R.id.submit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
