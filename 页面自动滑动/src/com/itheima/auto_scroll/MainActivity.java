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
		list.add(new AdapterBean(R.drawable.a, "���������ף��ҾͲ��ܵ���"));
		list.add(new AdapterBean(R.drawable.b, "�����������������ľ����ϸ裬�������ֳɺܴ�ĺ䶯"));
		list.add(new AdapterBean(R.drawable.c, "������Ӱ����������"));
		list.add(new AdapterBean(R.drawable.d, "������TV�񽵼�"));
		list.add(new AdapterBean(R.drawable.e, "��Ѫ��˿�ķ�ɱ"));
		mviewPage.setAdapter(new MyAdapter());
		initDot();
		
		//���õ�һ�ο�����ʱ����ֵĵ�һ������ҳ��ȡInteger.MAX_VALUEһ���ֵ��Ϊ��ҳ
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
	 * ���¶�ӦͼƬ���ı���Ϣ
	 */
	private void updateTypeAndDot() {
		int currentPage = mviewPage.getCurrentItem()%list.size();
		tv_intro.setText(list.get(currentPage).getText());
		
		//���������СԲ��
		for (int i = 0; i < layout_dot.getChildCount(); i++) {
			layout_dot.getChildAt(i).setEnabled(i == currentPage);
		}
	}
	/**
	 * ��ʼ�����һЩ����
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
		 * position:��ǰ��Ҫ���ٵĵڼ���page
		 * object����ǰ��Ҫ���ٵ�page
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//���ٵ�֮ǰ�����view
			container.removeView((View)object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(MainActivity.this, R.layout.adapter, null);
			ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
			
			AdapterBean aBean = list.get(position%list.size());
			ivImage.setImageResource(aBean.getResourceId());
			container.addView(view);//��view���뵽viewPage��ȥ
			return view;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		/*
		 * true:��ʾ��ȥ������ͼ���û���ġ�false��ȥ���µ��½�һ��
		 * arg0����ǰ������view
		 * arg1����Ҫ����������view
		 * (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}
	
}
