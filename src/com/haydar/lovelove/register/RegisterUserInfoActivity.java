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

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haydar.lovelove.R;

/**
 * @ClassName: RegisterUserInfoActivity
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: gjy
 * @date: 2014-8-25 下午2:59:26
 * 
 */

public class RegisterUserInfoActivity extends Activity implements
		OnClickListener {

	private String mPhone;
	private TextView mBirthDayTV;
	private LinearLayout mBirthdayLayout;
	private RadioGroup mRadioGroup;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				int year=msg.getData().getInt("year");
				int month=msg.getData().getInt("month")+1;
				int day=msg.getData().getInt("day");
				mBirthDayTV.setText(year+"年"+month+"月"+day+"号");
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_userinfo);
		initView();
	}

	private void initView() {
		mBirthDayTV = (TextView) findViewById(R.id.birthday_text);
		mBirthdayLayout = (LinearLayout) findViewById(R.id.birthday_lay);
		mBirthdayLayout.setOnClickListener(this);
		mRadioGroup=(RadioGroup)findViewById(R.id.radio_group_sex);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.birthday_lay:
			dateDialogShow();
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private void dateDialogShow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		CustomOnDateSetListener customOnDateSetListener = new CustomOnDateSetListener();
		DatePickerDialog dpd = new DatePickerDialog(this,
				customOnDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dpd.show();
	}

	class CustomOnDateSetListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Message msg = new Message();
			msg.what = 0;
			Bundle mBundle = new Bundle();
			mBundle.putInt("year", year);
			mBundle.putInt("month", monthOfYear);
			mBundle.putInt("day", dayOfMonth);
			msg.setData(mBundle);
			mHandler.sendMessage(msg);
		}

	}
}
