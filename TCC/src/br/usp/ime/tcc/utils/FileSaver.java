package br.usp.ime.tcc.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileSaver {
	public FileSaver() {
		
	}
	
	private void compressAndSaveToFile(Bitmap bitmapToBeSaved, File file)
			throws FileNotFoundException, IOException {
		OutputStream fOut = createNewOutPutStream(file);
		bitmapToBeSaved.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
		fOut.flush();
		fOut.close();
	}

	protected OutputStream createNewOutPutStream(File file)
			throws FileNotFoundException {
		OutputStream fOut = new FileOutputStream(file);
		return fOut;
	}
	
	private File getFileToBeSaved() {
		String timeStamp = Utils.getTimeStamp();

		File root = Environment.getExternalStorageDirectory();
		File file = new File(root.getAbsolutePath() + Constants.DEFAULT_FOLDER
				+ timeStamp + ".jpg");
		return file;
	}
	
	public File saveToFile(Bitmap bitmap) throws IOException {
		File file = null;
		if (bitmap != null) {
			file = getFileToBeSaved();

			compressAndSaveToFile(bitmap, file);
		}
		return file;
	}


}
