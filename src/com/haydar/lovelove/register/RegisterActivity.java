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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.haydar.lovelove.R;
import com.haydar.lovelove.util.LocalParams;
import com.haydar.lovelove.util.NetworkConn;
import com.sinosoft.library.loadingdialog.LoadingDialog;

/**
 * @ClassName: RegisterActivity
 * @Description:TODO(注册类)
 * @author: gjy
 * @date: 2014-8-20 下午4:55:42
 * 
 */

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText mEditText, mVerifyEditText;
	private Button mVerifyBtn, mPhoneVerify;
	private String mPhone = "",mCode="";
	private TextView mRegisterTitle;
	private LoadingDialog mLoadingDialog;
	private TimeCount time;
	private Context context;
	private LinearLayout mRegisterPhoneLayout, mRegisterVerifyPhone;
	private Handler mHandler = new Handler() {

		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mLoadingDialog.dismiss();
				ToggleVerifyPhone();
				break;
			case 1:
				mLoadingDialog=new LoadingDialog(context);
				mLoadingDialog.setText("正在验证……");
				mLoadingDialog.show();
				SMSSDK.unregisterAllEventHandler();
				SMSSDK.registerEventHandler(new EventHandler());
				SMSSDK.submitVerificationCode("86", mPhone, mCode);
				break;
			case 2:
				mLoadingDialog.dismiss();
				Intent intent=new Intent(RegisterActivity.this,RegisterUserInfoActivity.class);
				intent.putExtra("phone", mPhone);
				startActivity(intent);
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		SMSSDK.initSDK(this, LocalParams.SMS_APPKEY, LocalParams.SMD_APP_SECRET);
		initView();
	}

	private void initView() {
		context=this;
		mEditText = (EditText) findViewById(R.id.phoneNumber_edit);
		mVerifyEditText = (EditText) findViewById(R.id.phoneVerify_edit);
		mVerifyEditText.addTextChangedListener(watcher);
		mVerifyBtn = (Button) findViewById(R.id.register_btn);
		mPhoneVerify = (Button) findViewById(R.id.phone_verify_btn);
		mPhoneVerify.setOnClickListener(this);
		mVerifyBtn.setOnClickListener(this);
		mLoadingDialog = new LoadingDialog(this);
		mRegisterTitle = (TextView) findViewById(R.id.register_title);
		mRegisterPhoneLayout = (LinearLayout) findViewById(R.id.register_phone);
		mRegisterVerifyPhone = (LinearLayout) findViewById(R.id.register_verify_phone);
		time = new TimeCount(60000, 1000);//构造CountDownTimer对象
		togglePhoneLayout();
		
	}
	TextWatcher watcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			System.out.println("count=="+count);
			if(count==4){
				mCode=s.toString();
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
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
		if (TextUtils.isEmpty(mPhone)) {
			mEditText.setHint(this.getString(R.string.phone_none));
		} else if (regexPhone()) {
			mEditText.setText(null);
			mEditText.setHint(this.getString(R.string.phone_error));
		} else if (!TextUtils.isEmpty(mPhone)) {
			if (NetworkConn.getInstance(getApplicationContext())
					.isNetWorkConn()) {
				mLoadingDialog.setText("正在发送验证码……");
				mLoadingDialog.show();
				System.out.println("开始链接");
				SMSSDK.registerEventHandler(new SMSEventHandler());
				SMSSDK.getVerificationCode("86", mPhone);
			}else{
				Toast.makeText(this, "无法链接网络", Toast.LENGTH_SHORT).show();
			}

		}
	}

	/**
	 * 正则表达式手机号
	 * 
	 * @param phone2
	 * @return
	 */
	private boolean regexPhone() {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("^[1-9]d*|0$");
		Matcher m = p.matcher(mPhone);
		if (m.matches() || mPhone.length() == 11) {
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
		case R.id.phone_verify_btn:
			SMSSDK.unregisterAllEventHandler();
			SMSSDK.registerEventHandler(new SMSEventHandler());
			SMSSDK.getVerificationCode("86", mPhone);
			break;
		case R.id.login_btn:
			
		
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
			Message msg;
			switch (event) {
			case SMSSDK.EVENT_GET_VERIFICATION_CODE:
				msg = new Message();
				msg.what = 0;
				mHandler.sendMessage(msg);
				break;
			case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
				if(result==SMSSDK.RESULT_COMPLETE){
					msg = new Message();
					msg.what = 2;
					mHandler.sendMessage(msg);
				}
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
		time.start();

	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			mPhoneVerify.setText("重新发送验证码");
			mPhoneVerify.setTextColor(new Color().parseColor("#000000"));
			mPhoneVerify.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			mPhoneVerify.setClickable(false);
			mPhoneVerify.setTextColor(new Color().parseColor("#ff7c7d81"));
			mPhoneVerify.setText("重新发送验证码还有"+millisUntilFinished / 1000 + "秒");
			
		}
	}

}
