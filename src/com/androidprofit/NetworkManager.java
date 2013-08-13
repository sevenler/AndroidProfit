package com.androidprofit;

import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class NetworkManager {
	private static NetworkManager instance = null;

	private NetworkManager() {
	}

	public static NetworkManager instance() {
		if (instance == null) {
			synchronized (NetworkManager.class) {
				if (instance == null) {
					instance = new NetworkManager();
				}
			}
		}
		return instance;
	}
	
	public static final String DISABLED = "DISABLED"; // 网络不可用
	public static final String WIFI = "WIFI"; // wifi网络
	public static final String MOBILE = "MOBILE"; // 电信,移动,联通,等mobile网络
	public static final String CMWAP = "CMWAP"; // 移动wap
	public static final String UNIWAP = "UNIWAP"; // 联通wap
	public static final String CTWAP = "CTWAP"; // 电信wap
	public static final String OTHER = "OTHER"; // 其它未知网络
	public static final String GPRS = "GPRS";// 2G/3G 网络
	
	private static Uri URI_PREFERAPN = Uri.parse("content://telephony/carriers/preferapn");

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private  boolean is3G(int networkType, Context context) {
		return (networkType == TelephonyManager.NETWORK_TYPE_UMTS
				|| networkType == TelephonyManager.NETWORK_TYPE_HSDPA
				|| networkType == TelephonyManager.NETWORK_TYPE_EVDO_0
				|| networkType == TelephonyManager.NETWORK_TYPE_EVDO_A
				|| networkType == TelephonyManager.NETWORK_TYPE_EVDO_B || networkType == TelephonyManager.NETWORK_TYPE_HSPAP);
	}

	private  boolean is2G(int networkType, Context context) {
		// NETWORK_TYPE_UMTS 网络类型为UMTS
		// NETWORK_TYPE_EVDO_0 网络类型为EVDO0
		// NETWORK_TYPE_EVDO_A 网络类型为EVDOA
		// NETWORK_TYPE_HSDPA 网络类型为HSDPA

		// NETWORK_TYPE_CDMA 网络类型为CDMA
		// NETWORK_TYPE_EDGE 网络类型为EDGE
		// NETWORK_TYPE_GPRS 网络类型为GPRS
		//
		// 联通的3G为UMTS、HSPAP或HSDPA，电信的3G为EVDO,EVDO_A,EVDO_B,
		// 移动和联通的2G为GPRS或EDGE，电信的2G为CDMA
		// 参考于 http://wyoojune.blog.163.com/blog/static/57093325201332105128248/
		// http://www.cnblogs.com/lee0oo0/archive/2013/05/20/3089906.html
		// http://hi.baidu.com/neverever888/item/0c37d436553ddc1d9cc65ee0
		// TODO 移动的3G不知道什么值
		return (networkType == TelephonyManager.NETWORK_TYPE_GPRS
				|| networkType == TelephonyManager.NETWORK_TYPE_EDGE || networkType == TelephonyManager.NETWORK_TYPE_CDMA);
	}

	/**
	 * 获取网络的2G，3G类型
	 * 
	 * @param context
	 * @return 2G、3G、unknown、wifi或者未连接的情况下返回空字符
	 */
	private String getNetworkTypeGeneration(Context context,String type) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);//平板电脑 可能获取不到
		try {
			if (telephonyManager==null) {
				return "unknown";
			}
			int networkType = telephonyManager.getNetworkType();
			if (is3G(networkType, context)) {
				return "3G";
			} else if (is2G(networkType, context)) {
				return "2G";
			}
		} catch (Exception e) {
		}
		return "unknown";
	}

	public String getNetworkTypeAll(Context context) {
		String type=getNetworkType(context);
		return String.format("%s_%s", type,
				getNetworkTypeGeneration(context,type));
	}

	/**
	 * 网络类型： 0.无网络 1.wifi 2.net网络 3.移动wap 4.联通wap 4.电信wap 5.未知网络
	 **/
	public String getNetworkType(Context context) {
		try {
			ConnectivityManager connect = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo actNetInfo = connect.getActiveNetworkInfo();
			if (actNetInfo == null) {
				return DISABLED;
			}
			if (!actNetInfo.isAvailable()) {
				return DISABLED;
			}
			int netType = actNetInfo.getType();
			if (netType == ConnectivityManager.TYPE_WIFI) {
				return WIFI;
			}
			if (netType != ConnectivityManager.TYPE_MOBILE) {
				return MOBILE;
			}
			Cursor cursor = context.getContentResolver().query(URI_PREFERAPN, null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				String user = cursor.getString(cursor.getColumnIndex("user"));
				if (!TextUtils.isEmpty(user)) {
					cursor.getString(cursor.getColumnIndex("proxy"));
					if (user.startsWith("ctwap")) {
						return CTWAP;
					}
				}
			}
			cursor.close();
			String extrainfo = actNetInfo.getExtraInfo();
			if (extrainfo != null) {
				Locale current = context.getResources().getConfiguration().locale;
				extrainfo = extrainfo.toLowerCase(current);
				// 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
				if (extrainfo.equals("cmwap")) {
					return CMWAP;
				}
				if (extrainfo.equals("uniwap")) {
					return UNIWAP;
				}
				if (extrainfo.equals("uninet")) {
					return CTWAP;
				}
			}
			return MOBILE;
		} catch (Exception e) {
			return OTHER;
		}
	}

	/**
	 * 检查是否有可用网络
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkAvailable(Context context) {
		return isWifiConnected(context) || isMobileConnected(context);
	}

	/**
	 * 检查WIFI是否连接
	 * 
	 * @param context
	 * @return 
	 */
	public boolean isWifiConnected(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifi = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifi != null && wifi.isConnected()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 2G/3G是否连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isMobileConnected(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobile = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobile != null && mobile.isConnected()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
}
