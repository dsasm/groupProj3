package Holders;

public class CoinsToBuyHolder {
	
	private static String [] coinsToBuy = new String[2];
	
	public static void addCoinToBuy(int index, String symbol) {
		synchronized(coinsToBuy) {
			coinsToBuy[index] = symbol;
		}
	}
	public static String popMyCoin(int index) {
		String toReturn;
		synchronized(coinsToBuy) {
			toReturn = coinsToBuy[index];
			coinsToBuy[index] = null;
		}
		return toReturn;
	}
	
	public static boolean indexIsNull(int index) {
		
		synchronized(coinsToBuy) {
			return coinsToBuy[index] == null;
		}
	}
}
