
package com.androidprofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MobileAdapter(Context context, String[] values) {
		super(context, R.layout.item_down, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_down, parent, false);
		TextView textView = (TextView)rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView)rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		String s = values[position];


		if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageResource(R.drawable.ic_launcher);
		}

		return rowView;
	}
}
