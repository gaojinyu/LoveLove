package com.haydar.lovelove.splash;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haydar.lovelove.LocalParams;
import com.haydar.lovelove.R;

/**   
 * @ClassName:  SplashActivity   
 * @Description:TODO(欢迎页)   
 * @author: gjy  
 * @date:   2014-8-12 上午11:30:44   
 *      
 */  
public class SplashActivity extends Activity implements OnPageChangeListener{
	private RelativeLayout  mSplashYesFist;
	private FrameLayout mSplashNoFirst;
	private SharedPreferences sharedPreferences;
	private ViewPager mViewPager;
	private List<View> mViewList; // view视图list
	private List<ImageView> mImageViewList;
	private Context context;
	private Button mLoginBtn,mRegisterBtn,mQQLoginBtn,mSinoLoginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		context = this;
		mSplashNoFirst = (FrameLayout) findViewById(R.id.splash_layout_no_first);
		mSplashYesFist = (RelativeLayout) findViewById(R.id.splash_layout_yes_first);
		sharedPreferences = getSharedPreferences("lovelove",
				Activity.MODE_PRIVATE);
		if (sharedPreferences.getBoolean(LocalParams.SHARD_ISFIRST, true)) {
			mSplashNoFirst.setVisibility(View.GONE);
			mSplashYesFist.setVisibility(View.VISIBLE);
			initViewPager();
		} else {
			mSplashNoFirst.setVisibility(View.VISIBLE);
			mSplashYesFist.setVisibility(View.GONE);
		}

	}

	/**
	 * @Title: initViewPager
	 * @Description: TODO(初始化viewpager)
	 * @param:
	 * @return: void
	 * @throws
	 */
	private void initViewPager() {
		mViewPager = (ViewPager) mSplashYesFist
				.findViewById(R.id.splash_viewpager);
		mViewList = new ArrayList<View>();
		mViewList.add(LayoutInflater.from(context).inflate(
				R.layout.splash_zero, null));
		mViewList.add(LayoutInflater.from(context).inflate(R.layout.splash_one,
				null));
		mViewList.add(LayoutInflater.from(context).inflate(R.layout.splash_two,
				null));
		mViewList.add(LayoutInflater.from(context).inflate(
				R.layout.splash_three, null));
		mViewList.add(LayoutInflater.from(context).inflate(
				R.layout.splash_four, null));
		mImageViewList = new ArrayList<ImageView>();
		mImageViewList.add((ImageView) findViewById(R.id.splash_index_zero));
		mImageViewList.add((ImageView) findViewById(R.id.splash_index_one));
		mImageViewList.add((ImageView) findViewById(R.id.splash_index_two));
		mImageViewList.add((ImageView) findViewById(R.id.splash_index_three));
		mImageViewList.add((ImageView) findViewById(R.id.splash_index_four));
		mViewPager.setAdapter(new ViewPagerAdater(mViewList));
		mViewPager.setPageTransformer(true,
				new SplashPageTransformer());
		mViewPager.setCurrentItem(0);
		mImageViewList.get(0).setImageResource(
				R.drawable.splash_white_dot);
		mViewPager.setOnPageChangeListener(this);

		mViewPager.setOnTouchListener(new OnTouchListener() {
			float touchX = 0;

			@SuppressWarnings("static-access")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					touchX = event.getX();
				}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if ((event.getX() - touchX) > 0.0f) {
						mViewPager
								.setBackgroundColor(new Color().TRANSPARENT);
					} else {
						mViewPager
								.setBackgroundColor(new Color().WHITE);
					}
				}
				return false;
			}
		});
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		mViewPager.setCurrentItem(arg0);
		for (int i = 0; i < mImageViewList.size(); i++) {
			mImageViewList.get(i).setImageResource(
					R.drawable.splash_dark_dot);
			if (arg0 == i) {
				mImageViewList.get(i).setImageResource(
						R.drawable.splash_white_dot);
			}
		}
	}
}
