package generators;

import java.util.Random;

public class RandomUtils {

	public static int randomGaussian(double mean, double  standardDeviation) {
		Random randomer = new Random();
		double val = randomer.nextGaussian()*standardDeviation+mean;
		return (int) val;
	}

}
