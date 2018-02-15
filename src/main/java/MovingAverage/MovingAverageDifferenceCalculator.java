package MovingAverage;

import com.binance.api.client.domain.market.CandlestickInterval;

public interface MovingAverageDifferenceCalculator {
	
	public Float calculateLightweight(String symbol, CandlestickInterval largerInterval, CandlestickInterval smallerInterval);
	public Float calculateHeavyweight(String symbol, CandlestickInterval largerInterval, CandlestickInterval smallerInterval);
}
