package com.androidprofit;

import java.util.HashMap;
import java.util.LinkedHashMap;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class HomeActivity extends BaseActivity {

	private ViewPager pager;
	private MyPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pager = (ViewPager)findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		ViewGroup group = inflateTabBar(HomeActivity.this, R.layout.view_tab, new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				//需要手动调用来刷新数据
				IFragment fragment = (IFragment)adapter.getItem(arg0);
				fragment.onReflush();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		addTabsIntoActionBar(group);

       umengAgentInit();
	}

    /**
     * 初始化U友蒙的统计和升级提示
     */
    private void umengAgentInit(){
        MobclickAgent.onError(this);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.setDebugMode(true);

        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();

        UmengUpdateAgent.update(this);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
                Context ctx = HomeActivity.this;
                switch (updateStatus) {
                    case 0: // has update
                        Toast.makeText(ctx, updateInfo.updateLog, Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // has no update
                        Toast.makeText(ctx, "没有更新", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // none wifi
                        Toast.makeText(ctx, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // time out
                        Toast.makeText(ctx, "超时", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
	
	/**
	 * 填充ActionBar中的tab
	 * @param ctx
	 * @param res
	 * @param listener Viewpager的OnPageChangeListener事件
	 * @return
	 */
	private ViewGroup inflateTabBar(Context ctx, int res, OnPageChangeListener listener){
		ViewGroup group = (ViewGroup)View.inflate(ctx, res, null);
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)group.findViewById(R.id.tabs);
		tabs.setTextColorResource(R.color.black);
		tabs.setIndicatorColorResource(R.color.dark_blue);
		tabs.setViewPager(pager);
		//在PagerSlidingTabStrip内部会重置Pager的PageChangeListenner
		tabs.setOnPageChangeListener(listener);
		
		return group;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	/**
	 * 设置actionbar中使用自定义控件
	 */
	private void addTabsIntoActionBar(View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar ab = getActionBar();
			ab.setCustomView(view);

			ab.setDisplayShowCustomEnabled(true);
		}
	}
}

class MyPagerAdapter extends FragmentPagerAdapter {
	private final String[] TITLES = { "Task", "Home", "Exchange", "Feedback"};
	private final HashMap<Integer,Fragment> map = new LinkedHashMap<Integer,Fragment>();

	public static final int POSITION_HOME = 0;
	public static final int POSITION_PERSONAL = 1;
	public static final int POSITION_EXCHANGE = 2;
	public static final int POSITION_FEEDBACK = 3;
	
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = map.get(position);
		if (fragment == null){
			switch (position) {
				case POSITION_HOME:
					fragment = new DownloadTab();
					break;
				case POSITION_PERSONAL:
					fragment = new AccountTab();
					break;
				case POSITION_EXCHANGE:
					fragment = new ExchangeTab();
					break;
				case POSITION_FEEDBACK:
					fragment = new UmFeedbackTab();
					break;
				default:
					break;
			}
			
			map.put(position, fragment);
		}
		return fragment;
	}
}