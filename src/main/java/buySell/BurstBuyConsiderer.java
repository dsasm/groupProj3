package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.BurstClassifier;
import model.SymbolMetric;

@Component
public class BurstBuyConsiderer implements BuyConsiderer{
	
	Logger logger = LoggerFactory.getLogger(BurstBuyConsiderer.class);
	
	@Override
	public boolean shouldBuyNow(int threadIndex) {
		
		Float thisMetric = ThreadCoinHolders.getMetric(threadIndex);
		
		logger.info(threadIndex+" - "+thisMetric);
		return thisMetric >= Math.ceil(10*0.7);
	}

	@Override
	public boolean shouldBuyNow(String symbolToConsider) {
		// TODO Auto-generated method stub
		BurstClassifier thisMetric = (BurstClassifier) SymbolVsMetricSortedList.get(symbolToConsider).getMetric();
		
		logger.info(symbolToConsider+" - "+thisMetric.numberIncrease());
		return thisMetric.shouldBuy();
	}

}
