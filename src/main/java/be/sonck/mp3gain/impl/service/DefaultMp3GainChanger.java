package be.sonck.mp3gain.impl.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.sonck.mp3gain.api.service.Mp3GainChangeListener;
import be.sonck.mp3gain.api.service.Mp3GainChanger;
import be.sonck.mp3gain.impl.process.gain.RunnableMp3GainChanger;

public class DefaultMp3GainChanger implements Mp3GainChanger {

	@Override
	public void change(List<File> files, Mp3GainChangeListener listener, int change) {
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//		executorService.execute(new RunnableMp3GainChanger(listener, files, change));
//		executorService.shutdown();
		new RunnableMp3GainChanger(listener, files, change).run();
	}

	@Override
	public void change(File file, Mp3GainChangeListener listener, int change) {
		change(asList(file), listener, change);
	}
	
	private List<File> asList(File file) {
		List<File> files = new ArrayList<File>(1);
		files.add(file);
		return files;
	}
}
