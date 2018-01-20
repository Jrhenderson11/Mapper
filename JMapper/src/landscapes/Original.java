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
			break;
		case "normal":
			sea = false;
			initialise();

			// 100
			// makeforest(3, 6)
			// makeMountains(1, 5)
			// makeRiver(3, 5)

			// 200
			// makeforest(5, 15)
			// makeMountains(5, 16)
			// makeRiver(4, 12)

			// 300
			grid = Forest.makeForest(grid, 7, 15);
			grid = Mountains.makeMountains(grid, 8, 16);
			grid = Rivers.makeRivers(grid, 7, 12);
			grid = Lakes.makeLakes(grid, 0, 2);
			// 1000
			// makefor(est(8, 21)
			// makeMountains(7, 18)
			// makeRiver(7, 15)
			grid = Rivers.riverBanks(grid);
			
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
			grid = Islands.makeArchipelago(grid);
			break;
		case "black forest":
			sea = false;
			initialise();
			grid = Forest.makeForest(grid, 12, 21);
			grid = Mountains.makeMountains(grid, 7, 18);
			grid = Rivers.makeRivers(grid, 5, 15);

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
