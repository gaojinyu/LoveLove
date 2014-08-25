package com.haydar.lvoelove.im;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.accounts.AccountManager;
import android.graphics.Bitmap.Config;

import com.haydar.lovelove.util.LocalParams;

public class IMClient {

	private static IMClient imClient = null;
	private static XMPPConnection connection = null;
	
	private IMClient() {
		ConnectionConfiguration config = new ConnectionConfiguration(
				LocalParams.DOMAI, Integer.parseInt("5222"), LocalParams.DOMAI);
		connection = new XMPPTCPConnection(config);
		try {
			connection.connect();
			System.out.println("连接上");
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static IMClient getInstace() {
		if (imClient == null) {
			imClient = new IMClient();
		}

		return imClient;
	}

	public static void disConnect() {
		if (connection.isConnected()) {
			try {
				connection.disconnect();
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void login(String username, String password) {
		try {
			connection.login(username, password);
		} catch (SaslException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void register(String username, String password) {
		
		Registration reg = new Registration();
		reg.setType(IQ.Type.SET);
		Map<String, String> attributes = new HashMap<String, String>();
		System.out.println(username);
		System.out.println(password);
		attributes.put("username", "2");
		attributes.put("password", "2");
		attributes.put("email", "111111@qq.com");
		attributes.put("name", "1");
		attributes.put("android", "geolo_createUser_android");
		attributes.put("phone", "1111111");
		attributes.put("icon", "http://www.baidu.com");
		reg.setAttributes(attributes);
		reg.setTo(connection.getServiceName());
		try {
			PacketFilter filter = new AndFilter(new PacketIDFilter(
					reg.getPacketID()), new PacketTypeFilter(IQ.class));
			PacketCollector collector = connection
					.createPacketCollector(filter);
			connection.sendPacket(reg);
			IQ result = (IQ) collector.nextResult();
			collector.cancel();
			System.out.println(result.getType());
			System.out.println(result.toString());
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
}
