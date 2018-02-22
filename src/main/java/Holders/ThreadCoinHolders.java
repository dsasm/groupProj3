package Holders;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import MetricApplier.SymbolVsMetricSortedList;
import model.SymbolMetric;
import model.SymbolMetricBought;

@Component
public class ThreadCoinHolders implements Runnable{
	
	Logger logger = LoggerFactory.getLogger(ThreadCoinHolders.class);
	
	//An array of SymbolMetricBoughts that is the same length as the number of shouldBuys / shouldSells
	private static SymbolMetricBought [] symbolMetrics = new SymbolMetricBought[2];
	private static boolean ready = false;
	public static String getSymbol(int index) {
		String toReturn;
		synchronized(symbolMetrics) {
			toReturn = symbolMetrics[index].getSymbolMetric().getSymbol();
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

	@Override
	public void run() {
		while (true) {
			while(!SymbolVsMetricSortedList.isReady()) {
				
			}
			List<SymbolMetricBought> tempList = new LinkedList<SymbolMetricBought>();
			for(int i = 0; i < symbolMetrics.length; i++) {
				
				SymbolMetric thisMetric = SymbolVsMetricSortedList.get(i);
				if (symbolMetrics[i] == null && !ready) {
					symbolMetrics[i] = new SymbolMetricBought(thisMetric);
				}
				else {
					for (int j = 0; j < symbolMetrics.length; j++) {
						if (symbolMetrics[j].getSymbolMetric().getMetric() > thisMetric.getMetric()) {
							symbolMetrics[j] = new SymbolMetricBought(thisMetric);
							logger.info("Added : "+thisMetric.getSymbol()+ " to symbolMetrics for thread "+j );
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
