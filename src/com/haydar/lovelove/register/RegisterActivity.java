/**  
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  RegisterActivity.java   
 * @Package com.haydar.lovelove.register   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gjy     
 * @date:   2014-8-20 下午4:55:42   
 * @version V1.0     
 */
package com.haydar.lovelove.register;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.haydar.lovelove.R;
import com.haydar.lvoelove.im.IMClient;

/**
 * @ClassName: RegisterActivity
 * @Description:TODO(注册类)
 * @author: gjy
 * @date: 2014-8-20 下午4:55:42
 * 
 */

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText mEditText;
	private Button mVerifyBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		mEditText = (EditText) findViewById(R.id.phoneNumber_edit);
		mVerifyBtn=(Button)findViewById(R.id.register_btn);
		mVerifyBtn.setOnClickListener(this);
	}

	public void verify() {
		String str = String.valueOf(mEditText.getText()).trim();
		if (str != null || str != "") {
			System.out.println("开始链接");
			new Thread(new Runnable() {
				@Override
				public void run() {
					IMClient.getInstace();
					IMClient.register("1", "1");
				}
			}).start();
			
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_btn:
			verify();
			break;

		default:
			break;
		}
	}
}
