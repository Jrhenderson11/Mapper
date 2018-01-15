package landscapes;

import java.util.Random;
import java.math.*;
import heightmaps.generators.RandomUtils;

public class Original {

	private int width = 300;
	private int height = 300;
	private char[][] grid = new char[width][height];
	private boolean sea;
	private String mode;

	public void make() {
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

        //100
        //makefor(est(3, 6)
        //makeMountains(1, 5)
        //makeRiver(3, 5)

        //200
        //makefor(est(5, 15)
        //makeMountains(5, 16)
        //makeRiver(4, 12)

        //300
        makeForest(7, 15);
        makeMountains(8, 16);
        makeRivers(7, 12);

        //1000
        //makefor(est(8, 21)
        //makeMountains(7, 18)
        //makeRiver(7, 15)
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
			}
			while (this.grid[x][y] != 'w') {
				x = rand.nextInt(width - 1);
				y = rand.nextInt(height - 1);
			}
			island(x, y, "I");

			break;
		}

	}

	public void makeForest(int minForest, int maxForest) {
		Random rand = new Random();
		int numForest;
		int x, y;

		numForest = RandomUtils.randomInt(maxForest, minForest);

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
		Random rand = new Random();
		int numMountains;
		int x, y;

		numMountains = RandomUtils.randomInt(maxMountains, minMountains);

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

		//make core
		for (int iY = 0; iY < this.height; iY++) {
			for (int iX = 0; iX < width; iX++) { 
	             dist = (int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
			      if (dist<10) {
		                this.grid[iX][iY]= 'x';
			      }
			}
		}
		//add edges
		//make more efficient
		for (int i=0;i<=10;i++) {
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) { 
		            mountainEdge(iX, iY);
				}
			}
		}
	}

	public void mountainEdge(int x, int y) {
		if (x>0 && x<width && y>0 && y<height) {
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
		
		boolean stop=false;
	    //Makes a river starting at X and Y and flowing in a random direction
		//Pick Direction

		//           1
		//           |
		//       4-------2
		//           |
		//           3
		int direction, direction2, direction3, direction4;
	
		direction = (int) (RandomUtils.randomInt(4, 1));
	
		if (direction == 4) {
			direction2=1;
		} else {
			direction2 = direction + 1;
		}
		if (direction2 == 4) {
			direction3=1;
		} else {
			direction3 = direction2 + 1;
		}
		if (direction3 == 4) {
			direction4=1;
		} else {
			direction4 = direction3 + 1;
		}
		
		while (!stop) {
	        //Adds length
	        length = length + 1;

	        //Writes River
	        grid[x][y] = '=';


	        nextDirection = pickDirection(direction, direction2, direction3, direction4);

	        while (riverCanGo(nextDirection, x, y) == false) {
		        nextDirection = pickDirection(direction, direction2, direction3, direction4);
	        }

	        //Changes Direction
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

	        //Stops Crashing
	        if (x == -1 || x == width + 1 || y == -1 || y == height + 1) {
	            stop = true;
	        }
	        	        
	        //REACH SEA
	        if (!stop) {
	            if (grid[x][y] == 'w') {
	                stop = true;
	        	}
	        }


				//	        ' '' ''Branch
				//  	      '' ''Randomize()
				//	     	 '' ''Chance = CInt(Math.Ceiling(Rnd() * 1000))
				
				// 			 '' ''If Chance < 2 Then
				// 			 '' ''    River(X, Y)
				// 			 '' ''    Chance = 100
				// 			 '' ''End If			
		}
	}
	
	public int pickDirection(int direction, int direction2, int direction3, int direction4) {
		int nextDirection = direction;
		int nextDirectionNum = RandomUtils.randomInt(6, 1);

		nextDirectionNum = RandomUtils.randomInt(6, 1);
		
		switch (nextDirectionNum) {
		case 1:
			nextDirection = direction;
			break;
		case 2:
			nextDirection = direction;
			break;
		case 3:
			nextDirection = direction;
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
		            if (grid[x][y-1] == 'x') {
		                return false;
		            }
				}
		        //Y = Y - 1
			case 2:
		        if (x < width) {
		            if (grid[x+1][y] == 'x') {
		                return false;
		        	}
		        }
		        // X = X + 1
			case 3:
		        if (y < height) {
		            if (grid[x][y+1] == 'X') {
		                return false;
		            }
		        }
		        //' Y = Y + 1
			case 4:
		        if (x > 0) {
		            if (grid[x-1][y] == 'x') {
		                return false;
					}	
				}
		}
		return true;
	}

	public void forest(int posX, int posY, int type) {
		int dist;
		int min = 10;

		/*'----------------------------
	    '      Type
	    '   1: Dot Core
	    '   2: Circle Core (3)
	    '   3: Circle Core (7)
	    '   4: Circle core (12)
	    '------------------------------*/

		//Generate core
		switch (type) {
		case 1:
			//small = 1 block
			grid[posX][posY] = '^';
			break;
		case 2:
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) { 
					dist = (int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
                    if (dist < 3) {
                        grid[iX][iY] = '^';
                    }
				}
			}
			break;
		case 3:
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) { 
					dist = (int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (dist < 7) {
                        grid[iX][iY] = '^';
                    }
				}
			}
			break;
		case 4:
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) { 
					dist =(int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
					if (dist < 12) {
                        grid[iX][iY] = '^';
                    }
				}
			}
			
			break;
		}
		//ADD EDGES
		
	    for (int i=1; i<4;i++) {
	            //SCAN GRID
	    	for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) { 
					forestEdge(iX, iY);
				}
	    	}
	    }
	
	}

	public void forestEdge(int x, int y) {
		if (x>0 && x< width && y>0 && y< height) {
			if (grid[x][y] != 'w') {
				//checks not sea
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

	int findNumEdges(int x, int y, char tile) {
		int count=0;
		if (x != width - 1) {
	        if (grid[x+1][y] == tile) {
	            count++;
	        }
		}
	    if (x != 0) {
	        if (grid[x-1][y] == tile) {
	            count++;
	        }
	    }
	    if (y != height - 1) {
	        if (grid[x][y+1] == tile) {
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
		//width++height++
		/*
		    For iY = Ceiling(Y - ((SizeY / 2) - 1)) To Ceiling(Y + ((SizeY / 2) - 1)) Step 1
		        For iX = Ceiling(X - ((SizeX / 2) - 1)) To Ceiling(X + ((SizeX / 2) - 1)) Step 1
	            	Sett(iX, iY, Tile)
	        	Next
	    	Next
		End Sub
		*/
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
		int coreSize;
		int iterate=0;
		String edgeType = "Main";
		int islandSize;

		//--------------------------------------------------
		//		SIZES TABLE
		// "Main" = main island = Big and regular
		// "I" = irregular; small and dotty
		//--------------------------------------------------
		
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
		
		//make core
		if (size == "I") {
	        setBlock(posX, posY, 'Q', 3, 3);
		} else {
			//generate core according to coresize
			islandSize = width / 5;
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {
					coreSize = (int) Math.round(Math.abs(Math.sqrt(((iX - posX) * (iX - posX)) + ((iY - posY) * (iY - posY)))));
	                if (coreSize < islandSize) {
	                    grid[iX][iY] = 'Q';
	                }
				}
		    }
		}
		//iterate
		for (int i=0;i<iterate;i++) {
			for (int iY = 0; iY < this.height; iY++) {
				for (int iX = 0; iX < width; iX++) {		 
					islandEdge(iX, iY, edgeType);
				}
			}
		}
	    convert('Q', '.');
	}
	
	public void islandEdge(int x, int y, String type) {
		if (x>0 && x<width && y>0 && y<height) {
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
			
			}
		}
	}
	
	public void beach() {
		int numLand;
		int numBeach;
		
		for (int iY = 0; iY < this.height; iY++) {
			for (int iX = 0; iX < width; iX++) {		
				numLand = 0;
				numBeach = 0;
				numBeach = findNumEdges(iX, iY, '-');
				numLand = findNumEdges(iX, iY, '.');
				if (grid[iX][iY] == 'w' && numLand>0) {
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

		//Green Core
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) {		
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
	            if (distance < 5) {
	                grid[iX][iY] =  '.';
	            }
			}
		}
		
		
		//Green Edges
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) { 
				oasisEdge(iX, iY);
			}
		}
			
		//Pool
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) { 
				distance = (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
	            if (distance < 3) {
	            	grid[iX][iY] = '=';
	            }
			}
		}
		
		//Trees
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) { 
	            distance =  (int) Math.round(Math.abs(Math.sqrt(((iX - x) * (iX - x)) + ((iY - y) * (iY - y)))));
                if (distance < 10 && distance > 5) {
                    if (palmTreeChance(distance)) {
                        grid[iX][iY] = '.';
                    }
                }
			}
		}

		//block chance method
	
	}

	public void oasisEdge(int x, int y) {
		if (x>0 && x<width && y>0 && y<height) {
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
		//% proportional to distance
		switch (dist) {
			case 1:
				if (random < 10) {
					spawn =true;
				}
				break;
			case 2:
				if (random < 10) {
					spawn =true;
				}

				break;
			case 3:
				if (random < 10) {
					spawn =true;
				}
				break;
			case 4:
				if (random < 10) {
					spawn =true;
				}
				break;
			case 5:
				if (random < 10) {
					spawn =true;
				}
				break;
			case 6:
				if (random < 10) {
					spawn =true;
				}
				break;
			case 7:
				if (random < 10) {
					spawn =true;
				}
				break;
				
		}
		return spawn;
	}
	
	public void riverBanks() {
		for (int iY = 0; iY < height; iY++) {
			for (int iX = 0; iX < width; iX++) { 
				if (grid[iX][iY] == '-' && findNumEdges(iX, iY, '=') > 0) {
		                randomMake(iX, iY, 70, '.');
				}
			}
		}
	}
}	

