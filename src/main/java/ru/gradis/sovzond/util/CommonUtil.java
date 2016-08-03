package ru.gradis.sovzond.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by donchenko-y on 7/8/16.
 */
public class CommonUtil {

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static String concatStrings(String... strings) {
		StringBuilder sb = new StringBuilder();
		sb.append(strings);
		return sb.toString();
	}

}
