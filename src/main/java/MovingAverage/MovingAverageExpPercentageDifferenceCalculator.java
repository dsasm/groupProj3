package MovingAverage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;
import com.surf.dsasm.Utils.CandlestickIntervalUtils;
import com.surf.dsasm.Utils.CandlestickUtils;

public class MovingAverageExpPercentageDifferenceCalculator implements MovingAverageDifferenceCalculator{

	private RestClientInteractor clientInteractor;
	
	@Autowired
	public MovingAverageExpPercentageDifferenceCalculator(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	/**
	 * This is a method to calculate the FLAT (not exponential) moving average of a symbol</br>
	 * <b>This uses a 4 point average of a candlestick of each interval. Lightweight but less accurate</b>
	 * @param symbol				- The symbol whose moving average difference will be calculated
	 * @param largerInterval		- The larger interval of time
	 * @param smallerInterval		- The smaller interval of time
	 * @return						- The Difference between these moving averages
	 */
	@Override
	public Float calculateLightweight(String symbol, CandlestickInterval largerInterval,
			CandlestickInterval smallerInterval) {
		
		List<Candlestick> largerCandlesticks = clientInteractor.getCandlesticks(symbol, largerInterval);
		Candlestick latestCandlestick = largerCandlesticks.get(largerCandlesticks.size()-1);
		Float largerMovingAverage = MovingAverageUtils.fourPointAverageExp(latestCandlestick, largerInterval);
		
		List<Candlestick> smallerCandles = clientInteractor.getCandlesticks(symbol, smallerInterval);
		Candlestick smallerCandlesticks = smallerCandles.get(smallerCandles.size()-1);
		Float smallerMovingAverage = MovingAverageUtils.fourPointAverageExp(smallerCandlesticks, smallerInterval);
		
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
	@Override
	public Float calculateHeavyweight(String symbol, CandlestickInterval largerInterval,
			CandlestickInterval smallerInterval) {
		
		List<Candlestick> largerCandlesticks = clientInteractor.getCandlesticks(symbol, CandlestickInterval.ONE_MINUTE);
		Float largerAverage = CandlestickUtils.heavyWeightAverage(largerCandlesticks, largerInterval);
		
		List<Candlestick> smallerCandlesticks = clientInteractor.getCandlesticks(symbol, CandlestickInterval.ONE_MINUTE);
		Float smallerAverage = CandlestickUtils.heavyWeightAverage(smallerCandlesticks, smallerInterval);
		
		
		return (smallerAverage/ largerAverage)*100;
	}
}
