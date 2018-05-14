package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.BurstClassifier;
import model.Metric;
import model.SymbolMetric;

@Component
public class BurstBuyConsiderer implements BuyConsiderer{
	
	Logger logger = LoggerFactory.getLogger(BurstBuyConsiderer.class);
	

	@Override
	public boolean shouldBuyNow(String symbolToConsider, Metric thisMetric) {
		// TODO Auto-generated method stub
		
		return thisMetric.shouldBuy();
	}

}
