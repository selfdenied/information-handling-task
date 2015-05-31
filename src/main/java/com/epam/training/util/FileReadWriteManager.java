package com.epam.training.util;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.epam.training.constant.Constants;

/* the class contains static methods for reading/writing from/into a file */
public class FileReadWriteManager {
	/* getting the logger reference */
	private static final Logger LOG = Logger.getLogger(FileReadWriteManager.class);

	/*
	 * method allows reading from file using the standard java.io.BufferedReader
	 * class and some java.nio.file classes
	 */
	public static String readTextFromFile(String filePath) {
		Path fileP = Paths.get(filePath);
		Charset charset = Charset.forName(Constants.DEFAULT_CHARSET);
		StringBuilder fileContent = new StringBuilder();

		try (BufferedReader reader = Files.newBufferedReader(fileP, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.append(line).append(Constants.NEW_LINE_SYMBOL);
			}
		} catch (IOException exception) {
			LOG.error("Error. Unable to read from file!", exception);
		}
		return fileContent.substring(0, fileContent.length() - 1);
	}

	/*
	 * method allows writing into file using the standard java.io.BufferedWriter
	 * class and some java.nio.file classes
	 */
	public static void writeIntoFile(String content, String filePath) {
		Path fileP = Paths.get(filePath);
		Charset charset = Charset.forName(Constants.DEFAULT_CHARSET);

		try (BufferedWriter writer = Files.newBufferedWriter(fileP, charset)) {
			writer.write(content);
		} catch (IOException exception) {
			LOG.error("Error. Unable to write into file!", exception);
		}
		return;
	}
}
