package com.sloverse.util.resource;

import java.util.ArrayList;
import java.util.List;

import com.sloverse.util.FileUtil;
import com.sloverse.util.MathUtil;

public class ResourceDirectory {

	private String[] splitParentDirectory;
	private String parentDirectory;
	private String currentDirectory;
	private boolean isDirInJar;
	
	public ResourceDirectory(String parentDir, String currentDir, boolean isJar) {
		isDirInJar = isJar;
		createDirectory(parentDir, ResourceHelper.DEFAULT_DELIMITER);
		currentDirectory = currentDir;
	}
	
	private void createDirectory(String string, char delimiter) {
		StringBuilder b = new StringBuilder();
		List<String> dir = new ArrayList<>();
		int beginIndex = 0;
		
		for (int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);
			if (currentChar == delimiter) {
				b.append(FileUtil.getFileSeparator(isDirInJar));
				dir.add(string.substring(beginIndex, i));
				beginIndex = i + 1;
			}
			else {
				b.append(currentChar);
			}
		}
		dir.add(string.substring(beginIndex));
		
		parentDirectory = b.toString();
		splitParentDirectory = dir.toArray(new String[dir.size()]);
	}
	
	/**
	 * @param directoriesUp : The amount of directories to go up.
	 * @return A string with the correct path name. If directoriesUp is clamped between 1 and length of directory.
	 */
	public String getParentDir(int directoriesUp) {
		StringBuilder b = new StringBuilder();
		
		int directories = MathUtil.clampInt(directoriesUp, 1, splitParentDirectory.length);
		
		b.append(splitParentDirectory[0]);
		for (int i = 1; i < (splitParentDirectory.length - directories); i++) {
			b.append(FileUtil.getFileSeparator(isDirInJar) + splitParentDirectory[i]);
		}
		
		int dirIndex = (splitParentDirectory.length - directories);
		return (b.toString() + ((dirIndex == 0) ? "" : FileUtil.getFileSeparator(isDirInJar) + splitParentDirectory[dirIndex]));
	}
	
	public boolean isDirInJar() {
		return isDirInJar;
	}
	
	public String getParentDirectory() {
		return parentDirectory;
	}
	
	public String getCurrentDirectory() {
		return currentDirectory;
	}
	
	public String getFullDirectory() {
		return parentDirectory + FileUtil.getFileSeparator(isDirInJar) + currentDirectory;
	}
	
	@Override
	public String toString() {
		return getFullDirectory();
	}
}
