/**  
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  CustomSharedPreferences.java   
 * @Package com.haydar.lovelove.util   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gjy     
 * @date:   2014-8-20 上午10:09:57   
 * @version V1.0     
 */
package com.haydar.lovelove.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: CustomSharedPreferences
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: gjy
 * @date: 2014-8-20 上午10:09:57
 * 
 */

public class CustomSharedPreferences {

	private static CustomSharedPreferences customSharedPreferences = null;
	public static SharedPreferences sharedPreferences = null;

	private CustomSharedPreferences() {

	}

	public static CustomSharedPreferences getInstace(Context context,
			String name) {
		if (customSharedPreferences == null) {
			customSharedPreferences = new CustomSharedPreferences();
			sharedPreferences = context.getSharedPreferences(name,
					Activity.MODE_PRIVATE);
		}
		return customSharedPreferences;
	}
	
	

}
