package com.sloverse.util.resource;

import com.sloverse.util.FileUtil;

public class FileResourceLocation {

	private String fileName;
	private ResourceDirectory parentDirectory;
	private EnumFileExtension fileExtension;
	
	public FileResourceLocation(ResourceDirectory dir, String name, EnumFileExtension extension) {
		this(dir, name, extension, ResourceHelper.DEFAULT_DELIMITER);
	}
	
	public FileResourceLocation(ResourceDirectory dir, String name, EnumFileExtension extension, char delimiter) {
		parentDirectory = dir;
		fileName = name;
		fileExtension = extension;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFileNameWithExtension() {
		return fileName + fileExtension.getExtension();
	}
	
	public ResourceDirectory getParentDirectory() {
		return parentDirectory;
	}
	
	public String getFullPath() {
		return parentDirectory.getFullDirectory() + FileUtil.getFileSeparator(parentDirectory.isDirInJar()) + getFileNameWithExtension();
	}
	
	@Override
	public String toString() {
		return getFullPath();
	}
	
	public enum EnumFileExtension {
		TXT(".txt"),
		OBJ(".obj"),
		PNG(".png"),
		VS(".vs"),
		FS(".fs"),
		PROPERTIES(".bbs");
		
		String extension;
		private EnumFileExtension(String ext) {
			extension = ext;
		}
		
		public String getExtension() {
			return extension;
		}
	}
}
