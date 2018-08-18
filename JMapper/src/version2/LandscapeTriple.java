package version2;

import version2.Terrain.Biome;

public class LandscapeTriple {

	private double[][] elevation, moisture;
	private Biome[][] biome;
	
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
	public Biome[][] getBiome() {
		return biome;
	}
	public void setBiome(Biome[][] biome) {
		this.biome = biome;
	}
	
}
