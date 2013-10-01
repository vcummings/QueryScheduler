package com.budco.appReports.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtil {

	public static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}

	public static void writeToFile(BufferedWriter out, String result) throws IOException {
		try {
		        out.write(result);
		    } catch (IOException e) {
			    System.err.println("writeToFile");
				e.printStackTrace();
		    }
	}

	public static void writeNewLineToFile(BufferedWriter out, String result) throws IOException {
		try {
		        out.write("\n" + result);
		    } catch (IOException e) {
			    System.err.println("writeNewLineToFile");
				e.printStackTrace();
		    }
	}

	public static void closeFile(BufferedWriter out) throws IOException {
		writeNewLineToFile(out, " ");
		out.close();
	}

	public static BufferedWriter createFile(String fileName) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		return out;
	}
	
	public static void removeFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
		    file.delete();
		} else {
		    System.err.println(
		        "I cannot find '" + file + "' ('" + file.getAbsolutePath() + "')");
		}
	}
}
