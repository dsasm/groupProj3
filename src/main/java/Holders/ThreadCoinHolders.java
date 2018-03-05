package Holders;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import MetricApplier.SymbolVsMetricSortedList;
import model.State;
import model.SymbolMetric;
import model.SymbolMetricBought;

@Component
public class ThreadCoinHolders implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(ThreadCoinHolders.class);
	
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

	@Override
	public void run() {
		while (true) {
			while(!SymbolVsMetricSortedList.isReady()) {
				
			}
			List<SymbolMetric> tempList = new LinkedList<SymbolMetric>();
			for(int i = 0; i < symbolMetrics.length; i++) {
				
				SymbolMetric thisMetric = SymbolVsMetricSortedList.get(i);
				if (symbolMetrics[i] == null && !ready) {
					symbolMetrics[i] = new SymbolMetric(thisMetric);
				}
				else {
					
					//Ensure that this new metric isn't a symbol already being considered
					boolean inList = false;
					for (int j = 0; j < symbolMetrics.length; j++) {
						if (symbolMetrics[j].getSymbol().equals(thisMetric.getSymbol())) {
							inList = true;
						}
					}
					
					
					for (int j = 0; j < symbolMetrics.length; j++) {
						synchronized(symbolMetrics[j]) {
							if (symbolMetrics[j].getMetric() > thisMetric.getMetric() && !inList && symbolMetrics[j].getState().equals(State.LOOKING_AT)) {
								symbolMetrics[j] = new SymbolMetric(thisMetric);
								logger.info("Added : "+thisMetric.getSymbol()+ " to symbolMetrics for thread "+j );
								break;
							}
						}
					}
				}
			}
			try {
				Thread.sleep(30*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
