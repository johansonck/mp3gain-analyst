package be.sonck.mp3gain.impl.process;

public interface StreamReaderListener {

	void lineRead(String line);
	void done();
	void error(Exception e);
}
