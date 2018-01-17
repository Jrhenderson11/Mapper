package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Islands {

	public static String[][] makeIslands(String[][] grid, String type) {
		System.out.println("making islands");

		int width = grid.length;
		int height = grid[0].length;

		Random rand = new Random();
		int x, y;
		int numSmall;

		x = rand.nextInt(width - 1);
		y = rand.nextInt(height - 1);

		switch (type) {
		case "Main":

			grid = island(grid, x, y, "Main");
			numSmall = RandomUtils.randomPosGaussian(5, 3);
			break;

		case "archipelago":

			numSmall = RandomUtils.randomPosGaussian(12, 5);

			for (int i = 0; i <= numSmall; i++) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);

				while (grid[x][y] != "w") {
					x = rand.nextInt(width - 1);
					y = rand.nextInt(height - 1);
				}
				grid = island(grid, x, y, "I");
			}
			break;
		}
		return grid;

	}

	public static String[][] island(String[][] grid, int posX, int posY, String size) {
		System.out.println("island");

		int width = grid.length;
		int height = grid[0].length;
		int coreSize;
		int iterate = 0;
		String edgeType = "Main";
		int islandSize;

		// --------------------------------------------------
		// SIZES TABLE
		// "Main" = main island = Big and regular
		// "I" = irregular; small and dotty
		// --------------------------------------------------

		switch (size) {
		case "Main":
			edgeType = "Main";
			iterate = 10;
			break;
		case "I":
			edgeType = "Main";
			iterate = 12;
			break;
		}

		// make core
		if (size == "I") {
			// make irregular core
			//grid = Tools.setBlock(grid, posX, posY, "Q", RandomUtils.randomInt(6, 1), RandomUtils.randomInt(6, 1));

			coreSize = RandomUtils.randomPosGaussian(1, 3);
			grid = irregularCore(grid, posX, posY, coreSize);
		} else {
			// generate core according to coresize
			islandSize = width / 5;
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					coreSize = (int) Math
							.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (coreSize < islandSize) {
						grid[iX][iY] = "Q";
					}
				}
			}
		}

		for (int i = 0; i < iterate; i++) {
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					grid = islandEdge(grid, iX, iY, edgeType);
				}
			}
		}

		grid = Tools.convert(grid, "Q", ".");
		return grid;
	}

	public static String[][] irregularCore(String[][] grid, int posX, int posY, int size) {
		int width = grid.length;
		int height = grid[0].length;
	
		
		//pick num shapes
		int numShapes = RandomUtils.randomPosGaussian(1, size);
		
		//pick shapes
		for (int i=0; i<numShapes; i++) {
			//pick X and Y (gauss from posX)
			int x = RandomUtils.randomGaussian(posX, width/15);
			int y = RandomUtils.randomGaussian(posY, height/15);

			int islandWidth = RandomUtils.randomPosGaussian(5, 10);
			int islandHeight = RandomUtils.randomPosGaussian(5, 10);

			//pick shape
			switch (RandomUtils.randomInt(1, 0)) {
			case 0:
				//rect
				Tools.setBlock(grid, x, y, "Q", islandWidth, islandHeight);
				break;
			case 1:
				//ellipse
				Tools.makeEllipse(grid, x, y,islandWidth, islandHeight, "Q");
				break;
			}
			
			
			
		}
		
		
		return grid;
	}
	
	
	public static String[][] islandEdge(String[][] grid, int x, int y, String type) {
		int width = grid.length;
		int height = grid[0].length;
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (type) {
			case "Main":
				switch (Tools.findNumEdges(grid, x, y, "Q")) {
				case 1:
					grid = Tools.randomMake(grid, x, y, 30, "Q");
					break;
				case 2:
					grid = Tools.randomMake(grid, x, y, 30, "Q");
					break;
				case 3:
					grid = Tools.randomMake(grid, x, y, 50, "Q");
					break;
				case 4:
					grid[x][y] = "Q";
					break;

				}
				break;
			case "I":
				switch (Tools.findNumEdges(grid, x, y, "Q")) {
				case 1:
					grid = Tools.randomMake(grid, x, y, 5, "Q");
					break;
				case 2:
					grid = Tools.randomMake(grid, x, y, 30, "Q");
					break;
				case 3:
					grid = Tools.randomMake(grid, x, y, 50, "Q");
					break;
				case 4:
					grid[x][y] = "Q";
					break;

				}
				break;

			}
		}
		return grid;
	}

	public static String[][] beach(String[][] grid, int beachiness) {
		System.out.println("making beaches");
		int width = grid.length;
		int height = grid[0].length;
		int numLand;
		int numBeach;

		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				numLand = 0;
				numLand = Tools.findNumEdges8(grid, iX, iY, ".")/2;
				if (grid[iX][iY] == "w" && numLand > 0) {
					grid[iX][iY] = "-";
				}
			}
		}
		for (int i = 0; i < beachiness; i++) {
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					numBeach = 0;
					numBeach = Tools.findNumEdges8(grid, iX, iY, "-")/2;
					if (grid[iX][iY] == "w" && numBeach > 0) {
						grid = Tools.randomMake(grid, iX, iY, 50, "PLACE");
					}
				}
			}
			grid = Tools.convert(grid, "PLACE", "-");
		}

		return grid;
	}

}
