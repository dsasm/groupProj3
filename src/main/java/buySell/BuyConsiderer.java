package buySell;

import model.Metric;

public interface BuyConsiderer {
	
	public boolean shouldBuyNow(String symbolToConsider, Metric metric);
}
