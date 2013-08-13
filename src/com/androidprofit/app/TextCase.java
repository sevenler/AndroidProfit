
package com.androidprofit.app;

import java.util.Arrays;
import java.util.Date;

import android.test.AndroidTestCase;

import com.androidprofit.Util;
import com.androidprofit.user.Account;
import com.androidprofit.user.Experience;
import com.androidprofit.user.Record;
import com.google.gson.Gson;

public class TextCase extends AndroidTestCase {
	/**
	 * 测试ApkUtil的状态检查、根据状态过滤包
	 */
	public void testApkUtilCheckType() {

		PackageInfo[] pkgs = {
				new PackageInfo(
						"com.androidesk",
						"安卓壁纸",
						"3.0",
						25,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60, PackageInfo.UNKONWN | PackageInfo.EXPERIENCED),
				new PackageInfo(
						"com.androidesk",
						"安卓壁纸",
						"3.0",
						651,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60, PackageInfo.UNINSTALLED),
				new PackageInfo(
						"com.androidesk1",
						"安卓壁纸",
						"3.0",
						65,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60, PackageInfo.INSTALLED_UPDATE),
				new PackageInfo(
						"com.androidesk2",
						"安卓壁纸",
						"3.0",
						65,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60, PackageInfo.UNINSTALLED | PackageInfo.EXPERIENCED) };

		Gson gson = new Gson();
		String string = gson.toJson(pkgs);
		System.out.println(String.format("gson: %s", string));

		PackageInfo[] pi = gson.fromJson(string, PackageInfo[].class);
		System.out.println(String.format("PackageInfo:%s", Arrays.toString(pi)));

		android.content.pm.PackageManager pm = getContext().getPackageManager();
		PackageInfo pck = pkgs[0];

		Util.Assert(ApkUtil.checkType(pck.getType(), PackageInfo.UNKONWN));
		System.out.println(String.format("getType:%s", pck.getFormatType()));
		ApkUtil.checkInstallType(pm, pck);
		Util.Assert(ApkUtil.checkType(pck.getType(), PackageInfo.INSTALLED));
		Util.Assert(ApkUtil.checkType(pck.getType(), PackageInfo.EXPERIENCED));
		System.out.println(String.format("getType:%s", pck.getFormatType()));

		pck = pkgs[1];
		ApkUtil.checkInstallType(pm, pck);
		Util.Assert(ApkUtil.checkType(pck.getType(), PackageInfo.INSTALLED_UPDATE));
		System.out.println(String.format("getType:%s", pck.getFormatType()));

		pck = pkgs[2];
		ApkUtil.checkInstallType(pm, pck);
		Util.Assert(ApkUtil.checkType(pck.getType(), PackageInfo.UNINSTALLED));
		System.out.println(String.format("getType:%s", pck.getFormatType()));

		PackageInfo[] result = ApkUtil.filterInstallPackages(pm, pkgs);
		Util.Assert(result.length == 2);
		System.out.println(String.format("filterInstallPackages:%s", Arrays.toString(result)));

		Account account = com.androidprofit.user.AccountManager.instance().getAccount();
		Experience exp = account.getExperience();
		PackageInfo[] result1 = ApkUtil.filterInstallAndUnExperiencePackages(pm, exp, pkgs);
		Util.Assert(result1.length == 2);
		exp.addExperience(new Record("com.androidesk1", "安卓壁纸", new Date(), 10));
		result1 = ApkUtil.filterInstallAndUnExperiencePackages(pm, exp, pkgs);
		Util.Assert(result1.length == 1);
		System.out.println(String.format("filterInstallPackages:%s", Arrays.toString(result1)));
	}

	private static final int SIZE_NORMAL = 1 << 0;
	private static final int SIZE_HIGHT = 1 << 1;
	private static final int SIZE_LOWER = 1 << 2;

	public void testMothed1() {
		System.out.println(String.format("====================================="));
		int size = SIZE_NORMAL | SIZE_HIGHT | SIZE_LOWER;
		if ((SIZE_NORMAL & size) == SIZE_NORMAL) {
			System.out.println(String.format("%s", "SIZE_NORMAL", "SIZE_HIGHT", "SIZE_LOWER"));
		}
		if (((SIZE_NORMAL & size) == SIZE_NORMAL) && ((SIZE_HIGHT & size) == SIZE_HIGHT)) {
			System.out.println(String.format("%s %s", "SIZE_NORMAL", "SIZE_HIGHT", "SIZE_LOWER"));
		}

		size = size & ~SIZE_NORMAL;
		if ((SIZE_NORMAL & size) == SIZE_NORMAL) {
			System.out.println(String.format("%s", "SIZE_NORMAL", "SIZE_HIGHT", "SIZE_LOWER"));
		}
		if (((SIZE_HIGHT & size) == SIZE_HIGHT)) {
			System.out.println(String.format("%s", "SIZE_HIGHT", "SIZE_LOWER"));
		}
		System.out.println(String.format("====================================="));
	}
	
	public void testMothed2() {
		PackageManager pm = PackageManager.instance();
		PackageInfo[] pis = pm.loadDefaultPackages();
		
		Gson gson = new Gson();
		String string = gson.toJson(pis);
		System.out.println(String.format("%s", string));
	}
}
