import sys
import pygame

SAND  = (244, 209, 66)
PINK  = (186, 50, 174)
GRASS = (125, 186, 81)
BLUE  = (50, 127, 186)
GREY  = (98, 102, 101)
LIGHT_BLUE = (114, 219, 219)





COLOUR_TABLE = {'^': PINK, '=':BLUE, 'w':LIGHT_BLUE, '-':SAND, '.':GRASS, 'x':GREY}


def readmap(fname):
	file = open(fname, 'r')
	lines = file.readlines()
	file.close()
	try:
		height = len(lines)
		width  = len(lines[0].split(","))
	except Exception as e:
		print "invalid file format"
		exit(-1)
	grid = []	
	for y in range(height):
		grid.append([])
		for x in range(width):
			grid[y].append((lines[y].split(",")[x]))
	return grid

def get_max(grid):
	maximum =0
	for y in range(len(grid)):
		for x in range(len(grid[0])):
			if grid[y][x] > maximum:
				maximum = grid[y][x]
	return maximum

def drawmap(grid, name):
	window_height = 1000
	window_width = 1000
	screen = pygame.display.set_mode((window_width, window_height))
	#screen.fill(WHITE)

	pygame.display.set_caption("Drawer: " + fname)
	height = len(grid)
	width = len(grid[0])
	cell_width = window_width / width
	cell_height = window_height / height
	#maximum = 800
	for y in range(height):
		for x in range(width):
			#make rect
			rectangle = ((x*cell_width), (y*cell_height), cell_width, cell_height)  
			#print rectangle
			colour = COLOUR_TABLE[grid[y][x].strip()]

			pygame.draw.rect(screen, colour, rectangle)
	pygame.display.flip()



fname = "../JMapper/files/map.txt"
#fname = "map.txt"
if len(sys.argv) == 2:
	fname = sys.argv[1]

grid = readmap(fname)
drawmap(grid, fname)
done = False
while (not done):
	for event in pygame.event.get():  # User did something
		if event.type == pygame.QUIT:  # If user clicked close
			done = True  # Flag that we are done so we exit this loop