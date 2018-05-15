package MetricApplier;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.Rework.client.RestClientInteractor;
import com.surf.dsasm.idk.App;

import model.StandardDeviationClassifier;

public class StandardDeviationSearcher implements MetricApplier{
		
	private Logger logger = LoggerFactory.getLogger(BurstSearcher.class);
	
	private RestClientInteractor client;
	Map<String, StandardDeviationClassifier> symbolBursts = new HashMap<String, StandardDeviationClassifier>();
	
	private final int NUMBER_RUNS = 10;
	
	@Autowired
	public StandardDeviationSearcher(RestClientInteractor client) {
		this.client = client;
	}
	
	
	@Override
	public void run() {
		 
		for (String symbol : client.getListOfSymbols()) {
			if (symbol.endsWith("ETH")) {
			 symbolBursts.put(symbol, new StandardDeviationClassifier(symbol, NUMBER_RUNS));
			}
		}
		int counter = 0;
		
		while (true) {
			StandardDeviationClassifier top = new StandardDeviationClassifier();
			if (top.getCurrentStandardDev() == null)top.setCurrentStandardDev(1000d);
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
				for (Map.Entry<String, StandardDeviationClassifier> entry : symbolBursts.entrySet()) {
					SymbolVsMetricSortedList.put(entry.getKey(), (StandardDeviationClassifier )entry.getValue());
					//logger.info("put "+SymbolVsMetricSortedList.get(entry.getKey()).getSymbol()+" - "+SymbolVsMetricSortedList.get(entry.getKey()).getMetric());
				}
			}
			//do it N +1 times as first will all be true
			if (counter > NUMBER_RUNS+1 && SymbolVsMetricSortedList.getSize() >0 && !SymbolVsMetricSortedList.isReady()) {
				SymbolVsMetricSortedList.setReady(true);
				logger.info("Ready set");
			}
			if (SymbolVsMetricSortedList.isReady()) {
				for(Map.Entry<String, StandardDeviationClassifier> classif : symbolBursts.entrySet()) {
					if (classif.getValue().getCurrentStandardDev() < top.getCurrentStandardDev()) top = classif.getValue();
				}
				logger.info("Current Top : "+top.getSymbol()+" - "+top.getCurrentStandardDev());
			}
			int topInt = 0;
			
			
			try {
				Thread.sleep(20*1000/App.speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}
