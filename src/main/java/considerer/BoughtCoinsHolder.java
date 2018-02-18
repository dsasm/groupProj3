package considerer;

import model.BoughtInfo;

public class BoughtCoinsHolder {
	
	private static BoughtInfo []  currentlyBoughtCoins = new BoughtInfo[2];
	
	public static BoughtInfo getBought(Integer index) {
		synchronized (currentlyBoughtCoins) { 
			return currentlyBoughtCoins[index];
		}
	}
	
	public static void putBought(Integer index, BoughtInfo boughtInfo) {
		synchronized (currentlyBoughtCoins) { 
			currentlyBoughtCoins[index] = boughtInfo;
		}
	}
	
	public static void removeMyBought(Integer index) {
		synchronized (currentlyBoughtCoins) {
			currentlyBoughtCoins[index] = null;
		}
	}
	
	
}
