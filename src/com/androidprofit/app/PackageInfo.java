
package com.androidprofit.app;

import com.androidprofit.Util;

public class PackageInfo {
	public static int INSTALLED = 0; // 表示已经安装，且跟现在这个apk文件是一个版本
	public static int UNINSTALLED = 1; // 表示未安装
	public static int INSTALLED_UPDATE = 2; // 表示已经安装，版本比现在这个版本要低，可以点击按钮更新
	public static int UNKONWN = -1;// 未知

	private final String pck;// 应用包名
	private final String name;// 应用名称
	private final String vername;// 版本名称
	private final int vercode;// 版本号
	private final String description;// 描述

	private final int size;// apk包大小
	private final String url;// 下载链接

	private final float cost;// 体验收益
	private final int time;// 体验时间，单位s

	private int type;// 应用安装情况

	PackageInfo(String pck, String name, String vername, int vercode, int size, String url,
			String description, float cost, int time) {
		this(pck, name, vername, vercode, size, url, description, cost, time, UNKONWN);
	}

	PackageInfo(String pck, String name, String vername, int vercode, int size, String url,
			String description, float cost, int time, int type) {
		super();
		this.pck = pck;
		this.name = name;
		this.vername = vername;
		this.vercode = vercode;
		this.size = size;
		this.url = url;
		this.description = description;
		this.cost = cost;
		this.time = time;
		this.type = type;
	}

	public String getPck() {
		return pck;
	}

	public String getName() {
		return name;
	}

	public String getVername() {
		return vername;
	}

	public int getVercode() {
		return vercode;
	}

	public String getDescription() {
		return description;
	}

	public int getSize() {
		return size;
	}

	public String getFormatedSize() {
		return Util.Format.sizeUnit(size);
	}

	public String getUrl() {
		return url;
	}

	public float getCost() {
		return cost;
	}

	public int getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public String getFormatType() {
		return (type == INSTALLED ? "INSTALLED" : (type == UNINSTALLED ? "UNINSTALLED"
				: (type == INSTALLED_UPDATE ? "INSTALLED_UPDATE" : "UNKONWN")));
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PackageInfo [pck=" + pck + ", name=" + name + ", vername=" + vername + ", vercode="
				+ vercode + ", description=" + description + ", size=" + getFormatedSize()
				+ ", url=" + url + ", cost=" + cost + ", time=" + time + ", type=" + type + "]";
	}
}
