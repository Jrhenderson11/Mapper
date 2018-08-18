package drawer;

import java.util.function.Function;

import javafx.scene.paint.Color;

public class Gradient {

	private static double lerp(double x, double x0, double x1, double y0, double y1) {
		/*
		 * Linear interpolation of value y given min and max y values (y0 and y1), min
		 * and max x values (x0 and x1), and x value.
		 */
		return y0 + (y1 - y0) * ((x - x0) / (x1 - x0));
	}

	private static double[] rgb_lerp(double x, double x0, double x1, double[] c0, double[] c1) {
		// Linear interpolation of RGB color tuple c0 and c1.
		return (new double[] { Math.floor(lerp(x, x0, x1, c0[0], c1[0])), Math.floor(lerp(x, x0, x1, c0[1], c1[1])),
				Math.floor(lerp(x, x0, x1, c0[2], c1[2])) });
	}

	public static Function<Double, double[]> gradient_func(double[][] colors) {
		/*Build a waterfall color function from a list of RGB color tuples.  The
		returned function will take a numeric value from 0 to 1 and return a color
		interpolated across the gradient of provided RGB colors.
		*/
		double grad_width = 1.0 / (colors.length-1.0);

		Function<Double, double[]> _fun = (value) -> {
				//System.out.println("value: " + value);
				if (value <= 0.0) {
					//System.out.println("base");
					return colors[0];
				} else if (value >= 1.0) {
					//System.out.println("max");
					return colors[colors.length-1];
				} else {
					int pos = (int) (value / grad_width);
					//System.out.println("value: " + value);
					double[] c0 = colors[pos];
					//System.out.println("c0: "  + c0[0] + ", " + c0[1] + ", " + c0[2]);
					double[] c1 = colors[pos+1];
					//System.out.println("c1: "  + c1[0] + ", " + c1[1] + ", " + c1[2]);
					double x = (value % grad_width)/grad_width;
					return rgb_lerp(x, 0.0, 1.0, c0, c1);
				}
		};
		
		return _fun;
	}
}
