package be.sonck.mp3gain.impl.process.analysis;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import be.sonck.mp3gain.api.service.Mp3GainAnalysisListener;
import be.sonck.mp3gain.impl.process.RunnableStreamReader;

public class RunnableMp3GainAnalyst implements Runnable {
	
	private static final String MP3GAIN = "/usr/bin/mp3gain";
	
	public enum Command {
		
		CHECK_STORED_TAG_INFO(new String[] { MP3GAIN, "-o", "-s", "c" }),
		DELETE_STORED_TAG_INFO(new String[] { MP3GAIN, "-o", "-s", "d" }),
		TRACK_ANALYSIS(new String[] { MP3GAIN, "-o", "-e" }),
		FORCED_TRACK_ANALYSIS(new String[] { MP3GAIN, "-o", "-e", "-s", "r" }),
		ALBUM_ANALYSIS(new String[] { MP3GAIN, "-o" }),
		;
		
		private final String[] command;
		
		private Command(String[] command) {
			this.command = command;
		}

		public String[] getCommand() {
			return command;
		}
	}

	private final Mp3GainAnalysisListener listener;
	private final List<File> files;
	private final Command command;
	
	public RunnableMp3GainAnalyst(Mp3GainAnalysisListener listener, List<File> files, Command command) {
		this.listener = listener;
		this.files = files;
		this.command = command;
	}
	
	@Override
	public void run() {
		try {
			Process process = Runtime.getRuntime().exec(composeFullCommand());
			
			InputStreamListener inputStreamListener = new InputStreamListener(listener);
			ErrorStreamListener errorStreamListener = new ErrorStreamListener(files, listener);
			
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			executorService.execute(new RunnableStreamReader(process.getErrorStream(), errorStreamListener));
			executorService.execute(new RunnableStreamReader(process.getInputStream(), inputStreamListener));
			executorService.shutdown();
			try {
				executorService.awaitTermination(1, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}
			
			process.waitFor();
			
		} catch (Exception e) {
			listener.notifyError(e);
		}
	}

	private String[] composeFullCommand() {
		String[] commandParts = command.getCommand();
		
		int i = 0;
		String[] fullCommand = new String[commandParts.length + files.size()];
		
		for (int l = 0; l < commandParts.length; l++) {
			fullCommand[i++] = commandParts[l];
		}
		
		for (File file : files) {
			fullCommand[i++] = file.getAbsolutePath();
		}
		
		return fullCommand;
	}
}
