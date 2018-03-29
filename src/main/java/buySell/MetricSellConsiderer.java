package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import model.BoughtInfo;
import model.GradientCandleClassifier;
import model.Metric;

public class MetricSellConsiderer implements SellConsiderer{

	private RestClientInteractor clientInteractor;
	private Logger logger = LoggerFactory.getLogger(StandardDeviationSellConsiderer.class);
	
	@Autowired
	public MetricSellConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	@Override
	public BoughtInfo shouldSellNow(BoughtInfo boughtInfo, int holdingIndex) {
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String symbolToConsider = boughtInfo.getSymbol();
		//work out the difference between the original price and the price now
		Double priceDiff = Double.valueOf(clientInteractor.getLatestPrice(symbolToConsider).toString());
		
		BoughtInfo currentInfo = boughtInfo;//BoughtCoinsHolder.getBought(holdingIndex);
		logger.info("For thread : "+holdingIndex+"Should sell "+symbolToConsider+" - Bought at "+boughtInfo.getBoughtAt()+" - currently "+priceDiff+" - number increased "+((GradientCandleClassifier)SymbolVsMetricSortedList.get(symbolToConsider).getMetric()).getSumIncr());
		//highest profit so far is stored as the difference between the price then and the original price
		
		Metric metric = (Metric) SymbolVsMetricSortedList.get(symbolToConsider).getMetric();
		
		boolean shouldSell = metric.shouldSell(priceDiff, currentInfo);
		
		if (priceDiff > boughtInfo.getHighestProfit()) boughtInfo.setHighestProfit(priceDiff);
		
		boughtInfo.setShouldSell(shouldSell);
		return boughtInfo;
	}

	
}
