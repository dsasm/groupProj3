package buySell;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.BoughtInfo;

public class FakeEthereumHolder {
	
	private static Logger logger = LoggerFactory.getLogger(FakeEthereumHolder.class);
	
	private static Float numberOfEthereum = 100f;
	
	/**
	 * given a boughtInfo buys, works out the proportional quantity and returns the new BoughtInfo 
	 * @param boughtInfo	The currentBoughtInfo 
	 * @return				The quantity
	 */
	public static BoughtInfo newBuy(BoughtInfo boughtInfo) {
		
		synchronized (numberOfEthereum) {
			Integer quantity = (int) Math.floor(numberOfEthereum / 2 / boughtInfo.getBoughtAt()); 
			boughtInfo.setQuantity(quantity);
			logger.info("Buying "+boughtInfo.getSymbol()+" for "+boughtInfo.getBoughtAt()+ " Quantity "+boughtInfo.getQuantity());
			numberOfEthereum = numberOfEthereum - boughtInfo.getBoughtAt() * boughtInfo.getQuantity();
			return boughtInfo;
		}
	}
	
	public static void newSell(BoughtInfo boughtInfo, Float soldAt) {
		
		synchronized (numberOfEthereum) {
			
			logger.info("Selling "+boughtInfo.getSymbol()+" for "+soldAt+ " Quantity "+boughtInfo.getQuantity()+" current Eth: "+numberOfEthereum);
			numberOfEthereum = numberOfEthereum + soldAt * boughtInfo.getQuantity();
			logger.info("total Ethereum now : "+numberOfEthereum);
			File file = new File("EthereumUpdater.txt");
			
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write("Sold "+boughtInfo.getSymbol()+" Eth now "+ numberOfEthereum);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
