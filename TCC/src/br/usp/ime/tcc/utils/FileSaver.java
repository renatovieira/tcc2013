package br.usp.ime.tcc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;

public class FileSaver {
	public FileSaver() {
		
	}
	
	private void compressAndSaveToFile(Bitmap bitmapToBeSaved, File file)
			throws FileNotFoundException, IOException {
		OutputStream fOut = createNewOutPutStream(file);
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmapToBeSaved.compress(Bitmap.CompressFormat.JPEG, 85, bytes);
		
		fOut.write(bytes.toByteArray());		
		fOut.close();
	}

	protected OutputStream createNewOutPutStream(File file)
			throws IOException {
		file.createNewFile();
		
		OutputStream fOut = new FileOutputStream(file);
		return fOut;
	}
	
	public File saveToFile(Bitmap bitmap) throws IOException {
		File file = null;
		if (bitmap != null) {
			file = Utils.getFileToBeSaved();

			compressAndSaveToFile(bitmap, file);
		}
		return file;
	}


}
