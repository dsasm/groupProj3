import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import MetricApplier.SymbolVsMetricSortedList;
import model.SymbolMetric;

public class ThreadCoinHolders {
	
	public static SymbolMetricBought [] symbolMetrics = new SymbolMetricBought[2];
	private TaskExecutor task; 
	
	@Autowired
	public ThreadCoinHolders(TaskExecutor taskExecutor) {
		this.task = taskExecutor;
	}

	public void execute() {
		
		task.execute(new Runnable(){
			
			@Override
			public void run() {
				for(int i = 0; i < symbolMetrics.length; i++) {
					
					SymbolMetric thisMetric = SymbolVsMetricSortedList.get(i);
					for (int j = 0; j < symbolMetrics.length; j++) {
						synchronized (symbolMetrics) {
							if (!symbolMetrics[j].isBought() && symbolMetrics[j].getSymbolMetric().getMetric() > thisMetric.getMetric()) {
								symbolMetrics[j] = new SymbolMetricBought(thisMetric);
							}
						}
					}
				}
			}
		});
	}
	
	
}
