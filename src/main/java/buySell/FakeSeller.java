
package buySell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BoughtInfo;

@Component
public class FakeSeller implements Seller{
	
	private RestClientInteractor restClientInteractor;
	
	@Autowired
	public FakeSeller(RestClientInteractor restClientInteractor) {
		this.restClientInteractor = restClientInteractor;
	}
	
	@Override
	public void sell(BoughtInfo boughtInfo) {
		
		FakeEthereumHolder.newSell(boughtInfo, restClientInteractor.getLatestPrice(boughtInfo.getSymbol()));
	}

}
