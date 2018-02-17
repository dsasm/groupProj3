package MetricApplier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.SymbolMetric;

/**
 * The point of this method is to hold the Map that will be holding data regarding coins
 * as well as enforce thread-safety by ensuring the only way to add / read / get from that same map
 * is to be synchronized 
 */
public class SymbolVsMetricSortedList{
	
	private static List<SymbolMetric> symbolVsMetric = new LinkedList<SymbolMetric>();
	
	/**
	 * Thread-safely adds a Symbol / Value pair to the symbolVsMetric Map
	 * @param symbol		The Symbol key
	 * @param value			The value to be put against the symbol
	 */
	public static void put(String symbol, Float value) {
		synchronized (symbolVsMetric) {
			symbolVsMetric.add(new SymbolMetric(symbol,value));
			symbolVsMetric = symbolVsMetric.stream()
				.sorted((left, right) -> {return left.compareTo(right);})
				.collect(Collectors.toList());
		}
	}
	
	/**
	 * Thread-safely gets the SymbolMetric with the passed Symbol
	 * @param symbol		The symbol whose value you wish to get
	 * @return				The value associated with the symbol
	 */
	public static SymbolMetric get(String symbol) {
		SymbolMetric toReturn =  null;
		synchronized (symbolVsMetric) {
			for(int i = 0; i < symbolVsMetric.size();i++) {
				if (symbolVsMetric.get(i).getSymbol().equals(symbol)) {
					toReturn = new SymbolMetric(symbolVsMetric.get(i));
					break;
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * Thread-safely gets the SymbolMetric with the currentPosition
	 * @param symbol		The symbol whose value you wish to get
	 * @return				The value associated with the symbol
	 */
	public static SymbolMetric get(Integer index) {
		SymbolMetric toReturn =  null;
		synchronized (symbolVsMetric) {
			toReturn = new SymbolMetric(symbolVsMetric.get(index));
		}
		return toReturn;
	}
}