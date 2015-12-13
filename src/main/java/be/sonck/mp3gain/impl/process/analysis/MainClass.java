package be.sonck.mp3gain.impl.process.analysis;

import be.sonck.mp3gain.api.model.AlbumAnalysis;
import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.api.service.AnalysisInterpreter;
import be.sonck.mp3gain.api.service.AnalysisInterpreterFactory;
import be.sonck.mp3gain.api.service.Mp3GainAnalysisListener;
import be.sonck.mp3gain.impl.process.analysis.RunnableMp3GainAnalyst.Command;
import be.sonck.mp3gain.impl.service.Mp3Filter;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

	private static final File LOCATION = new File(
//			"/Users/johansonck/Documents/Johan/Informatica/Ontwikkeling/Java/Projects/mp3gain-analyst/samples/Cerulean/");
			"/Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/Red Hot Chili Peppers/Blood Sugar Sex Magik/");
//			"/Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/�l�f Arnalds/Innundir Skinni");
//			"/Volumes/Macintosh HD 2/iTunes/iTunes Music/Music/Compilations/Ken Burns - Jazz_ The Story of America's/");

	public static void main(String[] args) throws Exception {
		Mp3GainAnalysisListener listener = new Mp3GainAnalysisListener() {
			private BigDecimal targetVolume = new BigDecimal("89");
			
			@Override
			public void notifyProgress(File file, int trackNumber, int numberOfTracks, int progress) {
			}

			@Override
			public void notifyTrackComplete(Analysis analysis) {
				logTrack(analysis, targetVolume);
			}
			
			@Override
			public void notifyError(Exception exception) {
				exception.printStackTrace();
			}

			@Override
			public void notifyAlbumComplete(AlbumAnalysis info) {
				Analysis album = info.getAlbum();
				if (album != null) {
					logTrack(album, targetVolume);
				}
			}

			private void logTrack(Analysis track, BigDecimal targetVolume) {
				AnalysisInterpreter interpreter = AnalysisInterpreterFactory.create(track);
				System.out.println(track.getFile() + ": "
						+ toString(interpreter.determineVolume()) + " - "
						+ toString(interpreter.determineDbModification(targetVolume)) + " - "
						+ toString(interpreter.determineTechnicalModification(targetVolume)) + " - "
						+ interpreter.determineClipping(targetVolume) + " - "
						+ toString(interpreter.determineMaxNoClipDbGain()) + " - "
						+ toString(interpreter.determineMaxNoClipDb()));
				// System.out.println(track);
			}
			
			private String toString(BigDecimal bigDecimal) {
				return (bigDecimal == null ? null : bigDecimal.toPlainString());
			}
		};

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new RunnableMp3GainAnalyst(listener, 
				Arrays.asList(LOCATION.listFiles(Mp3Filter.getFileFilter())), Command.ALBUM_ANALYSIS));
		executorService.shutdown();
	}
}
