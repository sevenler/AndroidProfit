
package com.androidprofit;

import java.io.File;
import java.util.Date;
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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.androidprofit.AppAddedBoardcast.onAppListenner;
import com.androidprofit.app.PackageInfo;
import com.androidprofit.app.PackageManager;
import com.androidprofit.image.ImageCacheManager;
import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;
import com.androidprofit.user.Record;

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

	static final String MESSAGE_DOWNLOAD_FINISH = "Download apk %s finish";
	static final String MESSAGE_APP_STARTED = "App %s started";
	static final String MESSAGE_APP_RECORD = "App %s being recorded and cost %s";
	static final String TAG_DOWNLOAD = "download";

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

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	/**
	 * 下载app
	 */
	public static void downloadAndInstallApk(final Context ctx, String downloadPath,
			final PackageInfo pkg) {
		DownloadManager.Request request = new Request(Uri.parse(pkg.getUrl()));
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		request.setTitle(String.format(ctx.getString(R.string.alert_download), pkg.getName()));

		final String fullName = downloadPath + File.separator
				+ String.format("%s.apk", pkg.getName());
		File f = new File(fullName);
		request.setDestinationUri(Uri.fromFile(f));

		DownloadManager dm = (DownloadManager)ctx.getSystemService(Context.DOWNLOAD_SERVICE);
		long reference = dm.enqueue(request);
		DownloadBoardcast.registerAndAutoUnRegester(ctx, reference,
				new DownloadBoardcast.onDownloadListenner() {
					@Override
					public void onComplated(long reference) {
						install(ctx, pkg, fullName);
						Log.i(TAG_DOWNLOAD, String.format(MESSAGE_DOWNLOAD_FINISH, fullName));
					}
				});
	}

	/**
	 * 安装app
	 * 
	 * @param ctx
	 * @param path 安装的路径
	 */
	public static void install(final Context ctx, final PackageInfo pkg, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
		AppAddedBoardcast.registerAndAutoUnRegister(ctx, new onAppListenner() {
			@Override
			public void onComplated(String app) {
				Log.i(TAG_DOWNLOAD, String.format(MESSAGE_APP_STARTED, app));

				checkExperience(ctx, pkg);
			}
		});
	}

	/**
	 * 检测用户的体验是否满足要求
	 * 
	 * @param ctx
	 * @param app
	 */
	private static void checkExperience(Context ctx, PackageInfo pkg) {
		try {
			Thread.sleep(pkg.getExperienceTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean run = checkAppRuning(ctx, pkg.getPck());
		String message = run ? String.format("app %s is experience right", pkg.getPck())
				: "not experience";
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();

		if (run) accountExperienceRecord(pkg);
	}

	/**
	 * 记录用户使用情况
	 * 
	 * @param app 使用的app的包名
	 */
	private static void accountExperienceRecord(PackageInfo pkg) {
		// TODO 查询app的使用分值
		int cost = 10;

		Log.i(TAG_DOWNLOAD, String.format(MESSAGE_APP_RECORD, pkg.getPck(), cost));
		// 记录给用户
		AccountManager am = AccountManager.instance();
		Account account = am.getAccount();
		account.setMoney(account.getMoney() + cost);
		account.getExperience().addExperience(
				new Record(pkg.getPck(), pkg.getName(), new Date(), pkg.getCost()));
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

/**
 * 下载app的监听广播，接收 DownloadManager.ACTION_DOWNLOAD_COMPLETE Action
 * 下载完成后调用回调onDownloadListenner的onComplated
 * 
 * @author sujoe
 */
class DownloadBoardcast extends BroadcastReceiver {
	private final long mReference;
	private final onDownloadListenner mlistenner;
	private boolean mAutoUnRegister;

	public static interface onDownloadListenner {
		public void onComplated(long reference);
	}

	private DownloadBoardcast(long reference, onDownloadListenner listenner) {
		this(reference, listenner, false);
	}

	private DownloadBoardcast(long reference, onDownloadListenner listenner, boolean autoUnRegister) {
		mReference = reference;
		mlistenner = listenner;
		mAutoUnRegister = autoUnRegister;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		if ((mlistenner != null) && (reference == mReference)) mlistenner.onComplated(reference);
		if (mAutoUnRegister == true) context.unregisterReceiver(this);
	}

	public static void registerAndAutoUnRegester(Context ctx, long reference,
			onDownloadListenner listenner) {
		DownloadBoardcast db = new DownloadBoardcast(reference, listenner, true);

		IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		ctx.registerReceiver(db, filter);
	}
}

/**
 * 安装app的监听广播，接收 Intent.ACTION_PACKAGE_ADDED Action
 * 下载完成后调用回调onAppListenner的onComplated
 * 
 * @author sujoe
 */
class AppAddedBoardcast extends BroadcastReceiver {
	private final onAppListenner mlistenner;
	private boolean mAutoUnRegister;

	public static interface onAppListenner {
		public void onComplated(String app);
	}

	private AppAddedBoardcast(onAppListenner listenner) {
		this(listenner, false);
	}

	private AppAddedBoardcast(onAppListenner listenner, boolean autoUnRegister) {
		mlistenner = listenner;
		mAutoUnRegister = autoUnRegister;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent == null) return;

		if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
			String packageName = intent.getData().toString().substring(8);
			if (mlistenner != null) mlistenner.onComplated(packageName);
			if (mAutoUnRegister == true) context.unregisterReceiver(this);
		}
	}

	public static void registerAndAutoUnRegister(Context ctx, onAppListenner listenner) {
		AppAddedBoardcast asb = new AppAddedBoardcast(listenner, true);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addDataScheme("package");

		ctx.registerReceiver(asb, filter);
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
