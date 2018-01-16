package landscapes;

import java.util.Random;

import heightmaps.generators.RandomUtils;

public class Rivers {
 
	private static enum Direction {UP, DOWN, LEFT, RIGHT}
	
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
			grid = river(grid, x, y, 2, 1);
		}
		return grid;
	}

	public static String[][] river(String[][] grid, int posX, int posY, int strength, int bendyness) {

		int width = grid.length;
		int height = grid[0].length;
		
		int chance;
		int minlength = 20;
		int length = 0;

		int x = posX;
		int y = posY;

		Direction nextDirection = Direction.UP;
		Direction preferredDirection;
		switch (RandomUtils.randomInt(4, 1)) {
		case 1:
			preferredDirection = Direction.UP;
			break;
		case 2:
			preferredDirection = Direction.DOWN;
			break;
		case 3:
			preferredDirection = Direction.LEFT;
			break;
		default:
			preferredDirection = Direction.RIGHT;
			break;
		}
		
		boolean stop = false;
		while (!stop) {
			// Adds length
			length = length + 1;

			// Writes River
			grid[x][y] = "=";

			nextDirection = pickDirection(strength, preferredDirection);
			
			while (riverCanGo(grid, nextDirection, x, y) == false) {
				boolean passable = (riverCanGo(grid, Direction.UP, x, y) || (riverCanGo(grid, Direction.DOWN, x, y)) || (riverCanGo(grid, Direction.LEFT, x, y))
						|| (riverCanGo(grid, Direction.RIGHT, x, y)));
				if (!passable) {
					System.out.println("stopping");
					stop = true;
					break;
				}
				//System.out.println("x::");
				//System.out.println(x);
				//System.out.println(y);
				//System.out.println(passable);

				nextDirection = pickDirection(strength, preferredDirection);			}
			// System.out.println("end");

			// Changes Direction
			switch (nextDirection) {
			case UP:
				y = y + 1;
				break;
			case DOWN:
				y = y - 1;
				break;
			case LEFT:
				x = x - 1;
				break;
			case RIGHT:
				x = x + 1;
				break;
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
			
			//bend
			int percent = RandomUtils.randomInt(100, 1);
			if (percent<=bendyness) {
				switch (RandomUtils.randomInt(4, 1)) {
				case 1:
					preferredDirection = Direction.UP;
					break;
				case 2:
					preferredDirection = Direction.DOWN;
					break;
				case 3:
					preferredDirection = Direction.LEFT;
					break;
				default:
					preferredDirection = Direction.RIGHT;
					break;
				}
			}
		
		}
		return grid;
	}

	public static Direction pickDirection(int strength, Direction preferred) {
		Direction nextDirection;
		int numChoices = strength+3;
		int choice = RandomUtils.randomInt(numChoices, 1);
		Direction[] dirs =new Direction[3]; 
		int i=0;
		for (Direction dir:Direction.values()) {
			if (dir != preferred) {
				dirs[i++] = dir;
			}
		}
		
		switch (choice) {
		case 1:
			return dirs[0];
		case 2:
			return dirs[1];
		case 3:
			return dirs[2];
		default:
			return preferred;
		}
		

	}

	public static boolean riverCanGo(String[][] grid, Direction nextDirection, int x, int y) {
		int width = grid.length;
		int height = grid[0].length;

		switch (nextDirection) {
		case UP:
			if ((y < height - 1) && (grid[x][y + 1] == "x")) {
				return false;
			}
		case DOWN:
			if ((y > 0) && (grid[x][y - 1] == "x")) {
				return false;
			}
		case LEFT:
			if ((x > 0) && (grid[x - 1][y] == "x")) {
				return false;
			}
		case RIGHT:
			if ((x < width - 1) && (grid[x + 1][y] == "x")) {
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
