package be.sonck.mp3gain.api.service;

import java.io.File;
import java.util.List;

public interface Mp3GainChanger {

	void change(List<File> files, Mp3GainChangeListener listener, int change);
	void change(File file, Mp3GainChangeListener listener, int change);
}
