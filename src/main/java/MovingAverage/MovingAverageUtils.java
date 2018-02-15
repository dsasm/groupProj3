package MovingAverage;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Utils.CandlestickIntervalUtils;

public class MovingAverageUtils {
	public static Float fourPointAverageFlat(Candlestick candle) {
		Float open = Float.valueOf(candle.getOpen());
		Float close = Float.valueOf(candle.getClose());
		Float low = Float.valueOf(candle.getLow());
		Float high = Float.valueOf(candle.getHigh());
		return ((open+close+low+high) / 4);
	}
	
	
	/**
	 * Divides the minutes by 5, uses @fourPointAverage to get
	 * timePeriod
	 * Takes the 4 point average
	 * Applies weighting formula that does some funky shit
	 * @param candle
	 * @param minutes
	 * @return
	 */
	public static Float fourPointAverageExp(Candlestick candle, CandlestickInterval interval) {
		
		Integer minutes = CandlestickIntervalUtils.timeInMinutes(interval);
		
		//Divide minutes by 5 to get a more influential weighting
		Float timeperiod = ((float) minutes) / 10;
		Float toReturn = fourPointAverageFlat(candle);
			
		//Apply a weighting formula
		//Only put this variable in for debugging so can have a look at shit 
		//aids to debug immediate returns 
		Float retVal = toReturn +  (2 / (timeperiod + 1) ) * toReturn;
		return retVal;
	}
}
