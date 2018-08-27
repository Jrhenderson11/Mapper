package version2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import version2.FastNoise.NoiseType;

public class Terrain {

	public enum Biome {
		SEA, SHALLOW_SEA, WATER, DESERT, TEMPERATE_DESERT, SUBTROPICAL_DESERT, SHRUBLAND, GRASSLAND, SAVANNAH, TAIGA, FOREST, TEMPERATE_DECIDUOUS_FOREST, TEMPERATE_RAIN_FOREST, TROPICAL_RAIN_FOREST, TROPICAL_SEASONAL_FOREST, BEACH, SCORCHED, BARE, TUNDRA, SNOW
	}

	private int size = 300;

	private double[][] elevation, moisture;
	private Biome[][] biome;
	private double sealevel;

	public Terrain() {
		this.sealevel = 0.01;
		LandscapeTriple triple = Noise.simplexTerrainTriple((double) size, (double) size, this.sealevel);
		this.elevation = triple.getElevation();
		this.moisture = triple.getMoisture();
		this.biome = triple.getBiome();
	}

	public Terrain(int newSize) {
		this.size = newSize;
		this.sealevel = 0.01;
		LandscapeTriple triple = Noise.simplexTerrainTriple((double) size, (double) size, this.sealevel);
		this.elevation = triple.getElevation();
		this.moisture = triple.getMoisture();
		this.biome = triple.getBiome();

	}

