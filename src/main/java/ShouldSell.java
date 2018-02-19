import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import considerer.BoughtCoinsHolder;
import model.BoughtInfo;

@Component
public class ShouldSell {
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private SellConsiderer sellConsiderer;
	private Buyer buyer;
	private Seller seller;
	
	@Autowired
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
	
	public void execute() throws InterruptedException {
		//ThreadIndex only set once the initialization is complete
		while(!setThreadIndex) {
			
		}
		while(true) {
			
			//While theres no coin to buy do nothing
			while (CoinsToBuyHolder.indexIsNull(threadIndex)) {
				
			}
			//There is now a coin to buy, so pop it (removes from the list)
			String buyingSymbol = CoinsToBuyHolder.popMyCoin(threadIndex);
			//Buy that coin
			BoughtInfo bought = buyer.buy(buyingSymbol);
			//Put the fact it was Bought into a Bought coins holder TODO does this need to be removed ??
			BoughtCoinsHolder.putBought(threadIndex,bought);
			
			//Bought now so set the fact it should sell to false
			boolean shouldSell = false;
			
			//And while it shouldnt be sold
			while (!shouldSell) {
				
				//Update the info of the Order to whether or not it should be sold now
				bought = sellConsiderer.shouldSellNow(bought);
				
				//assign the while variable to whether or not it should be sold now
				shouldSell = bought.isShouldSell();
				
				//if not, wait a while before checking again
				if (!shouldSell) {
					Thread.sleep(30*1000);
				}
			}
			
			//Got here, so wants to be sold. So sell.
			seller.sell(bought);
		}
		
	}
}
