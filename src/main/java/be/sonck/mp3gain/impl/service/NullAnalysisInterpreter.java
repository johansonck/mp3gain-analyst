package be.sonck.mp3gain.impl.service;

import java.math.BigDecimal;

import be.sonck.mp3gain.api.model.Clipping;
import be.sonck.mp3gain.api.service.AnalysisInterpreter;

public class NullAnalysisInterpreter implements AnalysisInterpreter {

	@Override
	public BigDecimal determineVolume() {
		return null;
	}

	@Override
	public BigDecimal determineDbModification(BigDecimal desiredVolume) {
		return null;
	}

	@Override
	public BigDecimal determineMaxNoClipDbGain() {
		return null;
	}

	@Override
	public BigDecimal determineMaxNoClipDb() {
		return null;
	}

	@Override
	public Clipping determineClipping(BigDecimal desiredVolume) {
		return null;
	}

	@Override
	public BigDecimal determineTechnicalModification(BigDecimal desiredVolume) {
		return null;
	}
}
