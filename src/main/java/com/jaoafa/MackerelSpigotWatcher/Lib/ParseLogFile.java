package com.jaoafa.MackerelSpigotWatcher.Lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ParseLogFile {
	boolean fileExists = false;

	long severeCount = 0;
	long warningCount = 0;
	long infoCount = 0;
	long configCount = 0;
	long fineCount = 0;
	long finerCount = 0;
	long finestCount = 0;
	long errorCount = 0;

	public ParseLogFile() {
		File latestLogFile = new File("./logs/latest.log");
		if (!latestLogFile.exists()) {
			return;
		}
		fileExists = true;
		try {
			List<String> lines = Files.readAllLines(latestLogFile.toPath());
			severeCount = lines.stream().filter(line -> line != null && line.contains("/SEVERE]:")).count();
			warningCount = lines.stream().filter(line -> line != null && line.contains("/WARNING]:")).count();
			infoCount = lines.stream().filter(line -> line != null && line.contains("/INFO]:")).count();
			configCount = lines.stream().filter(line -> line != null && line.contains("/CONFIG]:")).count();
			fineCount = lines.stream().filter(line -> line != null && line.contains("/FINE]:")).count();
			finerCount = lines.stream().filter(line -> line != null && line.contains("/FINER]:")).count();
			finestCount = lines.stream().filter(line -> line != null && line.contains("/FINEST]:")).count();
			errorCount = lines.stream().filter(line -> line != null && line.contains("/ERROR]:")).count();
		} catch (IOException e) {
			return;
		}
	}

	public boolean isFileExists() {
		return fileExists;
	}

	public long getSevereCount() {
		return severeCount;
	}

	public long getWarningCount() {
		return warningCount;
	}

	public long getInfoCount() {
		return infoCount;
	}

	public long getConfigCount() {
		return configCount;
	}

	public long getFineCount() {
		return fineCount;
	}

	public long getFinerCount() {
		return finerCount;
	}

	public long getFinestCount() {
		return finestCount;
	}

	public long getErrorCount() {
		return errorCount;
	}
}
