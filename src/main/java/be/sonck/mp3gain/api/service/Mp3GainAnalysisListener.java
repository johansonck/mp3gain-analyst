package be.sonck.mp3gain.api.service;

import java.io.File;

import be.sonck.mp3gain.api.model.AlbumAnalysis;
import be.sonck.mp3gain.api.model.Analysis;

public interface Mp3GainAnalysisListener {

	void notifyProgress(File file, int trackNumber, int numberOfTracks, int progress);
	void notifyTrackComplete(Analysis analysis);
	void notifyAlbumComplete(AlbumAnalysis albumAnalysis);
	void notifyError(Exception exception);
}
