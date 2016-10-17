package ru.gradis.sovzond.portlet.service;

import org.springframework.stereotype.Service;

/**
 * Created by donchenko-y on 17.10.16.
 */

@Service
public class DefaultConfigService {

	public static String getDefaultConfig() {
		return "Hello from server!!!";
	}

}
