package main;

import java.io.IOException;

import filehandling.FileHandler;
import landscapes.Original;

public class Main {

	private static int size = 200;
	
	public static void main(String[] args) {
		//int[][] grid = new int[size][size];
		//grid = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
		Original map = new Original("normal");
		map.make();
		FileHandler.saveMap("files/map.txt", map.getGrid());
		drawMap();
	}
	
	public static void drawHeatmap() {
		try {
			Runtime.getRuntime().exec("python ../heatmap-render/heatmap.py files/map.txt");
		} catch (IOException e) {
			System.out.println("Cannot display");
		}
	}
	public static void drawMap() {
		try {
			Runtime.getRuntime().exec("python ../map-drawer/drawer.py files/map.txt");
		} catch (IOException e) {
			System.out.println("Cannot display");
		}
	}

}
