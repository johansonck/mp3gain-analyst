package be.sonck.mp3gain.api.service;

import java.math.BigDecimal;

import be.sonck.mp3gain.api.model.Clipping;

public interface AnalysisInterpreter {

	public static final BigDecimal DEFAULT_VOLUME = new BigDecimal("89");

	/**
	 * @return the volume (dB) of the current track
	 */
	public abstract BigDecimal determineVolume();

	/**
	 * @return the required modification (dB) to get the current track to the
	 *         desired volume
	 */
	public abstract BigDecimal determineDbModification(BigDecimal desiredVolume);
	
	/**
	 * @return the required technical modification to get the current track to the
	 *         desired volume
	 */
	public abstract BigDecimal determineTechnicalModification(BigDecimal desiredVolume);

	/**
	 * @return the maximum modification (dB) that can be added without clipping the track
	 */
	public abstract BigDecimal determineMaxNoClipDbGain();

	/**
	 * @return the maximum volume (dB) for this track without clipping the track
	 */
	public abstract BigDecimal determineMaxNoClipDb();

	/**
	 * @param desiredVolume
	 * @return a clipping indicator that indicates whether clipping will occur for this track
	 *         at the desire volume
	 */
	public abstract Clipping determineClipping(BigDecimal desiredVolume);

}