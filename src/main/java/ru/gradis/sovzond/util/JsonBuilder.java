package ru.gradis.sovzond.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by donchenko-y on 6/9/16.
 */
public class JsonBuilder {

	private static final Log log = LogFactoryUtil.getLog(JsonBuilder.class);

	public static String getJsonStringFromList(List list) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(list);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getJsonFromString(String json) {
		JSONFactory factory = JSONFactoryUtil.getJSONFactory();
		try {
			return factory.createJSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getJsonObj() {
		JSONFactory factory = JSONFactoryUtil.getJSONFactory();
		JSONObject jsonObject = factory.createJSONObject();
		return factory.createJSONObject();
	}

	public static String getStringFromJson(JSONObject jsonObject) {
		return jsonObject.toString();
	}


}
