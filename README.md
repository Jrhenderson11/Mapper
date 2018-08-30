# Mapper
Collection of stuff for making terrains / landscapes



## Parts:

- JMapper: Lots of java code for making terrains and landscapes

- heightmap-render: draws heightmaps from files representing matrix of heights, used to view results of mountain making

- map-drawer: draws maps created by JMapper in pretty colours

## 'ow it works:

the map is represented as a 2 dimensional array of strings, with various letters or combinations of letters representing terrian (grass/forest/water etc.)

The first step is to make the background layer (default land type). For deserts this is sand, islands need to be on water and everything else is on grass. Once the grid is covered in one of these it then starts putting other elements on like islands, mountains, forests or rivers.

There is a basic technique used for making most of these elements: first a core is generated at a position and then it is expanded to make it look more random / weathered

For example islands:

The core is composed of a random number of either rectangles or ellispes of random proporitons.
Then the island is expanded by making more tiles around the existing ones with chances proportional to how many adjacent tiles of the same type there are.
This makes interesting and varied shapes like the ones here:

![alt text](https://github.com/Jrhenderson11/Mapper/blob/master/JMapper/files/island.png "Island")


## 'ow the new stuff works

I read https://www.redblobgames.com/maps/terrain-from-noise/ which was really great and so I want to try implementing some myself (this is the version2 package inside JMapper). It is a much more traditional procedural generation algorithm based on layers of noise.

By layering different resolutions of random noise you can create natural looking patterns and use these to create landscapes. First it creates an elevation map that looks like natural hills / mountains (to an extent). This is rendered here in a view that shows mountains as red and shallow points as blue.
![alt text](https://github.com/Jrhenderson11/Mapper/blob/master/JMapper/files/heightmap1.png "Elevation map")


Putting everything below a fixed sea level as water and anything just above that as beach then you get realistic looking bodies of water. This is the simplest example of using the noise maps to create realistic terrains.

By creating another layer which determines moisture the combination of elevation and moisture can be used to determine biome: dry land could be desert while wet areas will be forests and jungles. I use simplex noise to generate the elevation map and perlin to create moisture, this is just because I think it looks slightly nicer. Here is a rendering of the moisture in the environment, notice how perlin noise vs simplex noise make different patterns.
The bright red blotches mark areas below sea level, this doesn't corrspond to moisture as sea level is dependent on elevation, but it makes sense to store information about water in one view.

![alt text](https://github.com/Jrhenderson11/Mapper/blob/master/JMapper/files/moisturemap1.png "Moisture levels")

The code here is a snippet showing how the combination of height and moisture can be used to determine biome:

```
	if (e < sealevel) {
			if (e > sealevel - 0.015) {
				return Biome.SHALLOW_SEA;
			} else {
				return Biome.SEA;
			}
		}
		if (e < sealevel + 0.01)
			return Biome.BEACH;

		if (e > 0.8) {
			if (m < 0.1)
				return Biome.MARSH;
			if (m < 0.2)
				return Biome.TUNDRA;
			if (m < 0.5)
				return Biome.SCORCHED;
			return Biome.SNOW;
		}

		if (e > 0.6) {
			if (m < 0.33)
				return Biome.TEMPERATE_DESERT;
			if (m < 0.66)
				return Biome.SHRUBLAND;
			return Biome.TAIGA;
		}

		if (e > 0.3) {
			if (m < 0.16)
				return Biome.TEMPERATE_DESERT;
			if (m < 0.50)
				return Biome.GRASSLAND;
			if (m < 0.83)
				return Biome.TEMPERATE_DECIDUOUS_FOREST;
			return Biome.TEMPERATE_RAIN_FOREST;
		}

		if (m < 0.3)
			return Biome.SUBTROPICAL_DESERT;
		if (m < 0.5)
			return Biome.GRASSLAND;
		if (m < 0.66)
			return Biome.TROPICAL_SEASONAL_FOREST;
		return Biome.TROPICAL_RAIN_FOREST;
	}

``` 

Here is an image showing what the terrain with biomes looks like; it doesn't actually use the same formula for calculating biomes as the one shown here as it is less realistic and doesnt make snow or certain types of forest. It is also shown with trees, plants and dinosaurs on to show how the biomes can be used to generate forests. This terrain does correspond with the elevation and moisture maps from above, so you can see where lakes have formed at low terrain and forests and desers have formed according to moisture levels.

![alt text](https://github.com/Jrhenderson11/Mapper/blob/master/JMapper/files/terrain1.png "Terrain")

Here is an example of what the terrain generation looked like when there were no trees and the moisture map was generated using simplex noise not perlin, you can see how the moisture patterns that determine forests are slightly more scattered and irregular.

![alt text](https://github.com/Jrhenderson11/Mapper/blob/master/JMapper/files/terrain2.png "Terrain")


## references

I'm using the java opensimplex implementation at https://gist.github.com/KdotJPG/b1270127455a94ac5d19

and also the fastNoise library from https://github.com/Auburns/FastNoise_Java/blob/master/FastNoise.java for other noise

this requires javax.vecmath which you can apparently install with `apt-get install libvecmath-java` but that didn't work for me so try http://www.java2s.com/Code/Jar/v/Downloadvecmath151jar.htm instead.