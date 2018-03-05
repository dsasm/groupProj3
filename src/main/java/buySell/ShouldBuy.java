package buySell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import Holders.CoinsToBuyHolder;
import Holders.ThreadCoinHolders;
import model.State;

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
		while(true) {
			while(!setThreadIndex) {
				logger.info("+++++ Buyer STUCK FOR SOME REASON ");
				
			}
			logger.info("+++++ Buyer Has Index Set "+threadIndex);
			while (ThreadCoinHolders.isNull(threadIndex)) {
				
			}
			logger.info("Buyer coinHolder no longer null");
			boolean consideringBuy = true;
			while(consideringBuy) {
				
				//While its coin is not in a state where you are only looking at it
				while(!ThreadCoinHolders.getState(threadIndex).equals(State.LOOKING_AT)) {
					
				}
				
				//get that coin and check if you should buy it
				String consideringSymbol = ThreadCoinHolders.getSymbol(threadIndex);
			
				boolean buyNow = buyConsiderer.shouldBuyNow(consideringSymbol);
				if (buyNow) {
					
					
					
					//If you should buy it, move it to the next state within its holder
					ThreadCoinHolders.shouldBuy(threadIndex);
					logger.info("Wants to buy "+consideringSymbol+" from thread "+threadIndex);
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
	
	
	
	
}