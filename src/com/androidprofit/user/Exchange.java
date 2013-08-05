
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
	private final int type;//兑换类型
	private final String num;//兑换的帐号
	
	public static final int EXCHANGE_TYPE_APLIY = 0;
	public static final int EXCHANGE_TYPE_MOBILE = 1;
	public static final int EXCHANGE_TYPE_TENCENTQQ = 2;
	
	public static final int EXCHANGE_STATUS_SUBMIT = 0;
	public static final int EXCHANGE_STATUS_SUCCESS = 1;

	public Exchange(Date time, int status, int type, float worth, String num) {
		super();
		this.time = time;
		this.status = status;
		this.type = type;
		this.worth = worth;
		this.num = num;
	}

	public String getNum() {
		return num;
	}

	public Date getTime() {
		return time;
	}
	
	public int getType() {
		return type;
	}

	public int getStatus() {
		return status;
	}

	public float getWorth() {
		return worth;
	}

	@Override
	public String toString() {
		return "Exchange [time=" + time + ", status=" + status + ", worth=" + worth + ", type="
				+ type + "]";
	}
}
