package com.sloverse.connection;

import java.io.IOException;

import com.sloverse.data.BundledObject;
import com.sloverse.util.logger.LoggerUtil;

public class ConnectionManager {

	public synchronized static void sendData(ConnectionThread t, BundledObject o) {
		
		try {
			t.getOutStream().writeObject(o);
		} catch (IOException e) {
			LoggerUtil.logError(ConnectionManager.class, "Error transmitting data.", e);
		}
	}
}
