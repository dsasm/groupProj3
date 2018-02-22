package com.surf.dsasm.Utils;

import java.util.List;

import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import MovingAverage.MovingAverageUtils;

public class CandlestickUtils {
	
	public static Candlestick responseToCandlestick(CandlestickEvent response) {
		Candlestick toReturn = new Candlestick();
		toReturn.setHigh(response.getHigh());
		toReturn.setClose(response.getClose());
		toReturn.setOpen(response.getOpen());
		toReturn.setLow(response.getLow());
		toReturn.setNumberOfTrades(response.getNumberOfTrades());
		toReturn.setVolume(response.getVolume());
		toReturn.setQuoteAssetVolume(response.getQuoteAssetVolume());
		toReturn.setOpenTime(response.getOpenTime());
		toReturn.setTakerBuyBaseAssetVolume(response.getTakerBuyBaseAssetVolume());
		toReturn.setTakerBuyQuoteAssetVolume(response.getTakerBuyQuoteAssetVolume());
		return toReturn;
	}
	
	public static Float heavyWeightAverage(List<Candlestick> candlesticks, CandlestickInterval interval) {
		Float largerSum = 0f;
		for (int i = candlesticks.size() -1; i > candlesticks.size() -1-CandlestickIntervalUtils.timeInMinutes(interval); i--) {
			largerSum += MovingAverageUtils.fourPointAverageFlat(candlesticks.get(i));
		}
		return largerSum / CandlestickIntervalUtils.timeInMinutes(interval);
		
	}
}
