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
public class TestDataClientInteractor implements RestClientInteractor{
	
	private static TestDataReader testDataReader;
	
	public TestDataClientInteractor() {
		testDataReader.run();
	}
	
	/**
	 * This is a Method which will call the API, get all current prices, strip away the prices and return the Symbols
	 * @return		- The {@code List} of Symbols
	 */
	public List<String> getListOfSymbols() {
		
		return TestDataReader.getAllPrices().stream()
				.map(price -> price.getSymbol())
				.collect(Collectors.toList());
	}
	
	public List<Candlestick> getCandlesticks(String symbol, CandlestickInterval interval){
		return null;
	}
	
	public Double getLatestPrice(String thisSymbol) {
		return Double.valueOf(TestDataReader.getLastPrice(thisSymbol));
	}
	
	public List<TickerPrice> getPrices(){
		return TestDataReader.getAllPrices();
	}
}
