package be.sonck.mp3gain.impl.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.sonck.mp3gain.api.model.AlbumAnalysis;
import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.api.service.Mp3GainAnalysisListener;
import be.sonck.mp3gain.api.service.Mp3GainAnalyst;
import be.sonck.mp3gain.impl.process.analysis.RunnableMp3GainAnalyst;
import be.sonck.mp3gain.impl.process.analysis.RunnableMp3GainAnalyst.Command;

public class DefaultMp3GainAnalyst implements Mp3GainAnalyst {
	
	private static class Mp3GainAnalysisListenerStub implements Mp3GainAnalysisListener {
		private AlbumAnalysis albumAnalysis;
		private Exception exception;
		private boolean complete;

		@Override
		public void notifyAlbumComplete(AlbumAnalysis info) {
			this.albumAnalysis = info;
			complete = true;
		}

		@Override
		public void notifyError(Exception exception) {
			this.exception = exception;
			complete = true;
		}

		@Override
		public void notifyProgress(File file, int trackNumber, int numberOfTracks, int progress) {
		}

		@Override
		public void notifyTrackComplete(Analysis analysis) {
		}

		public AlbumAnalysis getAlbumAnalysis() {
			return albumAnalysis;
		}

		public Exception getException() {
			return exception;
		}
		
		public boolean isComplete() {
			return complete;
		}
	}

	@Override
	public void analyse(List<File> files, boolean trackAnalysisOnly, Mp3GainAnalysisListener listener) {
		execute(files, listener, (trackAnalysisOnly ? Command.TRACK_ANALYSIS : Command.ALBUM_ANALYSIS));
	}

	@Override
	public void analyse(File file, Mp3GainAnalysisListener listener) {
		analyse(asList(file), true, listener);
	}

	@Override
	public AlbumAnalysis getStoredAnalysis(List<File> files) {
		Mp3GainAnalysisListenerStub stub = new Mp3GainAnalysisListenerStub();
		
		execute(files, stub, Command.CHECK_STORED_TAG_INFO);
		
		while (!stub.isComplete()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
		Exception e = stub.getException();
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		} else if (e != null) {
			throw new RuntimeException(e);
		}
		
		return stub.getAlbumAnalysis();
	}

	@Override
	public Analysis getStoredAnalysis(File file) {
		AlbumAnalysis albumAnalysis = getStoredAnalysis(asList(file));
		
		List<Analysis> tracks = albumAnalysis.getTracks();
		int size = tracks.size();
		if (size != 1) {
			throw new IllegalStateException("expected 1 tracks but got " + size);
		}
		
		return tracks.get(0);
	}
	
	private void execute(List<File> files, Mp3GainAnalysisListener listener, Command command) {
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.execute(new RunnableMp3GainAnalyst(listener, files, command));
//		executorService.shutdown();
		new RunnableMp3GainAnalyst(listener, files, command).run();
	}
	
	private List<File> asList(File file) {
		List<File> files = new ArrayList<File>(1);
		files.add(file);
		return files;
	}
}
