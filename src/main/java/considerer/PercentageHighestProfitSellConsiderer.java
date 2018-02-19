package considerer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import model.BoughtInfo;

@Component
public class PercentageHighestProfitSellConsiderer implements SellConsiderer{
	
	private RestClientInteractor clientInteractor;
	private Integer holdingIndex;
	
	@Autowired
	public PercentageHighestProfitSellConsiderer(RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
	}
	
	public void SetThreadIndex(Integer index) {
		this.holdingIndex = index;
	}
	
	@Override
	public boolean shouldSellNow(String symbolToConsider) {
		
				
		//work out the difference between the original price and the price now
		Float priceDiff = clientInteractor.getLatestPrice(symbolToConsider);
		BoughtInfo currentInfo = BoughtCoinsHolder.getBought(holdingIndex);
		//highest profit so far is stored as the difference between the price then and the original price
		if (currentInfo.getHighestProfit() / currentInfo.getBoughtAt() > 1.002) currentInfo.setPassedThreshhold(true);; 

		if (priceDiff > currentInfo.getHighestProfit()) currentInfo.setHighestProfit(priceDiff);
		
		//if the difference is bigger than the % decreace allowed for a coin then SELL SELL SELL
		if(currentInfo.isPassedThreshhold() && (currentInfo.getHighestProfit() / currentInfo.getBoughtAt() < 1.002)) return true;

		else if ((priceDiff-currentInfo.getBoughtAt()) /currentInfo.getHighestProfitDiff() < 0.97) return true;
		else if(priceDiff < (currentInfo.getBoughtAt()*0.98)) {
			return true;
		}
		
		else if (priceDiff < ((currentInfo.getHighestProfitDiff()*0.95)+currentInfo.getBoughtAt() )
				&& ((priceDiff-currentInfo.getBoughtAt()) /(currentInfo.getHighestProfit()) > 0.985) ) {
		    //TODO implement 	getConfidenceInMove();
			return gainConfidenceInSell(priceDiff, currentInfo.getSymbol(), 0);
		}
		//if the new price is bigger than the highestProfitSoFar then replace the highestProfit so far
				
				
			
		
		return false;
	}
	
	private boolean gainConfidenceInSell(Float basedOn, String symbol, int count) {
		//wait a half minute then check if it has increased, then buy
		//wait a half minute then check if it has increased, then buy
		count +=1;
		try {
			Thread.sleep(1000*10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Float newPrice = clientInteractor.getLatestPrice(symbol);
		if (newPrice < basedOn) {
			return true;
		}
		else if (newPrice == basedOn) return gainConfidenceInSell(basedOn, symbol, count);
		else if(count >3) {
			count=0;
			return true;
		}
		else{
			return false;}
	}
	
	
	
}