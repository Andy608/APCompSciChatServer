package com.sloverse.core;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sloverse.connection.HubServerManager;
import com.sloverse.util.ConnectionUtil;
import com.sloverse.util.FileUtil;
import com.sloverse.util.logger.LoggerUtil;
import com.sloverse.window.HubFrame;

/**
 * The starting file for the Sloverse Server Hub program.
 */
public class SloverseServerHub {

	public static final String NAME = "Sloverse Server Hub";
	public static final String VERSION = "Alpha V0.0.01";
	public static final String AUTHORS[] = new String[] {"Andy608 - Andrew Rimpici", "ISQUISHALL - Alex Frier"};
	
	private static HubFrame frame;
	
	public static void main(String[] args) {
		String path = (new File(SloverseServerHub.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParentFile().getPath();
		String decodedPath;
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			System.setProperty("org.lwjgl.librarypath", decodedPath + FileUtil.getFileSeparator(false));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		LoggerUtil.init();
		Thread.setDefaultUncaughtExceptionHandler(LoggerUtil.getInstance());
		Thread.currentThread().setName("Main Thread");
		
		frame = new HubFrame(NAME + " | " + VERSION, 900, 500);
		frame.setVisible(true);
		
		HubServerManager testServerManager = new HubServerManager(12081);
		
//		testServerManager.setIsServerToHubServerRunning(isServerToHubRunning);
		testServerManager.startServerToHubServer();
		testServerManager.setName("Hub Thread");
		LoggerUtil.logInfo(SloverseServerHub.class, "TEST!");
		boolean isServerRunning = ConnectionUtil.isServerRunning(testServerManager.getServerIP(), testServerManager.getPort(), HubServerManager.TIME_OUT);
		LoggerUtil.logInfo(SloverseServerHub.class, "SERVER IS CURRENTLY " + (isServerRunning ? "ONLINE" : "OFFLINE"));
		testServerManager.stopServerToHubServer();
	}
}
