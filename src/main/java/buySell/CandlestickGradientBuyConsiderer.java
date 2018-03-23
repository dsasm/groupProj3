package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.GradientCandleClassifier;

@Component
public class CandlestickGradientBuyConsiderer implements BuyConsiderer{


	Logger logger = LoggerFactory.getLogger(ConsistentGradientBuyConsiderer.class);
	
	private RestClientInteractor clientInteractor;
	
	@Autowired
	public CandlestickGradientBuyConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	@Override
	public boolean shouldBuyNow(int threadIndex) {
		
		Float thisMetric = ThreadCoinHolders.getMetric(threadIndex);
		
		logger.info(threadIndex+" - "+thisMetric);
		return thisMetric >= Math.ceil(10*0.7);
	}

	@Override
	public boolean shouldBuyNow(String symbolToConsider) {
		// TODO Auto-generated method stub
		GradientCandleClassifier thisMetric = (GradientCandleClassifier) SymbolVsMetricSortedList.get(symbolToConsider).getMetric();
		
		logger.info(symbolToConsider+" - "+thisMetric.getSumIncr());
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisMetric.shouldBuy();
	}

}
