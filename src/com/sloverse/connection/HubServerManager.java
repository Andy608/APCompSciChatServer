package com.sloverse.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sloverse.util.connection.ConnectionUtil;
import com.sloverse.util.logger.LoggerUtil;


public class HubServerManager extends Thread {

	private static AtomicBoolean isServerRunning;
	private int portNumber;
	private String currentIP;
	public static final int TIME_OUT = 10000;
	
	public HubServerManager(int port) {
		portNumber = port;
		currentIP = ConnectionUtil.getPublicIP();
		isServerRunning = new AtomicBoolean(false);
	}
	
	public synchronized void startServerToHubServer() {
		setRunning(true);
		start();
	}
	
	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			ConnectionUtil.isServerRunning(currentIP, portNumber, TIME_OUT);
			
			LoggerUtil.logInfo(HubServerManager.class, "BEFORE: " + isRunning());
			while (isRunning()) {
				LoggerUtil.logInfo(HubServerManager.class, "INSIDE: " + isRunning());
				//Accept incoming connection and create a new ServerToHubConnectionThread.
			}
			LoggerUtil.logInfo(HubServerManager.class, "AFTER: " + isRunning());
		} catch (IllegalArgumentException | IOException e) {
			LoggerUtil.logError(HubServerManager.class, e);
			setRunning(false);
		}
	}
	
	public String getServerIP() {
		return currentIP;
	}
	
	public int getPort() {
		return portNumber;
	}
	
	public synchronized void stopServerToHubServer() {
		setRunning(false);
	}
	
	public static synchronized boolean isRunning() {
		return isServerRunning.get();
	}
	
	public static synchronized void setRunning(boolean b) {
		LoggerUtil.logInfo(HubServerManager.class, "Setting 'isRunning' to " + b);
		isServerRunning.set(b);
	}
}
