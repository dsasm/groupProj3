package MetricApplier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface MetricApplier extends Runnable{
	
	public static Map<String, Float> symbolsVsMetric = new HashMap<String, Float>();
}
