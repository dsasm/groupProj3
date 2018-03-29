package buySell;

import model.Metric;

public interface BuyConsiderer {
	
	public <T> boolean shouldBuyNow(String symbolToConsider, Metric metric);
}
