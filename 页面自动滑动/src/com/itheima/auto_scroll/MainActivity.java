package com.itheima.auto_scroll;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ArrayList<AdapterBean> list = new ArrayList<AdapterBean>();
	private ViewPager mviewPage;
	private TextView tv_intro;
	private LinearLayout layout_dot;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mviewPage.setCurrentItem(mviewPage.getCurrentItem()+1);
			handler.sendEmptyMessageDelayed(0, 3000);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
		initData();
	}

	private void initData() {
		list.add(new AdapterBean(R.drawable.a, "巩俐不低俗，我就不能低俗"));
		list.add(new AdapterBean(R.drawable.b, "朴树回来继续唱他的经典老歌，引起了现成很大的轰动"));
		list.add(new AdapterBean(R.drawable.c, "北京电影升级的秘密"));
		list.add(new AdapterBean(R.drawable.d, "乐视网TV狂降价"));
		list.add(new AdapterBean(R.drawable.e, "热血屌丝的反杀"));
		mviewPage.setAdapter(new MyAdapter());
		initDot();
		
		//设置第一次开启的时候出现的第一次是首页，取Integer.MAX_VALUE一半的值设为首页
		int halfMax = Integer.MAX_VALUE / 2;
		int temp = halfMax%list.size();
		mviewPage.setCurrentItem(halfMax - temp);
		updateTypeAndDot();
		handler.sendEmptyMessageDelayed(0, 3000);
	}


	@SuppressWarnings("deprecation")
	private void initListener() {
		mviewPage.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				Log.e("MainActivity", "position" + position);
				updateTypeAndDot();
			}
			
			

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initView() {
		mviewPage = (ViewPager) findViewById(R.id.view_pager);
		tv_intro = (TextView) findViewById(R.id.tv_intro);
		layout_dot = (LinearLayout) findViewById(R.id.layout_dot);
	}
	/**
	 * 更新对应图片的文本信息
	 */
	private void updateTypeAndDot() {
		int currentPage = mviewPage.getCurrentItem()%list.size();
		tv_intro.setText(list.get(currentPage).getText());
		
		//更新下面的小圆点
		for (int i = 0; i < layout_dot.getChildCount(); i++) {
			layout_dot.getChildAt(i).setEnabled(i == currentPage);
		}
	}
	/**
	 * 初始化点的一些参数
	 */

	private void initDot() {
		for (int i = 0; i < list.size(); i++) {
			View view = new View(this);
			LayoutParams params = new LayoutParams(8, 8);
			if (i != 0) {
				params.leftMargin = 5;
			}
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.selector_dot);
			layout_dot.addView(view);
		}
	}
	
	class MyAdapter extends PagerAdapter{

		/**
		 * position:当前需要销毁的第几个page
		 * object：当前需要销毁的page
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//销毁掉之前缓存的view
			container.removeView((View)object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(MainActivity.this, R.layout.adapter, null);
			ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
			
			AdapterBean aBean = list.get(position%list.size());
			ivImage.setImageResource(aBean.getResourceId());
			container.addView(view);//将view加入到viewPage中去
			return view;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		/*
		 * true:表示不去创建视图，用缓存的。false：去重新的新建一个
		 * arg0：当前滑动的view
		 * arg1：将要滑动进来的view
		 * (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}
	
}
