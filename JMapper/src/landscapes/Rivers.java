package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Rivers {
 
	public static String[][] makeRivers(String[][] grid, int minRivers, int maxRivers) {
		Random rand = new Random();
		int width = grid.length;
		int height = grid[0].length;
		int numRivers;
		int x, y;

		numRivers = RandomUtils.randomInt(maxRivers, minRivers);
		System.out.println(numRivers + " rivers");
		for (int i = 0; i <= numRivers; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((grid[x][y] == "w") || (grid[x][y] == "x")) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			grid = river(grid, x, y);
		}
		return grid;
	}

	public static String[][] river(String[][] grid, int posX, int posY) {

		int width = grid.length;
		int height = grid[0].length;
		
		int chance;
		int minlength = 20;
		int length = 0;

		int x = posX;
		int y = posY;

		int nextDirection;

		boolean stop = false;
		// Makes a river starting at X and Y and flowing in a random direction
		// Pick Direction

		// 1
		// |
		// 4-------2
		// |
		// 3
		int direction, direction2, direction3, direction4;

		direction = (int) (RandomUtils.randomInt(4, 1));

		if (direction == 4) {
			direction2 = 1;
		} else {
			direction2 = direction + 1;
		}
		if (direction2 == 4) {
			direction3 = 1;
		} else {
			direction3 = direction2 + 1;
		}
		if (direction3 == 4) {
			direction4 = 1;
		} else {
			direction4 = direction3 + 1;
		}

		while (!stop) {
			// Adds length
			length = length + 1;

			// Writes River
			grid[x][y] = "=";

			nextDirection = pickDirection(direction, direction2, direction3, direction4);

			while (riverCanGo(grid, nextDirection, x, y) == false) {
				boolean passable = (riverCanGo(grid, 1, x, y) || (riverCanGo(grid, 2, x, y)) || (riverCanGo(grid, 3, x, y))
						|| (riverCanGo(grid, 4, x, y)));
				if (!passable) {
					System.out.println("stopping");
					stop = true;
					break;
				}
				System.out.println("x::");
				System.out.println(x);
				System.out.println(y);
				System.out.println(passable);

				nextDirection = pickDirection(direction, direction2, direction3, direction4);
			}
			// System.out.println("end");

			// Changes Direction
			switch (nextDirection) {
			case 1:
				y = y - 1;
			case 2:
				x = x + 1;
			case 3:
				y = y + 1;
			case 4:
				x = x - 1;
			}

			// Stops Crashing
			if (x == -1 || x == width + 1 || y == -1 || y == height + 1) {
				stop = true;
			}

			// REACH SEA
			if (!stop) {
				try {
					if (grid[x][y] == "w") {
						stop = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					stop = true;
				}
			}

			// " "" ""Branch
			// "" ""Randomize()
			// "" ""Chance = CInt(Math.Ceiling(Rnd() * 1000))

			// "" ""If Chance < 2 Then
			// "" "" River(X, Y)
			// "" "" Chance = 100
			// "" ""End If
		}
		return grid;
	}

	public static int pickDirection(int direction, int direction2, int direction3, int direction4) {
		int nextDirection = direction;
		int nextDirectionNum = RandomUtils.randomInt(7, 1);

		switch (nextDirectionNum) {
		case 1:
			nextDirection = direction;
			break;
		case 2:
			nextDirection = direction;
			break;
		case 3:
			nextDirection = direction2;
			break;
		case 4:
			nextDirection = direction2;
			break;
		case 5:
			nextDirection = direction3;
			break;
		case 6:
			nextDirection = direction4;
			break;
		}

		return nextDirection;
	}

	public static boolean riverCanGo(String[][] grid, int nextDirection, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;

		switch (nextDirection) {
		case 1:
			if ((y > 0) && (grid[x][y - 1] == "x")) {
				return false;
			}
		case 2:
			if ((x < width - 1) && (grid[x + 1][y] == "x")) {
				return false;
			}
		case 3:
			if ((y < height - 1) && (grid[x][y + 1] == "x")) {
				return false;
			}
		case 4:
			if ((x > 0) && (grid[x - 1][y] == "x")) {
				return false;
			}
		}
		return true;
	}

	public static String[][] riverBanks(String[][] grid) {
		System.out.println("making river banks");

		int width = grid.length;
		int height = grid[0].length;
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				if (grid[iX][iY] == "-" && Tools.findNumEdges(grid, iX, iY, "=") > 0) {
					grid = Tools.randomMake(grid, iX, iY, 70, ".");
				}
			}
		}
		return grid;
	}

}
