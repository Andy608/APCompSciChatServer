package com.sloverse.util.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

import com.sloverse.util.logger.LoggerUtil;

/**
 * A singleton class that can be used to test the connection to a
 * server or retrieve the server host's public ip address.
 */
public class ConnectionUtil {
	
	public static boolean isServerRunning(String ipAddress, int portNumber, int timeOut) {
		try (Socket testConnection = new Socket()) {
			testConnection.connect(new InetSocketAddress(InetAddress.getByName(ipAddress), portNumber), timeOut);
		}
		catch (IOException e) {
			LoggerUtil.logWarn(ConnectionUtil.class, "Unable to connect to server. Connection timed out.");
			return false;
		}
		
		LoggerUtil.logInfo(ConnectionUtil.class, "The server is running using ip: " + ipAddress + ":" + portNumber);
		return true;
	}
	
	public static String getPublicIP() {
		String externalIP = null;
		
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			LoggerUtil.logInfo(ConnectionUtil.class, "Querying Amazon's ip service...");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			externalIP = in.readLine();
			return externalIP;
		} catch (IOException e) {
			LoggerUtil.logError(ConnectionUtil.class, "Error trying to connect to Amazon's ip service. Visit: \'http://checkip.amazonaws.com\' if the problem persists.", e);
		}
		
		LoggerUtil.logWarn(ConnectionUtil.class, "External ip is null!");
		return externalIP;
	}
}
