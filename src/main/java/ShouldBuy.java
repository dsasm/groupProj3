import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import buyApplier.BuyApplier;

@Component
public class ShouldBuy {
	
	private boolean setThreadIndex = false;
	private Integer threadIndex;
	
	private BuyApplier buyApplier;
	
	
	@Autowired
	public ShouldBuy(BuyApplier buyApplier) {
		this.buyApplier = buyApplier;
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
		
			boolean buyNow = buyApplier.shouldBuyNow(consideringSymbol);
			if (buyNow) CoinsToBuyHolder.addCoinToBuy(threadIndex, consideringSymbol);
			else {
				Thread.sleep(1000);
			}
		}
		
	}
	
	
	
	
}
