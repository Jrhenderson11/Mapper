package drawer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import filehandling.FileHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import landscapes.Original;

public class Drawer extends Application {

	private static Color SAND = Color.rgb(244, 209, 66);
	private static Color PINK = Color.rgb(186, 50, 174);
	private static Color GRASS = Color.rgb(125, 186, 81);
	private static Color BLUE = Color.rgb(50, 127, 186);
	private static Color GREY = Color.rgb(98, 102, 101);
	private static Color LIGHT_BLUE = Color.rgb(114, 219, 245);
	private static Color DARK_GREEN = Color.rgb(50, 86, 29);
	
	private static HashMap<String, Color> COLOUR_TABLE = new HashMap<String, Color>();
	private static String[][] grid;
	private static int size = 200;
	private static Original map;
	private static String type = "archipelago";
	
	private static Point coords = new Point(0,0);
	private static int zoomLevel;
	private static int ZOOMSPEED = 2;
	private static int SPEED = 4;
	
	
	public static void setGrid(String[][] newGrid) {
		grid = newGrid;
	}

	public static void main(String[] args) {
		
		map = new Original(type);

		map.make();
		grid = map.getGrid();

		zoomLevel = grid.length;
		FileHandler.saveStringMap("files/map.txt", map.getGrid());

		setGrid(grid);
		fillColourTable();
		launch(args);
	}

	public static void fillColourTable() {
		COLOUR_TABLE.put("^", DARK_GREEN);
		COLOUR_TABLE.put("=", BLUE);
		COLOUR_TABLE.put("w", LIGHT_BLUE);
		COLOUR_TABLE.put("-", SAND);
		COLOUR_TABLE.put("x", GREY);
		COLOUR_TABLE.put(".", GRASS);
	}

	public void start(Stage theStage) {
		theStage.setTitle("JDrawer");

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(900, 900);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		int width = grid.length;
		int height = grid[0].length;

		
		
		
		double cell_width = canvas.getWidth() / zoomLevel;
		double cell_height = canvas.getHeight() / zoomLevel;

		System.out.println(cell_width);
		System.out.println(cell_height);
		ArrayList<String> input = new ArrayList<String>();

		FileHandler filer = new FileHandler();

		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if (e.getCode()==KeyCode.Z) { 
					filer.saveStringMap("files/map.txt", grid);
				} else if (e.getCode()==KeyCode.SPACE) { 
					map = new Original(type);
					map.make();
					grid = map.getGrid();
					drawMap(grid, canvas);
			
					theStage.show();

				} else if (e.getCode()==KeyCode.UP) {
					if (zoomLevel > 4) {
						zoomLevel-=ZOOMSPEED;
					}
					drawMap(grid, canvas);
					
					theStage.show();
				} else if (e.getCode()==KeyCode.DOWN) {
					if (zoomLevel < grid.length-1) {
						zoomLevel+=ZOOMSPEED;
					
						if (coords.y+zoomLevel > grid.length) {
							coords.translate(0, -ZOOMSPEED);
						}
						if (coords.x+zoomLevel > grid[0].length) {
							coords.translate(-ZOOMSPEED, 0);
						}
					}
					drawMap(grid, canvas);
					
					theStage.show();
				} else if (e.getCode()==KeyCode.W) {
					
					if (coords.y > 0 ) {
						coords.translate(0, -SPEED);
					}
					drawMap(grid, canvas);
				} else if (e.getCode()==KeyCode.S) {
					if (coords.y+zoomLevel < grid.length) {
						coords.translate(0, SPEED);
					}
					
					drawMap(grid, canvas);
				} else if (e.getCode()==KeyCode.A) {
					if (coords.x > 0 ) {
						coords.translate(-SPEED, 0);
					}
					drawMap(grid, canvas);
				} else if (e.getCode()==KeyCode.D) {
					if (coords.x+zoomLevel < grid[0].length) {
						coords.translate(SPEED, 0);
					}
					drawMap(grid, canvas);
				}
				
			}
		});
		
		

		
		drawMap(grid, canvas);

		theStage.show();
	}
	
	
	private void drawMap(String[][] map, Canvas canvas) {
		double cell_width = canvas.getWidth() / zoomLevel;
		double cell_height = canvas.getHeight() / zoomLevel;

		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		for (int iY = coords.y; iY < coords.y+zoomLevel; iY++) {
			for (int iX = coords.x; iX < coords.x+zoomLevel; iX++) {
				try {
					gc.setFill(COLOUR_TABLE.get(grid[iX][iY]));
					gc.fillRect(((iX-coords.x) * cell_width), ((iY-coords.y) * cell_height), Math.ceil(cell_width), Math.ceil(cell_height));
				} catch (Exception e) {
					
				}
			}
		}

	}
}