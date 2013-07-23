
package com.androidprofit;

import java.io.File;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
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
import android.widget.Toast;

import com.androidprofit.AppAddedBoardcast.onAppListenner;
import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;

public class DownloadTab {
	static final String[] MOBILE_OS = new String[] { "Android", "iOS", "WindowsMobile",
			"Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry", "Android", "iOS",
			"WindowsMobile", "Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry",
			"Android", "iOS", "WindowsMobile", "Blackberry", "Android", "iOS", "WindowsMobile",
			"Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry", "Android", "iOS",
			"WindowsMobile", "Blackberry", "Android", "iOS", "WindowsMobile", "Blackberry",
			"Android", "iOS", "WindowsMobile", "Blackberry" };

	static final String path = Environment.getExternalStorageDirectory() + File.separator
			+ Environment.DIRECTORY_DOWNLOADS;

	static final String MESSAGE_DOWNLOAD_FINISH = "Download apk %s finish";
	static final String MESSAGE_APP_STARTED = "App %s started";
	static final String MESSAGE_APP_RECORD = "App %s being recorded and cost %s";
	static final String TAG_DOWNLOAD = "download";

	public static ListView setMobileList(final Context ctx, ViewGroup root) {
		View.inflate(ctx, R.layout.view_listview, root);
		ListView list = (ListView)root.findViewById(R.id.list);
		list.setAdapter(new MobileAdapter(ctx, MOBILE_OS));
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final String download = "http://s.androidesk.com/apk/Androidesk-release-androidesk.apk";
				final String name = "androidesk.apk";
				download(ctx, Uri.parse(download), path, name);
			}
		});
		return list;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	/**
	 * 下载app
	 */
	public static void download(final Context ctx, Uri uri, String path, String name) {
		DownloadManager.Request request = new Request(uri);
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		request.setTitle(String.format(ctx.getString(R.string.alert_download), name));

		final String fullName = path + File.separator + name;
		File f = new File(fullName);
		request.setDestinationUri(Uri.fromFile(f));

		DownloadManager dm = (DownloadManager)ctx.getSystemService(Context.DOWNLOAD_SERVICE);
		long reference = dm.enqueue(request);
		DownloadBoardcast.register(ctx, reference, new DownloadBoardcast.onDownloadListenner() {
			@Override
			public void onComplated(long reference) {
				install(ctx, fullName);
				Log.i(TAG_DOWNLOAD, String.format(MESSAGE_DOWNLOAD_FINISH, fullName));
			}
		});
	}

	/**
	 * 安装app
	 * @param ctx
	 * @param path 安装的路径
	 */
	public static void install(final Context ctx, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
		AppAddedBoardcast.register( ctx, new onAppListenner() {
			@Override
			public void onComplated(String app) {
				Log.i(TAG_DOWNLOAD, String.format(MESSAGE_APP_STARTED, app));
				
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				boolean run = checkAppRuning(ctx, app);
				String message = run ? String.format("app %s is experience right", app) : "not experience"; 
				Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
				
				if(run) accountExperienceRecord(app);
			}
		});
	}
	
	/**
	 * 记录用户使用情况
	 * @param app 使用的app的包名
	 */
	private static void accountExperienceRecord(String app){
		//TODO 查询app的使用分值
		int cost = 10;
		
		Log.i(TAG_DOWNLOAD, String.format(MESSAGE_APP_RECORD, app, cost));
		//记录给用户
		AccountManager am = AccountManager.instance();
		Account account = am.getAccount();
		account.setMoney(account.getMoney() + cost);
	}

	private static boolean checkAppRuning(Context ctx, String pkg) {
		boolean isAppRunning = false;
		
		ActivityManager am = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(pkg)
					&& info.baseActivity.getPackageName().equals(pkg)) {
				isAppRunning = true;
				break;
			}
		}
		    
		return isAppRunning;
	}
}

/**
 * 下载app的监听广播，接收 DownloadManager.ACTION_DOWNLOAD_COMPLETE Action
 * 下载完成后调用回调onDownloadListenner的onComplated
 * @author sujoe
 *
 */
class DownloadBoardcast extends BroadcastReceiver {
	private final long mReference;
	private final onDownloadListenner mlistenner;

	public static interface onDownloadListenner {
		public void onComplated(long reference);
	}

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

/**
 * 安装app的监听广播，接收 Intent.ACTION_PACKAGE_ADDED Action
 * 下载完成后调用回调onAppListenner的onComplated
 * @author sujoe
 *
 */
class AppAddedBoardcast extends BroadcastReceiver {
	private final onAppListenner mlistenner;

	public static interface onAppListenner {
		public void onComplated(String app);
	}

	private AppAddedBoardcast(onAppListenner listenner) {
		mlistenner = listenner;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent == null) return;
		
		if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
			String packageName = intent.getData().toString().substring(8);
			if (mlistenner != null) mlistenner.onComplated(packageName);
		}
	}

	public static void register(Context ctx, onAppListenner listenner) {
		AppAddedBoardcast asb = new AppAddedBoardcast(listenner);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addDataScheme("package");

		ctx.registerReceiver(asb, filter);
	}
}
