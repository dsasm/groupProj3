package com.surf.dsasm.Rework.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.TickerPrice;

/**
 * This is a class to contain all methods to do with directly interacting with the API
 * @author apsimpsonmccaffrey
 *
 */
@Service
public class ActualClientInteractor implements RestClientInteractor{
	
	private static BinanceApiRestClient client;
	
	public ActualClientInteractor() {
		ActualClientInteractor.client = BinanceApiClientFactory.newInstance().newRestClient();
	}
	
	/**
	 * This is a Method which will call the API, get all current prices, strip away the prices and return the Symbols
	 * @return		- The {@code List} of Symbols
	 */
	public List<String> getListOfSymbols() {
		return client.getAllPrices().stream()
					.map(price -> price.getSymbol())
					.collect(Collectors.toList());
	}
	
	public List<Candlestick> getCandlesticks(String symbol, CandlestickInterval interval){
		return client.getCandlestickBars(symbol, interval);
	}
	
	public Double getLatestPrice(String thisSymbol) {
		return Double.valueOf(client.get24HrPriceStatistics(thisSymbol).getLastPrice());
	}
	
	public List<TickerPrice> getPrices(){
		return client.getAllPrices();
	}
}
