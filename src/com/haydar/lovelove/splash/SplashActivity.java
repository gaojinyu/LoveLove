package com.haydar.lovelove.splash;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haydar.lovelove.R;
import com.haydar.lovelove.util.LocalParams;
import com.haydar.lovelove.util.NetworkConn;
import com.tencent.a.b.m;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;

/**
 * @ClassName: SplashActivity
 * @Description:TODO(欢迎页)
 * @author: gjy
 * @date: 2014-8-12 上午11:30:44
 * 
 */
public class SplashActivity extends Activity implements OnPageChangeListener {
	private RelativeLayout mSplashYesFist;
	private FrameLayout mSplashNoFirst;
	private SharedPreferences sharedPreferences;
	private ViewPager mViewPager;
	private List<View> mViewList; // view视图list
	private List<ImageView> mImageViewList;
	private Context context;
	private Tencent mTencent;

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
	 * 登录
	 * 
	 * @param view
	 */
	public void login(View view) {

		if (NetworkConn.getInstance(context).isNetWorkConn()) {

		} else {
			Toast.makeText(context, this.getString(R.string.network_no_string),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 注册
	 * 
	 * @param view
	 */
	public void register(View view) {
		if (NetworkConn.getInstance(context).isNetWorkConn()) {
			System.out.println("登录");
		} else {
			Toast.makeText(context, this.getString(R.string.network_no_string),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * QQ登录
	 * 
	 * @param view
	 */
	public void loginByQQ(View view) {
		if (NetworkConn.getInstance(context).isNetWorkConn()) {
			if (!mTencent.isSessionValid()) {
				mTencent = Tencent.createInstance("1102003725",
						this.getApplicationContext());
				mTencent.login(this, "all", new IUiListener() {
					@Override
					public void onError(UiError arg0) {
						// TODO Auto-generated method stub
						System.out.println("onError---" + arg0.errorCode);
					}

					@Override
					public void onComplete(Object arg0) {
						// TODO Auto-generated method stub
						System.out.println("onComplete---" + arg0.toString());
					}

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						System.out.println("onCancel---");
					}
				});
			}
		} else {
			Toast.makeText(context, this.getString(R.string.network_no_string),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * sino登录
	 * 
	 * @param view
	 */
	public void loginBySino(View view) {
		if (NetworkConn.getInstance(context).isNetWorkConn()) {
			System.out.println("登录");
		} else {
			Toast.makeText(context, this.getString(R.string.network_no_string),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("resultCode--" + resultCode);
		System.out.println("resultCode--" + requestCode);
		mTencent.onActivityResult(requestCode, resultCode, data);
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
		mViewPager.setPageTransformer(true, new SplashPageTransformer());
		mViewPager.setCurrentItem(0);
		mImageViewList.get(0).setImageResource(R.drawable.splash_white_dot);
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
						mViewPager.setBackgroundColor(new Color().TRANSPARENT);
					} else {
						mViewPager.setBackgroundColor(new Color().WHITE);
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
			mImageViewList.get(i).setImageResource(R.drawable.splash_dark_dot);
			if (arg0 == i) {
				mImageViewList.get(i).setImageResource(
						R.drawable.splash_white_dot);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mTencent != null) {
			if (!mTencent.isSessionValid()) {
				mTencent.logout(getApplicationContext());
			}
		}
	}
}
