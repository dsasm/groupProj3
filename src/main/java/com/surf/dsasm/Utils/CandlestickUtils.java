package com.surf.dsasm.Utils;

import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.Candlestick;

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
}
