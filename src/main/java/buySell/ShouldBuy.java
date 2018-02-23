package buySell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import Holders.CoinsToBuyHolder;
import Holders.ThreadCoinHolders;

@Component
public class ShouldBuy implements Runnable{
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private BuyConsiderer buyConsiderer;
	private Logger logger = LoggerFactory.getLogger(ShouldBuy.class);
	
	

	public ShouldBuy(BuyConsiderer buyConsiderer) {
		this.buyConsiderer = buyConsiderer;
	}
	
	public void setThreadIndex(Integer threadIndex) {
		this.threadIndex = threadIndex;
		setThreadIndex = true;
	}

	@Override
	public void run() {
		while(!setThreadIndex) {
			logger.info("+++++ Buyer STUCK FOR SOME REASON ");
			
		}
		logger.info("+++++ Buyer Has Index Set "+threadIndex);
		while (ThreadCoinHolders.isNull(threadIndex)) {
			
		}
		logger.info("Buyer coinHolder no longer null");
		while(true) {
			String consideringSymbol = ThreadCoinHolders.getSymbol(threadIndex);
		
			boolean buyNow = buyConsiderer.shouldBuyNow(consideringSymbol);
			if (buyNow) {
				CoinsToBuyHolder.addCoinToBuy(threadIndex, consideringSymbol);
				logger.info("Wants to buy "+consideringSymbol+" from thread "+threadIndex);
				while (!CoinsToBuyHolder.indexIsNull(threadIndex)) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
	
	
	
}
