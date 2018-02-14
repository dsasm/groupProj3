package MetricApplier;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import MovingAverage.MovingAverageDifferenceCalculator;

@Service
public class FlatMovingAverageApplier {
	
	private RestClientInteractor clientInteractor;
	private TaskExecutor task; 
	private MovingAverageDifferenceCalculator movingAverageDifferenceCalculator;
	
	@Autowired
	public FlatMovingAverageApplier(RestClientInteractor clientInteractor
			, TaskExecutor taskExecutor
			, MovingAverageDifferenceCalculator movingAverageDifferenceCalculator) {
		this.clientInteractor = clientInteractor;
		this.task = taskExecutor;
		this.movingAverageDifferenceCalculator = movingAverageDifferenceCalculator;
	}
	
	public void execute() {
		
		task.execute(new Runnable(){
			List<String> listOfSymbols = new LinkedList<String>();
			@Override
			public void run() {
				listOfSymbols = clientInteractor.getListOfSymbols();
				for(String symbol : listOfSymbols) {
					Float toReturn = movingAverageDifferenceCalculator.calculateLightWeight(symbol, CandlestickInterval.TWO_HOURLY, CandlestickInterval.FIFTEEN_MINUTES);
				}
			}
			
		});
	}
	
	
	
}
