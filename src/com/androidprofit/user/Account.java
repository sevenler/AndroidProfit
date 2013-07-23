
package com.androidprofit.user;

public class Account {
	private String id;
	private String name;
	private String mail;
	private float money = 0;

	Account() {
	}

	Account(String id, String name, String mail, float money) {
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.money = money;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}
}
