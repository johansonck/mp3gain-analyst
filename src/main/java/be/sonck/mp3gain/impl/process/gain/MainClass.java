package be.sonck.mp3gain.impl.process.gain;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.sonck.mp3gain.api.service.Mp3GainChangeListener;
import be.sonck.mp3gain.impl.service.Mp3Filter;

public class MainClass {

	private static final File LOCATION = new File(
//			"/Users/johansonck/Documents/Johan/Informatica/Ontwikkeling/Java/Projects/mp3gain-analyst/samples/Cerulean/");
//			"/Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/Red Hot Chili Peppers/Blood Sugar Sex Magik/");
			"/Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/îlšf Arnalds/Innundir Skinni");
//			"/Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/Compilations/Ken Burns - Jazz_ The Story of America's/");

	public static void main(String[] args) throws Exception {
		Mp3GainChangeListener listener = new Mp3GainChangeListener() {
			
			@Override
			public void notifyTrackComplete(File file) {
				System.out.println("completed track " + file);
			}
			
			@Override
			public void notifyError(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void notifyProgress(File file, int trackNumber, int numberOfTracks, int progress) {
				System.out.println(progress + "% of track " + file + " [" + trackNumber + "/" + numberOfTracks + "]");
			}
		};

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new RunnableMp3GainChanger(listener, 
				Arrays.asList(LOCATION.listFiles(Mp3Filter.getFileFilter())), -4));
		executorService.shutdown();
	}
}
