
package com.androidprofit;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.androidprofit.app.PackageInfo;
import com.androidprofit.image.ImageCacheManager;

public class AppDetailActivity extends FragmentActivity {
	private PackageInfo pkg;

	public static final String DATA_APP_INFOR = "data_app_infor";
	private static final String MESSAGE_APP_INFOR = "initlized with app infor (%s)";
	private static final String MESSAGE_TAG = "AppDetailActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);

		Intent intent = getIntent();
		Parcelable data = intent.getParcelableExtra(DATA_APP_INFOR);
		Log.i(MESSAGE_TAG, String.format(MESSAGE_APP_INFOR, data));

		if (data == null) {
			finish();
		} else {
			pkg = (PackageInfo)data;
		}

		loadData();
	}

	static final String path = Environment.getExternalStorageDirectory() + File.separator
			+ Environment.DIRECTORY_DOWNLOADS;

	private void loadData() {
		NetworkImageView image = (NetworkImageView)findViewById(R.id.logo);
		TextView name = (TextView)findViewById(R.id.name);
		TextView size = (TextView)findViewById(R.id.size);
		TextView description = (TextView)findViewById(R.id.description);
		findViewById(R.id.download).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DownloadTab.downloadAndInstallApk(AppDetailActivity.this, path, pkg);
			}
		});

		image.setImageUrl(pkg.getIcon(), ImageCacheManager.getInstance().getImageLoader());
		name.setText(pkg.getName());
		size.setText(pkg.getFormatedSize());
		description.setText(pkg.getDescription());
	}
}
