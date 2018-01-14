package main;

import filehandling.FileHandler;
import generators.Maker;

public class Main {

	private static int size = 200;
	
	public static void main(String[] args) {
		FileHandler filer = new FileHandler();
		int[][] grid = new int[size][size];
		//grid = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
		Maker maker = new Maker();
		
		grid = maker.randomGrid(grid, 0, 150);
		
		filer.saveMap("files/map.txt", grid);
		
	}

}
