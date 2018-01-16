package landscapes;

import java.util.Random;

import filehandling.FileHandler;

import java.math.*;
import heightmaps.generators.RandomUtils;

public class Original {

	private int width = 300;
	private int height = 300;
	private char[][] grid = new char[width][height];
	private boolean sea;
	private String mode;

	public Original(String newMode) {
		this.mode = newMode;
	}

	public char[][] getGrid() {
		return this.grid;
	}

	public void make() {
		System.out.println("making");
		switch (mode) {
		case "test":
			sea = false;
			initialise();
			setBlock(25, 25, 'X', 1, 9);
			break;
		case "island":
			sea = true;
			initialise();
			makeIslands("Main");
			makeForest(1, 4);
			makeMountains(0, 3);
			beach();
			makeRivers(1, 3);
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
			makeForest(7, 15);
			makeMountains(8, 16);
			makeRivers(7, 12);

			// 1000
			// makefor(est(8, 21)
			// makeMountains(7, 18)
			// makeRiver(7, 15)
			break;
		case "desert":
			sea = false;
			initialise();
			makeMountains(0, 3);
			makeOasises(1, 4);
			makeRivers(0, 2);
			riverBanks();
			break;

		case "archipelago":
			sea = true;
			initialise();
			makeIslands("archipelago");
			beach();
			FileHandler.saveMap("files/map.txt", grid);
			makeForest(0, 6);
			makeRivers(0, 4);
			break;
		}
	}

