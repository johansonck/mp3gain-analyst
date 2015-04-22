package be.sonck.mp3gain.api.model;

import java.io.File;
import java.math.BigDecimal;

public class Analysis {

	public static final String ALBUM = "\"Album\"";

	private final File file;
	private final Integer gain;
	private final BigDecimal dbGain;
	private final BigDecimal maxAmplitude;
	private final Integer maxGlobalGain;
	private final Integer minGlobalGain;

	public Analysis(File file, Integer gain, BigDecimal dbGain, BigDecimal maxAmplitude,
			Integer maxGlobalGain, Integer minGlobalGain) {
		this.file = file;
		this.gain = gain;
		this.dbGain = dbGain;
		this.maxAmplitude = maxAmplitude;
		this.maxGlobalGain = maxGlobalGain;
		this.minGlobalGain = minGlobalGain;
	}

	public File getFile() {
		return file;
	}

	public Integer getGain() {
		return gain;
	}

	public BigDecimal getDbGain() {
		return dbGain;
	}

	public BigDecimal getMaxAmplitude() {
		return maxAmplitude;
	}

	public Integer getMaxGlobalGain() {
		return maxGlobalGain;
	}

	public Integer getMinGlobalGain() {
		return minGlobalGain;
	}

	@Override
	public String toString() {
		return "Analysis [file=" + file.getAbsolutePath() + ", gain=" + gain + ", dbGain="
				+ dbGain.toPlainString() + ", maxAmplitude=" + maxAmplitude.toPlainString()
				+ ", maxGlobalGain=" + maxGlobalGain + ", minGlobalGain=" + minGlobalGain + "]";
	}

	@Override
	public boolean equals(Object that) {
		if (that == null || !that.getClass().equals(getClass())) { return false; }
		
		return file.equals(((Analysis) that).file);
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}
}
