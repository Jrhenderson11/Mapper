package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Tools {

	public static int findNumEdges(String[][] grid, int x, int y, String tile) {
		int width = grid.length;
		int height = grid[0].length;

		int count = 0;
		if (x < (width - 1)) {
			if (grid[x + 1][y] == tile) {
				count++;
			}
		}
		if (x != 0) {
			if (grid[x - 1][y] == tile) {
				count++;
			}
		}
		if (y < (height - 1)) {
			if (grid[x][y + 1] == tile) {
				count++;
			}
		}
		if (y != 0) {
			if (grid[x][y - 1] == tile) {
				count++;
			}
		}
		return count;
	}

	public static int findNumEdges8(String[][] grid, int x, int y, String tile) {
		int width = grid.length;
		int height = grid[0].length;

		int count = 0;

		if (y < height - 1) {
			if (x < width - 1) {
				if (grid[x + 1][y + 1] == tile) {
					count++;
				}
			}
			if (x > 0) {
				if (grid[x - 1][y + 1] == tile) {
					count++;
				}
			}
		}
		if (y > 0) {
			if (x < width - 1) {
				if (grid[x + 1][y - 1] == tile) {
					count++;
				}
			}
			if (x > 0) {
				if (grid[x - 1][y - 1] == tile) {
					count++;
				}
			}
		}

		return count + findNumEdges(grid, x, y, tile);
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
		for (int y = posY; (y - posY < rectHeight) && (y < height); y++) {
			for (int x = posX; (x - posX < rectWidth) && (x < width); x++) {
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

	public static String[][] fillGrid(String[][] grid, String fillval) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x] = fillval;
			}
		}
		return grid;
	}

	public static String[][] randomGrid(String[][] grid, int base, int variation) {
		Random rand = new Random();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int val = rand.nextInt(base + variation) + base;
				// grid[y][x] = val;
			}
		}
		return grid;
	}

	public static String[][] makeRectangle(String[][] grid, int posX, int posY, int width, int height, String fillval) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[posY - y][posX + x] = fillval;
			}
		}

		return grid;
	}

	public static String[][] makeCircle(String[][] grid, int posX, int posY, int radius, String fillval) {
		int width = grid.length;
		int height = grid[0].length;

		for (int x = 0; x <= radius; x++) {
			for (int y = 0; y < Math.sqrt((radius * radius) - (x * x)) + 1; y++) {
				if (posY + y < (height - 1)) {
					if (posX + x < (width - 1)) {
						grid[posY + y][posX + x] = fillval;
					}
					if (posX - x > 0) {
						grid[posY + y][posX - x] = fillval;
					}
				}
				if (posY - y > 0) {
					if (posX + x < (width - 1)) {
						grid[posY - y][posX + x] = fillval;
					}
					if (posX - x > 0) {
						grid[posY - y][posX - x] = fillval;
					}
				}
			}
		}

		return grid;
	}

}