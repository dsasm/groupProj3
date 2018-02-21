package MetricApplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import MovingAverage.MovingAverageDifferenceCalculator;
@Service
public class MovingAverageApplier implements MetricApplier, TaskExecutor{
	
	private RestClientInteractor clientInteractor;
	private MovingAverageDifferenceCalculator movingAverageDifferenceCalculator;
	private Logger logger = LoggerFactory.getLogger(MovingAverageApplier.class);
	
	
	@Autowired
	public MovingAverageApplier(RestClientInteractor clientInteractor
			, MovingAverageDifferenceCalculator movingAverageDifferenceCalculator) {
		this.clientInteractor = clientInteractor;
		this.movingAverageDifferenceCalculator = movingAverageDifferenceCalculator;
	}
	
	public void execute() {

		logger.info("Starting up");
		execute(new Runnable(){
			
			
			
			@Override
			public void run() {
				logger.info("Starting Loop");
				//For each coin
				for(String symbol : clientInteractor.getListOfSymbols()) {
					
					//Get the movingAverage
					Float value = movingAverageDifferenceCalculator.calculateLightweight(symbol, CandlestickInterval.TWO_HOURLY, CandlestickInterval.FIFTEEN_MINUTES);
					
					//And put the movingAverage in the map next to its coin symbol
					SymbolVsMetricSortedList.put(symbol, value);
					
				}
				logger.info("Completed a loop of lightWeight metric application");
			}
			
		});
	}

	@Override
	public void execute(Runnable arg0) {
		arg0.run();
	}
	
	
	
}
