import org.springframework.beans.factory.annotation.Autowired;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BoughtInfo;

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
		return FakeEthereumHolder.newBuy(toReturn);
		
	}

}
