package MetricApplier;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BurstClassifier;
import model.ConsistentGradientClassifier;

public class ConsistentGradientSearcher implements MetricApplier{

	private Logger logger = LoggerFactory.getLogger(ConsistentGradientSearcher.class);
	
	private RestClientInteractor client;
	Map<String, ConsistentGradientClassifier> symbolBursts = new HashMap<String, ConsistentGradientClassifier>();
	
	private final int NUMBER_RUNS = 10;
	
	@Autowired
	public ConsistentGradientSearcher(RestClientInteractor client) {
		this.client = client;
	}
	
	
	@Override
	public void run() {
		 
		for (String symbol : client.getListOfSymbols()) {
			if (symbol.endsWith("ETH")) {
			 symbolBursts.put(symbol, new ConsistentGradientClassifier(symbol, NUMBER_RUNS));
			}
		}
		int counter = 0;
		while (true) {
			ConsistentGradientClassifier top = new ConsistentGradientClassifier();
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
				for (Map.Entry<String, ConsistentGradientClassifier> entry : symbolBursts.entrySet()) {
					SymbolVsMetricSortedList.put(entry.getKey(), (ConsistentGradientClassifier )entry.getValue());
					//logger.info("put "+SymbolVsMetricSortedList.get(entry.getKey()).getSymbol()+" - "+SymbolVsMetricSortedList.get(entry.getKey()).getMetric());
				}
			}
			//do it N +1 times as first will all be true
			if (counter > NUMBER_RUNS+1 && SymbolVsMetricSortedList.getSize() >0 && !SymbolVsMetricSortedList.isReady()) {
				SymbolVsMetricSortedList.setReady(true);
				logger.info("Ready set");
			}
			int topInt = 0;
			if (SymbolVsMetricSortedList.isReady()) {
				for(Map.Entry<String, ConsistentGradientClassifier> classif : symbolBursts.entrySet()) {
					if (top.getLastPrices().size() == 0) top = classif.getValue();
					else if (classif.getValue().getSumIncr() > top.getSumIncr()) top = classif.getValue();
				}
				logger.info("Current Top : "+top.getSymbol()+" - "+top.getSumIncr());
			}
			try {
				Thread.sleep(20*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
