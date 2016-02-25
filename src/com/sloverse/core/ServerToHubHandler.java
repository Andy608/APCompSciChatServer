package com.sloverse.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import com.sloverse.util.logger.LoggerUtil;

/**
 * Is responsible for handling the connections from all of the official servers
 * and updates info about their statuses in real time.
 */
public class ServerToHubHandler extends Thread {

	private static int portNumber;
	private volatile boolean isServerRunning;
	
	public ServerToHubHandler(int preferredPort) {
		super();
		portNumber = preferredPort;
		isServerRunning = false;
	}
	
	public void startServer() {
		isServerRunning = true;
		this.start();
	}
	
	public void stopServer() {
		isServerRunning = false;
	}
	
	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			LoggerUtil.logInfo(ServerToHubHandler.class, "IN");
			
			LoggerUtil.logInfo(ServerToHubHandler.class, "Starting server... Local ip: " + InetAddress.getLocalHost() + " : " + serverSocket.getLocalPort());
			
			while (isServerRunning) {
				//Accept incoming connection and create a new ServerToHubConnectionThread.
			}
			LoggerUtil.logInfo(ServerToHubHandler.class, "OUT");
		}
		catch (IllegalArgumentException | IOException e) {
			LoggerUtil.logError(getClass(), this, e);
			isServerRunning = false;
		}
	}
}
