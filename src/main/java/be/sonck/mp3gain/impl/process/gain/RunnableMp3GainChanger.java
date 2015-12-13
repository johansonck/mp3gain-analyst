package be.sonck.mp3gain.impl.process.gain;

import be.sonck.mp3gain.api.service.Mp3GainChangeListener;
import be.sonck.mp3gain.impl.process.Constants;
import be.sonck.mp3gain.impl.process.DefaultStreamReaderListener;
import be.sonck.mp3gain.impl.process.RunnableStreamReader;
import be.sonck.mp3gain.impl.process.StreamReaderListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnableMp3GainChanger implements Runnable {

	private static final String[] APPLY_GAIN = new String[] {Constants.MP3GAIN, "-g"};
	private final List<File> files;
	private final Mp3GainChangeListener listener;
	private final int change;

	public RunnableMp3GainChanger(Mp3GainChangeListener listener, List<File> files, int change) {
		this.listener = listener;
		this.files = files;
		this.change = change;
	}
	
	@Override
	public void run() {
		try {
			Process process = Runtime.getRuntime().exec(composeFullCommand());
			
			StreamReaderListener inputStreamListener = new DefaultStreamReaderListener();
			StreamReaderListener errorStreamListener = new ErrorStreamListener(files, listener);
			
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			executorService.execute(new RunnableStreamReader(process.getErrorStream(), errorStreamListener));
			executorService.execute(new RunnableStreamReader(process.getInputStream(), inputStreamListener));
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);
			
			process.waitFor();
			
		} catch (Exception e) {
			listener.notifyError(e);
		}
	}

	private String[] composeFullCommand() {
		String[] commandParts = APPLY_GAIN;
		
		int i = 0;
		String[] fullCommand = new String[commandParts.length + files.size() + 1];
		
		for (int l = 0; l < commandParts.length; l++) {
			fullCommand[i++] = commandParts[l];
		}
		
		fullCommand[i++] = Integer.toString(change);
		
		for (File file : files) {
			fullCommand[i++] = file.getAbsolutePath();
		}
		
		return fullCommand;
	}
}
