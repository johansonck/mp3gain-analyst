package be.sonck.mp3gain.impl.factory;

import be.sonck.mp3gain.impl.model.AnalysisTO;

public final class AnalysisTOFactory {

	private AnalysisTOFactory() {}
	
	public static AnalysisTO create(String outputLine) {
		return create(outputLine.split("\t"));
	}
	
	public static AnalysisTO create(String[] array) {
		if (array == null || (array.length != 6 && array.length != 11)) {
			throw new IllegalArgumentException("a String array with 6 or 11 elements is required");
		}
		
		int i = 0;
		
		if (array.length == 6) {
			return new AnalysisTO(array[i++], array[i++], array[i++], array[i++], array[i++], array[i++], 
					null, null, null, null, null);
		} else {
			return new AnalysisTO(array[i++], array[i++], array[i++], array[i++], array[i++], array[i++], 
					array[i++], array[i++], array[i++], array[i++], array[i++]);
		}
	}
}
