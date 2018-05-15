package com.surf.dsasm.idk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.Rework.client.TestDataReader;

public class PricesScript {	
	
	static Logger LOGGER = LoggerFactory.getLogger(PricesScript.class);
	
	private static List<TickerPrice> allCurrentPrices = new LinkedList<TickerPrice>();
	public static boolean readyToRead;
	
	public static void run() {
		readyToRead = false;
		
		File file = new File("Prices.txt");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String delimSymbol = null;
			int counter = 0;
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
										tagAndWriteToFile(counter);
										counter++;
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
	private static void tagAndWriteToFile(int counter) {
		File file = new File("Prices.txt");
		FileWriter fileReader;
		try {
			fileReader = new FileWriter(file);
			BufferedWriter bufferedReader = new BufferedWriter(fileReader);
			List<TickerPrice> pricesToAppend = new LinkedList<TickerPrice>();
			for (TickerPrice price: allCurrentPrices) {
				bufferedReader.append(price.getSymbol()+"="+price.getPrice()+"="+counter+",");
			}
			bufferedReader.append("\n");
			bufferedReader.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
