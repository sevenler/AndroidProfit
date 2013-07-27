
package com.androidprofit.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.androidprofit.user.Experience;
import com.androidprofit.user.Record;

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
	 * 判断apks列表是否体验过
	 * 
	 * @param exp 用户的app体验列表 Account.getExperience得到
	 * @param apks
	 */
	public static void checkExperienceType(Experience exp, PackageInfo[] apks) {
		HashMap<String, String> map = getExperienceMap(exp);
		for (PackageInfo pkg : apks) {
			int status = checkExperienceType(map, pkg);
			pkg.setType(status);
		}
	}

	/**
	 * 过滤已经安装的应用包
	 * 
	 * @param pm 使用Context.getPackageManager()得到
	 * @param apks
	 * @return
	 */
	public static PackageInfo[] filterInstallPackages(android.content.pm.PackageManager pm,
			PackageInfo[] apks) {
		checkInstallType(pm, apks);

		ArrayList<PackageInfo> result = new ArrayList<PackageInfo>();
		for (PackageInfo pkg : apks) {
			if (checkType(pkg.getType(), PackageInfo.UNINSTALLED)) {
				result.add(pkg);
			}
		}
		PackageInfo[] notInstalledPkgs = result.toArray(new PackageInfo[result.size()]);
		return notInstalledPkgs;
	}

	/**
	 * 过滤已经安装的并且没有体验过的包
	 * 
	 * @param pm 使用Context.getPackageManager()得到
	 * @param exp 用户的app体验记录
	 * @param apks 需要检测的app列表
	 * @return
	 */
	public static PackageInfo[] filterInstallAndUnExperiencePackages(
			android.content.pm.PackageManager pm, Experience exp, PackageInfo[] apks) {
		checkInstallType(pm, apks);
		checkExperienceType(exp, apks);

		ArrayList<PackageInfo> result = new ArrayList<PackageInfo>();
		for (PackageInfo pkg : apks) {
			if (pkg.getType() == PackageInfo.UNINSTALLED) {
				result.add(pkg);
			}
		}
		PackageInfo[] notInstalledPkgs = result.toArray(new PackageInfo[result.size()]);
		return notInstalledPkgs;
	}

	/**
	 * 检查包的类型是否符合条件
	 * 
	 * @param type PackageInfo.getType()获取
	 * @param condition 条件组合 例如：PackageInfo.INSTALLED | PackageInfo.EXPERIENCED
	 * @return
	 */
	public static boolean checkType(int type, int condition) {
		boolean result = false;
		if ((type & (condition)) == condition) {
			result = true;
		}
		return result;
	}

	/**
	 * 检查包的安装状态
	 * 
	 * @param map HashMap<Packagename，VersionCode>
	 * @param pkg 需要检测的包信息
	 * @return
	 */
	private static int checkInstallType(HashMap<String, Integer> map, PackageInfo pkg) {
		int vercode = pkg.getVercode();
		String apkname = pkg.getPck();

		// 重置安装状态位
		int result = (pkg.getType() & (PackageInfo.EXPERIENCED));
		if (map.containsKey(apkname)) {
			int code = map.get(apkname);
			if (vercode > code) {
				result = result | PackageInfo.INSTALLED_UPDATE;
			} else {
				result = result | PackageInfo.INSTALLED;
			}
		} else result = result | PackageInfo.UNINSTALLED;
		return result;
	}

	/**
	 * 检查包是否已经体验过
	 * 
	 * @param map 已经体验过的包Map HashMap<Packagename，Appname>
	 * @param pkg 需要检测的包名
	 * @return
	 */
	private static int checkExperienceType(HashMap<String, String> map, PackageInfo pkg) {
		String apkname = pkg.getPck();

		int result = pkg.getType() & ~PackageInfo.EXPERIENCED;
		if (map.containsKey(apkname)) {
			result = result | PackageInfo.EXPERIENCED;
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

	/**
	 * 通过用户安装体验列表生成已经体验过的应用map
	 * 
	 * @param exp 用户安装体验列表
	 * @return 应用map HashMap<Packagename，Appname>
	 */
	private static HashMap<String, String> getExperienceMap(Experience exp) {
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		Record[] experience = exp.getExperienceArray();
		for (Record re : experience) {
			map.put(re.getPkg(), re.getName());
		}
		return map;
	}
}
