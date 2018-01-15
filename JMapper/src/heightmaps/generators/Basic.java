package heightmaps.generators;

import java.util.Random;

public class Basic {
	
	public static int[][] addGrids(int[][] grid1, int[][] grid2) {
		int height = grid1.length;
		int width = grid1[0].length;
		int[][] newGrid = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				newGrid[y][x] = grid1[y][x]+grid2[y][x];
			}
		}
		return newGrid;
	}
	
	public static int[][] copyGrid(int[][] grid, int posX, int posY, int width, int height) {
		int[][] newGrid = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				newGrid[y][x] = grid[posY + y][posX + x];
			}
		}
		return newGrid;
	}

	public static int[][] ceiling(int[][][] grids) {

		int height = grids[0].length;
		int width = grids[0][0].length;

		int[][] highest = new int[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int maximum = 0;
				for (int layer = 0; layer < grids.length; layer++) {
					if (grids[layer][y][x] > maximum) {
						maximum = grids[layer][y][x];
					}
				}
				highest[y][x] = maximum;
			}
		}

		return highest;
	}

	public static int[][] fillGrid(int[][] grid, int fillval) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x] = fillval;
			}
		}
		return grid;
	}

	public static int[][] randomGrid(int[][] grid, int base, int variation) {
		Random rand = new Random();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int val = rand.nextInt(base + variation) + base;
				grid[y][x] = val;
			}
		}
		return grid;
	}

	public static int[][] makeRectangle(int[][] grid, int posX, int posY, int width, int height, int fillval) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[posY - y][posX + x] = fillval;
			}
		}

		return grid;
	}

	public static int[][] makeCircle(int[][] grid, int posX, int posY, int radius, int fillval) {

		for (int x = 0; x < radius + 1; x++) {
			for (int y = 0; y < Math.sqrt((radius * radius) - (x * x)) + 1; y++) {
				grid[posY + y][posX + x] = fillval;
				grid[posY + y][posX - x] = fillval;
				grid[posY - y][posX + x] = fillval;
				grid[posY - y][posX - x] = fillval;
			}
		}

		return grid;
	}

	public static int[][] makeCone(int[][] grid, int posX, int posY, int radius, int height) {
		grid[posY][posX] = height;

		for (int x = 0; x < radius + 1; x++) {
			for (int y = 0; y < Math.sqrt((radius * radius) - (x * x)) + 1; y++) {
				int dist = (int) Math.sqrt((x * x) + (y * y));
				int fillval = height - ((height / radius) * dist);
				grid[posY + y][posX + x] = fillval;
				grid[posY + y][posX - x] = fillval;
				grid[posY - y][posX + x] = fillval;
				grid[posY - y][posX - x] = fillval;
			}
		}

		return grid;
	}

	public static int getSurround4(int[][] grid, int x, int y) {
		return (grid[y][x + 1] + grid[y][x - 1] + grid[y + 1][x] + grid[y - 1][x]);
	}

	public static int getSurround8(int[][] grid, int x, int y) {
		return getSurround4(grid, x, y)
				+ (grid[y + 1][x - 1] + grid[y - 1][x + 1] + grid[y + 1][x + 1] + grid[y - 1][x - 1]);
	}

	public static int getSurroundRadius(int[][] grid, int posX, int posY, int radius) {
		int sum = 0;
		for (int x = 0; x < radius; x++) {
			for (int y = 0; y < Math.sqrt((radius * radius) - (x * x)); y++) {
				if (posX != 0 && posY != 0) {
					sum += grid[posY - y][posX - x];
				} else if (posX != 0 && posY != grid.length) {
					sum += grid[posY + y][posX - x];
				} else if (posX != grid[0].length && posY != grid.length) {
					sum += grid[posY + y][posX + x];
				} else if (posX != grid[0].length && posY != 0) {
					sum += grid[posY - y][posX + x];
				}
			}
		}
		return sum;
	}

	public static int getAvgSurroundRadius(int[][] grid, int posX, int posY, int radius) {
		int count = 0;
		int sum = 0;

		for (int x = 0; x < radius + 1; x++) {
			for (int y = 0; y < Math.sqrt((radius * radius) - (x * x)) + 1; y++) {
				if ((posY - y > -1) && (posX - x > -1)) {
					sum += grid[posY - y][posX - x];
					// count++;
				} else if ((posY + y < grid.length) && (posX - x > -1)) {
					sum += grid[posY + y][posX - x];
					// count++;
				} else if ((posY + y < grid.length) && (posX + x < grid[0].length)) {
					sum += grid[posX + x][posX + x];
					// count++;
				} else if ((posY - y > -1) && (posX + x < grid[0].length)) {
					sum += grid[posY - y][posX + x];
					// count++;
				}
				count += 4;

			}
		}
		return sum / count;

	}

	public static int[][] distribute(int[][] grid, int passes) {
		int[][] layer = grid;

		for (int i = 0; i < passes; i++) {
			for (int y = 1; y < grid.length - 1; y++) {
				for (int x = 1; x < grid[0].length - 1; x++) {
					int surround = getSurround8(grid, x, y);
					grid[y][x] = layer[y][x] + (surround / 4);
				}
			}
			layer = grid;
		}

		return grid;
	}

	public static int[][] distribute2(int[][] grid, int passes) {
		int[][] layer = grid;

		for (int i = 0; i < passes; i++) {
			for (int y = 1; y < grid.length - 1; y++) {
				for (int x = 1; x < grid[0].length - 1; x++) {
					int avgsurround = getAvgSurroundRadius(grid, x, y, 3);
					int diff = Math.abs(avgsurround - layer[y][x]);
					if (layer[y][x] > avgsurround) {
						grid[y][x] = layer[y][x] - Math.abs(RandomUtils.randomGaussian(0, diff / 20));
					} else {
						grid[y][x] = layer[y][x] + Math.abs(RandomUtils.randomGaussian(diff, diff / 2));
					}
				}
			}
			layer = grid;
		}

		return grid;
	}

	public static int[][] distribute3(int[][] grid, int passes) {
		int[][] layer = grid;

		for (int i = 0; i < passes; i++) {
			for (int y = 1; y < grid.length - 1; y++) {
				for (int x = 1; x < grid[0].length - 1; x++) {
					int avgsurround = getAvgSurroundRadius(grid, x, y, 2);
					int diff = Math.abs(avgsurround - layer[y][x]);
					if (layer[y][x] > avgsurround) {
						// grid[y][x] = layer[y][x] - Math.abs(RandomUtils.randomGaussian(0, diff/10));
					} else {
						grid[y][x] = layer[y][x] + Math.abs(RandomUtils.randomGaussian(diff, diff));
					}
				}
			}
			layer = grid;
		}

		return grid;
	}

	public static int[][] disolve(int[][] grid, int passes) {
		int[][] layer = grid;

		for (int i = 0; i < passes; i++) {
			for (int y = 1; y < grid.length - 1; y++) {
				for (int x = 1; x < grid[0].length - 1; x++) {
					// int surround = (layer[y][x + 1] + layer[y][x - 1] + layer[y + 1][x] + layer[y
					// - 1][x]) / 4;
					int surround = getSurround4(grid, x, y) / 4;
					// int max
					int diff = Math.abs(surround - layer[y][x]);
					if (layer[y][x] > surround) {
						grid[y][x] -= Math.abs(RandomUtils.randomGaussian(0, diff / 2));
					}
				}
			}
			layer = grid;
		}

		return grid;
	}

}
