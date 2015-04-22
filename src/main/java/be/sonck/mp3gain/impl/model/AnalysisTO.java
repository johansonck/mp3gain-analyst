package be.sonck.mp3gain.impl.model;

public class AnalysisTO {

	private final String file;
	private final String gain;
	private final String dbGain;
	private final String maxAmplitude;
	private final String maxGlobalGain;
	private final String minGlobalGain;
	private final String albumGain;
	private final String albumDbGain;
	private final String albumMaxAmplitude;
	private final String albumMaxGlobalGain;
	private final String albumMinGlobalGain;
	

	public AnalysisTO(String file, String gain, String dbGain,
			String maxAmplitude, String maxGlobalGain, String minGlobalGain,
			String albumGain, String albumDbGain, String albumMaxAmplitude,
			String albumMaxGlobalGain, String albumMinGlobalGain) {
		this.file = file;
		this.gain = gain;
		this.dbGain = dbGain;
		this.maxAmplitude = maxAmplitude;
		this.maxGlobalGain = maxGlobalGain;
		this.minGlobalGain = minGlobalGain;
		this.albumGain = albumGain;
		this.albumDbGain = albumDbGain;
		this.albumMaxAmplitude = albumMaxAmplitude;
		this.albumMaxGlobalGain = albumMaxGlobalGain;
		this.albumMinGlobalGain = albumMinGlobalGain;
	}

	public String getFile() {
		return file;
	}

	public String getGain() {
		return gain;
	}

	public String getDbGain() {
		return dbGain;
	}

	public String getMaxAmplitude() {
		return maxAmplitude;
	}

	public String getMaxGlobalGain() {
		return maxGlobalGain;
	}

	public String getMinGlobalGain() {
		return minGlobalGain;
	}

	public String getAlbumGain() {
		return albumGain;
	}

	public String getAlbumDbGain() {
		return albumDbGain;
	}

	public String getAlbumMaxAmplitude() {
		return albumMaxAmplitude;
	}

	public String getAlbumMaxGlobalGain() {
		return albumMaxGlobalGain;
	}

	public String getAlbumMinGlobalGain() {
		return albumMinGlobalGain;
	}
}
