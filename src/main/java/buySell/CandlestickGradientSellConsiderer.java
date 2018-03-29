package buySell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.SymbolVsMetricSortedList;
import model.BoughtInfo;
import model.GradientCandleClassifier;
import model.Metric;

@Component
public class CandlestickGradientSellConsiderer implements SellConsiderer{

}
