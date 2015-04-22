package be.sonck.mp3gain.api.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumAnalysis {

	private final Analysis album;
	private final List<Analysis> tracks;
	
	public AlbumAnalysis(Analysis album, List<Analysis> tracks) {
		this.album = album;
		this.tracks = new ArrayList<Analysis>(tracks);
	}

	public Analysis getAlbum() {
		return album;
	}

	public List<Analysis> getTracks() {
		return Collections.unmodifiableList(tracks);
	}
}
