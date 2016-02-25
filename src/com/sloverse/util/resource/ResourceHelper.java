package com.sloverse.util.resource;

import com.sloverse.core.SloverseServerHub;
import com.sloverse.util.FileUtil;
import com.sloverse.util.SystemUtil;
import com.sloverse.util.SystemUtil.EnumOS;

public final class ResourceHelper {

	protected static final char DEFAULT_DELIMITER = '/';
	
	public static final ResourceDirectory PROGRAM_APPDATA_DIRECTORY;
//	public static final ResourceDirectory INSTALLATION_DIRECTORY;
	private static final EnumOS SYSTEM_OS = SystemUtil.getOSType();
	
	static {
		switch (SYSTEM_OS) {
		case WINDOWS: {
			PROGRAM_APPDATA_DIRECTORY = new ResourceDirectory(System.getenv("APPDATA"), SloverseServerHub.NAME, false); break;
//			INSTALLATION_DIRECTORY = new ResourceDirectory("C:/Program Files (x86)/" + Info.NAME, "natives", false); break;
		}
		case OSX: {
			PROGRAM_APPDATA_DIRECTORY = new ResourceDirectory(System.getProperty("user.home") + FileUtil.getFileSeparator(false) + "Library" + FileUtil.getFileSeparator(false) + "Application Support", SloverseServerHub.NAME, false); break;
//			INSTALLATION_DIRECTORY = new ResourceDirectory("C:/Program Files (x86)/" + Info.NAME, "natives", false); break; //TODO: CHANGE THIS TO THE CORRECT DIRECTORY.
		}
		default: {
			PROGRAM_APPDATA_DIRECTORY = new ResourceDirectory(System.getProperty("user.home"), SloverseServerHub.NAME, false); break;
//			INSTALLATION_DIRECTORY = new ResourceDirectory("C:/Program Files (x86)/" + Info.NAME, "natives", false); break; //TODO: CHANGE THIS TO THE CORRECT DIRECTORY.
		}
		}
	}
}
