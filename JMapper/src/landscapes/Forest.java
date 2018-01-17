package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Forest {

	public static String[][] makeForest(String[][] grid, int minForest, int maxForest) {
		System.out.println("making Forest");
		int width = grid.length;
		int height = grid[0].length;
		Random rand = new Random();
		int numForest;
		int x, y;
		System.out.println(minForest);
		System.out.println(maxForest);
		numForest = RandomUtils.randomInt(maxForest, minForest);
		System.out.println(numForest);
		for (int i = 0; i <= numForest; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while (grid[x][y] == "w") {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			grid = Forest.forest(grid, x, y, 1);
		}
		return grid;

	}
	
	public static String[][] forest(String[][] grid, int posX, int posY, int type) {
		System.out.println("making new forest");
		int dist;
		int min = 10;

		int width = grid.length;
		int height = grid[0].length;
		
		// Generate core
		switch (type) {
		case 1:
			// small = 1 block
			grid[posX][posY] = "^";
			break;
		case 2:
			grid = Tools.makeCircle(grid, posX, posY, 6, "^");
			break;
		case 3:
			grid = Tools.makeCircle(grid, posX, posY, 7, "^");			break;
		case 4:
			grid = Tools.makeCircle(grid, posX, posY, 12, "^");
			break;
		}
		// ADD EDGES

		for (int i = 1; i < 4; i++) {
			// SCAN GRID
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					grid = forestEdge(grid, iX, iY);
				}
			}
		}
		return grid;

	}

	public static String[][] forestEdge(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;
		if (x > 0 && x < width && y > 0 && y < height) {
			if (grid[x][y] != "w") {
				// checks not sea
				switch (Tools.findNumEdges(grid, x, y, "^")) {
				case 1:
					Tools.randomMake(grid, x, y, 40, "^");
					break;
				case 2:
					Tools.randomMake(grid, x, y, 30, "^");
					break;
				case 3:
					Tools.randomMake(grid, x, y, 55, "^");
					break;
				case 4:
					Tools.randomMake(grid, x, y, 75, "^");
					break;
				}
			}
		}
		return grid;
	}
	
	

}
