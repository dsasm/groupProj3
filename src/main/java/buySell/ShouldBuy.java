package buySell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Holders.CoinsToBuyHolder;
import Holders.ThreadCoinHolders;

@Component
public class ShouldBuy {
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private BuyConsiderer buyConsiderer;
	private Logger logger = LoggerFactory.getLogger(ShouldBuy.class);
	
	
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
			if (buyNow) {
				CoinsToBuyHolder.addCoinToBuy(threadIndex, consideringSymbol);
				logger.info("Wants to buy "+consideringSymbol+" from thread "+threadIndex);
			}
			else {
				Thread.sleep(1000);
			}
		}
		
	}
	
	
	
	
}
