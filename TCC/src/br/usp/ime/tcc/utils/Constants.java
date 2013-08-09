package br.usp.ime.tcc.utils;

public final class Constants {
	public static final int MAX_INTENSITY = 100;
	public static final int PROGRESS = MAX_INTENSITY / 2;
	public static final int DEFAULT_TOLERANCE = 25;

	public static final int TAKE_PICTURE = 1;
	public static final int SELECT_PICTURE = 2;

	public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
	public static final String DEFAULT_FOLDER = "/DCIM/Camera/";
	public static final String FILTER_TYPE = "filterType";
	public static final String IMAGE_PATH = "imagePath";

	public static final int REQUIRED_DIMENSION = 1024;

	public static final int VISOCOR_FILTER = 0;
	public static final int COLOR_HIGHLIGHT_FILTER = 1;
	public static final int SIMULATION_FILTER = 2;

	public static final int DEUTERANOPIA_SIMULATION_FILTER = 2;
	public static final int PROTANOPIA_SIMULATION_FILTER = 3;
	public static final int TRITANOPIA_SIMULATION_FILTER = 4;

	public static final int COLOR_PICKER_REQUEST_CODE = 1;

	public static final String RED = "red";
	public static final String GREEN = "green";
	public static final String BLUE = "blue";
	public static final int MIN_COLOR_VALUE = 0;
	public static final int MAX_COLOR_VALUE = 255;
}
