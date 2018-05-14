package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.BurstClassifier;
import model.ConsistentGradientClassifier;
import model.Metric;

@Component
public class ConsistentGradientBuyConsiderer implements BuyConsiderer{


	Logger logger = LoggerFactory.getLogger(ConsistentGradientBuyConsiderer.class);
	
	private RestClientInteractor clientInteractor;
	
	@Autowired
	public ConsistentGradientBuyConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	

	@Override
	public boolean shouldBuyNow(String symbolToConsider, Metric metric) {
		// TODO Auto-generated method stub
		ConsistentGradientClassifier thisMetric = (ConsistentGradientClassifier) metric;
		
		logger.info(symbolToConsider+" - "+thisMetric.getSumIncr());
		return thisMetric.shouldBuy();
	}
}
