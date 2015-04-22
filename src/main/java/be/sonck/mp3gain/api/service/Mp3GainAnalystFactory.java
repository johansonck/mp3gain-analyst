package be.sonck.mp3gain.api.service;

import be.sonck.mp3gain.impl.service.DefaultMp3GainAnalyst;

public class Mp3GainAnalystFactory {
	
	private static Class<? extends Mp3GainAnalyst> implementationClass = DefaultMp3GainAnalyst.class;
	
	
	public static void setImplementationClass(Class<? extends Mp3GainAnalyst> implementationClass) {
		Mp3GainAnalystFactory.implementationClass = implementationClass;
	}

	public static Mp3GainAnalyst create() {
		try {
			return implementationClass.newInstance();
			
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
