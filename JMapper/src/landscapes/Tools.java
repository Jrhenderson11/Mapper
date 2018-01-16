package landscapes;

import heightmaps.generators.RandomUtils;

public class Tools {

	public static int findNumEdges(String[][] grid, int x, int y, String tile) {
		int width = grid.length;
		int height = grid[0].length;
		
		int count = 0;
		if (x < (width-1)) {
			if (grid[x + 1][y] == tile) {
				count++;
			}
		}
		if (x != 0) {
			if (grid[x - 1][y] == tile) {
				count++;
			}
		}
		if (y < (height-1)) {
			if (grid[x][y + 1] == tile) {
				count++;
			}
		}
		if (y != 0) {
			if (grid[x][y-1] == tile) {
				count++;
			}
		}
		return count;
	}

	public static String[][] randomMake(String[][] grid, int x, int y, int chance, String tile) {
		int ran = RandomUtils.randomInt(100, 0);
		if (ran <= chance) {
			grid[x][y] = tile;
		}
		return grid;
	}

	public static String[][] setBlock(String[][] grid, int posX, int posY, String tile, int rectWidth, int rectHeight) {
		int width = grid.length;
		int height = grid[0].length;
		for (int y = posY; (y-posY<rectHeight) && (y<height); y++) {
			for (int x = posX; (x-posX < rectWidth) && (x<width); x++) {
				grid[x][y] = tile;
			}
		}
		return grid;
	}

	public static String[][] convert(String[][] grid, String from, String to) {
		int width = grid.length;
		int height = grid[0].length;
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				if (grid[iX][iY] == from) {
					grid[iX][iY] = to;
				}
			}
		}
		return grid;
	}

}
