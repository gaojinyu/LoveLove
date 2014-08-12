package com.haydar.lovelove.splash;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerAdater extends PagerAdapter {
	private List<View> mViewList;
	public ViewPagerAdater(List<View> mViewList) {
		this.mViewList=mViewList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		 return arg0 == arg1;  
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		  container.removeView((View)object);
		  object=null;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView((View) mViewList.get(position));
		   return mViewList.get(position);
		   
	}

	

}
