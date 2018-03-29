package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import ThreadCoinHolders.ThreadCoinHolders;
import model.Metric;

public class MetricBuyConsiderer implements BuyConsiderer{
	
	Logger logger = LoggerFactory.getLogger(ConsistentGradientBuyConsiderer.class);
	
	private RestClientInteractor clientInteractor;
	
	@Autowired
	public MetricBuyConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}

	@Override
	public  boolean shouldBuyNow(String symbolToConsider, Metric metric) {
		
		return metric.shouldBuy();
	}
}
