package com.haydar.lovelove.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConn {

	private static NetworkConn networkConn=null;
	private static  ConnectivityManager conn=null;
	private NetworkConn(){
	}
	
	public static synchronized NetworkConn getInstance(Context context){
		if(networkConn==null){
			networkConn=new NetworkConn();
			conn=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return networkConn;
	}
	

	/**
	 * 判断是否连网
	 * @return
	 */
	public static  boolean isNetWorkConn(){
		NetworkInfo networkInfo = conn.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}
	
	
	/**
	 * 判断wifi是否可用
	 * @return
	 */
	public static boolean isWifiNetWorkConn(){
		NetworkInfo networkInfo = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo!=null){
			return networkInfo.isAvailable();
		}
		return false;
	}
	
	
	/**
	 * 判断mobiel网络是否可用
	 * @return
	 */
	public static boolean isMobileNetwordConn(){
		NetworkInfo networkInfo = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo!=null){
			return networkInfo.isAvailable();
		}
		return false;
	}
}
