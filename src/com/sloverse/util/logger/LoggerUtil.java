package com.sloverse.util.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sloverse.core.SloverseServerHub;
import com.sloverse.util.FileUtil;
import com.sloverse.util.resource.FileResourceLocation;
import com.sloverse.util.resource.FileResourceLocation.EnumFileExtension;
import com.sloverse.util.resource.ResourceDirectory;
import com.sloverse.util.resource.ResourceHelper;

public class LoggerUtil implements Thread.UncaughtExceptionHandler {

	private static final ResourceDirectory LOG_PROP_DIR = new ResourceDirectory(ResourceHelper.PROGRAM_APPDATA_DIRECTORY.getFullDirectory(), "logger", false);
	private static final ResourceDirectory LOG_DIR = new ResourceDirectory(LOG_PROP_DIR.getFullDirectory(), "reports", false);
	private static final FileResourceLocation LOG_PROPERTIES = new FileResourceLocation(LOG_PROP_DIR, "ssh_logger", EnumFileExtension.PROPERTIES);
	private static final String DATE_FORMAT = "dd_MM_yyyy HH_mm_ss";
	
	private static LoggerUtil loggerUtil;
	
	private LoggerUtil() {
		loggerUtil = this;
		
		File filePath = new File(LOG_PROPERTIES.getFullPath());
		
		if (!filePath.exists()) {
			new File(LOG_PROPERTIES.getParentDirectory().getFullDirectory()).mkdirs();
			
			try (PrintStream writer = new PrintStream(LOG_PROPERTIES.getFullPath(), "UTF-8")) {
				
				writer.println("log4j.rootLogger=debug, consoleLogger" + FileUtil.ENTER +
						"log4j.logger.org.apache=debug, consoleLogger" + FileUtil.ENTER +
						"# Set type of consoleLogger" + FileUtil.ENTER +
						"log4j.appender.consoleLogger=org.apache.log4j.ConsoleAppender" + FileUtil.ENTER +
						"log4j.appender.consoleLogger.layout=org.apache.log4j.PatternLayout" + FileUtil.ENTER +
						"# Set layout for the consoleLogger" + FileUtil.ENTER +
						"log4j.appender.consoleLogger.layout.ConversionPattern=[%d{HH:mm:ss}] [%p] [%c{1}]: %m%n");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		PropertyConfigurator.configure(LOG_PROPERTIES.getFullPath());
	}
	
	public static void init() {
		if (loggerUtil == null) {
			new LoggerUtil();
		}
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logError(this.getClass(), t, e);
	}
	
	private static Logger getLogger(Class<?> c) {
		return LogManager.getLogger(c.getName());
	}
	
	public static void logInfo(Class<?> c, String infoMessage) {
		getLogger(c).log(Level.INFO, infoMessage);
	}
	
	public static void logWarn(Class<?> c, String warnMessage) {
		logWarn(c, Thread.currentThread(), warnMessage);
	}
	
	public static void logError(Class<?> c, Throwable e) {
		logError(c, Thread.currentThread(), e);
	}
	
	public static void logWarn(Class<?> c, Thread t, String warnMessage) {
		getLogger(c).warn("Warning coming from class: " + t.getName() + " | " + warnMessage);
	}
	
	public static void logWarn(Class<?> c, Throwable e, String warnMessage, boolean logToFile) {
		logWarn(c, Thread.currentThread(), e, warnMessage, logToFile);
	}
	
	public static void logWarn(Class<?> c, Thread t, Throwable e, String warnMessage, boolean logToFile) {
		String date = getDate(DATE_FORMAT);
		String message = FileUtil.ENTER + "---------- " + SloverseServerHub.NAME + " Warning Report ----------" + FileUtil.ENTER + FileUtil.ENTER + date
				+ FileUtil.ENTER + FileUtil.ENTER + "Keep calm! I can get back up from this!" + FileUtil.ENTER + FileUtil.ENTER
				+ "Details about the crash are listed below" + FileUtil.ENTER
				+ "---------------------------------------" + FileUtil.ENTER + FileUtil.ENTER +
				"Crash occured in class: " + t.getName() + " | " + warnMessage;
		
		getLogger(c).warn(message, e);
		
		if (logToFile)
			logToErrorFile(date, message, e);
	}
	
	/**
	 * Should be called when an uncaught exception occurs.
	 * @param c: The class where the error is coming from.
	 * @param t: The thread the error occurred on.
	 * @param e: The throwable, or actual error itself.
	 */
	public static void logError(Class<?> c, Thread t, Throwable e) {
		String date = getDate(DATE_FORMAT);
		String message = FileUtil.ENTER + "---------- " + SloverseServerHub.NAME + " Error Report ----------" + FileUtil.ENTER + FileUtil.ENTER + date
				+ FileUtil.ENTER + FileUtil.ENTER + "Help! I've fallen and I can't get up!" + FileUtil.ENTER + FileUtil.ENTER
				+ "Details about the crash are listed below" + FileUtil.ENTER
				+ "---------------------------------------" + FileUtil.ENTER + FileUtil.ENTER +
				"Crash occured in class: " + t.getName();
		
		getLogger(c).error(message, e);
		logToErrorFile(date, message, e);
	}
	
	private static void logToErrorFile(String date, String message, Throwable e) {
		File f = new File(LOG_DIR.getFullDirectory());
		boolean append = false;
		if (f.exists()) {
			append = true;
		}
		else {
			f.mkdirs();
		}
		
		FileResourceLocation fileName = new FileResourceLocation(LOG_DIR, "log_report_" + date, EnumFileExtension.TXT);
		
		try (PrintStream writer = new PrintStream(new FileOutputStream(fileName.getFullPath(), append))) {
			writer.println(message + FileUtil.ENTER);
			writer.println(e.getClass() + ": " + e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
                writer.println("\t" + e.getStackTrace()[i].toString());
            }
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private static String getDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	public static LoggerUtil getInstance() {
		return loggerUtil;
	}
}
