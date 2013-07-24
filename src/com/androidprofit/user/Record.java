
package com.androidprofit.user;

import java.util.Date;

/**
 * 体验记录
 * 
 * @author sujoe
 */
public class Record {
	private final String pkg;// 包名
	private final Date time;// 操作时间
	private final float cost;// 价值

	public Record(String pkg, Date time, float cost) {
		super();
		this.pkg = pkg;
		this.time = time;
		this.cost = cost;
	}

	public String getPkg() {
		return pkg;
	}

	public Date getTime() {
		return time;
	}

	public float getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return "Record [pkg=" + pkg + ", time=" + time + ", cost=" + cost + "]";
	}
}
