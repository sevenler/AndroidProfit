
package com.androidprofit.user;

import android.util.Log;

public class Account {
	private final String id;
	private final Experience experience;

	private String name;
	private String mail;
	private float money = 0;

	Account(String id) {
		this(id, null, null, 0);
	}

	Account(String id, String name, String mail, float money) {
		this(id, name, mail, money, new Experience(id));
	}

	Account(String id, String name, String mail, float money, Experience experience) {
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.money = money;
		this.experience = experience;
	}

	public String getId() {
		return id;
	}

	public Experience getExperience() {
		return experience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public float getMoney() {
		Log.i("Account", String.format("account getMoney (%s)", this.money));
		return money;
	}

	public void setMoney(float money) {
		Log.i("Account", String.format("account setMoney (%s,%s)", this.money, money));
		this.money = money;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", experience=" + experience + ", name=" + name + ", mail="
				+ mail + ", money=" + money + "]";
	}
}
