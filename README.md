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



