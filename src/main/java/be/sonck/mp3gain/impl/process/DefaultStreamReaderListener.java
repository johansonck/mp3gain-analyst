package be.sonck.mp3gain.impl.process;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DefaultStreamReaderListener implements StreamReaderListener {

	private final StringWriter stringWriter = new StringWriter();
	
	private Exception exception;

	@Override
	public void error(Exception e) {
		this.exception = e;
	}

	@Override
	public void done() {
	}

	@Override
	public void lineRead(String line) {
		new PrintWriter(stringWriter).println(line);
	}
	
	public Exception getException() {
		return exception;
	}
	
	public String getString() {
		return stringWriter.toString();
	}
}
