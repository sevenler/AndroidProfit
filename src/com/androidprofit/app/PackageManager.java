
package com.androidprofit.app;

import java.io.File;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.Toast;

import com.androidprofit.R;
import com.androidprofit.Util;
import com.androidprofit.app.AppAddedBoardcast.onAppListenner;
import com.androidprofit.user.Account;
import com.androidprofit.user.AccountManager;
import com.androidprofit.user.Analyzer;
import com.androidprofit.user.Experience;
import com.androidprofit.user.Record;

public class PackageManager {
	private static PackageManager mPackageInfoManager;

	public static PackageManager instance() {
		if (mPackageInfoManager == null) mPackageInfoManager = new PackageManager();

		return mPackageInfoManager;
	}

	private static final String MESSAGE_DOWNLOAD_FINISH = "Download apk %s finish";
	private static final String MESSAGE_APP_STARTED = "App %s started";
	private static final String MESSAGE_APP_RECORD = "App %s being recorded and cost %s";
	public static final String TAG_DOWNLOAD = "download";
	
	public static final String DEFEAULT_APP_DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + File.separator
	+ Environment.DIRECTORY_DOWNLOADS;
	
	private PackageInfo[] mPackageInfo;
	private Context mContext;

	private PackageManager() {
		mPackageInfo = loadDefaultPackages();
	}

	public Context getContext() {
		return mContext;
	}

	public void init(Context ctx) {
		this.mContext = ctx;
	}

	public PackageInfo[] getPackages() {
		// 每次读取的时候过滤掉安装的应用
		PackageInfo[] pkgs = mPackageInfo;
		if (mContext != null) {
			Account account = com.androidprofit.user.AccountManager.instance().getAccount();
			Experience exp = account.getExperience();
			pkgs = ApkUtil.filterInstallAndUnExperiencePackages(mContext.getPackageManager(), exp,
					pkgs);
		} else {
			Log.e("PackageManager", " PackageManager's context not initalized ",
					new ExceptionInInitializerError());
		}

		return pkgs;
	}

	public void setPackages(PackageInfo[] mPackageInfo) {
		this.mPackageInfo = mPackageInfo;
	}

	PackageInfo[] loadDefaultPackages() {
		PackageInfo[] pkgs = {
				new PackageInfo(
						"com.androidesk",
						"安卓壁纸",
						"3.0",
						65,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://img.wdjimg.com/mms/icon/v1/4/b6/15a570a7afdaa94d8def8800fd0c5b64_256_256.png",
						"http://img.wdjimg.com/mms/icon/v1/4/b6/15a570a7afdaa94d8def8800fd0c5b64_256_256.png",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 10 * 1000),
				new PackageInfo(
						"com.tencent.mm",
						"微信",
						"4.5",
						290,
						1800000,
						"http://apps.wandoujia.com/apps/com.tencent.mm/download?pos=www/detail",
						"http://img.wdjimg.com/mms/icon/v1/c/6b/9714706da4d9b3870dfa16a22cceb6bc_256_256.png",
						"http://img.wdjimg.com/mms/icon/v1/c/6b/9714706da4d9b3870dfa16a22cceb6bc_256_256.png",
						"微信，超过3亿人使用，曾在27个国家和地区的App Store排行榜上排名第一。 <br />微信能够通过手机网络给好友发送语音、文字消息、表情、图片和视频，你还可以分享照片到朋友圈。通过摇一摇、查看附近的人，你可以认识新的朋友。使用扫一扫，你可以扫描二维码。与公众号互动，你还能够听明星唱歌、看新闻、设提醒…… <br />微信在iPhone、Android、Symbian、Windows Phone、BlackBerry等手机平台上都可以使用，并提供有多种语言界面。",
						10, 10 * 1000),
				new PackageInfo(
						"com.douban.movie",
						"豆瓣电影",
						"3.0",
						65,
						1800000,
						"http://apps.wandoujia.com/apps/com.douban.movie/download?pos=www/detail",
						"http://img.wdjimg.com/mms/icon/v1/9/f4/e2fed53a563d696d990484de34729f49_256_256.png",
						"http://img.wdjimg.com/mms/icon/v1/9/f4/e2fed53a563d696d990484de34729f49_256_256.png",
						"看电影，请只带上愉悦的心<br />主要功能 <br />- 权威的电影评分br />- 购票简单，支付轻松，支持支付宝付款。",
						10, 10 * 1000),
				new PackageInfo(
						"com.androideskddd",
						"UC浏览器",
						"3.0",
						65,
						1800000,
						"http://apps.wandoujia.com/apps/com.UCMobile/download?pos=www/detail",
						"http://img.wdjimg.com/mms/icon/v1/a/5f/2fc8afed32938550693c79136f5b15fa_256_256.png",
						"http://img.wdjimg.com/mms/icon/v1/a/5f/2fc8afed32938550693c79136f5b15fa_256_256.png",
						"UC浏览器 读小说 看视频.上微博.玩游戏.网上购物等都能享受最流畅的移动互联网体验",
						10, 10 * 1000) };
		
		return pkgs;
	}
	
