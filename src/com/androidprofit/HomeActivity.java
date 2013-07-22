package com.androidprofit;

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

		
		ViewGroup group = inflateTabBar(HomeActivity.this, R.layout.view_tab);
		addTabsIntoActionBar(group);
	}
	
	private ViewGroup inflateTabBar(Context ctx, int res){
		ViewGroup group = (ViewGroup)View.inflate(ctx, res, null);
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)group.findViewById(R.id.tabs);
		tabs.setTextColorResource(R.color.black);
		tabs.setIndicatorColorResource(R.color.dark_blue);
		tabs.setViewPager(pager);
		
		return group;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void addTabsIntoActionBar(View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar ab = getActionBar();
			ab.setCustomView(view);

			ab.setDisplayShowCustomEnabled(true);
		}
	}

}

class MyPagerAdapter extends FragmentPagerAdapter {
	private final String[] TITLES = { "Categories", "Home", "Top Paid" };

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
		return TabFragment.newInstance(position);
	}

}
