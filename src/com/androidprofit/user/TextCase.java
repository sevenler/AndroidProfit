
package com.androidprofit.user;

import java.util.Date;
import android.test.AndroidTestCase;
import com.google.gson.Gson;

public class TextCase extends AndroidTestCase {
	public void testMothed() {

		Date date = new Date();
		
		Account account = AccountManager.instance().getAccount();

		Experience experience = account.getExperience();
		experience.addExperience(new Record("com.androidesk", "安卓壁纸",  date, 20));
		experience.addExperience(new Record("com.androidesk.live","安卓动态壁纸", date, 10));
		experience.addExchange(new Exchange(date, 0, 20));
		experience.addExchange(new Exchange(date, 0, 10));

		Gson gson = new Gson();
		String gs = gson.toJson(account);
		System.out.println(String.format("gs:%s", gs));

		Account ac = gson.fromJson(gs, Account.class);
		System.out.println(String.format("Account:%s", ac));
	}
}
