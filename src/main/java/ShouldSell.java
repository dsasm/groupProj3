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
		while(!setThreadIndex) {
			
		}
		while(true) {
			while (CoinsToBuyHolder.indexIsNull(threadIndex)) {
				
			}
			String buyingSymbol = CoinsToBuyHolder.popMyCoin(threadIndex);
			BoughtInfo bought = buyer.buy(buyingSymbol);
			BoughtCoinsHolder.putBought(threadIndex,bought);
			//TODO sort out this updating the boughtInfo with new relational information when considering if to sell
			boolean shouldSell = false;
			
			while (!shouldSell) {
				bought = sellConsiderer.shouldSellNow(bought);
				shouldSell = bought.isShouldSell();
				if (!shouldSell) {
					Thread.sleep(30*1000);
				}
			}
			 
			seller.sell(bought);
		}
		
	}
}
