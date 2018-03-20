package buySell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import Holders.CoinsToBuyHolder;
import ThreadCoinHolders.ThreadCoinHolders;
import considerer.BoughtCoinsHolder;
import model.BoughtInfo;
import model.State;

@Component
public class ShouldSell implements Runnable{
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private SellConsiderer sellConsiderer;
	private Buyer buyer;
	private Seller seller;
	
	private Logger logger = LoggerFactory.getLogger(ShouldSell.class);
	
	public ShouldSell(SellConsiderer sellConsiderer
			, Buyer buyer
			, Seller seller) {
		this.sellConsiderer = sellConsiderer;
		this.buyer = buyer;
		this.seller = seller;
	}
	
	public void setThreadIndex(Integer threadIndex) {
		this.threadIndex = threadIndex;
		setThreadIndex = true;
	}

	@Override
	public void run() {
		//ThreadIndex only set once the initialization is complete
		while(!setThreadIndex) {
			
		}
		//While theres no coin to buy do nothing
		while (ThreadCoinHolders.isNull(threadIndex)) {
			
		}
		logger.info("Seller starting up with index "+threadIndex);
		while(true) {
			
			while(!ThreadCoinHolders.getState(threadIndex).equals(State.SHOULD_BUY)) {
				
			}
			
			//There is now a coin to buy, so pop it (removes from the list)
			String buyingSymbol = ThreadCoinHolders.getSymbol(threadIndex);
			//Buy that coin
			BoughtInfo bought = buyer.buy(buyingSymbol);
			//Put the fact it was Bought into a Bought coins holder TODO does this need to be removed ??
			ThreadCoinHolders.buy(threadIndex);
			//BoughtCoinsHolder.putBought(threadIndex, bought);
			logger.info("Bought "+buyingSymbol+" within thread "+threadIndex);
			//Bought now so set the fact it should sell to false
			boolean shouldSell = false;
			
			//And while it shouldnt be sold
			while (!shouldSell) {
				
				//Update the info of the Order to whether or not it should be sold now
				bought = sellConsiderer.shouldSellNow(bought, threadIndex);
				
				//assign the while variable to whether or not it should be sold now
				shouldSell = bought.isShouldSell();
				
				//if not, wait a while before checking again
				if (!shouldSell) {
					try {
						Thread.sleep(10*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			//Got here, so wants to be sold. So sell.
			ThreadCoinHolders.shouldSell(threadIndex);
			seller.sell(bought);
			ThreadCoinHolders.sell(threadIndex);
			BoughtCoinsHolder.removeMyBought(threadIndex);
			logger.info("Sold "+buyingSymbol+" within thread "+threadIndex);
		}

	}
}
