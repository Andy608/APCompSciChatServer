package com.sloverse.util;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class SystemUtil {
	
	private static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private static GraphicsDevice gd = ge.getDefaultScreenDevice();
	private static DisplayMode display = gd.getDisplayMode();
	private static final Dimension screenSize = new Dimension(display.getWidth(), display.getHeight());
	
	public static GraphicsEnvironment getGraphicsEnvironment() {
		return ge;
	}
	
	public static GraphicsDevice getGraphicsDevice() {
		return gd;
	}
	
	public static DisplayMode getDisplay() {
		return display;
	}
	
	public static Dimension getScreenSize() {
		return screenSize;
	}
	
	public static EnumOS getOSType() {
        String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : 
        	(s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : EnumOS.UNKNOWN)))));
    }
	
	public static enum EnumOS {
    	WINDOWS,
    	OSX,
    	LINUX,
        SOLARIS,
        UNKNOWN;
    }
}
