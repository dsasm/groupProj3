import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import considerer.BuyConsiderer;

@Component
public class ShouldBuy {
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private BuyConsiderer buyConsiderer;
	
	
	@Autowired
	public ShouldBuy(BuyConsiderer buyConsiderer) {
		this.buyConsiderer = buyConsiderer;
	}
	
	public void setThreadIndex(Integer threadIndex) {
		this.threadIndex = threadIndex;
		setThreadIndex = true;
	}

	public void execute() throws InterruptedException {
		while(!setThreadIndex) {
			
		}
		while (ThreadCoinHolders.isNull(threadIndex)) {
			
		}
		while(true) {
			String consideringSymbol = ThreadCoinHolders.getSymbol(threadIndex);
		
			boolean buyNow = buyConsiderer.shouldBuyNow(consideringSymbol);
			if (buyNow) CoinsToBuyHolder.addCoinToBuy(threadIndex, consideringSymbol);
			else {
				Thread.sleep(1000);
			}
		}
		
	}
	
	
	
	
}
