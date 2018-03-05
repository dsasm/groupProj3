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
public class MovingAverageApplier implements MetricApplier{
	
	private RestClientInteractor clientInteractor;
	private MovingAverageDifferenceCalculator movingAverageDifferenceCalculator;
	private Logger logger = LoggerFactory.getLogger(MovingAverageApplier.class);
	
	
	@Autowired
	public MovingAverageApplier(RestClientInteractor clientInteractor
			, MovingAverageDifferenceCalculator movingAverageDifferenceCalculator) {
		this.clientInteractor = clientInteractor;
		this.movingAverageDifferenceCalculator = movingAverageDifferenceCalculator;
	}
	
	@Override
	public void run() {
		// logger.info("Starting Loop");
		//For each coin
		while (true) {
			for(String symbol : clientInteractor.getListOfSymbols()) {
				if (symbol.endsWith("ETH")) {
					//Get the movingAverage
					Float value = movingAverageDifferenceCalculator.calculateHeavyweight(symbol, CandlestickInterval.TWO_HOURLY, CandlestickInterval.FIFTEEN_MINUTES);
					
					//And put the movingAverage in the map next to its coin symbol
					SymbolVsMetricSortedList.put(symbol, value);
					logger.info("Done coin "+symbol + " Value: "+value);
				}
				
			}
			SymbolVsMetricSortedList.setReady(true);
			logger.info("Completed a loop of lightWeight metric application");

		}
		
	}
	
	
	
}
