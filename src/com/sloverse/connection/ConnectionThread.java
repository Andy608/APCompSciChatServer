package com.sloverse.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sloverse.data.BundledObject;
import com.sloverse.util.logger.LoggerUtil;

/**
 * Thread that handles a connection between the Hub Server and a Game Server.
 */
public class ConnectionThread extends Thread {

	private Socket socket;
	private static int nextConnectionID;
	
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public ConnectionThread(Socket s) {
		super();
		this.setName("ConnectionID " + nextConnectionID++);
		socket = s;
	}
	
	public ObjectOutputStream getOutStream() {
		return outStream;
	}
	
	public ObjectInputStream getInStream() {
		return inStream;
	}
	
	@Override
	public void run() {
		
		try {
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
		
			Object o;
			while (HubServerManager.isRunning() && socket.isConnected() && !socket.isClosed()) {
				o = inStream.readObject();
				
				if (o != null) {
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
}
