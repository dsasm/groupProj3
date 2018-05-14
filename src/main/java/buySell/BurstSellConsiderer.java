package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import considerer.PercentageHighestProfitSellConsiderer;
import model.BoughtInfo;
import model.BurstClassifier;
import model.Metric;
import model.SymbolMetric;

@Component
public class BurstSellConsiderer implements SellConsiderer{

	private RestClientInteractor clientInteractor;
	private Logger logger = LoggerFactory.getLogger(BurstSellConsiderer.class);
	
	@Autowired
	public BurstSellConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	@Override
	public BoughtInfo shouldSellNow(BoughtInfo boughtInfo, int holdingIndex) {
		
		String symbolToConsider = boughtInfo.getSymbol();
		//work out the difference between the original price and the price now
		Double priceDiff = clientInteractor.getLatestPrice(symbolToConsider);
		BoughtInfo currentInfo = boughtInfo;//BoughtCoinsHolder.getBought(holdingIndex);
		logger.info("For thread : "+holdingIndex+"Should sell "+symbolToConsider+" - Bought at "+boughtInfo.getBoughtAt()+" - currently "+priceDiff+" - number increased "+((BurstClassifier)SymbolVsMetricSortedList.get(symbolToConsider).getMetric()).numberIncrease());
		//highest profit so far is stored as the difference between the price then and the original price
		
		Metric metric = (Metric) SymbolVsMetricSortedList.get(symbolToConsider).getMetric();
		
		boolean shouldSell = metric.shouldSell(priceDiff, currentInfo);
		

		if (priceDiff > boughtInfo.getHighestProfit()) boughtInfo.setHighestProfit(priceDiff);
		
		logger.info("Should Sell? "+shouldSell);
		boughtInfo.setShouldSell(shouldSell);
		
		return boughtInfo;
	}

}
