package drawer;

import java.util.HashMap;

import filehandling.FileHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import landscapes.Original;

public class Drawer extends Application {

	private static Color SAND = Color.rgb(244, 209, 66);
	private static Color PINK = Color.rgb(186, 50, 174);
	private static Color GRASS = Color.rgb(125, 186, 81);
	private static Color BLUE = Color.rgb(50, 127, 186);
	private static Color GREY = Color.rgb(98, 102, 101);
	private static Color LIGHT_BLUE = Color.rgb(114, 219, 219);
	private static Color DARK_GREEN = Color.rgb(50, 86, 29);

	private static HashMap<String, Color> COLOUR_TABLE = new HashMap<String, Color>();

	private static String[][] grid;

	private static int size = 200;

	public static void setGrid(String[][] newGrid) {
		grid = newGrid;
	}

	public static void main(String[] args) {
		Original map = new Original("normal");
		map.make();
		grid = map.getGrid();
		FileHandler.saveStringMap("files/map.txt", map.getGrid());

		setGrid(grid);
		fillColourTable();
		launch(args);
	}

	public static void fillColourTable() {
		COLOUR_TABLE.put("^", DARK_GREEN);
		COLOUR_TABLE.put("=", BLUE);
		COLOUR_TABLE.put("W", LIGHT_BLUE);
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

		double cell_width = canvas.getWidth() / width;
		double cell_height = canvas.getHeight() / height;

		System.out.println(cell_width);
		System.out.println(cell_height);
		
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {

				gc.setFill(COLOUR_TABLE.get(grid[iX][iY]));
				gc.fillRect((iX * cell_width), (iY * cell_height), cell_width, cell_height);
				theStage.show();

			}
		}

		theStage.show();
	}
}
