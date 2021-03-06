package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.GradientCandleClassifier;
import model.Metric;

@Component
public class CandlestickGradientBuyConsiderer implements BuyConsiderer{


	Logger logger = LoggerFactory.getLogger(ConsistentGradientBuyConsiderer.class);
	
	private RestClientInteractor clientInteractor;
	
	@Autowired
	public CandlestickGradientBuyConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}

	@Override
	public  boolean shouldBuyNow(String symbolToConsider, Metric thisMetric) {
		
		return thisMetric.shouldBuy();
	}

}
