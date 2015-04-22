package be.sonck.mp3gain.api.model;

import java.io.File;

public class ProgressIndicator {

	private final File file;
	private final int trackNumber;
	private final int numberOfTracks;
	private final int progress;

	public ProgressIndicator(File file, int trackNumber, int numberOfTracks, int progress) {
		this.file = file;
		this.trackNumber = trackNumber;
		this.numberOfTracks = numberOfTracks;
		this.progress = progress;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public int getProgress() {
		return progress;
	}

	public File getFile() {
		return file;
	}
}
