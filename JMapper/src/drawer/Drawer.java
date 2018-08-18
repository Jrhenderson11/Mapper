package drawer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

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
import version2.Noise;
import version2.Terrain;
import version2.Terrain.Biome;

public class Drawer extends Application {

	private enum Mode {
		OLD, TERRAIN, HEIGHTMAP, MOISTURE
	}

	private Mode mode = Mode.TERRAIN;

	private static Color SAND = Color.rgb(244, 209, 66);
	private static Color PINK = Color.rgb(186, 50, 174);
	private static Color GRASS = Color.rgb(125, 186, 81);
	private static Color BLUE = Color.rgb(50, 127, 186);
	private static Color GREY = Color.rgb(98, 102, 101);
	private static Color LIGHT_BLUE = Color.rgb(114, 219, 245);
	private static Color DARK_GREEN = Color.rgb(50, 86, 29);
	private static Color SCORCHED = Color.rgb(211, 182, 40);
	private static Color SHRUB = Color.rgb(195, 218, 66);
	private static Color TAIGA = Color.rgb(174, 218, 66);
	private static Color SAVANNAH = Color.rgb(167, 187, 41);
	private static Color DECIDUOUS = Color.rgb(61, 153, 34);
	private static Color JUNGLE = Color.rgb(43, 117, 22);
	private static Color TUNDRA = Color.rgb(163, 154, 105);

	private static HashMap<String, Color> COLOUR_TABLE = new HashMap<String, Color>();
	private static HashMap<Biome, Color> BIOME_COLOUR_TABLE = new HashMap<Biome, Color>();

	private static String[][] grid;
	private static double[][] heightmap;
	private static Terrain terrain;

	private static int size = 200;
	private static Original map;
	private static String type = "archipelago";

	private static Point coords = new Point(0, 0);
	private static int zoomLevel;
	private static int ZOOMSPEED = 2;
	private static int SPEED = 4;

	public static void setGrid(String[][] newGrid) {
		grid = newGrid;
	}

	public static void setHeightmap(double[][] newHeightmap) {
		heightmap = newHeightmap;
	}

	public static void main(String[] args) {

		// old map
		map = new Original(type);
		map.make();
		grid = map.getGrid();
		zoomLevel = grid.length;
		// FileHandler.saveStringMap("files/map.txt", map.getGrid());
		setGrid(grid);

		// heightmap
		heightmap = (Noise.simplexElevation(new double[size][size]));
		heightmap = Noise.scaleGrid(heightmap);
		System.out.println("scaled map");
		setHeightmap(heightmap);

		// new terrain
		terrain = new Terrain();

		fillColourTables();
		launch(args);
	}

