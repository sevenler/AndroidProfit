
package com.androidprofit;

import java.io.File;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidprofit.app.PackageInfo;
import com.androidprofit.app.PackageManager;

public class AppDetailActivity extends Activity {
	private PackageInfo pkg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		
		pkg = PackageManager.instance().getmPackages()[0];
		
		loadData();
	}

	static final String path = Environment.getExternalStorageDirectory() + File.separator
			+ Environment.DIRECTORY_DOWNLOADS;
	
	private void loadData() {
		ImageView image = (ImageView)findViewById(R.id.logo);
		TextView name = (TextView)findViewById(R.id.name);
		TextView size = (TextView)findViewById(R.id.size);
		TextView description = (TextView)findViewById(R.id.description);
		findViewById(R.id.download).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String download = "http://s.androidesk.com/apk/Androidesk-release-androidesk.apk";
				final String name = "androidesk.apk";
				DownloadTab.download(AppDetailActivity.this, Uri.parse(download), path, name);
			}
		});

		name.setText(pkg.getName());
		size.setText(pkg.getFormatedSize());
		description.setText(pkg.getDescription());
	}
}
