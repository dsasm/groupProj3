package buySell;

public interface BuyConsiderer {
	
	public boolean shouldBuyNow(String symbolToConsider);
	
	public boolean shouldBuyNow(int index);
}
