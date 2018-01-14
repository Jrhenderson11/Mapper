package generators;

import java.util.Random;

public class Maker {

	public int[][] fillGrid(int[][] grid, int fillval) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				grid[x][y] = fillval;
			}
		}
		return grid;
	}

	public int[][] randomGrid(int[][] grid, int base, int variation) {
		Random rand = new Random();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				int val = rand.nextInt(base + variation) + base;
				grid[x][y] = val;
			}
		}
		return grid;
	}

	public int[][] makeRectangle(int[][] grid, int posX, int posY, int width, int height, int fillval) {
		
		for (int x=0;x<width;x++) {
			for (int y=0;y<height;y++) {
				grid[posY + y][posX + x] = fillval;			
			}
		}
		
		return grid;
	}

	public int[][] makeCircle(int[][] grid, int posX, int posY, int radius, int fillval) {

		for (int x = 0; x < radius; x++) {
			for (int y = 0; y < Math.sqrt((radius * radius) - (x * x)); y++) {
				grid[posY + y][posX + x] = fillval;
				grid[posY + y][posX - x] = fillval;
				grid[posY - y][posX + x] = fillval;
				grid[posY - y][posX - x] = fillval;
			}
		}

		return grid;
	}
}
