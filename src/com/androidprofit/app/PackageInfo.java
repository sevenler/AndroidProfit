
package com.androidprofit.app;

import android.os.Parcel;
import android.os.Parcelable;

import com.androidprofit.Util;

public class PackageInfo implements Parcelable {
	public static int INSTALLED = 0; // 表示已经安装，且跟现在这个apk文件是一个版本
	public static int UNINSTALLED = 1; // 表示未安装
	public static int INSTALLED_UPDATE = 2; // 表示已经安装，版本比现在这个版本要低，可以点击按钮更新
	public static int UNKONWN = -1;// 未知

	private String pck;// 应用包名
	private String name;// 应用名称
	private String vername;// 版本名称
	private int vercode;// 版本号
	private String description;// 描述

	private int size;// apk包大小
	private String url;// 下载链接
	private String icon;// 图标地址
	private String iconThumb;// 图标地址缩略图

	private float cost;// 体验收益
	private int experienceTime;// 体验时间，单位s

	private int type;// 应用安装情况

	PackageInfo() {

	}

	PackageInfo(String pck, String name, String vername, int vercode) {
		this(pck, name, vername, vercode, 0, null, null, null, null, 0, 0);
	}

	PackageInfo(String pck, String name, String vername, int vercode, int size, String url,
			String icon, String iconThumb, String description, float cost, int time) {
		this(pck, name, vername, vercode, size, url, icon, iconThumb, description, cost, time,
				UNKONWN);
	}

	PackageInfo(String pck, String name, String vername, int vercode, int size, String url,
			String icon, String iconThumb, String description, float cost, int time, int type) {
		super();
		this.pck = pck;
		this.name = name;
		this.vername = vername;
		this.vercode = vercode;
		this.size = size;
		this.url = url;
		this.icon = icon;
		this.iconThumb = iconThumb;
		this.description = description;
		this.cost = cost;
		this.experienceTime = time;
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

	public String getIcon() {
		return icon;
	}

	public String getIconThumb() {
		return iconThumb;
	}

	public float getCost() {
		return cost;
	}

	public int getType() {
		return type;
	}

	public int getExperienceTime() {
		return experienceTime;
	}

	public void setExperienceTime(int experienceTime) {
		this.experienceTime = experienceTime;
	}

	public String getFormatType() {
		return (type == INSTALLED ? "INSTALLED" : (type == UNINSTALLED ? "UNINSTALLED"
				: (type == INSTALLED_UPDATE ? "INSTALLED_UPDATE" : "UNKONWN")));
	}

	public void setType(int type) {
		this.type = type;
	}

	public static void setINSTALLED(int iNSTALLED) {
		INSTALLED = iNSTALLED;
	}

	public static void setUNINSTALLED(int uNINSTALLED) {
		UNINSTALLED = uNINSTALLED;
	}

	public static void setINSTALLED_UPDATE(int iNSTALLED_UPDATE) {
		INSTALLED_UPDATE = iNSTALLED_UPDATE;
	}

	public static void setUNKONWN(int uNKONWN) {
		UNKONWN = uNKONWN;
	}

	public void setPck(String pck) {
		this.pck = pck;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVername(String vername) {
		this.vername = vername;
	}

	public void setVercode(int vercode) {
		this.vercode = vercode;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIconThumb(String iconThumb) {
		this.iconThumb = iconThumb;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "PackageInfo [pck=" + pck + ", name=" + name + ", vername=" + vername + ", vercode="
				+ vercode + ", description=" + description + ", size=" + getFormatedSize()
				+ ", url=" + url + ", cost=" + cost + ", time=" + experienceTime + ", type=" + type
				+ "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(pck);
		dest.writeValue(name);
		dest.writeValue(vername);
		dest.writeValue(vercode);
		dest.writeValue(size);
		dest.writeValue(url);
		dest.writeValue(icon);
		dest.writeValue(iconThumb);
		dest.writeValue(description);
		dest.writeValue(cost);
		dest.writeValue(experienceTime);
		dest.writeValue(type);
	}

	public static final Parcelable.Creator<PackageInfo> CREATOR = new Parcelable.Creator<PackageInfo>() {
		public PackageInfo createFromParcel(Parcel source) {
			final PackageInfo f = new PackageInfo();
			f.pck = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.name = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.vername = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.vercode = (Integer)source.readValue(PackageInfo.class.getClassLoader());
			f.size = (Integer)source.readValue(PackageInfo.class.getClassLoader());
			f.url = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.icon = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.iconThumb = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.description = (String)source.readValue(PackageInfo.class.getClassLoader());
			f.cost = (Float)source.readValue(PackageInfo.class.getClassLoader());
			f.experienceTime = (Integer)source.readValue(PackageInfo.class.getClassLoader());
			f.type = (Integer)source.readValue(PackageInfo.class.getClassLoader());
			return f;
		}

		public PackageInfo[] newArray(int size) {
			throw new UnsupportedOperationException();
		}

	};
}
