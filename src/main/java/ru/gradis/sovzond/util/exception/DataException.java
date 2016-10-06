package ru.gradis.sovzond.util.exception;

import org.springframework.dao.DataAccessException;

/**
 * Created by donchenko-y on 05.10.16.
 */

public class DataException extends DataAccessException {

	public DataException(DataAccessException ex) {
		super(ex.getMessage(), ex.getCause());
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

}
