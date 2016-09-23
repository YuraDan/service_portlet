package ru.gradis.sovzond.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.Files.lines;

/**
 * Created by donchenko-y on 7/8/16.
 */
public class CommonUtil {

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@NotNull
	public static String concatStrings(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static String newConcatStrings(String... strings) {
		return String.join("", strings);
	}

	public static ResponseEntity<String> getOkResponseFromString(String str) {
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	public static ResponseEntity<String> getBadResponseFromString(String str) {
		return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
	}

}
