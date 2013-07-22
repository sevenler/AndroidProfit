package com.androidprofit;

import java.io.File;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DownloadTab {
	static final String[] MOBILE_OS = new String[] { "Android", "iOS", "WindowsMobile",
			"Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry", "Android", "iOS",
			"WindowsMobile", "Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry",
			"Android", "iOS", "WindowsMobile", "Blackberry", "Android", "iOS", "WindowsMobile",
			"Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry", "Android", "iOS",
			"WindowsMobile", "Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry",
			"Android", "iOS", "WindowsMobile", "Blackberry" };

	static final String download = "http://s.androidesk.com/apk/Androidesk-release-androidesk.apk";
	
	static final String path = Environment.getExternalStorageDirectory() + File.separator
			+ Environment.DIRECTORY_DOWNLOADS;
	static final String name = "androidesk.apk";

	static final String MESSAGE_DOWNLOAD_FINISH = "Download apk %s finish";
	static final String TAG_DOWNLOAD = "download";

	public static ListView setMobileList(final Context ctx, ViewGroup root) {
		View.inflate(ctx, R.layout.view_listview, root);
		ListView list = (ListView)root.findViewById(R.id.list);
		list.setAdapter(new MobileAdapter(ctx, MOBILE_OS));
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				download(ctx, Uri.parse(download), path, name);
			}
		});
		return list;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static void download(final Context ctx, Uri uri, String path, String name) {
		DownloadManager.Request request = new Request(uri);
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		request.setTitle(String.format(ctx.getString(R.string.alert_download), name));
		
		final String fullName = path + File.separator + name;
		File f = new File(fullName);
		request.setDestinationUri(Uri.fromFile(f));

		DownloadManager dm = (DownloadManager)ctx.getSystemService(Context.DOWNLOAD_SERVICE);
		long reference = dm.enqueue(request);
		DownloadBoardcast.register(ctx, reference, new onDownloadListenner() {
			@Override
			public void onComplated(long reference) {
				install(ctx, fullName);
				Log.i(TAG_DOWNLOAD, String.format(MESSAGE_DOWNLOAD_FINISH, fullName));
			}
		});
	}

	public static void install(Context ctx, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
	}
}

class DownloadBoardcast extends BroadcastReceiver {
	private final long mReference;
	private final onDownloadListenner mlistenner;

	private DownloadBoardcast(long reference, onDownloadListenner listenner) {
		mReference = reference;
		mlistenner = listenner;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		if ((mlistenner != null) && (reference == mReference)) mlistenner.onComplated(reference);
	}

	public static void register(Context ctx, long reference, onDownloadListenner listenner) {
		DownloadBoardcast db = new DownloadBoardcast(reference, listenner);

		IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		ctx.registerReceiver(db, filter);
	}
}

interface onDownloadListenner {
	public void onComplated(long reference);
}
