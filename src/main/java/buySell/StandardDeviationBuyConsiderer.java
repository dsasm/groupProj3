package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import ThreadCoinHolders.ThreadCoinHolders;
import model.BurstClassifier;
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
	public boolean shouldBuyNow(int threadIndex) {
		
		Float thisMetric = ThreadCoinHolders.getMetric(threadIndex);
		
		logger.info(threadIndex+" - "+thisMetric);
		return thisMetric >= Math.ceil(10*0.7);
	}

	@Override
	public boolean shouldBuyNow(String symbolToConsider) {
		// TODO Auto-generated method stub
		StandardDeviationClassifier thisMetric = (StandardDeviationClassifier) SymbolVsMetricSortedList.get(symbolToConsider).getMetric();
		
		logger.info(symbolToConsider+" - "+thisMetric.getCurrentStandardDev()+" curr price "+clientInteractor.getLatestPrice(symbolToConsider));
		
		if (thisMetric.shouldBuy(Double.parseDouble(clientInteractor.getLatestPrice(symbolToConsider).toString()))) {
			try {
				Thread.sleep(30*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return thisMetric.shouldBuy(Double.parseDouble(clientInteractor.getLatestPrice(symbolToConsider).toString()));
		}
		else return false;
	}
}
