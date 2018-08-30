package version2;

import java.util.Random;

import version2.Terrain.Biome;

public class Noise {

	// BOO!

	public static double[][] scaleIntGrid(int[][] oldgrid) {
		// scales an integer grid to values between 0 and 1
		int width = oldgrid[0].length, height = oldgrid.length;
		double[][] grid = new double[width][height];
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (oldgrid[x][y] < min) {
					min = oldgrid[x][y];
				} else if (oldgrid[x][y] > max) {
					max = oldgrid[x][y];
				}
			}
		}
		System.out.println("max: " + max);
		System.out.println("min: " + min);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = (((double) oldgrid[x][y]) - min) / (max - min);
				if (grid[x][y] != 0) {
					System.out.println(grid[x][y]);
				}
			}
		}

		return grid;
	}

	public static double[][] scaleGrid(double[][] oldgrid) {
		int width = oldgrid[0].length, height = oldgrid.length;
		double[][] grid = new double[width][height];
		double max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (oldgrid[x][y] < min) {
					min = oldgrid[x][y];
				} else if (oldgrid[x][y] > max) {
					max = oldgrid[x][y];
				}
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = (((double) oldgrid[x][y]) - min) / (max - min);

			}
		}
		return grid;
	}

	public static double[][] scaleAndInvertIntGrid(int[][] oldgrid) {
		int width = oldgrid[0].length, height = oldgrid.length;
		double[][] grid = new double[width][height];
		double max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (oldgrid[x][y] < min) {
					min = oldgrid[x][y];
				} else if (oldgrid[x][y] > max) {
					max = oldgrid[x][y];
				}
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = 1 - (((double) oldgrid[x][y]) - min) / (max - min);
			}
		}
		return grid;
	}

	public static double[][] getSimplexGrid(int size) {

		Random random = new Random();

		OpenSimplexNoise generator = new OpenSimplexNoise(random.nextLong());
		double[][] grid = new double[size][size];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				grid[x][y] = generator.eval(x, y);
				System.out.println(generator.eval(x, y));
			}
		}

		return grid;
	}

	private static double noise1(OpenSimplexNoise gen1, double nx, double ny) {
		return gen1.eval(nx, ny) / 2 + 0.5;
	}

	private static double noise2(FastNoise gen2, double nx, double ny) {
		return gen2.GetPerlin((float) nx, (float) ny) / 2 + 0.5;
	}

	public static double[][] filter(int resolution, double[][] grid) {

		OpenSimplexNoise generator = new OpenSimplexNoise(new Random().nextLong());

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {

				grid[x][y] *= generator.eval(x / resolution, y / resolution);

			}
		}

		return grid;
	}

	public static double[][] simplexElevation(double[][] oldgrid) {
		double width = oldgrid[0].length, height = oldgrid.length;

		double[][] grid = new double[(int) width][(int) height];
		double exponent = 5.31;

		double e1 = 0.81, e2 = 0.88, e3 = 0.4, e4 = 0.4, e5 = 0.06, e6 = 0.06;
		Random random = new Random();

		OpenSimplexNoise gen1 = new OpenSimplexNoise(random.nextLong());

		double elevation;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				double nx = x / width - 0.5;
				double ny = y / height - 0.5;

				elevation = (e1 * noise1(gen1, 1 * nx, 1 * ny) + e2 * noise1(gen1, 2 * nx, 2 * ny)
						+ e3 * noise1(gen1, 4 * nx, 4 * ny) + e4 * noise1(gen1, 8 * nx, 8 * ny)
						+ e5 * noise1(gen1, 16 * nx, 16 * ny) + e6 * noise1(gen1, 32 * nx, 32 * ny));
				elevation /= (e1 + e2 + e3 + e4 + e5 + e6);
				// System.out.println("elev before exp: " + elevation);
				elevation = Math.pow(elevation, exponent);
				grid[x][y] = elevation;
				// System.out.println(grid[x][y]);

			}
		}
		return grid;
	}

	public static LandscapeTriple simplexTerrainTriple(double width, double height, double sealevel) {

		LandscapeTriple triple = new LandscapeTriple();

		double[][] e = new double[(int) width][(int) height];
		double[][] m = new double[(int) width][(int) height];
		Biome[][] b = new Biome[(int) width][(int) height];
		double exponent = 5.31;

		double e1 = 0.81, e2 = 0.88, e3 = 0.94, e4 = 0.4, e5 = 0.06, e6 = 0.06;
		double m1 = 1, m2 = 0.9, m3 = 0.38, m4 = 0.3, m5 = 0.33, m6 = 0.3;
		Random random = new Random();

		OpenSimplexNoise gen1 = new OpenSimplexNoise(random.nextLong());
		OpenSimplexNoise gen2 = new OpenSimplexNoise(new Random().nextLong());
		FastNoise gen3 = new FastNoise(random.nextInt());
		// 2.5
		gen3.SetFrequency(7.5f);

		double elevation, moisture;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double nx = x / (width / 2) - 0.5;
				double ny = y / (height / 2) - 0.5;

				elevation = (e1 * noise1(gen1, 1 * nx, 1 * ny) + e2 * noise1(gen1, 2 * nx, 2 * ny)
						+ e3 * noise1(gen1, 4 * nx, 4 * ny) + e4 * noise1(gen1, 8 * nx, 8 * ny)
						+ e5 * noise1(gen1, 16 * nx, 16 * ny) + e6 * noise1(gen1, 32 * nx, 32 * ny));
				elevation /= (e1 + e2 + e3 + e4 + e5 + e6);
				elevation = Math.pow(elevation, exponent);

				nx = x / (width * 5) - 0.5;
				ny = y / (height * 5) - 0.5;

				moisture = (m1 * noise2(gen3, 1 * nx, 1 * ny) + m2 * noise2(gen3, 2 * nx, 2 * ny)
						+ m3 * noise2(gen3, 4 * nx, 4 * ny) + m4 * noise2(gen3, 8 * nx, 8 * ny)
						+ m5 * noise2(gen3, 16 * nx, 16 * ny) + m6 * noise2(gen3, 32 * nx, 32 * ny));

				moisture /= (m1 + m2 + m3 + m4 + m5 + m6);
				e[x][y] = elevation;
				m[x][y] = moisture;
			}
		}
		e = scaleGrid(e);
		m = scaleGrid(m);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				b[x][y] = Terrain.getBiome(e[x][y], m[x][y], sealevel);
				if (b[x][y] == Biome.SEA || b[x][y] == Biome.SHALLOW_SEA || b[x][y] == Biome.WATER) {
					m[x][y] = 1;
				}
			}
		}

		triple.setElevation(e);
		triple.setMoisture(m);
		triple.setBiome(b);

		return triple;
	}

	public static double[][] simplexIsland(double[][] oldgrid) {
		double width = oldgrid[0].length, height = oldgrid.length;

		double[][] grid = new double[(int) width][(int) height];
		double elevation, moisture;

		double a = 0.04, b = 1.21, c = 2.40, d;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double nx = x / width - 0.5;
				double ny = y / height - 0.5;

				d = 2 * Math.max(Math.abs(nx), Math.abs(ny));
				elevation = oldgrid[x][y] + a - b * Math.pow(d, c);
			}
		}

		return grid;
	}

}
