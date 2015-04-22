package be.sonck.mp3gain.api.service;

import be.sonck.mp3gain.impl.service.DefaultMp3GainChanger;

public class Mp3GainChangerFactory {
	
	private static Class<? extends Mp3GainChanger> implementationClass = DefaultMp3GainChanger.class;
	
	
	public static void setImplementationClass(Class<? extends Mp3GainChanger> implementationClass) {
		Mp3GainChangerFactory.implementationClass = implementationClass;
	}

	public static Mp3GainChanger create() {
		try {
			return implementationClass.newInstance();
			
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
