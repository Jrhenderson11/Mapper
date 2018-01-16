package landscapes;

import java.util.Random;

import filehandling.FileHandler;
import heightmaps.generators.RandomUtils;

public class Original {

	private int width = 300;
	private int height = 300;
	private String[][] grid = new String[width][height];
	private boolean sea;
	private String mode;

	public Original(String newMode) {
		this.mode = newMode;
	}

	public String[][] getGrid() {
		return this.grid;
	}

	public void make() {
		System.out.println("making");
		switch (mode) {
		case "test":
			sea = false;
			initialise();
			Tools.setBlock(grid, 25, 25, "X", 1, 9);
			break;
		case "island":
			sea = true;
			initialise();
			grid = Islands.makeIslands(grid, "Main");
			grid = Forest.makeForest(grid, 1, 4);
			grid = Mountains.makeMountains(grid, 0, 3);
			grid = Islands.beach(grid);
			grid = Rivers.makeRivers(grid, 1, 3);
			break;
		case "normal":
			sea = false;
			initialise();

			// 100
			// makefor(est(3, 6)
			// makeMountains(1, 5)
			// makeRiver(3, 5)

			// 200
			// makefor(est(5, 15)
			// makeMountains(5, 16)
			// makeRiver(4, 12)

			// 300
			grid = Forest.makeForest(grid, 7, 15);
			grid = Mountains.makeMountains(grid, 8, 16);
			grid = Rivers.makeRivers(grid, 7, 12);

			// 1000
			// makefor(est(8, 21)
			// makeMountains(7, 18)
			// makeRiver(7, 15)
			break;
		case "desert":
			sea = false;
			initialise();
			grid = Mountains.makeMountains(grid, 0, 3);
			grid = Lakes.makeOasises(grid, 1, 4);
			grid = Rivers.makeRivers(grid, 0, 2);
			grid = Rivers.riverBanks(grid);
			break;

		case "archipelago":
			sea = true;
			initialise();
			grid = Islands.makeIslands(grid, "archipelago");
			grid = Islands.beach(grid);
			grid = Forest.makeForest(grid, 0, 6);
			grid = Rivers.makeRivers(grid, 0, 4);
			break;
		}
	}

	public void initialise() {
		// INITS this.grid
		if (sea) {
			// makeS SEA
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					this.grid[iX][iY] = "w";
				}
			}
		} else {
			if (this.mode.equals("desert")) {
				// makeS SAND
				for (int iY = 0; iY < this.height; iY++) {
					for (int iX = 0; iX < width; iX++) {
						this.grid[iX][iY] = "-";
					}
				}
			} else if (this.mode.equals("normal") || this.mode.equals("test")) {
				// makeS GRASS
				for (int iY = 0; iY < this.height; iY++) {
					for (int iX = 0; iX < width; iX++) {
						this.grid[iX][iY] = ".";
					}
				}
			}
		}

	}
}
