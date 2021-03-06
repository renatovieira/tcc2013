package br.usp.ime.tcc.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.usp.ime.tcc.utils.FileSaver;

import android.graphics.Bitmap;
import android.os.Environment;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Bitmap.class, Environment.class })
public class FileSaverTest {
	private FileSaver fileSaver;
	
	private void mockEnvironmentgetExternalStorage() {
		File mockRoot = mock(File.class);
		when(mockRoot.getAbsolutePath()).thenReturn("");

		PowerMockito.mockStatic(Environment.class);
		when(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)).thenReturn(mockRoot);
	}
	
	private FileSaver mockCreateNewOutputStreamFromFileSaver()
			throws IOException, FileNotFoundException {
		FileSaver mockFileSaver = mock(FileSaver.class);
		doCallRealMethod().when(mockFileSaver).saveToFile(any(Bitmap.class));

		OutputStream mockOutputStream = mock(OutputStream.class);
		when(mockFileSaver.createNewOutPutStream(any(File.class))).thenReturn(
				mockOutputStream);
		return mockFileSaver;
	}
	
	//Tests

	@Before
	public void setUp() throws Exception {
		fileSaver = new FileSaver();
	}

	@Test
	public void saveToFileShouldReturnNullIfNullBitmap() throws IOException {
		assertNull(fileSaver.saveToFile(null));
	}

	@Test
	public void fileShouldNotBeNullIfValidBitmap() throws IOException {
		Bitmap bmp = PowerMockito.mock(Bitmap.class);
		
		mockEnvironmentgetExternalStorage();

		FileSaver mockFileSaver = mockCreateNewOutputStreamFromFileSaver();

		assertNotNull(mockFileSaver.saveToFile(bmp));
	}
}
