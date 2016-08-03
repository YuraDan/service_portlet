package ru.gradis.sovzond.jdbc.sql;

/**
 * Created by donchenko-y on 6/8/16.
 */
public class SqlStorage {

	public final static Integer TEXT = 2147483647;
	public final static Integer BYTEA = 2147483647;

	public static final String GET_ALL_PARCEL = "SELECT * FROM public.parcel";

	public static final String GET_ALL_COUNTRY = "SELECT * FROM public.country";

}
