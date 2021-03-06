package com.surf.dsasm.Rework.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.idk.App;

public class TestDataReader implements Runnable{
	
	Logger LOGGER = LoggerFactory.getLogger(TestDataReader.class);
	
	private static List<TickerPrice> allCurrentPrices = new LinkedList<TickerPrice>();
	public static boolean readyToRead;
	
	@Override
	public void run() {
		readyToRead = false;
	
		
		File file = new File("Prices.txt");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String delimSymbol = null;
			
			while(bufferedReader.ready()) {
					String currentLine = bufferedReader.readLine();
					
					String [] currentPrices = currentLine.split(",");
					Thread.sleep(2000/App.speed);
					
					for (String price : currentPrices) {
						
						TickerPrice priceToStore = new TickerPrice();

						
						if (!price.equals("")) {
							if (delimSymbol != null) {
								if (price.split("=")[0].equals(delimSymbol) ) {
									if ( allCurrentPrices.size()>0) {
										if (!readyToRead) { 
											
											LOGGER.info("READY TO READ TEST DATA "+allCurrentPrices.size());
											readyToRead = true;
										}
										Thread.sleep(2000/App.speed);
										allCurrentPrices = new LinkedList<TickerPrice>();
										
									}
								}
							}
							
							if (delimSymbol == null) {
								LOGGER.info("New Delim: "+price.split("=")[0]);
								delimSymbol = price.split("=")[0];
							}
							 
							
							String [] symbolPrice = price.split("=");
							
							priceToStore.setSymbol(symbolPrice[0]);
							priceToStore.setPrice(symbolPrice[1]);
							allCurrentPrices.add(priceToStore);
						}
					}
					
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<TickerPrice> getAllPrices() {
		return allCurrentPrices;
	}
	
	public static String getLastPrice(String priceToGet) {
		Optional<TickerPrice> optional = allCurrentPrices.stream().filter(x -> x.getSymbol().equals(priceToGet)).findFirst();
		return optional.get().getPrice();
		
	}

}
