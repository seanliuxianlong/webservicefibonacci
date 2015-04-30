package fibonacci.algorithm;

/**
 * @author SeanLiu The class is to getFibonacci string
 * 
 */
public class CacheCalculator implements ICalculator {

	public CacheCalculator() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fibonacci.ICalculator#getFnData(int)
	 */
	@Override
	public String getFnData(String n) {

		return MemoryCache.getInstance().fetchFromCache(n);

	}
}
