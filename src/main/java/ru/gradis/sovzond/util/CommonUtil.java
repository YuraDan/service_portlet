package ru.gradis.sovzond.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;


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

	public static String hexStringToStringByAscii(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			String oneHexa = hexString.substring(i * 2, i * 2 + 2);
			bytes[i] = Byte.parseByte(oneHexa, 16);
		}
		try {
			return new String(bytes, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	// TODO: 30.09.16 delete later 
	public void printCookies(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException, PortalException, SystemException, EncryptorException {
		Cookie[] cookies = req.getCookies();
		String userId = null;
		String password = null;
		String companyId = null;
		String uuid = null;
		// Getting the cookies from the servlet request
		for (Cookie c : cookies) {
			System.out.println(c.getName() + "=" + c.getValue() + " ");
			companyId = c.getName().equals("COMPANY_ID") ? c.getValue() : companyId;
			userId = c.getName().equals("ID") ? hexStringToStringByAscii(c.getValue()) : userId;
			password = c.getName().equals("PASSWORD") ? hexStringToStringByAscii(c.getValue()) : password;
			uuid = c.getName().equals("USER_UUID") ? hexStringToStringByAscii(c.getValue()) : uuid;
		}
		Company company = CompanyLocalServiceUtil.getCompany(Long.valueOf(companyId));
		Key key = company.getKeyObj();
		System.out.println("ID_DECRYPT: " + Encryptor.decrypt(key, userId));
		System.out.println("USER_UID_DECR: " + Encryptor.decrypt(key, uuid));
		if (userId != null && password != null && companyId != null) {
			KeyValuePair kvp = UserLocalServiceUtil.decryptUserId(Long.parseLong(companyId), userId, password);
			System.out.println("user = " + kvp.getKey() + "password = " + kvp.getValue());
			User u = UserLocalServiceUtil.getUserById(Long.valueOf(kvp.getKey()));
			System.out.println("fullname = " + u.getFullName());
		}

	}

}
