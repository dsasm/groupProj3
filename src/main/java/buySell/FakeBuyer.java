package buySell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BoughtInfo;

@Component
public class FakeBuyer implements Buyer{
	
	private RestClientInteractor restClientInteractor;
	
	@Autowired
	public FakeBuyer(RestClientInteractor restClientInteractor) {
		this.restClientInteractor = restClientInteractor;
	}

	@Override
	public BoughtInfo buy(String symbol) {
		BoughtInfo toReturn = new BoughtInfo();
		Double boughtAt = restClientInteractor.getLatestPrice(symbol);
		toReturn.setBoughtAt(boughtAt);
		toReturn.setSymbol(symbol);
		toReturn.setHighestProfit(boughtAt);
		toReturn.setPassedThreshhold(false);
		toReturn.setShouldSell(false);
		toReturn.setTimeBoughtAt(System.currentTimeMillis());
		return FakeEthereumHolder.newBuy(toReturn);
		
	}

}
