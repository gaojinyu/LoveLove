package com.haydar.lvoelove.im;

import java.io.IOException;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;

public class IMConnection extends XMPPConnection{

	protected IMConnection(ConnectionConfiguration configuration) {
		super(configuration);
	}

	@Override
	public String getUser() {
		return getUser();
	}

	@Override
	public String getConnectionID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnonymous() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecureConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void sendPacketInternal(Packet packet)
			throws NotConnectedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsingCompression() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void connectInternal() throws SmackException, IOException,
			XMPPException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String username, String password, String resource)
			throws XMPPException, SmackException, SaslException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginAnonymously() throws XMPPException, SmackException,
			SaslException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void shutdown() {
		// TODO Auto-generated method stub
		
	}

	
}
