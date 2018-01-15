import sys
import math
import pygame

#colours
BLACK    = (   0,   0,   0)
WHITE    = ( 255, 255, 255)
GREEN    = (   0, 255,   0)
RED      = ( 255,   0,   0)
BLUE     = (   0,   0, 255)

def lerp(x, x0, x1, y0, y1):
	"""Linear interpolation of value y given min and max y values (y0 and y1),
	min and max x values (x0 and x1), and x value.
	"""
	return y0 + (y1 - y0)*((x - x0)/(x1 - x0))

def rgb_lerp(x, x0, x1, c0, c1):
	"""Linear interpolation of RGB color tuple c0 and c1."""
	return (math.floor(lerp(x, x0, x1, float(c0[0]), float(c1[0]))),
			math.floor(lerp(x, x0, x1, float(c0[1]), float(c1[1]))),
			math.floor(lerp(x, x0, x1, float(c0[2]), float(c1[2]))))

def gradient_func(colors):
	"""Build a waterfall color function from a list of RGB color tuples.  The
	returned function will take a numeric value from 0 to 1 and return a color
	interpolated across the gradient of provided RGB colors.
	"""
	grad_width = 1.0 / (len(colors)-1.0)
	def _fun(value):
		if value <= 0.0:
			return colors[0]
		elif value >= 1.0:
			return colors[-1]
		else:
			pos = int(value / grad_width)
			c0 = colors[pos]
			c1 = colors[pos+1]
			x = (value % grad_width)/grad_width
			return rgb_lerp(x, 0.0, 1.0, c0, c1)
	return _fun

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
			grid[y].append(int(lines[y].split(",")[x]))
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

	pygame.display.set_caption("Heatmap: " + fname)
	height = len(grid)
	width = len(grid[0])
	cell_width = window_width / width
	cell_height = window_height / height
	grad = gradient_func([(0, 0, 255), (0, 255, 255), (255, 255, 0), (255, 0, 0)])

	 
	maximum = get_max(grid)
	#maximum = 800
	for y in range(height):
		for x in range(width):
			#make rect
			rectangle = ((x*cell_width), (y*cell_height), cell_width, cell_height)  
			#print rectangle
			colour = grad(float(grid[y][x]) / maximum)

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
	

