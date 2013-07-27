
package com.androidprofit.app;

import android.content.Context;
import android.util.Log;

public class PackageManager {
	private static PackageManager mPackageInfoManager;

	public static PackageManager instance() {
		if (mPackageInfoManager == null) mPackageInfoManager = new PackageManager();

		return mPackageInfoManager;
	}

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
			pkgs = ApkUtil.filterInstallPackages(mContext.getPackageManager(), pkgs);
		} else {
			Log.e("PackageManager", " PackageManager's context not initalized ",
					new ExceptionInInitializerError());
		}

		return pkgs;
	}

	public void setPackages(PackageInfo[] mPackageInfo) {
		this.mPackageInfo = mPackageInfo;
	}

	private PackageInfo[] loadDefaultPackages() {
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
						"看电影，请只带上愉悦的心情，其他的交给豆瓣电影吧！ <br />观影必备APP，汇聚千万影迷的真实评分、评论 <br />即刻下载，享受专属特惠影票，更有好座位为你预留！ <br /><br />主要功能 <br />- 权威的电影评分和精彩影评，千万影迷的真实观影感受，为你的观影做决策，“看什么”不再是问题； <br />- 手机直接购票，还可提前选定好座位。现场排长队还选不到好座位的情况再也不会发生了； <br />- 票价优惠，并不定期举办免费或特惠观影活动； <br />- 提供超过2500家影院的实时放映时间表，随时随地查看影片场次； <br />- 可定位离自己最近的电影院，实时获取影片放映时间和可购票场次； <br />- 购票简单，支付轻松，支持支付宝付款。",
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
						"UC浏览器是一款全球领先的智能手机浏览器，拥有独创的U3内核和云端技术，完美以支持HTML5应用，具有智能、极速、安全，易扩展等特性，让您在阅资讯、读小说、看视频、上微博、玩游戏、网上购物等都能享受最流畅的移动互联网体验。",
						10, 10 * 1000) };
		return pkgs;
	}

}
