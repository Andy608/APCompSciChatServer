package com.sloverse.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sloverse.data.bundles.BundledObject;
import com.sloverse.util.logger.LoggerUtil;

/**
 * Thread that handles a connection between the Hub Server and client/offical server.
 */
public class ConnectionThread extends Thread {

	private Socket socket;
	private int connectionID;
	private static int nextConnectionID;
	
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public ConnectionThread(Socket s) {
		super();
		connectionID = nextConnectionID++;
		this.setName("ConnectionID " + connectionID);
		socket = s;
	}
	
	@Override
	public void run() {
		
		try {
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
		
			Object o = null;
			while (HubServerManager.isRunning() && socket.isConnected() && !socket.isClosed()) {
				while (o != null) {
					o = inStream.readObject();
				
					if (o instanceof BundledObject) {
						//do things
					}
					else {
						//Received invalid object.
					}
				}
			}
		} catch (ClassNotFoundException e) {
			LoggerUtil.logError(ConnectionThread.class, e);
		} catch (IOException e) {
			LoggerUtil.logError(ConnectionThread.class, "Connection error occurred with connection thread: " + this.getName(), e);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ConnectionThread)) return false;
		ConnectionThread other = (ConnectionThread)o;
		if (connectionID == other.connectionID) return true;
		return false;
	}
	
	public ObjectOutputStream getOutStream() {
		return outStream;
	}
	
	public ObjectInputStream getInStream() {
		return inStream;
	}
	
	public int getConnectionID() {
		return connectionID;
	}
}