	public static void fillColourTables() {
		COLOUR_TABLE.put("^", DARK_GREEN);
		COLOUR_TABLE.put("=", BLUE);
		COLOUR_TABLE.put("w", LIGHT_BLUE);
		COLOUR_TABLE.put("-", SAND);
		COLOUR_TABLE.put("x", GREY);
		COLOUR_TABLE.put(".", GRASS);

		BIOME_COLOUR_TABLE.put(Biome.SEA, LIGHT_BLUE);
		BIOME_COLOUR_TABLE.put(Biome.WATER, BLUE);
		BIOME_COLOUR_TABLE.put(Biome.DESERT, SAND);
		BIOME_COLOUR_TABLE.put(Biome.TEMPERATE_DESERT, SAND);
		BIOME_COLOUR_TABLE.put(Biome.SUBTROPICAL_DESERT, SAND);
		BIOME_COLOUR_TABLE.put(Biome.SHRUBLAND, SHRUB);
		BIOME_COLOUR_TABLE.put(Biome.GRASSLAND, GRASS);
		BIOME_COLOUR_TABLE.put(Biome.SAVANNAH, SAVANNAH);
		BIOME_COLOUR_TABLE.put(Biome.TAIGA, TAIGA);
		BIOME_COLOUR_TABLE.put(Biome.FOREST, DARK_GREEN);
		BIOME_COLOUR_TABLE.put(Biome.TEMPERATE_DECIDUOUS_FOREST, DECIDUOUS);
		BIOME_COLOUR_TABLE.put(Biome.TEMPERATE_RAIN_FOREST, JUNGLE);
		BIOME_COLOUR_TABLE.put(Biome.TROPICAL_RAIN_FOREST, DARK_GREEN);
		BIOME_COLOUR_TABLE.put(Biome.TROPICAL_SEASONAL_FOREST, DECIDUOUS);
		BIOME_COLOUR_TABLE.put(Biome.BEACH, SAND);
		BIOME_COLOUR_TABLE.put(Biome.SCORCHED, SCORCHED);
		BIOME_COLOUR_TABLE.put(Biome.BARE, GREY);
		BIOME_COLOUR_TABLE.put(Biome.TUNDRA, TUNDRA);
		BIOME_COLOUR_TABLE.put(Biome.SNOW, Color.WHITE);
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
				if (e.getCode() == KeyCode.Z) {
					filer.saveStringMap("files/map.txt", grid);
				} else if (e.getCode() == KeyCode.SPACE) {

					if (mode == Mode.OLD) {
						map = new Original(type);
						map.make();
						grid = map.getGrid();
						drawMap(grid, canvas);
					} else {
						terrain = new Terrain();
						if (mode == Mode.TERRAIN) {
							drawTerrain(terrain, canvas);
						} else if (mode==Mode.HEIGHTMAP) {
							drawHeightMap(terrain.getElevation(), canvas);
						} else {
							drawHeightMap(terrain.getMoisture(), canvas);
						}
					}
					theStage.show();

				} else if (e.getCode() == KeyCode.UP) {
					if (zoomLevel > 4) {
						zoomLevel -= ZOOMSPEED;
					}
					drawMap(grid, canvas);

					theStage.show();
				} else if (e.getCode() == KeyCode.DOWN) {
					if (zoomLevel < grid.length - 1) {
						zoomLevel += ZOOMSPEED;

						if (coords.y + zoomLevel > grid.length) {
							coords.translate(0, -ZOOMSPEED);
						}
						if (coords.x + zoomLevel > grid[0].length) {
							coords.translate(-ZOOMSPEED, 0);
						}
					}
					drawMap(grid, canvas);

					theStage.show();
				} else if (e.getCode() == KeyCode.W) {

					if (coords.y > 0) {
						coords.translate(0, -SPEED);
					}
					drawMap(grid, canvas);
				} else if (e.getCode() == KeyCode.S) {
					if (coords.y + zoomLevel < grid.length) {
						coords.translate(0, SPEED);
					}

					drawMap(grid, canvas);
				} else if (e.getCode() == KeyCode.A) {
					if (coords.x > 0) {
						coords.translate(-SPEED, 0);
					}
					drawMap(grid, canvas);
				} else if (e.getCode() == KeyCode.D) {
					if (coords.x + zoomLevel < grid[0].length) {
						coords.translate(SPEED, 0);
					}
					drawMap(grid, canvas);
				}

				if (e.getCode() == KeyCode.M) {
					if (mode == Mode.MOISTURE) {
						drawMap(grid, canvas);
						mode = Mode.OLD;
					} else if (mode == Mode.OLD) {
						drawTerrain(terrain, canvas);
						mode = Mode.TERRAIN;
					} else if (mode == Mode.TERRAIN) {
						drawHeightMap(terrain.getElevation(), canvas);
						mode = Mode.HEIGHTMAP;
					} else {
						drawHeightMap(terrain.getMoisture(), canvas);
						mode = Mode.MOISTURE;
					}
				}
			}

		});

		// drawMap(grid, canvas);
		// drawHeightMap(heightmap, canvas);
		drawTerrain(terrain, canvas);
		theStage.show();
	}

	private void drawMap(String[][] map, Canvas canvas) {
		double cell_width = canvas.getWidth() / zoomLevel;
		double cell_height = canvas.getHeight() / zoomLevel;

		GraphicsContext gc = canvas.getGraphicsContext2D();

		for (int iY = coords.y; iY < coords.y + zoomLevel; iY++) {
			for (int iX = coords.x; iX < coords.x + zoomLevel; iX++) {
				try {
					gc.setFill(COLOUR_TABLE.get(grid[iX][iY]));
					gc.fillRect(((iX - coords.x) * cell_width), ((iY - coords.y) * cell_height), Math.ceil(cell_width),
							Math.ceil(cell_height));
				} catch (Exception e) {

				}
			}
		}

	}

	private void drawHeightMap(double[][] map, Canvas canvas) {
		double cell_width = canvas.getWidth() / zoomLevel;
		double cell_height = canvas.getHeight() / zoomLevel;

		// colour = grad(float(grid[y][x]) / maximum)
		Function<Double, double[]> grad = Gradient
				.gradient_func(new double[][] { { 0, 0, 255 }, { 0, 255, 255 }, { 255, 255, 0 }, { 255, 0, 0 } });

		GraphicsContext gc = canvas.getGraphicsContext2D();

		for (int iY = coords.y; iY < coords.y + zoomLevel; iY++) {
			for (int iX = coords.x; iX < coords.x + zoomLevel; iX++) {
				try {
					double[] test = grad.apply((Double) map[iX][iY]);
					// System.out.println(test[0] + ", " + test[1] + ", " + test[2]);

					gc.setFill(Color.rgb((int) test[0], (int) test[1], (int) test[2]));
					gc.fillRect(((iX - coords.x) * cell_width), ((iY - coords.y) * cell_height), Math.ceil(cell_width),
							Math.ceil(cell_height));
				} catch (Exception e) {

				}
			}
		}

	}

	private void drawTerrain(Terrain terrain, Canvas canvas) {
		double cell_width = canvas.getWidth() / zoomLevel;
		double cell_height = canvas.getHeight() / zoomLevel;

		GraphicsContext gc = canvas.getGraphicsContext2D();

		for (int iY = coords.y; iY < coords.y + zoomLevel; iY++) {
			for (int iX = coords.x; iX < coords.x + zoomLevel; iX++) {
				try {
					gc.setFill(BIOME_COLOUR_TABLE.get(terrain.getBiomeLayer()[iX][iY]));
					gc.fillRect(((iX - coords.x) * cell_width), ((iY - coords.y) * cell_height), Math.ceil(cell_width),
							Math.ceil(cell_height));
				} catch (Exception e) {

				}
			}
		}
		// drawHeightMap(terrain.getElevation(), canvas);
	}
}