	/**
	 * 下载app并安装
	 *  @param context 上下文环境 内部在使用context.startActivity().如果context不是Activity，则会报错
	 * @param pkg 安装包的信息
	 */
	public void downloadAndInstallApk(final Activity context,  final PackageInfo pkg) {
		downloadAndInstallApk(context, DEFEAULT_APP_DOWNLOAD_PATH, pkg);
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	/**
	 * 下载app并安装
	 *  @param context 上下文环境 内部在使用context.startActivity().如果context不是Activity，则会报错
	 *  @param downloadPath 安装的路径
	 * @param pkg 安装包的信息
	 */
	public void downloadAndInstallApk(final Activity context, String downloadPath, final PackageInfo pkg) {
        Analyzer.analysePackage(context, pkg, Analyzer.EVENT_TYPE_DOWNLOAD);

		DownloadManager.Request request = new Request(Uri.parse(pkg.getUrl()));
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		request.setTitle(String.format(context.getString(R.string.alert_download), pkg.getName()));

		final String fullName = downloadPath + File.separator
				+ String.format("%s.apk", pkg.getName());
		File f = new File(fullName);
		request.setDestinationUri(Uri.fromFile(f));

		DownloadManager dm = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
		long reference = dm.enqueue(request);
		DownloadBoardcast.registerAndAutoUnRegester(context, reference,
				new DownloadBoardcast.onDownloadListenner() {
					@Override
					public void onComplated(long reference) {
						install(context, pkg, fullName);
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
	public void install(final Context context, final PackageInfo pkg, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
		AppAddedBoardcast.registerAndAutoUnRegister(context, new onAppListenner() {
			@Override
			public void onComplated(String app) {
                String message = String.format("experience the app in %s seconed", pkg.getExperienceTime());
                Util.MyToast(context, message, Toast.LENGTH_SHORT).show();
				Log.i(TAG_DOWNLOAD, String.format(MESSAGE_APP_STARTED, app));

				checkExperience(context, pkg);
			}
		});
	}

	/**
	 * 检测用户的体验是否满足要求
	 * 
	 * @param ctx
	 * @param pkg 安装包的信息
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
        Util.MyToast(ctx, message, Toast.LENGTH_SHORT).show();

		if (run) {
            Analyzer.analysePackage(ctx, pkg, Analyzer.EVENT_TYPE_INSTALLED);
            accountExperienceRecord(pkg);
        }
	}

	/**
	 * 记录用户使用情况
	 * 
	 * @param pkg 安装包的信息
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

	/**
	 * 检查应用pkg是否在运行
	 * @param ctx
	 * @param pkg 应用的包名
	 * @return
	 */
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
 * 安装app的监听广播，接收 Intent.ACTION_PACKAGE_ADDED Action
 * 下载完成后调用回调onAppListenner的onComplated
 * 
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

/**
 * 下载app的监听广播，接收 DownloadManager.ACTION_DOWNLOAD_COMPLETE Action
 * 下载完成后调用回调onDownloadListenner的onComplated
 * 
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
