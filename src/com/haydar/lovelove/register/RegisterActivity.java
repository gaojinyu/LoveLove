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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.haydar.lovelove.R;
import com.haydar.lovelove.util.LocalParams;
import com.sinosoft.library.loadingdialog.LoadingDialog;
import com.tencent.a.b.m;

/**
 * @ClassName: RegisterActivity
 * @Description:TODO(注册类)
 * @author: gjy
 * @date: 2014-8-20 下午4:55:42
 * 
 */

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText mEditText,mVerifyEditText;
	private Button mVerifyBtn;
	private String mPhone="";
	private TextView mRegisterTitle;
	private LoadingDialog mLoadingDialog;
	private LinearLayout mRegisterPhoneLayout,mRegisterVerifyPhone;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mLoadingDialog.dismiss();
				ToggleVerifyPhone();
				break;
			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		SMSSDK.initSDK(this, LocalParams.SMS_APPKEY,
				LocalParams.SMD_APP_SECRET);
		initView();
	}

	private void initView() {
		mEditText = (EditText) findViewById(R.id.phoneNumber_edit);
		mVerifyEditText=(EditText)findViewById(R.id.phoneVerify_edit);
		mVerifyBtn = (Button) findViewById(R.id.register_btn);
		mVerifyBtn.setOnClickListener(this);
		mLoadingDialog=new LoadingDialog(this);
		mRegisterTitle=(TextView)findViewById(R.id.register_title);
		mRegisterPhoneLayout=(LinearLayout)findViewById(R.id.register_phone);
		mRegisterVerifyPhone=(LinearLayout)findViewById(R.id.register_verify_phone);
		togglePhoneLayout();
	}

	
	/**
	 * 显示填写手机号页面
	 */
	public void togglePhoneLayout() {
		
		mRegisterTitle.setText("注册");
		mRegisterPhoneLayout.setVisibility(View.VISIBLE);
		mRegisterVerifyPhone.setVisibility(View.GONE);
		mEditText.requestFocus();
	}

	public void verify() {
		mPhone = String.valueOf(mEditText.getText()).trim();
		if(TextUtils.isEmpty(mPhone)){
			mEditText.setHint(this.getString(R.string.phone_none));
		}else if(regexPhone()){
			mEditText.setText(null);
			mEditText.setHint(this.getString(R.string.phone_error));
		}else if(!TextUtils.isEmpty(mPhone)) {
			mLoadingDialog.show();
			System.out.println("开始链接");
			SMSSDK.registerEventHandler(new SMSEventHandler());
			SMSSDK.getVerificationCode("86", mPhone);
		}
	}

	/**
	 * 正则表达式手机号
	 * @param phone2
	 * @return
	 */
	private boolean regexPhone() {
		// TODO Auto-generated method stub
		Pattern p=Pattern.compile("^[1-9]d*|0$");
		Matcher m=p.matcher(mPhone);
		if(m.matches()||mPhone.length()==11){
			return false;
		}
		return true;
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

	class SMSEventHandler extends EventHandler {

		@Override
		public void afterEvent(int event, int result, Object object) {
			// TODO Auto-generated method stub
			System.out.println("afterEvent");
			switch (event) {
			case SMSSDK.EVENT_GET_VERIFICATION_CODE:
				Message msg=new Message();
				msg.what=0;
				mHandler.sendMessage(msg);
				break;
			default:
				break;
			}
		}

		@Override
		public void beforeEvent(int arg0, Object arg1) {
			// TODO Auto-generated method stub
			System.out.println("beforeEvent");

		}

		@Override
		public void onRegister() {
			// TODO Auto-generated method stub
			super.onRegister();
			System.out.println("onRegister");
		}

		@Override
		public void onUnregister() {
			// TODO Auto-generated method stub
			super.onUnregister();
		}

	}

	public void getMapItem(HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		Iterator iter = hashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			System.out.println("key--" + key);
			Object val = entry.getValue();
			System.out.println("val---" + val);
		}
	}

	/**
	 * 显示填写验证码页面
	 */
	public void ToggleVerifyPhone() {
		mEditText.setEnabled(false);
		mVerifyEditText.setEnabled(true);
		mRegisterTitle.setText(mPhone);
		mRegisterPhoneLayout.setVisibility(View.GONE);
		mRegisterVerifyPhone.setVisibility(View.VISIBLE);
		mVerifyBtn.setVisibility(View.GONE);
		mVerifyEditText.requestFocus();
		
	}

}
