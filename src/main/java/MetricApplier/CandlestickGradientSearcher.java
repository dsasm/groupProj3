package MetricApplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.TickerPrice;
import com.surf.dsasm.Rework.client.RestClientInteractor;
import com.surf.dsasm.idk.App;

import model.GradientCandleClassifier;

@Component
public class CandlestickGradientSearcher implements MetricApplier{

	private Logger logger = LoggerFactory.getLogger(ConsistentGradientSearcher.class);
	
	private RestClientInteractor client;
	Map<String, GradientCandleClassifier> symbolBursts = new HashMap<String, GradientCandleClassifier>();
	
	private final int NUMBER_RUNS = 10;
	
	@Autowired
	public CandlestickGradientSearcher(RestClientInteractor client) {
		this.client = client;
	}
	
	
	@Override
	public void run() {
		 
		for (String symbol : client.getListOfSymbols()) {
			if (symbol.endsWith("ETH")) {
			 symbolBursts.put(symbol, new GradientCandleClassifier(symbol, NUMBER_RUNS));
			}
		}
		int counter = 0;
		while (continueRunning) {
			GradientCandleClassifier top = new GradientCandleClassifier();
			for (TickerPrice price : client.getPrices()) {
				if (price.getSymbol().endsWith("ETH")) {
					if (symbolBursts.containsKey(price.getSymbol())) {
						List<Candlestick> sticks = client.getCandlesticks(price.getSymbol(), CandlestickInterval.ONE_MINUTE);
						symbolBursts.get(price.getSymbol()).addNewPrice(sticks.get(sticks.size() -1));
					}
					
				}
			}
			logger.info(counter+" run through");
			counter++;
			if (counter >= NUMBER_RUNS+1 ) {
				for (Map.Entry<String, GradientCandleClassifier> entry : symbolBursts.entrySet()) {
					SymbolVsMetricSortedList.put(entry.getKey(), (GradientCandleClassifier )entry.getValue());
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
				for(Map.Entry<String, GradientCandleClassifier> classif : symbolBursts.entrySet()) {
					if (top.getLastPrices().size() == 0) top = classif.getValue();
					else if (classif.getValue().getSumIncr() > top.getSumIncr()) top = classif.getValue();
				}
				logger.info("Current Top : "+top.getSymbol()+" - "+top.getSumIncr());
			}
			try {
				Thread.sleep(60*1000/App.speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
