package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import ThreadCoinHolders.ThreadCoinHolders;
import considerer.PercentageHighestProfitSellConsiderer;
import model.BoughtInfo;
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
		Float priceDiff = clientInteractor.getLatestPrice(symbolToConsider);
		BoughtInfo currentInfo = boughtInfo;//BoughtCoinsHolder.getBought(holdingIndex);
		logger.info("For thread : "+holdingIndex+"Should sell "+symbolToConsider+" - Bought at "+boughtInfo.getBoughtAt()+" - currently "+priceDiff+" - number increased "+ThreadCoinHolders.getMetric(holdingIndex));
		//highest profit so far is stored as the difference between the price then and the original price

		boughtInfo.setShouldSell(ThreadCoinHolders.getMetric(holdingIndex) <= 5.0f);
		return boughtInfo;
	}

}