	public void initialise() {
		// INITS this.grid
		if (sea) {
			// makeS SEA
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					this.grid[iX][iY] = 'w';
				}
			}
		} else {
			if (this.mode.equals("desert")) {
				// makeS SAND
				for (int iY = 0; iY < this.height; iY++) {
					for (int iX = 0; iX < width; iX++) {
						this.grid[iX][iY] = '-';
					}
				}
			} else if (this.mode.equals("normal") || this.mode.equals("test")) {
				// makeS GRASS
				for (int iY = 0; iY < this.height; iY++) {
					for (int iX = 0; iX < width; iX++) {
						this.grid[iX][iY] = '.';
					}
				}
			}
		}

	}

	public void makeIslands(String type) {
		System.out.println("making islands");
		Random rand = new Random();
		int x, y;
		int numSmall;

		x = rand.nextInt(width - 1);
		y = rand.nextInt(height - 1);

		switch (type) {
		case "Main":

			island(x, y, "Main");
			numSmall = RandomUtils.randomPosGaussian(5, 3);
			break;

		case "archipelago":

			numSmall = RandomUtils.randomPosGaussian(12, 5);

			for (int i = 0; i <= numSmall; i++) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);

				while (this.grid[x][y] != 'w') {
					x = rand.nextInt(width - 1);
					y = rand.nextInt(height - 1);
				}
				island(x, y, "I");
			}
			break;
		}

	}

	public void makeForest(int minForest, int maxForest) {
		System.out.println("making Forest");
		Random rand = new Random();
		int numForest;
		int x, y;
		System.out.println(minForest);
		System.out.println(maxForest);
		numForest = RandomUtils.randomInt(maxForest, minForest);
		System.out.println(numForest);
		for (int i = 0; i <= numForest; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while (this.grid[x][y] == 'w') {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			forest(x, y, 1);
		}

	}

	public void makeMountains(int minMountains, int maxMountains) {
		System.out.println("making mountains "  +minMountains + ":" + maxMountains);
		Random rand = new Random();
		int numMountains;
		int x, y;
		//numMountains = (rand.nextInt(minMountains, maxMountains))-minMountains;
		numMountains = RandomUtils.randomInt(maxMountains, minMountains);
		System.out.println(numMountains + " mountains");
		for (int i = 0; i <= numMountains; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((this.grid[x][y] == 'w') || (this.grid[x][y] == 'x')) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			mountain(x, y, "biggish");
		}
	}

	public void makeRivers(int minRivers, int maxRivers) {
		Random rand = new Random();
		int numRivers;
		int x, y;

		numRivers = RandomUtils.randomInt(maxRivers, minRivers);
		System.out.println(numRivers + " rivers");
		for (int i = 0; i <= numRivers; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((this.grid[x][y] == 'w') || (this.grid[x][y] == 'x')) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			river(x, y);
		}
	}

	public void makeOasises(int minOases, int maxOases) {
		System.out.println("making oasises");
		Random rand = new Random();
		int numOases;
		int x, y;

		numOases = RandomUtils.randomInt(maxOases, minOases);

		for (int i = 0; i <= numOases; i++) {
			x = rand.nextInt(width - 1);
			y = rand.nextInt(height - 1);
			while ((this.grid[x][y] == 'w') || (this.grid[x][y] == 'x')) {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			oasis(x, y);
		}
	}

	public void mountain(int posX, int posY, String size) {
		int dist;
		int min = 10;

		// make core
		for (int iY = 0; iY < this.height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				dist = (int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
				if (dist < 10) {
					this.grid[iX][iY] = 'x';
				}
			}
		}
		// add edges
		// make more efficient
		for (int i = 0; i <= 10; i++) {
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					mountainEdge(iX, iY);
				}
			}
		}
	}

	public void mountainEdge(int x, int y) {
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (findNumEdges(x, y, 'x')) {
			case 1:
				randomMake(x, y, 5, 'x');
				break;
			case 2:
				randomMake(x, y, 25, 'x');
				break;
			case 3:
				randomMake(x, y, 50, 'x');
				break;
			case 4:
				grid[x][y] = 'x';
				break;
			}
		}
	}

	public void river(int posX, int posY) {
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
			grid[x][y] = '=';

			nextDirection = pickDirection(direction, direction2, direction3, direction4);
			
			while (riverCanGo(nextDirection, x, y) == false) {
				boolean passable =(riverCanGo(1, x, y) || (riverCanGo(2, x, y)) || (riverCanGo(3, x, y)) || (riverCanGo(4, x, y))); 
				if (!passable) {
					System.out.println("stopping");
					stop=true;
					break;
				}
				System.out.println("x::");
				System.out.println(x);
				System.out.println(y);
				System.out.println(passable);
				
				nextDirection = pickDirection(direction, direction2, direction3, direction4);
			}
//			System.out.println("end");

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
					if (grid[x][y] == 'w') {
						stop = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					stop = true;
				}
			}

			// ' '' ''Branch
			// '' ''Randomize()
			// '' ''Chance = CInt(Math.Ceiling(Rnd() * 1000))

			// '' ''If Chance < 2 Then
			// '' '' River(X, Y)
			// '' '' Chance = 100
			// '' ''End If
		}
	}

	public int pickDirection(int direction, int direction2, int direction3, int direction4) {
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

	public boolean riverCanGo(int nextDirection, int x, int y) {
	

		switch (nextDirection) {
			case 1:
				if (y > 0) {
					if (grid[x][y - 1] == 'x') {
						return false;
					}
				}
				// Y = Y - 1
			case 2:
				if (x < width-1) {
					if (grid[x + 1][y] == 'x') {
						return false;
					}
				}
				// X = X + 1
			case 3:
				if (y < height-1) {
					if (grid[x][y + 1] == 'x') {
						return false;
					}
				}
				// ' Y = Y + 1
			case 4:
				if (x > 0) {
					if (grid[x - 1][y] == 'x') {
						return false;
					}
				}
		}
		return true;
	}

	public void forest(int posX, int posY, int type) {
		System.out.println("making new forest");
		int dist;
		int min = 10;

		// Generate core
		switch (type) {
		case 1:
			// small = 1 block
			grid[posX][posY] = '^';
			break;
		case 2:
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					dist = (int) Math
							.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (dist < 3) {
						grid[iX][iY] = '^';
					}
				}
			}
			break;
		case 3:
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					dist = (int) Math
							.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (dist < 7) {
						grid[iX][iY] = '^';
					}
				}
			}
			break;
		case 4:
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					dist = (int) Math
							.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (dist < 12) {
						grid[iX][iY] = '^';
					}
				}
			}

			break;
		}
		// ADD EDGES

		for (int i = 1; i < 4; i++) {
			// SCAN GRID
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					forestEdge(iX, iY);
				}
			}
		}

	}

	public void forestEdge(int x, int y) {
		if (x > 0 && x < width && y > 0 && y < height) {
			if (grid[x][y] != 'w') {
				// checks not sea
				switch (findNumEdges(x, y, '^')) {
				case 1:
					randomMake(x, y, 40, '^');
					break;
				case 2:
					randomMake(x, y, 30, '^');
					break;
				case 3:
					randomMake(x, y, 55, '^');
					break;
				case 4:
					randomMake(x, y, 75, '^');
					break;
				}
			}
		}
	}

	public int findNumEdges(int x, int y, char tile) {
		int count = 0;
		if (x < (width-1)) {
			if (grid[x + 1][y] == tile) {
				count++;
			}
		}
		if (x != 0) {
			if (grid[x - 1][y] == tile) {
				count++;
			}
		}
		if (y < (height-1)) {
			if (grid[x][y + 1] == tile) {
				count++;
			}
		}
		if (y != 0) {
			if (grid[x][y-1] == tile) {
				count++;
			}
		}
		return count;
	}

	public void randomMake(int x, int y, int chance, char tile) {
		int ran = RandomUtils.randomInt(100, 0);
		if (ran <= chance) {
			grid[x][y] = tile;
		}
	}

	public void setBlock(int posX, int posY, char tile, int width, int height) {
		for (int y = posY; (y-posY<height) && (y<this.height); y++) {
			for (int x = posX; (x-posX < width) && (x<this.width); x++) {
				grid[x][y] = tile;
			}
		}
	}

	public void convert(char from, char to) {
		for (int iY = 0; iY < this.height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				if (grid[iX][iY] == from) {
					grid[iX][iY] = to;
				}
			}
		}
	}

	public void island(int posX, int posY, String size) {
		System.out.println("island");
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
			System.out.println("island1");
			setBlock(posX, posY, 'Q', 3, 3);
		} else {
			System.out.println("island2");
			// generate core according to coresize
			islandSize = width / 5;
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					coreSize = (int) Math
							.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (coreSize < islandSize) {
						grid[iX][iY] = 'Q';
					}
				}
			}
		}
		System.out.println("island3");
		// iterate
		for (int i = 0; i < iterate; i++) {
			for (int iY = 0; iY < height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					islandEdge(iX, iY, edgeType);
				}
			}
		}
		
		this.convert('Q', '.');
	}

	public void islandEdge(int x, int y, String type) {
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (type) {
			case "Main":
				switch (findNumEdges(x, y, 'Q')) {
				case 1:
					randomMake(x, y, 30, 'Q');
					break;
				case 2:
					randomMake(x, y, 30, 'Q');
					break;
				case 3:
					randomMake(x, y, 50, 'Q');
					break;
				case 4:
					grid[x][y] = 'Q';
					break;

				}
				break;
			case "I":
				switch (findNumEdges(x, y, 'Q')) {
				case 1:
					randomMake(x, y, 5, 'Q');
					break;
				case 2:
					randomMake(x, y, 30, 'Q');
					break;
				case 3:
					randomMake(x, y, 50, 'Q');
					break;
				case 4:
					grid[x][y] = 'Q';
					break;

				}
				break;

			}
		}
	}

	public void beach() {
		System.out.println("making beaches");
		int numLand;
		int numBeach;

		for (int iY = 0; iY < this.height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				numLand = 0;
				numBeach = 0;
				numBeach = findNumEdges(iX, iY, '-');
				numLand = findNumEdges(iX, iY, '.');
				if (grid[iX][iY] == 'w' && numLand > 0) {
					grid[iX][iY] = '-';
				}
				if (grid[iX][iY] == 'w' && numBeach > 0) {
					randomMake(iX, iY, 60, '-');
				}
			}
		}
	}

	public void oasis(int x, int y) {
		int distance;
		int numTrees = RandomUtils.randomInt(11, 5);

		// Green Core
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 5) {
					grid[iX][iY] = '.';
				}
			}
		}

		// Green Edges
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				oasisEdge(iX, iY);
			}
		}

		// Pool
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 3) {
					grid[iX][iY] = '=';
				}
			}
		}

		// Trees
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
				if (distance < 10 && distance > 5) {
					if (palmTreeChance(distance)) {
						grid[iX][iY] = '.';
					}
				}
			}
		}

		// block chance method

	}

	public void oasisEdge(int x, int y) {
		if (x > 0 && x < width && y > 0 && y < height) {
			switch (findNumEdges(x, y, '.')) {
			case 1:
				randomMake(x, y, 30, '.');
				break;
			case 2:
				randomMake(x, y, 30, '.');
				break;
			case 3:
				randomMake(x, y, 50, '.');
			case 4:
				grid[x][y] = '.';
				break;
			}
		}
	}

	public boolean palmTreeChance(int dist) {
		boolean spawn = false;
		int random = RandomUtils.randomInt(100, 0);
		// % proportional to distance
		switch (dist) {
		case 1:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 2:
			if (random < 10) {
				spawn = true;
			}

			break;
		case 3:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 4:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 5:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 6:
			if (random < 10) {
				spawn = true;
			}
			break;
		case 7:
			if (random < 10) {
				spawn = true;
			}
			break;

		}
		return spawn;
	}

	public void riverBanks() {
		System.out.println("making river banks");
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {
				if (grid[iX][iY] == '-' && findNumEdges(iX, iY, '=') > 0) {
					randomMake(iX, iY, 70, '.');
				}
			}
		}
	}
}
