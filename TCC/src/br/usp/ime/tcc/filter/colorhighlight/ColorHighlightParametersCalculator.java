package br.usp.ime.tcc.filter.colorhighlight;

public class ColorHighlightParametersCalculator {
	private int red, green, blue;
	private int redTolerance, greenTolerance, blueTolerance;

	public ColorHighlightParametersCalculator(int red, int green, int blue,
			int redTolerance, int greenTolerance, int blueTolerance) {
		setColors(red, green, blue);
		setTolerances(redTolerance, greenTolerance, blueTolerance);
	}

	public boolean pixelShouldBeHighlighted(int pixelR, int pixelG, int pixelB) {
		return squaredDistanceDividedBySquaredTolerance(pixelR, red,
				redTolerance)
				+ squaredDistanceDividedBySquaredTolerance(pixelG, green,
						greenTolerance)
				+ squaredDistanceDividedBySquaredTolerance(pixelB, blue,
						blueTolerance) <= 1;
	}

	private double squaredDistanceDividedBySquaredTolerance(
			int pixelColor, int color, int colorTolerance) {
		int sqrDist = squaredDistance(pixelColor, color);

		if (colorTolerance == 0)
			return (sqrDist == 0 ? 0.0 : 1.0);

		return (1.0 * sqrDist / (colorTolerance * colorTolerance));
	}

	private int squaredDistance(int oneValue, int otherValue) {
		return (oneValue - otherValue) * (oneValue - otherValue);
	}

	private void setColors(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	private void setTolerances(int redTol, int greenTol, int blueTol) {
		this.redTolerance = redTol;
		this.greenTolerance = greenTol;
		this.blueTolerance = blueTol;
	}
}
