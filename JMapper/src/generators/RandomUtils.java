package generators;

import java.util.Random;

public class RandomUtils {

	public static double randomGaussian(double mean, double  standardDeviation) {
		Random randomer = new Random();
		double val = randomer.nextGaussian()*standardDeviation+mean;
		return val;
	}

}
