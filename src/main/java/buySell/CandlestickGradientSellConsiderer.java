package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import model.BoughtInfo;
import model.GradientCandleClassifier;

@Component
public class CandlestickGradientSellConsiderer implements SellConsiderer{

	private RestClientInteractor clientInteractor;
	private Logger logger = LoggerFactory.getLogger(StandardDeviationSellConsiderer.class);
	
	@Autowired
	public CandlestickGradientSellConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	@Override
	public BoughtInfo shouldSellNow(BoughtInfo boughtInfo, int holdingIndex) {

		String symbolToConsider = boughtInfo.getSymbol();
		//work out the difference between the original price and the price now
		Float priceDiff = clientInteractor.getLatestPrice(symbolToConsider);
		
		BoughtInfo currentInfo = boughtInfo;//BoughtCoinsHolder.getBought(holdingIndex);
		logger.info("For thread : "+holdingIndex+"Should sell "+symbolToConsider+" - Bought at "+boughtInfo.getBoughtAt()+" - currently "+priceDiff+" - number increased "+((GradientCandleClassifier)SymbolVsMetricSortedList.get(symbolToConsider).getMetric()).getSumIncr());
		//highest profit so far is stored as the difference between the price then and the original price
		
		GradientCandleClassifier extractedClassifier = (GradientCandleClassifier) SymbolVsMetricSortedList.get(symbolToConsider).getMetric();
		
		boolean shouldSell = extractedClassifier.getSumIncr() <= 5 || priceDiff < 0.995*boughtInfo.getBoughtAt() || priceDiff < 0.998*boughtInfo.getHighestProfit();
		
		
		logger.info("highestProfit "+boughtInfo.getHighestProfit() + "looking to sell at "+0.998*boughtInfo.getHighestProfit());
		//Gain confidence if it wants to sell so soon after buying
		
		if (!shouldSell && boughtInfo.getTimeBoughtAt() < 1000*120 && priceDiff < 0.995*boughtInfo.getBoughtAt()) {
			Float currPrice = new Float(priceDiff);
			while (currPrice == priceDiff) {
				try {
					Thread.sleep(10*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currPrice = clientInteractor.getLatestPrice(symbolToConsider);
			}
			logger.info("tried gaining confidence "+currPrice+" vs should sell at at"+0.998*boughtInfo.getBoughtAt());
			shouldSell = currPrice < 0.998*boughtInfo.getBoughtAt() ;
			
			
		}
		
		boughtInfo.setShouldSell(shouldSell);
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (priceDiff > boughtInfo.getHighestProfit()) boughtInfo.setHighestProfit(priceDiff);
		return boughtInfo;
	}

}
