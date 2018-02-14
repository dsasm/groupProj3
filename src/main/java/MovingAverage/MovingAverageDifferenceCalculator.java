package MovingAverage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;

public class MovingAverageDifferenceCalculator {

	private RestClientInteractor clientInteractor;
	
	@Autowired
	public MovingAverageDifferenceCalculator(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	/**
	 * This is a method to calculate the FLAT (not exponential) moving average of a symbol
	 * @param symbol				- The symbol whos moving average difference will be calculated
	 * @param largerInterval		- The larger interval of time
	 * @param smallerInterval		- The smaller interval of time
	 * @return						- The Difference between these moving averages
	 */
	public Float calculateLightWeight(String symbol, CandlestickInterval largerInterval, CandlestickInterval smallerInterval) {
		List<Candlestick> largerCandlesticks = clientInteractor.getCandlesticks(symbol, largerInterval);
		return 0f;
	}
}
