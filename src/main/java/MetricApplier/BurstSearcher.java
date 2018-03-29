package MetricApplier;

import java.util.HashMap;
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
	
	private final int NUMBER_RUNS = 10;
	
	@Autowired
	public BurstSearcher(RestClientInteractor client) {
		this.client = client;
	}
	
	
	@Override
	public void run() {
		 
		for (String symbol : client.getListOfSymbols()) {
			if (symbol.endsWith("ETH")) {
			 symbolBursts.put(symbol, new BurstClassifier(symbol, NUMBER_RUNS));
			}
		}
		int counter = 0;
		while (continueRunning) {
			for (TickerPrice price : client.getPrices()) {
				if (price.getSymbol().endsWith("ETH")) {
					if (symbolBursts.containsKey(price.getSymbol())) {
						symbolBursts.get(price.getSymbol()).addNewPrice(Double.valueOf(price.getPrice()));
					}
					
				}
			}
			logger.info(counter+" run through");
			counter++;
			if (counter >= NUMBER_RUNS+1 ) {
				for (Map.Entry<String, BurstClassifier> entry : symbolBursts.entrySet()) {
					SymbolVsMetricSortedList.put(entry.getKey(), (BurstClassifier )entry.getValue());
					//logger.info("put "+SymbolVsMetricSortedList.get(entry.getKey()).getSymbol()+" - "+SymbolVsMetricSortedList.get(entry.getKey()).getMetric());
				}
			}
			//do it N +1 times as first will all be true
			if (counter > NUMBER_RUNS+1 && SymbolVsMetricSortedList.getSize() >0 && !SymbolVsMetricSortedList.isReady()) {
				SymbolVsMetricSortedList.setReady(true);
				logger.info("Ready set");
			}
			int topInt = 0;
			BurstClassifier top = new BurstClassifier();
			
			for(Map.Entry<String, BurstClassifier> classif : symbolBursts.entrySet()) {
				if (classif.getValue().numberIncrease() > top.numberIncrease()) top = classif.getValue();
			}
			logger.info("Current Top : "+top.getSymbol()+" - "+top.numberIncrease());
			try {
				Thread.sleep(20*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
