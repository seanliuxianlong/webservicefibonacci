package fibonacci.resource;

import java.math.BigInteger;
import java.text.MessageFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fibonacci.algorithm.CacheCalculator;
import fibonacci.algorithm.ICalculator;

@Path("/query")
public class Fibonacci {

	/** this can be injected */
	private ICalculator calculator = new CacheCalculator();

	private static final String ILLEGAL_INPUT = "{0} is illegal, the input can't be negative";
	/**
	 * Access path£º{host}:{port}/fibonacci/query/x
	 * 
	 * @Title: getFnData
	 * @param n
	 * @return int <span style="font-family: sans-serif;">(n beyond
	 *         fnData.size() and n<0 return -1)</span>
	 * @throws
	 */
	@GET
	@Path("key/{key}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFnData(@PathParam("key") String key) {
		BigInteger bin = new BigInteger(key);
		if(bin.compareTo(BigInteger.ZERO) < 0){
			String message = MessageFormat.format(ILLEGAL_INPUT, key);
			return message;
		}
		
		StringBuilder sb = new StringBuilder();
 		for (BigInteger i = BigInteger.ZERO; i.compareTo(bin) == -1 ; i = i.add(BigInteger.ONE)) {
			sb.append(calculator.getFnData(i.toString()));
			sb.append(" ");
		}
		return sb.toString();
	}

}
