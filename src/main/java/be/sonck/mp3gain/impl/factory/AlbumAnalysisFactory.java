package be.sonck.mp3gain.impl.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import be.sonck.mp3gain.api.model.AlbumAnalysis;
import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.impl.model.AnalysisTO;

public final class AlbumAnalysisFactory {

	private AlbumAnalysisFactory() {}
	
	public static AlbumAnalysis create(InputStream inputStream) {
		try {
			List<AnalysisTO> toList = new ArrayList<AnalysisTO>();
			Analysis album = null;
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			
			String line = bufferedReader.readLine();
			
			// skip first line
			line = bufferedReader.readLine();
			
			while (line != null) {
				AnalysisTO to = AnalysisTOFactory.create(line);
				if (Analysis.ALBUM.equals(to.getFile())) {
					album = AnalysisFactory.createTrackInfo(to);
				} else {
					toList.add(to);
				}
				
				line = bufferedReader.readLine();
			}
			
			if (album == null && toList.size() > 0) {
				album = AnalysisFactory.createAlbumInfo(toList.get(0));
			}
			
			List<Analysis> tracks = new ArrayList<Analysis>();
			for (AnalysisTO to : toList) {
				tracks.add(AnalysisFactory.createTrackInfo(to));
			}
			
			return new AlbumAnalysis(album, tracks);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
