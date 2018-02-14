package MovingAverage;

import com.binance.api.client.domain.market.Candlestick;

public class MovingAverageUtils {
	public static Float fourPointAverageFlat(Candlestick candle) {
		Float open = Float.valueOf(candle.getOpen());
		Float close = Float.valueOf(candle.getClose());
		Float low = Float.valueOf(candle.getLow());
		Float high = Float.valueOf(candle.getHigh());
		return ((open+close+low+high) / 4);
	}
}
