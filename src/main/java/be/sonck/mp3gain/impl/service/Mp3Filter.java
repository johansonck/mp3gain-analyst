package be.sonck.mp3gain.impl.service;

import java.io.FileFilter;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.SuffixFileFilter;

public final class Mp3Filter extends SuffixFileFilter {
	
	private static final Mp3Filter INSTANCE = new Mp3Filter();
	
	private Mp3Filter() {
		super(new String[] {"mp3", "MP3"});
	}
	
	public static FileFilter getFileFilter() {
		return INSTANCE;
	}
	
	public static FilenameFilter getFilenameFilter() {
		return INSTANCE;
	}
}
