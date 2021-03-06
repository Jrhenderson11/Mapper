package main;

import java.io.IOException;

import drawer.Drawer;
import filehandling.FileHandler;
import heightmaps.generators.Mountains;
import javafx.application.Application;
import javafx.stage.Stage;
import landscapes.Original;

public class Main extends Application {

	private static int size = 200;
	
	public static void main(String[] args) {
		//int[][] grid = new int[size][size];
		//grid = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
		mountain();
		
		/*Original map = new Original("desert");
		map.make();
		String[][] grid = map.getGrid();
		FileHandler.saveStringMap("files/map.txt", map.getGrid());
		Drawer drawer = new Drawer();
		Drawer.setGrid(grid);
		Drawer.fillColourTable();
		Drawer.launch();

		drawMap();*/
	}
	
	private static void mountain() {
		int[][] grid = new int[size][size];
		grid = Mountains.make3ConeMountain(grid);
		FileHandler.saveMap("files/mountain.csv", grid);
		drawHeatmap("files/mountain.csv");
	}
	
	public static void drawHeatmap(String fname) {
		try {
			Runtime.getRuntime().exec("python ../heightmap-render/heightmap.py " + fname);
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
