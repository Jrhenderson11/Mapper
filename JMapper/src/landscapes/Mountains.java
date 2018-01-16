package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Mountains {

	public static String[][] makeMountains(String[][] grid, int minMountains, int maxMountains) {
		int width = grid.length;
		int height = grid[0].length;
		
		System.out.println("making mountains "  +minMountains + ":" + maxMountains);
		Random rand = new Random();
		int numMountains;
		int x, y;
		//numMountains = (rand.nextInt(minMountains, maxMountains))-minMountains;
		numMountains = RandomUtils.randomInt(maxMountains, minMountains);
		System.out.println(numMountains + " mountains");
		for (int i = 0; i <= numMountains; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((grid[x][y] == "w") || (grid[x][y] == "x")) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			mountain(grid, x, y, "biggish");
		}
		return grid;
	}

	public static String[][] mountain(String[][] grid, int posX, int posY, String size) {
		int width = grid.length;
		int height = grid[0].length;
		
		int dist;
		int min = 10;

		// make core
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				dist = (int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
				if (dist < 10) {
					grid[iX][iY] = "x";
				}
			}
		}
		// add edges
		// make more efficient
		for (int i = 0; i <= 10; i++) {
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					mountainEdge(grid, iX, iY);
				}
			}
		}
		return grid;
	}

	public static String[][] mountainEdge(String[][] grid, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;
		
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (Tools.findNumEdges(grid,x, y, "x")) {
			case 1:
				grid = Tools.randomMake(grid, x, y, 5, "x");
				break;
			case 2:
				grid = Tools.randomMake(grid, x, y, 25, "x");
				break;
			case 3:
				grid = Tools.randomMake(grid, x, y, 50, "x");
				break;
			case 4:
				grid[x][y] = "x";
				break;
			}
		}
		return grid;
	}


}
