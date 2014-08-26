/**  
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  RegisterUserInfoActivity.java   
 * @Package com.haydar.lovelove.register   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gjy     
 * @date:   2014-8-25 下午2:59:26   
 * @version V1.0     
 */  
package com.haydar.lovelove.register;

import com.haydar.lovelove.R;

import android.app.Activity;
import android.os.Bundle;

/**   
 * @ClassName:  RegisterUserInfoActivity   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: gjy  
 * @date:   2014-8-25 下午2:59:26   
 *      
 */

public class RegisterUserInfoActivity extends Activity{

	private String mPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_userinfo);
		initView();
	}
	private void initView() {
		//mPhone=getIntent().getStringExtra("phone");

				
	}
}
