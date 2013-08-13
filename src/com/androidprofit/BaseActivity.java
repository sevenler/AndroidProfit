package com.androidprofit;

import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created with IntelliJ IDEA.
 * User: sujoe
 * Date: 13-8-6
 * Time: 下午8:58
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends FragmentActivity {
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
