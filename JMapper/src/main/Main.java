package main;

import java.io.IOException;

import filehandling.FileHandler;
import heightmaps.generators.Basic;
import heightmaps.generators.Mountains;

public class Main {

	private static int size = 200;
	
	public static void main(String[] args) {
		FileHandler filer = new FileHandler();
		int[][] grid = new int[size][size];
		//grid = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
		Basic maker = new Basic();
		
		grid = Basic.fillGrid(grid, 0);
		//grid = maker.randomGrid(grid, 0, 150);
		//grid = maker.makeCircle(grid, 100, 100, 20, 255);
		Mountains mountainer =  new Mountains();
		grid = mountainer.make3ConeMountain(grid);
		filer.saveMap("files/map.txt", grid);
		draw();
	}
	
	public static void draw() {
		try {
			Runtime.getRuntime().exec("python ../heatmap-render/heatmap.py files/map.txt");
		} catch (IOException e) {
			System.out.println("Cannot display");
		}
	}

}
