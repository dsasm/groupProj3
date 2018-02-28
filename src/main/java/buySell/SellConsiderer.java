package buySell;
import model.BoughtInfo;

public interface SellConsiderer {
	
	/**
	 * A method to determine whether the current boughtInfo should be sold
	 * @param boughtInfo		The boughtInfo to consider
	 * @return					The new BoughtInfo with a shouldSell field indicating current intentions
	 */
	public BoughtInfo shouldSellNow(BoughtInfo boughtInfo);
	
	public void setThreadIndex(Integer threadIndex);
}
