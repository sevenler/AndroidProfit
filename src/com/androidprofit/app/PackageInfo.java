
package com.androidprofit.app;

import com.androidprofit.Util;

public class PackageInfo {
	private final String pck;//应用包名
	private final String name;//应用名称
	private final String vername;//版本名称
	private final int vercode;//版本号
	private final String description;//描述

	private final int size;//apk包大小
	private final String url;//下载链接
	
	private final float cost;//体验收益
	private final int time;//体验时间，单位s

	PackageInfo(String pck, String name, String vername, int vercode, int size,
			String url, String description, float cost, int time) {
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
	
	public String getFormatedSize(){
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

	@Override
	public String toString() {
		return "PackageInfo [pck=" + pck + ", name=" + name + ", vername=" + vername + ", vercode="
				+ vercode + ", description=" + description + ", size=" + getFormatedSize() + ", url=" + url
				+ ", cost=" + cost + ", time=" + time + "]";
	}
}
