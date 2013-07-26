
package com.androidprofit.app;

import java.util.Arrays;

import android.test.AndroidTestCase;

import com.google.gson.Gson;

public class TextCase extends AndroidTestCase {
	public void testMothed() {

		PackageInfo[] pkgs = {
				new PackageInfo(
						"com.androidesk",
						"安卓壁纸",
						"3.0",
						25,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60),
				new PackageInfo(
						"com.androidesk",
						"安卓壁纸",
						"3.0",
						651,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60),
				new PackageInfo(
						"com.androidesk1",
						"安卓壁纸",
						"3.0",
						65,
						1800000,
						"http://apps.wandoujia.com/apps/com.androidesk/download?pos=www/detail",
						"安卓壁纸,无广告安全绿色,是排名第一的手机壁纸美化软件. <br /><br />我们专注于为安卓手机提供免费高清手机壁纸,优质动态壁纸等美化服务! <br /><br />安卓壁纸之最： <br /><br />全球用户量第一的壁纸软件； <br /><br />拥有30万+高清壁纸,位居全国榜首； <br /><br />每日1000+更新,精挑细选最用心； <br /><br />300种不同分类,专辑,一眼发现大爱； <br /><br />网罗全球最优秀动态壁纸,手机随心而变! <br /><br />主要功能： <br /><br />一键设置,自动匹配； <br /><br />下载壁纸,高清免费； <br /><br />搜索功能,有求壁应； <br /><br />标签互动,趣味升级； <br /><br />在线收藏,更省流量 <br />",
						10, 60) };

		Gson gson = new Gson();
		String string = gson.toJson(pkgs);
		System.out.println(String.format("gson: %s", string));

		PackageInfo[] pi = gson.fromJson(string, PackageInfo[].class);
		System.out.println(String.format("PackageInfo:%s", Arrays.toString(pi)));
		
		android.content.pm.PackageManager pm = getContext().getPackageManager();
		PackageInfo pck = pkgs[0];
		
		System.out.println(String.format("getType:%s", pck.getFormatType()));
		ApkUtil.checkInstallType(pm, pck);
		System.out.println(String.format("getType:%s", pck.getFormatType()));
		
		pck = pkgs[1];
		ApkUtil.checkInstallType(pm, pck);
		System.out.println(String.format("getType:%s", pck.getFormatType()));
		
		pck = pkgs[2];
		ApkUtil.checkInstallType(pm, pck);
		System.out.println(String.format("getType:%s", pck.getFormatType()));
	}
}
