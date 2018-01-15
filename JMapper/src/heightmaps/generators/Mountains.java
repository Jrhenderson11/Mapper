package heightmaps.generators;

public class Mountains {

	public int[][] makeConeMountain(int[][] grid) {
		//select x, y
		
		int posY = grid.length/2;
		int posX = (grid[0].length)/2;
		
		int radius = (int) (RandomUtils.randomGaussian(20, 4)); 
		int fillVal = 35;
		
//		grid = Basic.makeCircle(grid, posX, posY, radius, fillVal);
		
		//peak and distribute
		int peakHeight = 300;
		int peakX = RandomUtils.randomGaussian(posX, 3);
		int peakY = RandomUtils.randomGaussian(posY, 3);
		//grid[peakY][peakX] = peakHeight;
		System.out.println(peakX);
		System.out.println(peakY);
		//grid = Basic.makeCircle(grid, peakX, peakY, 3, peakHeight);
		//grid = Basic.distribute2(grid, 100);
		grid = Basic.makeCone(grid, posX, posY, radius, peakHeight);
		return grid;
	}
	
	public int[][] make3ConeMountain(int[][] grid) {
		//select x, y
		
		int posY = grid.length/2;
		int posX = (grid[0].length)/2;
		int radius1 = 0;
		int radius2 = 0;
		int radius3 = 0;
		radius1 = (int) (RandomUtils.randomGaussian(30, 5));
		while (radius2 < radius1) {
			radius2 = (int) (RandomUtils.randomGaussian(45, 4));
		}
		while (radius3 < radius2) {
			radius3 = (int) (RandomUtils.randomGaussian(60, 4));
		}		
		
		int fillVal = 35;
		
		//peak and distribute
		int peakHeight1 = 300;
		int peakHeight2 = 250;
		int peakHeight3 = 200;
		
		int[][] grid1 = Basic.copyGrid(grid, 0, 0, grid[0].length, grid.length);
		int[][] grid2 = Basic.copyGrid(grid, 0, 0, grid[0].length, grid.length);
		int[][] grid3 = Basic.copyGrid(grid, 0, 0, grid[0].length, grid.length);
		
		int peakX = RandomUtils.randomGaussian(posX, 3);
		int peakY = RandomUtils.randomGaussian(posY, 3);
		//grid[peakY][peakX] = peakHeight;
		System.out.println(peakX);
		System.out.println(peakY);
		//grid = Basic.makeCircle(grid, peakX, peakY, 3, peakHeight);
		//grid = Basic.distribute2(grid, 100);
		grid1 = Basic.makeCone(grid1, posX, posY, radius3, peakHeight3);
		grid2 = Basic.makeCone(grid2, posX, posY, radius2, peakHeight2);
		grid3 = Basic.makeCone(grid3, posX, posY, radius1, peakHeight1);
		
		//int[][][] grids = {grid1, grid2, grid3};
		
		grid = Basic.ceiling(new int[][][]{grid1});
		//grid = Basic.addGrids(grid1, grid3);
		
		return grid;
	}
	
	
}
