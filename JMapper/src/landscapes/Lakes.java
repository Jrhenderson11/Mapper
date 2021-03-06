package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Lakes {
	public static String[][] makeOasises(String[][] grid, int minOases, int maxOases) {
		int width = grid.length;
		int height = grid[0].length;

		System.out.println("making oasises");
		Random rand = new Random();
		int numOases;
		int x, y;

		numOases = RandomUtils.randomInt(maxOases, minOases);

		for (int i = 0; i <= numOases; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((grid[x][y] == "w") || (grid[x][y] == "x")) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			grid = oasis(grid, x, y);
		}
		return grid;
	}

	public static String[][] makeLakes(String[][] grid, int minLakes, int maxLakes) {
		int width = grid.length;
		int height = grid[0].length;

		System.out.println("making oasises");
		Random rand = new Random();
		int numOases;
		int x, y;

		numOases = RandomUtils.randomInt(maxLakes, minLakes);

		for (int i = 0; i <= numOases; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((grid[x][y] == "w") || (grid[x][y] == "x")) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			grid = lake(grid, x, y);
		}
		return grid;
	}

	public static String[][] lake(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;

		int distance;
		int numTrees = RandomUtils.randomInt(11, 5);

		// Lake
		// grid = Tools.makeCircle(grid, x, y, RandomUtils.randomPosGaussian(15, 5),
		// "=");
		
		grid = Tools.makeEllipse(grid, x, y, RandomUtils.randomPosGaussian(15, 7), RandomUtils.randomPosGaussian(15, 7),
				"L");
		grid = expandLake2(grid, "L", 7);
		grid = Tools.convert(grid, "L", "=");
		// Expand Pool

		// Green Edges
		int numWater;
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				numWater = Tools.findNumEdges(grid, iX, iY, "=");
				if (grid[iX][iY] == "w" && numWater > 0) {
					grid[iX][iY] = ".";
				}
			}
		}
			// Trees
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 10 && distance > 5) {
					if (palmTreeChance(distance)) {
						grid[iX][iY] = "^";
					}
				}
			}
		}

		// block chance method
		return grid;
	}

	public static String[][] oasis(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;

		int distance;
		int numTrees = RandomUtils.randomInt(11, 5);

		// Pool
		grid = Tools.makeCircle(grid, y, x, RandomUtils.randomPosGaussian(5, 4), "=");

		// Expand Pool

		// Green Edges
		int numWater;
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				numWater = Tools.findNumEdges(grid, iX, iY, "=");
				if (grid[iX][iY] == "w" && numWater > 0) {
					grid[iX][iY] = ".";
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					grid = oasisEdge(grid, iX, iY);
				}
			}
		}
		// Trees
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 10 && distance > 5) {
					if (palmTreeChance(distance)) {
						grid[iX][iY] = "^";
					}
				}
			}
		}

		// block chance method
		return grid;
	}
	
	public static String[][] expandLake(String[][] grid, String waterTile, int iter) {
		int width = grid.length;
		int height = grid[0].length;
		for (int i = 0; i < iter; i++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x > 0 && x < width && y > 0 && y < height) {
						if (grid[x][y] != "w") {
							// checks not sea
							switch (Tools.findNumEdges8(grid, x, y, waterTile)/2) {
							case 1:
								Tools.randomMake(grid, x, y, 10, waterTile);
								break;
							case 2:
								Tools.randomMake(grid, x, y, 60, waterTile);
								break;
							case 3:
								Tools.randomMake(grid, x, y, 55, waterTile);
								break;
							case 4:
								Tools.randomMake(grid, x, y, 75, waterTile);
								break;
							}
						}
					}
				}
			}
		}
		return grid;
	}
	
	public static String[][] expandLake2(String[][] grid, String waterTile, int iter) {
		int width = grid.length;
		int height = grid[0].length;
		for (int i = 0; i < iter; i++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x > 0 && x < width && y > 0 && y < height) {
						if (grid[x][y] != "w") {
							// checks not sea
							int num = Tools.findNumEdgesRadius(grid, x, y, waterTile, 5);
							if (num > Tools.findNumTilesRadius(grid, x, y, 5)/10) { 	
								Tools.randomMake(grid, x, y, (num*10), "LE");
							}			
						}
					}
				}
			}
		}
		grid = Tools.convert(grid, "LE", waterTile);
		return grid;
	}

	//smears SE
	public static String[][] expandLake3(String[][] grid, String waterTile, int iter) {
		int width = grid.length;
		int height = grid[0].length;
		for (int i = 0; i < iter; i++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x > 0 && x < width && y > 0 && y < height) {
						if (grid[x][y] != "w") {
							// checks not sea
							int num = Tools.findNumEdgesRadius(grid, x, y, waterTile, 3);
							if (num > Tools.findNumTilesRadius(grid, x, y, 3)/5) { 	
								Tools.randomMake(grid, x, y, (num*10), waterTile);
							}			
						}
					}
				}
			}
		}
		grid = Tools.convert(grid, "LE", waterTile);
		return grid;
	}
	
	public static String[][] sprinkleWater(String[][] grid, String waterTile, int iter) {
		int width = grid.length;
		int height = grid[0].length;
		for (int i = 0; i < iter; i++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (x > 0 && x < width && y > 0 && y < height) {
						if (grid[x][y] != "w") {
							// checks not sea
							int num = Tools.findNumEdgesRadius(grid, x, y, waterTile, 3);
							num /= Tools.findNumTilesRadius(grid, x, y, 3);
							Tools.randomMake(grid, x, y, (num*40), waterTile);
							
						}
					}
				}
			}
		}
		return grid;
	}

	public static String[][] oasisEdge(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (Tools.findNumEdges8(grid, x, y, ".") / 2) {
			case 1:
				grid = Tools.randomMake(grid, x, y, 30, ".");
				break;
			case 2:
				grid = Tools.randomMake(grid, x, y, 60, ".");
				break;
			case 3:
				grid = Tools.randomMake(grid, x, y, 60, ".");
			case 4:
				grid[x][y] = ".";
				break;
			}
		}
		return grid;
	}

	public static boolean palmTreeChance(int dist) {
		boolean spawn = false;
		int random = RandomUtils.randomInt(100, 0);
		// % proportional to distance
		switch (dist) {
		case 1:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 2:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 3:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 4:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 5:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 6:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 7:
			if (random < 10) {
				spawn = true;
			}
			break;

		}
		return spawn;
	}
}