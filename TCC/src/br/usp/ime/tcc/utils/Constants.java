package br.usp.ime.tcc.utils;


public final class Constants {
	public static final int MAX_INTENSITY = 100;
	public static final int PROGRESS = MAX_INTENSITY / 2;
	
	public static final String IMAGE = "image";
	
	public static final int TAKE_PICTURE = 1;
	public static final int SELECT_PICTURE = 2;
	
	public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
	public static final String DEFAULT_FOLDER = "/DCIM/Camera/";
	public static final int REQWIDTH = 500;
	public static final int REQHEIGHT = 500;
	public static final String IMAGE_PATH = "imagePath";
	public static final String IMAGE_ORIENTATION = "imageOrientation";
	
	private Constants() {
		throw new AssertionError();
	}
}
