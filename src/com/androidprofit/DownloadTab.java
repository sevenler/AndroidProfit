
package com.androidprofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.androidprofit.app.PackageInfo;
import com.androidprofit.app.PackageManager;
import com.androidprofit.image.ImageCacheManager;

public class DownloadTab extends Fragment implements IFragment {
	private ListView mList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout mFrameLayout = new FrameLayout(getActivity());
		mFrameLayout.setLayoutParams(params);

		View.inflate(getActivity(), R.layout.view_download, mFrameLayout);
		mList = (ListView)mFrameLayout.findViewById(R.id.list);

		return mFrameLayout;
	}

	public void inflateData(final Context ctx, ListView list) {
		final PackageInfo[] pkgs = PackageManager.instance().getPackages();
		list.setAdapter(new DownloadAppListAdapter(ctx, pkgs));
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), AppDetailActivity.class);
				intent.putExtra(AppDetailActivity.DATA_APP_INFOR, pkgs[arg2]);
				ctx.startActivity(intent);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		inflateData(getActivity(), mList);
	}

	@Override
	public void onReflush() {
		inflateData(getActivity(), mList);
	}
}

class DownloadAppListAdapter extends ArrayAdapter<PackageInfo> {
	private final Context context;
	private final PackageInfo[] values;

	public DownloadAppListAdapter(Context context, PackageInfo[] values) {
		super(context, R.layout.item_download, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_download, parent, false);
		TextView textView = (TextView)rowView.findViewById(R.id.label);
		NetworkImageView imageView = (NetworkImageView)rowView.findViewById(R.id.logo);

		PackageInfo pi = values[position];
		if (pi != null) textView.setText(pi.getName());
		imageView.setImageUrl(pi.getIcon(), ImageCacheManager.getInstance().getImageLoader());

		return rowView;
	}
}