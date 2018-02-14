package MovingAverage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;

public class MovingAveragePercentageDifferenceCalculator {

	private RestClientInteractor clientInteractor;
	
	@Autowired
	public MovingAveragePercentageDifferenceCalculator(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	/**
	 * This is a method to calculate the FLAT (not exponential) moving average of a symbol</br>
	 * <b>This uses a 4 point average of a candlestick of each interval. Lightweight but less accurate</b>
	 * @param symbol				- The symbol whos moving average difference will be calculated
	 * @param largerInterval		- The larger interval of time
	 * @param smallerInterval		- The smaller interval of time
	 * @return						- The Difference between these moving averages
	 */
	public Float calculateLightWeight(String symbol, CandlestickInterval largerInterval, CandlestickInterval smallerInterval) {
		List<Candlestick> largerCandlesticks = clientInteractor.getCandlesticks(symbol, largerInterval);
		Candlestick latestCandlestick = largerCandlesticks.get(largerCandlesticks.size()-1);
		Float largerMovingAverage = MovingAverageUtils.fourPointAverageFlat(latestCandlestick);
		
		List<Candlestick> smallerCandles = clientInteractor.getCandlesticks(symbol, smallerInterval);
		Candlestick smallerCandlesticks = smallerCandles.get(smallerCandles.size()-1);
		Float smallerMovingAverage = MovingAverageUtils.fourPointAverageFlat(smallerCandlesticks);
		
		return (smallerMovingAverage / largerMovingAverage ) *100;
	}
	
	/**
	 * This is a method to calculate the FLAT (not exponential) moving average of a symbol </br>
	 * <b>This uses a moving average of 1-min interval candlesticks. More accurate but (depending on the interval much more resource intensive)</b>
	 * @param symbol				- The symbol whos moving average difference will be calculated
	 * @param largerInterval		- The larger interval of time
	 * @param smallerInterval		- The smaller interval of time
	 * @return						- The Difference between these moving averages
	 */
	public Float calculateHeavyWeight(String symbol, CandlestickInterval largerInterval, CandlestickInterval smallerInterval) {
		List<Candlestick> largerCandlesticks = clientInteractor.getCandlesticks(symbol, largerInterval);
		
		
		return 0f;
	}
}
