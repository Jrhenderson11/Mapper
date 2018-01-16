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

	public static String[][] oasis(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;
		
		int distance;
		int numTrees = RandomUtils.randomInt(11, 5);

		// Green Core
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 5) {
					grid[iX][iY] = ".";
				}
			}
		}

		// Green Edges
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				grid = oasisEdge(grid, iX, iY);
			}
		}

		// Pool
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 3) {
					grid[iX][iY] = "=";
				}
			}
		}

		// Trees
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 10 && distance > 5) {
					if (palmTreeChance(distance)) {
						grid[iX][iY] = ".";
					}
				}
			}
		}

		// block chance method
		return grid;
	}

	public static String[][] oasisEdge(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (Tools.findNumEdges(grid,x, y, ".")) {
			case 1:
				grid = Tools.randomMake(grid, x, y, 30, ".");
				break;
			case 2:
				grid = Tools.randomMake(grid, x, y, 30, ".");
				break;
			case 3:
				grid = Tools.randomMake(grid, x, y, 50, ".");
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
