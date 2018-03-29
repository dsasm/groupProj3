package MetricApplier;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Metric;
import model.SymbolMetric;

/**
 * The point of this method is to hold the Map that will be holding data regarding coins
 * as well as enforce thread-safety by ensuring the only way to add / read / get from that same map
 * is to be synchronized 
 * 
 * Metric is a Generic thing, as long as its numeric it will be sorted
 */
public class SymbolVsMetricSortedList{
	
	private static List<SymbolMetric> symbolVsMetric = new LinkedList<SymbolMetric>();
	private static boolean ready = false;
	
	static Logger logger = LoggerFactory.getLogger(SymbolVsMetricSortedList.class);
	
	/**
	 * Thread-safely adds a Symbol / Value pair to the symbolVsMetric Map
	 * @param <T>
	 * @param symbol		The Symbol key
	 * @param value			The value to be put against the symbol
	 */
	public static void put(String symbol, Metric value) {
		synchronized (symbolVsMetric) {
			
			
			if (!ready && symbolVsMetric.stream().filter(symMet -> symMet.getSymbol().equals(symbol)).collect(Collectors.toList()).size() == 0) symbolVsMetric.add(new SymbolMetric(symbol,value));
			
			else {
				if (symbolVsMetric.stream().filter(symMet -> symMet.getSymbol().equals(symbol)).collect(Collectors.toList()).size() == 0) {
					symbolVsMetric.add(new SymbolMetric(symbol,value));
				}
				else{
					symbolVsMetric.stream()
					.filter(symMet -> symMet.getSymbol().equals(symbol))
					.forEach(symMet -> symMet.setMetric(value));
				}
			}
			
			symbolVsMetric.sort((left, right) -> {return right.compareTo(left);});
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
	
	public static int getSize() {
		synchronized (symbolVsMetric) {
			return symbolVsMetric.size();
		}
	}
	
	public static void setReady(boolean ready) { SymbolVsMetricSortedList.ready = ready; 
			logger.info("Now Ready");}
	public static boolean isReady() {return ready;}
}
