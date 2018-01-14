package generators;

import java.util.Random;

public class Maker {
	
	public int[][] fillGrid(int[][] grid, int fillval) {
		for (int y=0;y<grid.length;y++) {
			for (int x=0;x<grid.length;x++) {
				grid[x][y] = fillval;
			}	
		}
		return grid;
	}
	
	public int[][] randomGrid(int[][] grid, int base, int variation) {
		Random rand = new Random();
	
		for (int y=0;y<grid.length;y++) {
			for (int x=0;x<grid.length;x++) {
				int val = rand.nextInt(base+variation) + base;
				grid[x][y] = val;
			}	
		}
		return grid;
	}
	
	
	
	public int[][] makeCircle(int[][] grid, int x, int y, int radius, int fillval) {

		
		
		
		
		
		
		
		
		
		return grid;
	}
}
