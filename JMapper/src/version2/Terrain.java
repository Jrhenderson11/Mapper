package version2;

public class Terrain {

	public enum Biome {
		SEA, WATER, DESERT, TEMPERATE_DESERT, SUBTROPICAL_DESERT, SHRUBLAND, GRASSLAND, SAVANNAH, TAIGA, FOREST, TEMPERATE_DECIDUOUS_FOREST, TEMPERATE_RAIN_FOREST, TROPICAL_RAIN_FOREST, TROPICAL_SEASONAL_FOREST, BEACH, SCORCHED, BARE, TUNDRA, SNOW
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
	}

	public static Biome getBiome(double e, double m, double sealevel) {

		if (e < sealevel)
			return Biome.SEA;
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

		if (m < 0.16)
			return Biome.SUBTROPICAL_DESERT;
		if (m < 0.33)
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

}