/*
Sub Temple(ByVal CenteX As Integer, ByVal CentreY As Integer, ByVal Type As String)
    Type = UCase(Type)
    'Makes Temple

    '###############################################################
    'TYPE:

    '       PREFIX  |   DESIGN     |   ENDFIX 
    '--------------------------------------------------
    '       Shape   |   Type       |   Size 
    '----------------------------------------------
    '
    'E.g;  "RDS" = Rectangle, Desert, Small   

    'PREFIX
    'R = Rectangle
    'S = Square
    'G = Geometric (regular)

    'MIDDLEFIX
    'D = Desert
    'A = Assymetric
    'C = Complex

    'ENDFIX
    'H = Huge
    'B = Big
    'M = Medium
    'S = Small

    '###############################################################

    Dim Shape As String = Type(0)
    Dim Design As String = Type(1)
    Dim Size As String = Type(2)

    Dim Direction As String
    Dim RandomNumber As Integer

    Dim Width As Integer
    Dim Height As Integer
    Dim temp As Integer

    'CLIP SIZE DOES NOT UTILIZE 0 ELEMenT
    Dim ClipSize As Integer = 12



    RandomNumber = randomer.Next(1, 4)
    Select Case RandomNumber
        Case 1
            Direction = "N"
        Case 2
            Direction = "E"
        Case 3
            Direction = "S"
        Case 4
            Direction = "W"
    End Select

    RandomNumber = randomer.Next(1, 2)

    'CREATE WIDTH AND HEIGHT ACCORDING TO SIZE
    Select Case Size
        Case "H"
            'Huge = 10*10

            If RandomNumber = 1 Then
                Width = 10
            Else
                Width = 9
            End If

            If Shape = "S" Then
                'Square
                Height = Width

            Else

            End If

        Case "B"
            'Big = 6*6

            If RandomNumber = 1 Then
                Width = 7
            Else
                Width = 6
            End If

            If Shape = "S" Then
                'Square
                Height = Width
            Else


            End If

        Case "M"
            'Medium = 5*5 or 4*4

            If RandomNumber = 1 Then
                Width = 5
            Else
                Width = 4
            End If

            If Shape = "S" Then
                'Square
                Height = Width
            Else
            End If

        Case "S"
            'Small = 3*3 or 2*2 

            If RandomNumber = 1 Then
                Width = 3
            Else
                Width = 2
            End If

            If Shape = "S" Then
                'Square
                Height = Width
            Else

            End If

    End Select

    Dim Clip(ClipSize, ClipSize) As String
    'Clear Clip
    For iY = 0 To Height - 1
        For iX = 0 To Width - 1
            Clip(iX, iY) = " "
        Next
    Next

End Sub

Sub Paste(ByVal X As Integer, ByVal Y As Integer, ByVal PasteArray(,) As String, ByVal width As Integer, ByVal height As Integer)

End Sub

*/