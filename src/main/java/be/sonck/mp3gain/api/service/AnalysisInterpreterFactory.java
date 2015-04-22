package be.sonck.mp3gain.api.service;

import be.sonck.mp3gain.api.model.Analysis;
import be.sonck.mp3gain.impl.service.DefaultAnalysisInterpreter;
import be.sonck.mp3gain.impl.service.NullAnalysisInterpreter;

public final class AnalysisInterpreterFactory {
	
	private AnalysisInterpreterFactory() {}

	public static AnalysisInterpreter create(Analysis analysis) {
		if (analysis == null || analysis.getDbGain() == null) {
			return new NullAnalysisInterpreter();
		} else {
			return new DefaultAnalysisInterpreter(analysis);
		}
	}
}
