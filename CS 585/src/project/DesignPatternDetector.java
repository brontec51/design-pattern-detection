package project;

import java.io.File;

public class DesignPatternDetector {

	public void detect() {

		File[] dirs = new File("res-folder/master").listFiles()[0].listFiles();

		File srcDir = null;

		for (int i = 0; i < dirs.length; i++)
			if (dirs[i].getName().equals("src"))
				srcDir = dirs[i];

		if (srcDir == null)
			return;

		detectPattern(srcDir);
	}

	private void detectPattern(File srcDir) {

		if (srcDir.isDirectory()) {

			// VisitorPatternDetector.detect(srcDir.getAbsolutePath() +
			// File.separator);

			ChainOfResponsibilityPatternDetector.detect(srcDir
					.getAbsolutePath() + File.separator);

			if (srcDir.list().length > 0) {

				File[] subDirs = srcDir.listFiles();

				for (File subDir : subDirs)
					detectPattern(subDir);
			}
		}
	}
}
