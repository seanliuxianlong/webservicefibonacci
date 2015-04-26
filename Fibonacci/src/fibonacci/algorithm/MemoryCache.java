package fibonacci.algorithm;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeanLiu
 * The memory cache can set cache size to store data
 */
public class MemoryCache {

	private static MemoryCache INSTANCE = new MemoryCache();

	private Map<String, String> fnData = new ConcurrentHashMap<String, String>();

	/** this can be configured */
	private final BigInteger maxSize = new BigInteger("5");

	
	private void setFnData() {
		for (BigInteger index = BigInteger.ZERO; index.compareTo(maxSize) < 0; index = index
				.add(BigInteger.ONE)) {
			fnData.put(index.toString(), calculater(index.toString()));
		}
	}

	private String calculater(String n) {
		if (fnData.get(n) != null) {
			return fnData.get(n);
		} else if (n.equals("0")) {
			return "0";
		} else if (n.equals("1")) {
			return "1";
		} else {
			BigInteger tmp1 = new BigInteger(n).subtract(BigInteger.ONE);
			BigInteger tmp2 = tmp1.subtract(BigInteger.ONE);
			BigInteger result = new BigInteger(calculater(tmp1.toString()))
					.add(new BigInteger(calculater(tmp2.toString())));
			// store others value
			fnData.put(n, result.toString());
			return result.toString();
		}
	}

	private MemoryCache() {
		setFnData();
	}

	public static MemoryCache getInstance() {
		return INSTANCE;
	}
    /**
     * The method is to fetch Fibonacci String
     * @param n
     * @return Fibonacci String
     */
	public String fetchFromCache(String n) {
		if (fnData.get(n) == null) {
			fnData.put(n, calculater(n));
		}
		return fnData.get(n);
	}
}
