package be.sonck.mp3gain.api.service;

import java.io.File;

public interface Mp3GainChangeListener {

	void notifyProgress(File file, int trackNumber, int numberOfTracks, int progress);
	void notifyTrackComplete(File file);
	void notifyError(Exception e);
}
