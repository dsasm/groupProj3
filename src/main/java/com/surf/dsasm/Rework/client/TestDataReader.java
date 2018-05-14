package com.surf.dsasm.Rework.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.binance.api.client.domain.market.TickerPrice;

public class TestDataReader implements Runnable{
	
	private static List<TickerPrice> allCurrentPrices = new LinkedList<TickerPrice>();
	
	@Override
	public void run() {
		
		File file = new File("Prices.txt");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while(bufferedReader.ready()) {
				synchronized (allCurrentPrices){
					String currentLine = bufferedReader.readLine();
					String [] currentPrices = currentLine.split(",");
					
					allCurrentPrices = new LinkedList<TickerPrice>();
					
					for (String price : currentPrices) {
						
						TickerPrice priceToStore = new TickerPrice();
						
						String [] symbolPrice = price.split("=");
						
						priceToStore.setPrice(symbolPrice[1]);
						priceToStore.setPrice(symbolPrice[0]);
						allCurrentPrices.add(priceToStore);
					}
					
				}
				Thread.sleep(2000);
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
