package be.sonck.mp3gain.impl.process.analysis;

import java.util.ArrayList;
import java.util.List;

import be.sonck.mp3gain.api.model.AlbumAnalysis;
import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.api.service.Mp3GainAnalysisListener;
import be.sonck.mp3gain.impl.factory.AnalysisFactory;
import be.sonck.mp3gain.impl.factory.AnalysisTOFactory;
import be.sonck.mp3gain.impl.model.AnalysisTO;
import be.sonck.mp3gain.impl.process.StreamReaderListener;

class InputStreamListener implements StreamReaderListener {
	
	private List<Analysis> trackAnalyses = new ArrayList<Analysis>();
	private Analysis albumTrackAnalysis;
	private boolean first = true;
	private AlbumAnalysis albumAnalysis;
	private final Mp3GainAnalysisListener listener;
	private AnalysisTO firstTO;
	
	public InputStreamListener(Mp3GainAnalysisListener listener) {
		this.listener = listener;
	}

	@Override
	public void lineRead(String line) {
		// ignore the first line
		if (first) {
			first = false;
			return;
		}
		
		// sample where the line contains only track gain information:
		// 01 The Power Of Equality.mp3    -1      -1.950000	31618.612984	185	111
		
		// sample where the line contains only album gain information:
		// "Album"	-2	-3.120000	34114.924472	187	111
		
		// sample where the line contains both track and album gain information:
		// 01 Vinur Minn.mp3	6	9.750000	4614.062080	169	99	8	11.750000	4662.755328	169	98
		
		AnalysisTO to = AnalysisTOFactory.create(line);
		
		if (firstTO == null) { firstTO = to; }
		
		if (Analysis.ALBUM.equals(to.getFile())) {
			albumTrackAnalysis = AnalysisFactory.createTrackInfo(to);
		} else {
			Analysis analysis = AnalysisFactory.createTrackInfo(to);
			trackAnalyses.add(analysis);
			listener.notifyTrackComplete(analysis);
		}
	}

	@Override
	public void done() {
		if (albumTrackAnalysis == null && firstTO != null) {
			// There was no separate line containing album information.
			// Look for album information in the first line.
			albumTrackAnalysis = AnalysisFactory.createAlbumInfo(firstTO);
			
			if (albumTrackAnalysis == null && trackAnalyses.size() == 1) {
				// When there's only one track, the album gain may be marked as "NA".
				// In this case, simply use the single track info as album info as well.
				// sample:
				// 02 Long Train Runnin'.mp3	-1	-1.240000	32950.255616	210	107	NA	NA	NA	NA	NA
				albumTrackAnalysis = AnalysisFactory.createTrackInfo(firstTO);
			}
		}
		
		albumAnalysis = new AlbumAnalysis(albumTrackAnalysis, trackAnalyses);
		listener.notifyAlbumComplete(albumAnalysis);
	}
	
	@Override
	public void error(Exception e) {
		listener.notifyError(e);
	}

	public AlbumAnalysis getAlbumAnalysis() {
		return albumAnalysis;
	}
}
