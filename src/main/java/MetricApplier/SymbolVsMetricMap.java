package MetricApplier;

import java.util.HashMap;
import java.util.Map;

/**
 * The point of this method is to hold the Map that will be holding data regarding coins
 * as well as enforce thread-safety by ensuring the only way to add / read / get from that same map
 * is to be synchronized 
 */
public class SymbolVsMetricMap {
	
	private static Map<String, Number> symbolVsMetric = new HashMap<String, Number>();
	
	/**
	 * Thread-safely adds a Symbol / Value pair to the symbolVsMetric Map
	 * @param symbol		The Symbol key
	 * @param value			The value to be put against the symbol
	 */
	public static void put(String symbol, Number value) {
		synchronized (symbolVsMetric) {
			symbolVsMetric.put(symbol, value);
		}
	}
	
	/**
	 * Thread-safely gets the value associated with a Symbol key
	 * @param symbol		The symbol whose value you wish to get
	 * @return				The value associated with the symbol
	 */
	public static Number get(String symbol) {
		Number toReturn = 0f;
		synchronized (symbolVsMetric) {
			toReturn = symbolVsMetric.get(symbol);
		}
		return toReturn;
	}
	
}
