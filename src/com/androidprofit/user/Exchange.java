
package com.androidprofit.user;

import java.util.Date;

/**
 * 兑换记录
 * 
 * @author sujoe
 */
public class Exchange {
	private final Date time;// 操作时间
	private final int status;// 状态
	private final float worth;// 兑换多少钱

	public Exchange(Date time, int status, float worth) {
		super();
		this.time = time;
		this.status = status;
		this.worth = worth;
	}

	public Date getTime() {
		return time;
	}

	public int getStatus() {
		return status;
	}

	public float getWorth() {
		return worth;
	}

	@Override
	public String toString() {
		return "Exchange [time=" + time + ", status=" + status + ", worth=" + worth + "]";
	}
}
