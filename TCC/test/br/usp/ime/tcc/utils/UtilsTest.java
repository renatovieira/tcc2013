package br.usp.ime.tcc.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ContentResolver.class)
public class UtilsTest {
	private static final String CORRECT_PATH = "correctPath";

	private Cursor mockCursorForUse() {
		Cursor c = mock(Cursor.class);
		when(c.moveToFirst()).thenReturn(true);
		when(c.getColumnIndex(any(String.class))).thenReturn(0);
		when(c.getString(0)).thenReturn(CORRECT_PATH);
		return c;
	}

	private ContentResolver mockContentResolverForUse(Uri mockUri) {
		Cursor mockCursor = mockCursorForUse();

		ContentResolver mockContentResolver = PowerMockito
				.mock(ContentResolver.class);

		when(
				mockContentResolver.query(any(Uri.class), any(String[].class),
						any(String.class), any(String[].class),
						any(String.class))).thenReturn(mockCursor);
		return mockContentResolver;
	}

	// Tests
	@Test
	public void selectedPicturePathShouldBeNullIfInvalidUri() {
		ContentResolver mockContentResolver = mock(ContentResolver.class);

		assertNull(Utils.getSelectedPicturePath(null, mockContentResolver));
	}

	@Test
	public void shouldReturnTheCorrectPath() {
		Uri mockUri = mock(Uri.class);

		ContentResolver mockContentResolver = mockContentResolverForUse(
				mockUri);

		assertEquals(CORRECT_PATH,
				Utils.getSelectedPicturePath(mockUri, mockContentResolver));
	}
}
