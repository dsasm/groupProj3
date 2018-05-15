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
		testDataReader = new TestDataReader();
		new Thread(testDataReader).start();
	}
	
	/**
	 * This is a Method which will call the API, get all current prices, strip away the prices and return the Symbols
	 * @return		- The {@code List} of Symbols
	 */
	public List<String> getListOfSymbols() {
		while(!TestDataReader.readyToRead) {}
		return TestDataReader.getAllPrices().stream()
				.map(price -> price.getSymbol())
				.collect(Collectors.toList());
	}
	
	public List<Candlestick> getCandlesticks(String symbol, CandlestickInterval interval){
		while(!TestDataReader.readyToRead) {}
		return null;
	}
	
	public Double getLatestPrice(String thisSymbol) {
		while(!TestDataReader.readyToRead) {}
		return Double.valueOf(TestDataReader.getLastPrice(thisSymbol));
	}
	
	public List<TickerPrice> getPrices(){
		while(!TestDataReader.readyToRead) {}
		return TestDataReader.getAllPrices();
	}
}