	public static Biome getBiome(double e, double m, double sealevel) {

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
				return Biome.SCORCHED;
			if (m < 0.2)
				return Biome.BARE;
			if (m < 0.5)
				return Biome.TUNDRA;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double[][] getElevation() {
		return elevation;
	}

	public void setElevation(double[][] elevation) {
		this.elevation = elevation;
	}

	public double[][] getMoisture() {
		return moisture;
	}

	public void setMoisture(double[][] moisture) {
		this.moisture = moisture;
	}

	public Biome[][] getBiomeLayer() {
		return biome;
	}

	public void setBiome(Biome[][] biome) {
		this.biome = biome;
	}

	public double getSealevel() {
		return sealevel;
	}

	public void setSealevel(double sealevel) {
		this.sealevel = sealevel;
	}

	public List<Point.Double> getTreePositions() {
		System.out.println("[*] generating trees");
		HashMap<Biome, Integer> TREE_PROBABILITY_TABLE = new HashMap<Biome, Integer>();
		List<Point.Double> trees = new ArrayList<>();
		TREE_PROBABILITY_TABLE.put(Biome.SEA, 100);
		TREE_PROBABILITY_TABLE.put(Biome.SHALLOW_SEA, 100);
		TREE_PROBABILITY_TABLE.put(Biome.WATER, 100);
		TREE_PROBABILITY_TABLE.put(Biome.DESERT, 9);
		TREE_PROBABILITY_TABLE.put(Biome.TEMPERATE_DESERT, 9);
		TREE_PROBABILITY_TABLE.put(Biome.SUBTROPICAL_DESERT, 8);
		TREE_PROBABILITY_TABLE.put(Biome.SHRUBLAND, 6);
		TREE_PROBABILITY_TABLE.put(Biome.GRASSLAND, 7);
		TREE_PROBABILITY_TABLE.put(Biome.SAVANNAH, 6);
		TREE_PROBABILITY_TABLE.put(Biome.TAIGA, 4);
		TREE_PROBABILITY_TABLE.put(Biome.FOREST, 3);
		TREE_PROBABILITY_TABLE.put(Biome.TEMPERATE_DECIDUOUS_FOREST, 3);
		TREE_PROBABILITY_TABLE.put(Biome.TEMPERATE_RAIN_FOREST, 1);
		TREE_PROBABILITY_TABLE.put(Biome.TROPICAL_RAIN_FOREST, 1);
		TREE_PROBABILITY_TABLE.put(Biome.TROPICAL_SEASONAL_FOREST, 1);
		TREE_PROBABILITY_TABLE.put(Biome.BEACH, 100);
		TREE_PROBABILITY_TABLE.put(Biome.SCORCHED, 9);
		TREE_PROBABILITY_TABLE.put(Biome.BARE, 100);
		TREE_PROBABILITY_TABLE.put(Biome.TUNDRA, 15);
		TREE_PROBABILITY_TABLE.put(Biome.SNOW, 100);
		// int R = 3;
		// from https://www.redblobgames.com/maps/terrain-from-noise/#trees

		FastNoise generator = new FastNoise(new Random().nextInt());
		generator.SetNoiseType(NoiseType.Value);
		for (int yc = 0; yc < size; yc++) {
			for (int xc = 0; xc < size; xc++) {
				double max = 0;
				// there are more efficient algorithms than this
				int R = TREE_PROBABILITY_TABLE.get(biome[xc][yc]);
				for (int yn = yc - R; yn <= yc + R; yn++) {
					for (int xn = xc - R; xn <= xc + R; xn++) {
						double e = generator.GetWhiteNoiseInt(xn, yn);
						if (e > max) {
							max = e;
						}
					}
				}
				if (generator.GetWhiteNoiseInt(xc, yc) == max) {
					// place tree at xc,yc
					trees.add(new Point.Double(xc, yc));
				}
			}
		}
		return trees;
	}

	public List<Point.Double> getGrassPositions() {
		System.out.println("[*] generating grass");
		HashMap<Biome, Integer> GRASS_PROBABILITY_TABLE = new HashMap<Biome, Integer>();
		List<Point.Double> grass = new ArrayList<>();
		GRASS_PROBABILITY_TABLE.put(Biome.SEA, 100);
		GRASS_PROBABILITY_TABLE.put(Biome.SHALLOW_SEA, 100);
		GRASS_PROBABILITY_TABLE.put(Biome.WATER, 100);
		GRASS_PROBABILITY_TABLE.put(Biome.DESERT, 9);
		GRASS_PROBABILITY_TABLE.put(Biome.TEMPERATE_DESERT, 9);
		GRASS_PROBABILITY_TABLE.put(Biome.SUBTROPICAL_DESERT, 8);
		GRASS_PROBABILITY_TABLE.put(Biome.SHRUBLAND, 4);
		GRASS_PROBABILITY_TABLE.put(Biome.GRASSLAND, 2);
		GRASS_PROBABILITY_TABLE.put(Biome.SAVANNAH, 3);
		GRASS_PROBABILITY_TABLE.put(Biome.TAIGA, 3);
		GRASS_PROBABILITY_TABLE.put(Biome.FOREST, 6);
		GRASS_PROBABILITY_TABLE.put(Biome.TEMPERATE_DECIDUOUS_FOREST, 7);
		GRASS_PROBABILITY_TABLE.put(Biome.TEMPERATE_RAIN_FOREST, 7);
		GRASS_PROBABILITY_TABLE.put(Biome.TROPICAL_RAIN_FOREST, 7);
		GRASS_PROBABILITY_TABLE.put(Biome.TROPICAL_SEASONAL_FOREST, 8);
		GRASS_PROBABILITY_TABLE.put(Biome.BEACH, 100);
		GRASS_PROBABILITY_TABLE.put(Biome.SCORCHED, 9);
		GRASS_PROBABILITY_TABLE.put(Biome.BARE, 100);
		GRASS_PROBABILITY_TABLE.put(Biome.TUNDRA, 15);
		GRASS_PROBABILITY_TABLE.put(Biome.SNOW, 100);
		// int R = 3;
		// from https://www.redblobgames.com/maps/terrain-from-noise/#trees

		FastNoise generator = new FastNoise(new Random().nextInt());
		generator.SetNoiseType(NoiseType.Value);
		for (int yc = 0; yc < size; yc++) {
			for (int xc = 0; xc < size; xc++) {
				double max = 0;
				// there are more efficient algorithms than this
				int R = GRASS_PROBABILITY_TABLE.get(biome[xc][yc]);
				for (int yn = yc - R; yn <= yc + R; yn++) {
					for (int xn = xc - R; xn <= xc + R; xn++) {
						double e = generator.GetWhiteNoiseInt(xn, yn);
						if (e > max) {
							max = e;
						}
					}
				}
				if (generator.GetWhiteNoiseInt(xc, yc) == max) {
					grass.add(new Point.Double(xc, yc));
				}
			}
		}
		return grass;
	}

	public List<Point.Double> getBushPositions() {
		System.out.println("[*] generating bushes");
		HashMap<Biome, Integer> BUSH_PROBABILITY_TABLE = new HashMap<Biome, Integer>();
		List<Point.Double> bushes = new ArrayList<>();
		BUSH_PROBABILITY_TABLE.put(Biome.SEA, 100);
		BUSH_PROBABILITY_TABLE.put(Biome.SHALLOW_SEA, 100);
		BUSH_PROBABILITY_TABLE.put(Biome.WATER, 100);
		BUSH_PROBABILITY_TABLE.put(Biome.DESERT, 9);
		BUSH_PROBABILITY_TABLE.put(Biome.TEMPERATE_DESERT, 6);
		BUSH_PROBABILITY_TABLE.put(Biome.SUBTROPICAL_DESERT, 6);
		BUSH_PROBABILITY_TABLE.put(Biome.SHRUBLAND, 3);
		BUSH_PROBABILITY_TABLE.put(Biome.GRASSLAND, 4);
		BUSH_PROBABILITY_TABLE.put(Biome.SAVANNAH, 3);
		BUSH_PROBABILITY_TABLE.put(Biome.TAIGA, 3);
		BUSH_PROBABILITY_TABLE.put(Biome.FOREST, 6);
		BUSH_PROBABILITY_TABLE.put(Biome.TEMPERATE_DECIDUOUS_FOREST, 10);
		BUSH_PROBABILITY_TABLE.put(Biome.TEMPERATE_RAIN_FOREST, 10);
		BUSH_PROBABILITY_TABLE.put(Biome.TROPICAL_RAIN_FOREST, 10);
		BUSH_PROBABILITY_TABLE.put(Biome.TROPICAL_SEASONAL_FOREST, 10);
		BUSH_PROBABILITY_TABLE.put(Biome.BEACH, 100);
		BUSH_PROBABILITY_TABLE.put(Biome.SCORCHED, 9);
		BUSH_PROBABILITY_TABLE.put(Biome.BARE, 100);
		BUSH_PROBABILITY_TABLE.put(Biome.TUNDRA, 6);
		BUSH_PROBABILITY_TABLE.put(Biome.SNOW, 100);
		// int R = 3;
		// from https://www.redblobgames.com/maps/terrain-from-noise/#trees

		FastNoise generator = new FastNoise(new Random().nextInt());
		generator.SetNoiseType(NoiseType.Value);
		for (int yc = 0; yc < size; yc++) {
			for (int xc = 0; xc < size; xc++) {
				double max = 0;
				// there are more efficient algorithms than this
				int R = BUSH_PROBABILITY_TABLE.get(biome[xc][yc]);
				for (int yn = yc - R; yn <= yc + R; yn++) {
					for (int xn = xc - R; xn <= xc + R; xn++) {
						double e = generator.GetWhiteNoiseInt(xn, yn);
						if (e > max) {
							max = e;
						}
					}
				}
				if (generator.GetWhiteNoiseInt(xc, yc) == max) {
					bushes.add(new Point.Double(xc, yc));
				}
			}
		}
		return bushes;
	}

}