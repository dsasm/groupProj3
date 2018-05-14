package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.BurstClassifier;
import model.Metric;
import model.StandardDeviationClassifier;

@Component
public class StandardDeviationBuyConsiderer implements BuyConsiderer{
	
	Logger logger = LoggerFactory.getLogger(StandardDeviationBuyConsiderer.class);
	private RestClientInteractor clientInteractor;
	
	@Autowired
	public StandardDeviationBuyConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	@Override
	public boolean shouldBuyNow(String symbolToConsider, Metric metric) {
		// TODO Auto-generated method stub
		StandardDeviationClassifier thisMetric = (StandardDeviationClassifier) metric;
		
		logger.info(symbolToConsider+" - "+thisMetric.getCurrentStandardDev()+" curr price "+clientInteractor.getLatestPrice(symbolToConsider));
		
		if (thisMetric.shouldBuy()) {
			try {
				Thread.sleep(30*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return thisMetric.shouldBuy();
		}
		else return false;
	}
}
