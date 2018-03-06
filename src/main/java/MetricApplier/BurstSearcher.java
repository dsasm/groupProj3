package MetricApplier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BurstClassifier;

@Service
public class BurstSearcher implements MetricApplier{
	
	private Logger logger = LoggerFactory.getLogger(BurstSearcher.class);
	
	private RestClientInteractor client;
	Map<String, BurstClassifier> symbolBursts = new HashMap<String, BurstClassifier>();
	
	private final int NUMBER_RUNS = 5;
	
	@Autowired
	public BurstSearcher(RestClientInteractor client) {
		this.client = client;
	}
	
	
	@Override
	public void run() {
		 
		for (String symbol : client.getListOfSymbols()) {
			 symbolBursts.put(symbol, new BurstClassifier(symbol, NUMBER_RUNS));
		}
		int counter = 0;
		while (true) {
			for (TickerPrice price : client.getPrices()) {
				if (price.getSymbol().endsWith("ETH")) {
					if (symbolBursts.containsKey(price.getSymbol())) {
						symbolBursts.get(price.getSymbol()).addNewPrice(Float.valueOf(price.getPrice()));
					}
					
					if (symbolBursts.get(price.getSymbol()).shouldBuy()) {
						SymbolVsMetricSortedList.put(price.getSymbol(), (float) symbolBursts.get(price.getSymbol()).numberIncrease());
						logger.info("Putting "+price.getSymbol()+" into metricList Increased "+symbolBursts.get(price.getSymbol()).numberIncrease());
					}
				}
			}
			logger.info(counter+" run through");
			counter++;
			//do it N +1 times as first will all be true
			if (counter > NUMBER_RUNS+1) {
				SymbolVsMetricSortedList.setReady(true);
				logger.info("Ready set");
			}
			
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
