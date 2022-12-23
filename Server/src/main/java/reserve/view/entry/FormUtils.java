package reserve.view.entry;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.misc.Href;
import org.takes.rq.RqForm;

import reserve.controller.UserController;
import reserve.model.User;

public class FormUtils {
	
	public static final String IDENTITY_PROP_USER_ID_KEY = "userId";
	
	private static UserController identificationManager;
	
	public static void setIdentificationManager(UserController controller) {
		if(identificationManager != null)
			throw new IllegalArgumentException("An identification manager has been set already");
		identificationManager = controller;
	}
	
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
	
	private static <T extends Enum<T>> T getParamEnum(Iterable<String> values, String param, Class<T> enumClass, boolean nullable) throws IllegalArgumentException {
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
	
	private static LocalDate getParamDate(Iterable<String> values, String param, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(values, param, nullable);
		if(value == null)
			return null;
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid date format for '" + param + "'");
		}
	}
	
	public static LocalDate getParamDate(Href href, String param, boolean nullable) throws IllegalArgumentException {
		return getParamDate(href.param(param), param, nullable);
	}
	
	public static LocalDate getParamDate(RqForm form, String param, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamDate(form.param(param), param, nullable);
	}
	
	private static UUID getParamUUID(Iterable<String> values, String param, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(values, param, nullable);
		if(value == null)
			return null;
		try {
			return UUID.fromString(value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid date format for '" + param + "'");
		}
	}

	public static UUID getParamUUID(RqForm form, String param, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamUUID(form.param(param), param, nullable);
	}

	public static UUID getParamUUID(Href href, String param, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamUUID(href.param(param), param, nullable);
	}
	
	private static Boolean getParamBool(Iterable<String> values, String param, boolean nullable) throws IllegalArgumentException {
		String value = getSingleParamValue(values, param, nullable);
		if(value == null)
			return null;
		try {
			return "true".equals(value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid date format for '" + param + "'");
		}
	}

	public static Boolean getParamBool(RqForm form, String param, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamBool(form.param(param), param, nullable);
	}

	public static Boolean getParamBool(Href href, String param, boolean nullable) throws IllegalArgumentException, IOException {
		return getParamBool(href.param(param), param, nullable);
	}
	
	public static User getUserIdentity(Request request) throws IOException {
		Objects.requireNonNull(identificationManager, "No indentification manager registered yet!");
		Identity identity = new RqAuth(request).identity();
		if(identity == Identity.ANONYMOUS)
			return null;
		User user = identificationManager.getById(identity.properties().get(IDENTITY_PROP_USER_ID_KEY));
		if(user == null)
			throw new IllegalStateException("User could not be registered! They were probably deleted");
		return user;
	}
	
}
