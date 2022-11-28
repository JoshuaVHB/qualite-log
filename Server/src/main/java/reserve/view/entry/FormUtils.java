package reserve.view.entry;

import java.util.Iterator;

import org.takes.misc.Href;

public class FormUtils {
	
	public static final String IDENTITY_PROP_USER_ID_KEY = "userId";
	
	public static String getSingleParamValue(Href href, String param, boolean nullable) {
		Iterator<String> values = href.param(param).iterator();
		if(!values.hasNext()) {
			if(nullable)
				return null;
			throw new IllegalArgumentException("Missing value for '" + param + "'");
		}
		String value = values.next();
		if(values.hasNext())
			throw new IllegalArgumentException("Multiple values for '" + param + "' ?");
		return value;
	}
	
	public static String getParamString(Href href, String param, String format, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(href, param, nullable);
		if (value == null)
			return value;
		if(!value.matches(format))
			throw new IllegalArgumentException("Invalid value for '" + param + "'");
		return value;
	}
	
	public static Integer getParamInt(Href href, String param, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(href, param, nullable);
		if (value == null)
			return null;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid value for '" + param + "', expected an int");
		}
	}
	
	public static <T extends Enum<T>> T getParamEnum(Href href, String param, Class<T> enumClass, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(href, param, nullable);
		if(value == null)
			return null;
		return Enum.valueOf(enumClass, value);
	}
	
	public static boolean hasParam(Href href, String param) {
		return getSingleParamValue(href, param, true) != null;
	}
	
}
