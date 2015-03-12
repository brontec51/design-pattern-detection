package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

	static void unzip(String zipFileName) {

		try {
			delete(new File("res-folder/master"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		byte[] buffer = new byte[2048];

		try {

			FileInputStream fInput = new FileInputStream(zipFileName);

			ZipInputStream zipInput = new ZipInputStream(fInput);

			ZipEntry entry = zipInput.getNextEntry();

			while (entry != null) {

				String entryName = "res-folder/master/" + entry.getName();

				File file = new File(entryName);

				// System.out.println("Unzip file " + entryName + " to " +
				// file.getAbsolutePath());

				// create the directories of the zip directory

				if (entry.isDirectory()) {

					File newDir = new File(file.getAbsolutePath());

					if (!newDir.exists()) {

						boolean success = newDir.mkdirs();

						if (success == false)
							System.out.println("Problem creating Folder");
					}
				}

				else {

					FileOutputStream fOutput = new FileOutputStream(file);

					int count = 0;

					while ((count = zipInput.read(buffer)) > 0) {

						// write 'count' bytes to the file output stream

						fOutput.write(buffer, 0, count);
					}

					fOutput.close();
				}

				// close ZipEntry and take the next one

				zipInput.closeEntry();

				entry = zipInput.getNextEntry();
			}

			// close the last ZipEntry

			zipInput.closeEntry();

			zipInput.close();

			fInput.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				// System.out.println("Directory is deleted : " +
				// file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					// System.out.println("Directory is deleted : " +
					// file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			// System.out.println("File is deleted : " +
			// file.getAbsolutePath());
		}
	}
}
