package be.sonck.mp3gain.impl.process.gain;

import be.sonck.mp3gain.api.service.Mp3GainChangeListener;
import be.sonck.mp3gain.impl.process.StreamReaderListener;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

class ErrorStreamListener implements StreamReaderListener {

	private final List<File> files;
	private final Mp3GainChangeListener listener;
	
	private File currentTrack;
	private int indexCurrentTrack;

	public ErrorStreamListener(List<File> files, Mp3GainChangeListener listener) {
		this.files = files;
		this.listener = listener;
	}

	@Override
	public void lineRead(String line) {
		if (StringUtils.isBlank(line)) { return; }
		
		if (line.startsWith("Applying gain")) {
			handleTrackStarted(line);
		} else if (line.endsWith("bytes written")) {
			handleProgress(line);
		} else if (line.equals("done")) {
			handleTrackDone();
		}
	}

	@Override
	public void done() {
	}

	@Override
	public void error(Exception e) {
	}
	
	private void handleTrackStarted(String line) {
		// sample input line:
		// Applying gain change of -4 to /Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/Olaf Arnalds/Innundir Skinni/04 Vinkonur.mp3...
		
		String filename = line.substring(line.indexOf('/'), line.length() - 3);
		currentTrack = new File(filename);
		++indexCurrentTrack;
	}

	private void handleProgress(String line) {
		// sample input line:
		// 4% of 5776650 bytes written
		
		int progress = Integer.parseInt(line.substring(0, line.indexOf('%')));
		listener.notifyProgress(currentTrack, indexCurrentTrack, files.size(), progress);
	}

	private void handleTrackDone() {
		listener.notifyTrackComplete(currentTrack);
	}
}
