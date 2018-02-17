package MetricApplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import MovingAverage.MovingAverageDifferenceCalculator;
@Service
public class MovingAverageApplier implements MetricApplier{
	
	private RestClientInteractor clientInteractor;
	private TaskExecutor task; 
	private MovingAverageDifferenceCalculator movingAverageDifferenceCalculator;
	
	
	@Autowired
	public MovingAverageApplier(RestClientInteractor clientInteractor
			, TaskExecutor taskExecutor
			, MovingAverageDifferenceCalculator movingAverageDifferenceCalculator) {
		this.clientInteractor = clientInteractor;
		this.task = taskExecutor;
		this.movingAverageDifferenceCalculator = movingAverageDifferenceCalculator;
	}
	
	public void execute() {
		
		task.execute(new Runnable(){
			
			
			
			@Override
			public void run() {
				
				//For each coin
				for(String symbol : clientInteractor.getListOfSymbols()) {
					
					//Get the movingAverage
					Float value = movingAverageDifferenceCalculator.calculateLightweight(symbol, CandlestickInterval.TWO_HOURLY, CandlestickInterval.FIFTEEN_MINUTES);
					
					//And put the movingAverage in the map next to its coin symbol
					SymbolVsMetricSortedList.put(symbol, value);
					
				}
			}
			
		});
	}
	
	
	
}
