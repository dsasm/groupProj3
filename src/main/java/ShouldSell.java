import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShouldSell {
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private SellConsiderer sellConsiderer;
	private Buyer buyer;
	
	@Autowired
	public ShouldSell(SellConsiderer sellConsiderer
			, Buyer buyer) {
		this.sellConsiderer = sellConsiderer;
		this.buyer = buyer;
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
			while(true) {
				String buyingSymbol = CoinsToBuyHolder.popMyCoin(threadIndex);
				buyer.buy(buyingSymbol);
				boolean sellNow = sellConsiderer.shouldSellNow(buyingSymbol);
				if (sellNow) CoinsToBuyHolder.addCoinToBuy(threadIndex, buyingSymbol);
				else {
					Thread.sleep(1000);
				}
			}
		}
		
	}
}
