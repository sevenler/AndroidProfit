package com.androidprofit;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;
import com.androidprofit.user.Exchange;
import com.androidprofit.user.Experience;

public class ExchangeSendActivity extends Activity {
	public static final String ACTION_EXCHANGE_SEND = "com.androidprofit.action.exchange.send";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_exchange_send);
		
		final Account account = AccountManager.instance().getAccount();
		TextView surplus = (TextView)findViewById(R.id.surplus);
		surplus.setText(account.getMoney() + "");
		
		final Spinner type = (Spinner)findViewById(R.id.type);
		String[] choices = getResources().getStringArray(R.array.exchange_type_choices);
		final int[] values = getResources().getIntArray(R.array.exchange_type_values);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.view_exchange_type_spinner_item, choices);
		type.setAdapter(adapter);
		
		final EditText edit = (EditText)findViewById(R.id.exchange);
		findViewById(R.id.submit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int exchangeType = values[type.getSelectedItemPosition()];
				final int exchange = Integer.parseInt(edit.getText().toString());
				Exchange exc = new Exchange(new Date(), Exchange.EXCHANGE_STATUS_SUBMIT, exchangeType, exchange, "18310097292");
				Experience exp = account.getExperience();
				exp.addExchange(exc);
				
				finish();
			}
		});
	}
}
