package com.androidprofit;

import java.util.HashMap;
import java.util.LinkedHashMap;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

public class HomeActivity extends FragmentActivity {

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
	private final String[] TITLES = { "Categories", "Home"};
	private final HashMap<Integer,Fragment> map = new LinkedHashMap<Integer,Fragment>();

	public static final int POSITION_HOME = 0;
	public static final int POSITION_PERSONAL = 1;
	public static final int POSITION_EXCHANGE = 2;
	
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
					break;
				default:
					fragment = new Fragment();
					break;
			}
			
			map.put(position, fragment);
		}
		return fragment;
	}
}