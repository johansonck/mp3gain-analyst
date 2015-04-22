package be.sonck.mp3gain.impl.factory;

import java.io.File;
import java.util.List;

import be.sonck.mp3gain.api.model.ProgressIndicator;

public final class ProgressIndicatorFactory {

	private ProgressIndicatorFactory() {
	}

	public static ProgressIndicator create(List<File> files, String line) {
		try {
			// sample line when only one track is processed:
			// 20% of 9242352 bytes analyzed
			
			// sample line when more tracks are processed:
			// [1/10] 20% of 9242352 bytes analyzed
			
			// When the line doesn't have track counter information, the default is 1.
			String trackNumber = "1";
			String numberOfTracks = "1";
			
			if (line.startsWith("[")) {
				trackNumber = line.substring(1, line.indexOf('/'));
				numberOfTracks = line.substring(line.indexOf('/') + 1, line.indexOf(']'));
			}

			// If the line has track counter information, skip it before proceeding.
			String editedLine = line;
			if (line.startsWith("[")) {
				editedLine = editedLine.substring(editedLine.indexOf(" ") + 1);
			}
			
			String progress = editedLine.substring(0, editedLine.indexOf('%'));

			Integer trackNumberInt = toInteger(trackNumber);
			
			return new ProgressIndicator(files.get(trackNumberInt - 1), trackNumberInt,
					toInteger(numberOfTracks), toInteger(progress));
			
		} catch (Exception e) {
			System.err.println("Error occurred for line '" + line + "'");
			throw new RuntimeException(e);
		}
	}

	private static int toInteger(String value) {
		return Integer.parseInt(value.trim());
	}
}
