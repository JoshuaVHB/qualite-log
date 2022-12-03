package reserve.util;

@FunctionalInterface
public interface LoggerFactory {

	public Logger getLogger(String name, int logLevel);
	
	public default Logger getLogger(String name) {
		return getLogger(name, Logger.LEVEL_INFO);
	}
	
}
