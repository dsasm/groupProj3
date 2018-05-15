package com.surf.dsasm.idk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.Rework.client.RestClientInteractor;

@Component
public class DataGatherer {
	
	private RestClientInteractor restClientInteractor;
	private Logger logger = LoggerFactory.getLogger(DataGatherer.class);
	
	@Autowired 
	public DataGatherer(RestClientInteractor restClientInteractor) {
		this.restClientInteractor = restClientInteractor;
	}
	
	
	public void gatherInfo() throws InterruptedException {
		long linesGathered=0l;
		File file = new File("Prices.txt");
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		
			
			long before = System.currentTimeMillis();
			while(System.currentTimeMillis() < before+1000*60*60*22 ) {
				
				List<TickerPrice> prices = restClientInteractor.getPrices();
				StringBuilder representation = new StringBuilder();
				
				for (TickerPrice price : prices) {
					if (!representation.equals(new StringBuilder())) representation.append(",");
					representation.append(price.getSymbol()+"="+price.getPrice());
				}
				
				representation.append("\n");
				writer.write(representation.toString());
				writer.flush();
				linesGathered++;
				logger.info(Long.valueOf(linesGathered).toString());
				Thread.sleep(2000l);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
