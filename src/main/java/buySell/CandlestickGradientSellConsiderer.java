package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import model.BoughtInfo;
import model.GradientCandleClassifier;
import model.Metric;

@Component
public class CandlestickGradientSellConsiderer implements SellConsiderer{

	private RestClientInteractor clientInteractor;
	private Logger logger = LoggerFactory.getLogger(CandlestickGradientSellConsiderer.class);
	
	@Autowired
	public CandlestickGradientSellConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	@Override
	public BoughtInfo shouldSellNow(BoughtInfo boughtInfo, int holdingIndex) {

		//work out the difference between the original price and the price now
		Double priceDiff = clientInteractor.getLatestPrice(boughtInfo.getSymbol());
		BoughtInfo currentInfo = boughtInfo;//BoughtCoinsHolder.getBought(holdingIndex);
		
		Metric metric = (Metric) SymbolVsMetricSortedList.get(boughtInfo.getSymbol()).getMetric();
		
		boolean shouldSell = metric.shouldSell(priceDiff, currentInfo);
		
		if (priceDiff > boughtInfo.getHighestProfit()) boughtInfo.setHighestProfit(priceDiff);
		
		logger.info("Should Sell? "+shouldSell);
		boughtInfo.setShouldSell(shouldSell);
		
		return boughtInfo;
	}

}
