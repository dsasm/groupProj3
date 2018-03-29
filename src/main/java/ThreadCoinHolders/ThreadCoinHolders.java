package ThreadCoinHolders;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import MetricApplier.SymbolVsMetricSortedList;
import model.Metric;
import model.State;
import model.SymbolMetric;
import model.SymbolMetricBought;

@Component
public class ThreadCoinHolders implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(ThreadCoinHolders.class);
	public static boolean continueRunning = true;
	
	//An array of SymbolMetricBoughts that is the same length as the number of shouldBuys / shouldSells
	private static SymbolMetric[] symbolMetrics = new SymbolMetric[2];
	private static boolean ready = false;
	public static String getSymbol(int index) {
		String toReturn;
		synchronized(symbolMetrics) {
			toReturn = symbolMetrics[index].getSymbol();
		}
		return toReturn;
	}
	
	public static Metric getMetric(int index) {
		Metric toReturn;
		synchronized(symbolMetrics) {
			logger.info(""+index+" - "+symbolMetrics[index]+" symbol "+symbolMetrics[index].getSymbol()+" value "+symbolMetrics[index].getMetric());
			toReturn = symbolMetrics[index].getMetric();
			return toReturn;
		}
	}
	
	public static Metric getMetric(String symbol) {
		Metric toReturn;
		synchronized(symbolMetrics) {
			for (SymbolMetric symMet : symbolMetrics) {
				if (symMet.getSymbol().equals(symbol)) return symMet.getMetric();
			}
			return null;
		}
	}
	
	public static boolean isNull(int index) {
		synchronized (symbolMetrics) {
			return symbolMetrics[index] == null;
		}
	}
	public static boolean isReady() {return ready;}
	public static void setReady(boolean readyNew) {ready = readyNew;}
	
	public static void shouldBuy(int threadIndex) {
		synchronized(symbolMetrics[threadIndex]) {
			if (symbolMetrics[threadIndex].getState().equals(State.LOOKING_AT)) {
				symbolMetrics[threadIndex].nextState();
				logger.info("Changed " +threadIndex+" state to "+symbolMetrics[threadIndex].getState());
			}
		}
	}
	
	public static void buy(int threadIndex) {
		synchronized(symbolMetrics[threadIndex]) {
			if (symbolMetrics[threadIndex].getState().equals(State.SHOULD_BUY)) {
				symbolMetrics[threadIndex].nextState();
				logger.info("Changed " +threadIndex+" state to "+symbolMetrics[threadIndex].getState());
			}
		}
	}
	
	public static void shouldSell(int threadIndex) {
		synchronized(symbolMetrics[threadIndex]) {
			if (symbolMetrics[threadIndex].getState().equals(State.BOUGHT)) {
				symbolMetrics[threadIndex].nextState(); 
				logger.info("Changed " +threadIndex+" state to "+symbolMetrics[threadIndex].getState());
			}
		}
	}
	
	public static void sell(int threadIndex) {
		synchronized(symbolMetrics[threadIndex]) {
			if (symbolMetrics[threadIndex].getState().equals(State.SHOULD_SELL)) {
				symbolMetrics[threadIndex].nextState(); 
				logger.info("Changed " +threadIndex+" state to "+symbolMetrics[threadIndex].getState());
				symbolMetrics[threadIndex].nextState(); 
				logger.info("Changed " +threadIndex+" state to "+symbolMetrics[threadIndex].getState());
			}
		}
	}
	
	public static State getState(int threadIndex) {
		synchronized(symbolMetrics[threadIndex]) {
			return symbolMetrics[threadIndex].getState();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (continueRunning) {
			while(!SymbolVsMetricSortedList.isReady()) {
				
			}
			
			List<SymbolMetric> tempList = new LinkedList<SymbolMetric>();
			int loopLimit = symbolMetrics.length;
			if (SymbolVsMetricSortedList.getSize() < symbolMetrics.length) loopLimit = SymbolVsMetricSortedList.getSize();
			for (int i = 0 ; i < symbolMetrics.length; i++) {
				if (symbolMetrics[i] != null) {
					symbolMetrics[i] = new SymbolMetric(SymbolVsMetricSortedList.get(symbolMetrics[i].getSymbol()));
				}
			}
			for(int i = 0; i < loopLimit; i++) {
				if (SymbolVsMetricSortedList.get(i).getMetric() != null) {
					SymbolMetric thisMetric = SymbolVsMetricSortedList.get(i);
					if (symbolMetrics[i] == null && !ready) {
						symbolMetrics[i] = new SymbolMetric(thisMetric);
					}
					else {
						
						//Ensure that this new metric isn't a symbol already being considered
						boolean inList = false;
						for (int j = 0; j < loopLimit; j++) {
							if (symbolMetrics[j].getSymbol().equals(thisMetric.getSymbol())) {
								inList = true;
							}
						}
						
						
						for (int j = 0; j < loopLimit; j++) {
							synchronized(symbolMetrics[j]) {
								logger.info("Considering : "+symbolMetrics[j].getSymbol()+" | "+symbolMetrics[j].getMetric()+" vs "+thisMetric.getSymbol()+" | "+thisMetric.getMetric()+ " list? "+inList+" state ? "+symbolMetrics[j].getState());
								if (symbolMetrics[j].compareTo(thisMetric) < 0 && !inList && symbolMetrics[j].getState().equals(State.LOOKING_AT)) {
									symbolMetrics[j] = new SymbolMetric(thisMetric);
									logger.info("Added : "+thisMetric.getSymbol()+ " to symbolMetrics for thread "+j );
									break;
								}
								else if (symbolMetrics[j].compareTo(thisMetric) < 0 && inList && symbolMetrics[j].getSymbol().equals(thisMetric.getSymbol())) {
									symbolMetrics[j].setMetric(SymbolVsMetricSortedList.get(symbolMetrics[j].getSymbol()).getMetric());
								}
							}
						}
					}
				}
			}
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
