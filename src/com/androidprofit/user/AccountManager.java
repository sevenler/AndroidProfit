
package com.androidprofit.user;

public class AccountManager {
	private final Account mAccount;

	private AccountManager(Account account) {
		mAccount = account;
	}

	private AccountManager() {
		this(new Account("00001", "Johnny Hong", "johnnyxyzw@mail.com", 1000));
	}

	public Account getAccount() {
		return mAccount;
	}

	public static AccountManager accountManager;

	public static AccountManager instance() {
		if (accountManager == null) accountManager = new AccountManager();
		return accountManager;
	}
}
