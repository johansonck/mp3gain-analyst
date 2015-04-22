package be.sonck.mp3gain.impl.service;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.math.RoundingMode;

import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.api.model.Clipping;
import be.sonck.mp3gain.api.service.AnalysisInterpreter;

/**
 * The analysis returned from mp3gain always assumes the normalization target is
 * 89 dB. This class allows you to calculate the required dB modification for
 * your custom target volume.
 */
public class DefaultAnalysisInterpreter implements AnalysisInterpreter {

	private static final BigDecimal ONE_MILLION = new BigDecimal("1000000");
	private static final BigDecimal DB_GAIN_INCREMENT = new BigDecimal("1.5");
	
	// 32767
	private static final BigDecimal HIGH_INTEGER = new BigDecimal("2").pow(15).subtract(BigDecimal.ONE);

	private final Analysis analysis;

	public DefaultAnalysisInterpreter(Analysis analysis) {
		this.analysis = analysis;
	}

	@Override
	public BigDecimal determineVolume() {
		return DEFAULT_VOLUME.subtract(getDbGain()).setScale(1, HALF_UP);
	}

	@Override
	public BigDecimal determineDbModification(BigDecimal desiredVolume) {
		return roundToIncrement(determineDbDeviation(desiredVolume).add(getDbGain()));
	}

	@Override
	public BigDecimal determineTechnicalModification(BigDecimal desiredVolume) {
		return determineDbDeviation(desiredVolume).add(getDbGain()).divide(DB_GAIN_INCREMENT, 0, HALF_UP);
	}
	
	@Override
	public BigDecimal determineMaxNoClipDbGain() {
		BigDecimal maxAmplitude = analysis.getMaxAmplitude();
		if (maxAmplitude == null || maxAmplitude.compareTo(ZERO) <= 0
				|| maxAmplitude.compareTo(ONE_MILLION) >= 0) {
			return ZERO;
		}
		
		// 4 * log2(32767 / maxAmplitude)
		BigDecimal log = log2(HIGH_INTEGER.doubleValue() / maxAmplitude.doubleValue());
		BigDecimal maxNoClipMp3Gain = new BigDecimal("4").multiply(log).setScale(0, RoundingMode.FLOOR);
		return maxNoClipMp3Gain.multiply(DB_GAIN_INCREMENT);
	}
	
	@Override
	public BigDecimal determineMaxNoClipDb() {
		return determineVolume().add(determineMaxNoClipDbGain());
	}

	@Override
	public Clipping determineClipping(BigDecimal desiredVolume) {
		if (doFirstCalculation(desiredVolume).compareTo(HIGH_INTEGER) <= 0) { 
			return Clipping.NO; 
		} else if (doSecondCalculation(desiredVolume).compareTo(HIGH_INTEGER) <= 0) {
			return Clipping.YES;
		} else {
			return Clipping.MAYBE;
		}
	}
	
	private BigDecimal log2(double x) {
		return BigDecimal.valueOf(Math.log(x) / Math.log(2));
	}

	private BigDecimal doSecondCalculation(BigDecimal desiredVolume) {
		BigDecimal gain = new BigDecimal("83").subtract(desiredVolume.subtract(analysis.getDbGain()));
		double pow = Math.pow(2, gain.doubleValue() / 6.0206);
		
		return analysis.getMaxAmplitude().multiply(new BigDecimal(pow));
	}

	private BigDecimal doFirstCalculation(BigDecimal desiredVolume) {
		BigDecimal gain = analysis.getDbGain()
				.add(determineDbDeviation(desiredVolume))
				.divide(DB_GAIN_INCREMENT, 0, HALF_UP);
		
		double pow = Math.pow(2, gain.doubleValue() / 4.0);
		
		return analysis.getMaxAmplitude().multiply(new BigDecimal(pow));
	}

	private BigDecimal determineDbDeviation(BigDecimal desiredVolume) {
		return desiredVolume.subtract(DEFAULT_VOLUME);
	}

	private BigDecimal roundToIncrement(BigDecimal value) {
		return value
				.divide(DB_GAIN_INCREMENT, 0, HALF_UP)
				.multiply(DB_GAIN_INCREMENT)
				.setScale(DB_GAIN_INCREMENT.scale(), HALF_UP);
	}

	private BigDecimal getDbGain() {
		BigDecimal dbGain = analysis.getDbGain();
		return (dbGain == null ? ZERO : dbGain);
	}
}
