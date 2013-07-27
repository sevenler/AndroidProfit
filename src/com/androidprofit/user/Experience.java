
package com.androidprofit.user;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;

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
		Log.i("Experience", String.format(" Add experience %s ", record));
		experience.add(record);
	}

	public void addExchange(Exchange exchange) {
		this.exchange.add(exchange);
	}

	public Record[] getExperienceArray() {
		return experience.toArray(new Record[experience.size()]);
	}

	public Exchange[] getExchangeArray() {
		return exchange.toArray(new Exchange[exchange.size()]);
	}
	
	public List<Record> getExperience() {
		return experience;
	}

	public List<Exchange> getExchange() {
		return exchange;
	}

	@Override
	public String toString() {
		return "Experience [UId=" + uid + ", experience=" + experience + ", exchange=" + exchange
				+ "]";
	}
}
