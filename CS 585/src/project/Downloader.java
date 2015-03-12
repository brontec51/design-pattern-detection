package project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Downloader {

	public static void download(String link) throws Throwable {

			InputStream is = new URL(link).openStream();

		try {
			OutputStream os = new FileOutputStream("res-folder/master.zip");

			byte[] buffer = new byte[4096];
			int bytesRead;
			// read from is to buffer
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			// flush OutputStream to write any buffered data to file
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
