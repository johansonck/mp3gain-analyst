package be.sonck.mp3gain.impl.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class RunnableStreamReader implements Runnable {
	
	private final InputStream inputStream;
	private final StreamReaderListener listener;
	

	public RunnableStreamReader(InputStream inputStream, StreamReaderListener listener) {
		this.inputStream = inputStream;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				listener.lineRead(line.trim());
			}
			
			listener.done();
			
		} catch (Exception e) {
			listener.error(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
}
