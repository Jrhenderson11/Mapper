package heightmaps.generators;

import java.util.Random;

public class RandomUtils {

	public static int randomGaussian(double mean, double  standardDeviation) {
		Random randomer = new Random();
		double val = randomer.nextGaussian()*standardDeviation+mean;
		return (int) val;
	}
	
	public static int randomPosGaussian(double mean, double  standardDeviation) {
		Random randomer = new Random();
		double val = randomer.nextGaussian()*standardDeviation+mean;
		return Math.max((int) val, 0);
	}

	public static int randomInt(int max, int min) {
		Random rand = new Random();
		return rand.nextInt(min+max)-min;
	}
	
}
