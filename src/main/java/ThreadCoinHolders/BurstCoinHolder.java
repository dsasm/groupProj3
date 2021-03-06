package ThreadCoinHolders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;
import model.SymbolMetric;

public class BurstCoinHolder implements Runnable{
	
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
		// TODO Auto-generated method stub
		
	}
}
