package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BoughtInfo;

@Component
public class StandardDeviationSellConsiderer implements SellConsiderer{

	private RestClientInteractor clientInteractor;
	private Logger logger = LoggerFactory.getLogger(StandardDeviationSellConsiderer.class);
	
	@Autowired
	public StandardDeviationSellConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	@Override
	public BoughtInfo shouldSellNow(BoughtInfo boughtInfo, int holdingIndex) {

		String symbolToConsider = boughtInfo.getSymbol();
		// TODO Auto-generated method stub
		Float priceDiff = clientInteractor.getLatestPrice(symbolToConsider);
		BoughtInfo currentInfo = boughtInfo;//BoughtCoinsHolder.getBought(holdingIndex);
		boughtInfo.setShouldSell();
		
		logger.info("Should sell? Current"+priceDiff+" BoughtAt "+boughtInfo.getBoughtAt()+" HP "+boughtInfo.getHighestProfit());
		logger.info("Would sell at "+0.998*boughtInfo.getBoughtAt()+" or "+boughtInfo.getHighestProfit()*0.998);
		

		if (priceDiff > boughtInfo.getHighestProfit()) boughtInfo.setHighestProfit(priceDiff);
		
		return boughtInfo;
	}

}
