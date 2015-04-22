package be.sonck.mp3gain.impl.process.analysis;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import be.sonck.mp3gain.api.model.ProgressIndicator;
import be.sonck.mp3gain.api.service.Mp3GainAnalysisListener;
import be.sonck.mp3gain.impl.factory.ProgressIndicatorFactory;
import be.sonck.mp3gain.impl.process.StreamReaderListener;

class ErrorStreamListener implements StreamReaderListener {

	private final Mp3GainAnalysisListener listener;
	private final List<File> files;

	public ErrorStreamListener(List<File> files, Mp3GainAnalysisListener listener) {
		this.files = files;
		this.listener = listener;
	}

	@Override
	public void lineRead(String line) {
		if (StringUtils.isBlank(line)) { return; }
		
		ProgressIndicator indicator = ProgressIndicatorFactory.create(files, line);
		listener.notifyProgress(indicator.getFile(), indicator.getTrackNumber(), indicator.getNumberOfTracks(),
				indicator.getProgress());
	}
	
	@Override
	public void error(Exception e) {
		listener.notifyError(e);
	}

	@Override
	public void done() {
	}
}
