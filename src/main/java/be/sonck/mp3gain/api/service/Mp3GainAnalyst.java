package be.sonck.mp3gain.api.service;

import java.io.File;
import java.util.List;

import be.sonck.mp3gain.api.model.AlbumAnalysis;
import be.sonck.mp3gain.api.model.Analysis;

public interface Mp3GainAnalyst {

	void analyse(List<File> files, boolean trackAnalysisOnly, Mp3GainAnalysisListener listener);
	void analyse(File file, Mp3GainAnalysisListener listener);
	
	AlbumAnalysis getStoredAnalysis(List<File> files);
	Analysis getStoredAnalysis(File file);
}
