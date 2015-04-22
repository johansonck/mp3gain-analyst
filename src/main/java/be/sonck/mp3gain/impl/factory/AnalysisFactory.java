package be.sonck.mp3gain.impl.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.impl.model.AnalysisTO;

public final class AnalysisFactory {

	private AnalysisFactory() {}

	public static Analysis createTrackInfo(AnalysisTO to) {
		return create(to.getFile(), to.getGain(), to.getDbGain(), to.getMaxAmplitude(),
				to.getMaxGlobalGain(), to.getMinGlobalGain());
	}

	public static Analysis createAlbumInfo(AnalysisTO to) {
		if (to.getAlbumGain() == null) { return null; }
		if (to.getAlbumGain().equals("NA")) { return null; }

		return create(Analysis.ALBUM, to.getAlbumGain(), to.getAlbumDbGain(),
				to.getAlbumMaxAmplitude(), to.getAlbumMaxGlobalGain(), to.getAlbumMinGlobalGain());
	}

	private static Analysis create(String file, String gain, String dbGain, String maxAmplitude,
			String maxGlobalGain, String minGlobalGain) {

		return new Analysis(toFile(file), toInteger(gain), toBigDecimal(dbGain),
				toBigDecimal(maxAmplitude), toInteger(maxGlobalGain), toInteger(minGlobalGain));
	}

	private static File toFile(String file) {
		if (Analysis.ALBUM.equals(file)) { return null; }
		
		File realFile = new File(file);
		if (!realFile.exists()) {
			throw new RuntimeException(new FileNotFoundException(file));
		}
		
		return realFile;
	}

	private static BigDecimal toBigDecimal(String value) {
		return ("NA".equals(value) ? null : new BigDecimal(value));
	}

	private static Integer toInteger(String value) {
		return ("NA".equals(value) ? null : Integer.parseInt(value));
	}
}
