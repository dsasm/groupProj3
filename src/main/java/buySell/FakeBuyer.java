package buySell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BoughtInfo;

@Component
public class FakeBuyer implements Buyer{
	
	private RestClientInteractor clientInteractor;
	
	@Autowired
	public FakeBuyer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}

	@Override
	public BoughtInfo buy(String symbol) {
		BoughtInfo toReturn = new BoughtInfo();
		Float boughtAt = clientInteractor.getLatestPrice(symbol);
		toReturn.setBoughtAt(boughtAt);
		toReturn.setSymbol(symbol);
		toReturn.setHighestProfit(boughtAt);
		toReturn.setPassedThreshhold(false);
		toReturn.setShouldSell(false);
		return FakeEthereumHolder.newBuy(toReturn);
		
	}

}
