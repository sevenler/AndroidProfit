
package com.androidprofit.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 获取手机上apk文件信息类，主要是判断是否安装再手机上了，安装的版本比较现有apk版本信息
 */
public class ApkUtil {
	/***
	 * 判断该应用在手机中的安装情况
	 */
	public static int checkInstallType(android.content.pm.PackageManager pm, PackageInfo apk) {
		HashMap<String, Integer> map = getInstallPackageMap(pm);
		int status = checkInstallType(map, apk);
		apk.setType(status);
		return status;
	}

	/**
	 * 判断apks列表是否安装
	 * 
	 * @param pm 使用Context.getPackageManager()得到
	 * @param apks
	 */
	public static void checkInstallType(android.content.pm.PackageManager pm, PackageInfo[] apks) {
		HashMap<String, Integer> map = getInstallPackageMap(pm);
		for (PackageInfo pkg : apks) {
			int status = checkInstallType(map, pkg);
			pkg.setType(status);
		}
	}

	/**
	 * 过滤已经安装的应用包
	 * @param pm 使用Context.getPackageManager()得到
	 * @param apks 
	 * @return
	 */
	public static PackageInfo[] filterInstallPackages(android.content.pm.PackageManager pm,
			PackageInfo[] apks) {
		checkInstallType(pm, apks);

		ArrayList<PackageInfo> result = new ArrayList<PackageInfo>();
		for (PackageInfo pkg : apks) {
			if(pkg.getType() == PackageInfo.UNINSTALLED){
				result.add(pkg);
			}
		}
		PackageInfo[] notInstalledPkgs = result.toArray(new PackageInfo[result.size()]);
		return notInstalledPkgs;
	}

	/**
	 * 检查包的安装状态
	 * 
	 * @param map HashMap<Packagename，VersionCode>
	 * @param pkg 包信息
	 * @return
	 */
	private static int checkInstallType(HashMap<String, Integer> map, PackageInfo pkg) {
		int vercode = pkg.getVercode();
		String apkname = pkg.getPck();

		int result = PackageInfo.UNINSTALLED;
		if (map.containsKey(apkname)) {
			int code = map.get(apkname);
			result = PackageInfo.INSTALLED;
			if (vercode > code) {
				result = PackageInfo.INSTALLED_UPDATE;
			}
		}
		return result;
	}

	/**
	 * 获取所有的安装的包
	 * 
	 * @param pm 使用Context.getPackageManager()得到
	 * @return HashMap<Packagename，VersionCode>
	 */
	private static HashMap<String, Integer> getInstallPackageMap(
			android.content.pm.PackageManager pm) {
		HashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		List<android.content.pm.PackageInfo> pakageinfos = pm
				.getInstalledPackages(android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES);
		for (android.content.pm.PackageInfo pi : pakageinfos) {
			map.put(pi.packageName, pi.versionCode);
		}
		return map;
	}
}
