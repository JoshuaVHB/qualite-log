package reserve.view.entry;

import java.io.IOException;
import java.util.Iterator;

import org.takes.misc.Href;
import org.takes.rq.RqForm;

public class FormUtils {
	
	public static final String IDENTITY_PROP_USER_ID_KEY = "userId";
	
	private static String getSingleParamValue(Iterable<String> values, String param, boolean nullable) {
		Iterator<String> valuesIt = values.iterator();
		if(!valuesIt.hasNext()) {
			if(nullable)
				return null;
			throw new IllegalArgumentException("Missing value for '" + param + "'");
		}
		String value = valuesIt.next();
		if(valuesIt.hasNext())
			throw new IllegalArgumentException("Multiple values for '" + param + "' ?");
		return value;
	}
	
	private static String getParamString(Iterable<String> values, String param, String format, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(values, param, nullable);
		if (value == null)
			return value;
		if(!value.matches(format))
			throw new IllegalArgumentException("Invalid value for '" + param + "'");
		return value;
	}
	
	public static String getParamString(RqForm form, String param, String format, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamString(form.param(param), param, format, nullable);
	}
	
	public static String getParamString(Href href, String param, String format, boolean nullable) throws IllegalArgumentException {
		return getParamString(href.param(param), param, format, nullable);
	}
	
	private static Integer getParamInt(Iterable<String> values, String param, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(values, param, nullable);
		if (value == null)
			return null;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid value for '" + param + "', expected an int");
		}
	}
	
	public static Integer getParamInt(Href href, String param, boolean nullable) throws IllegalArgumentException {
		return getParamInt(href.param(param), param, nullable);
	}
	
	public static Integer getParamInt(RqForm form, String param, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamInt(form.param(param), param, nullable);
	}
	
	public static <T extends Enum<T>> T getParamEnum(Iterable<String> values, String param, Class<T> enumClass, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(values, param, nullable);
		if(value == null)
			return null;
		return Enum.valueOf(enumClass, value);
	}
	
	public static <T extends Enum<T>> T getParamEnum(Href href, String param, Class<T> enumClass, boolean nullable) throws IllegalArgumentException {
		return getParamEnum(href.param(param), param, enumClass, nullable);
	}
	
	public static <T extends Enum<T>> T getParamEnum(RqForm form, String param, Class<T> enumClass, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamEnum(form.param(param), param, enumClass, nullable);
	}
	
	public static boolean hasParam(Href href, String param) {
		return getSingleParamValue(href.param(param), param, true) != null;
	}
	
	public static boolean hasParam(RqForm form, String param) throws IOException {
		return getSingleParamValue(form.param(param), param, true) != null;
	}
	
}
