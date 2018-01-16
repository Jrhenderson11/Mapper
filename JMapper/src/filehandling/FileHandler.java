package filehandling;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

	public static boolean saveMap(String fileName, int[][] grid) {
		String[] lines = new String[grid.length];
		int j =0;
		for (int[] nums : grid) {
			String line = Integer.toString(nums[0]);
			
			for (int i = 1; i < nums.length; i++) {
				line += ("," + Integer.toString(nums[i]));
			}
			lines[j++] = line;
		}
		/*
		for (String line:lines) {
			System.out.println("line: " + line);
		}
		*/		
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(fileName));
			try {
				for (String l : lines) {
					output.write(l);
					output.newLine();
				}
				output.close();
				System.out.println("map written to " + fileName);
				return true;
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			System.out.print("ERROR: file " + fileName + " not found");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;

	}

	public static boolean saveCharMap(String fileName, char[][] grid) {
		String[] lines = new String[grid.length];
		int j =0;
		for (char[] chars : grid) {
			String line = "" + chars[0];
			for (int i=0;i<chars.length;i++) {
				line+=("," + chars[i]);
			}
			lines[j++] = line;
		}
		/*
		for (String line:lines) {
			System.out.println("line: " + line);
		}
		*/		
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(fileName));
			try {
				for (String l : lines) {
					output.write(l);
					output.newLine();
				}
				output.close();
				System.out.println("map written to " + fileName);
				return true;
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			System.out.print("ERROR: file " + fileName + " not found");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean saveStringMap(String fileName, String[][] grid) {
		String[] lines = new String[grid.length];
		int j =0;
		for (String[] chars : grid) {
			String line = "" + chars[0];
			for (int i=0;i<chars.length;i++) {
				line+=("," + chars[i]);
			}
			lines[j++] = line;
		}
		/*
		for (String line:lines) {
			System.out.println("line: " + line);
		}
		*/		
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(fileName));
			try {
				for (String l : lines) {
					output.write(l);
					output.newLine();
				}
				output.close();
				System.out.println("map written to " + fileName);
				return true;
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			System.out.print("ERROR: file " + fileName + " not found");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}


}
