package fibonacci.algorithm;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeanLiu The memory cache can set cache size to store data
 */
public class MemoryCache {

	// the single instance of MemoryCache
	private static MemoryCache INSTANCE = new MemoryCache();

	// use concurrentHash map to store the cached data
	private Map<String, String> fnData = new ConcurrentHashMap<String, String>();

	/**
	 * calculate the fibonacci number
	 * 
	 * @param s
	 *            input
	 * @return the fibonacci String
	 */
	private String calculater(String s) {

		try {
			Integer n = Integer.parseInt(s);
			System.out.println("number" + n);
			// We only support the largest input 206 and refuse more big number
			// in order to avoid overflow.
			if (n >= 207 || n < 0) {
				return "Your input is out of range that we supported, our range is 0 to 206.";
			} else {
				if (fnData.get(n) != null) {
					return fnData.get(n);
				} else if (n.equals("0")) {
					return "0";
				} else if (n.equals("1")) {
					return "1";
				} else {
					BigInteger f[] = new BigInteger[n];
					f[0] = BigInteger.ZERO;
					f[1] = BigInteger.ONE;
					StringBuffer fibonaccisb = new StringBuffer();
					fibonaccisb.append(f[0] + " ");
					fibonaccisb.append(f[1] + " ");
					for (int i = 2; i < n; i++) {
						f[i] = f[i - 2].add(f[i - 1]);
						fibonaccisb.append(f[i] + " ");

					}
					// store values
					String result = fibonaccisb.toString();
					fnData.put(s, result);
					return result;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "-1";
		}
	}

	private MemoryCache() {

	}

	public static MemoryCache getInstance() {
		return INSTANCE;
	}

	/**
	 * The method is to fetch Fibonacci String
	 * 
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
