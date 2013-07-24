
package com.androidprofit.user;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户使用记录
 * 
 * @author sujoe
 */
public class Experience {
	private final String uid;
	private List<Record> experience = new LinkedList<Record>();
	private List<Exchange> exchange = new LinkedList<Exchange>();
	
	public String getUId() {
		return uid;
	}

	Experience(String id) {
		uid = id;
	}

	public void addExperience(Record record) {
		experience.add(record);
	}

	public void addExchange(Exchange exchange) {
		this.exchange.add(exchange);
	}

	@Override
	public String toString() {
		return "Experience [UId=" + uid + ", experience=" + experience + ", exchange=" + exchange
				+ "]";
	}
}
