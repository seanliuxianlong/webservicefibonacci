package fibonacci.resource;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
    private BigInteger bin =BigInteger.ZERO;
	private static final String ILLEGAL_INPUT = "{0} is illegal, the input can't be negative";

	/**
	 * Access path£º{host}:{port}/fibonacci/query/x
	 * 
	 * @Title: getFnData
	 * @param n
	 * @return int <span style="font-family: sans-serif;">(n beyond
	 *         fnData.size() and n<0 return error message)</span>
	 * @throws
	 */
	@GET
	@Path("key/{key}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFnData(@PathParam("key") String key) {
		BigInteger bin = new BigInteger(key);
		this.bin =bin;
		if (bin.compareTo(BigInteger.ZERO) < 0) {
			String message = MessageFormat.format(ILLEGAL_INPUT, key);
			return message;
		}
		return calculator.getFnData(key);
	}
	
	/** 
     * produce  
     * 
     * @param queue 
     */ 
    public synchronized void produce(BlockingQueue<BigInteger> queue) { 
        //test whether need to produce
        while (queue.remainingCapacity()  == 0) { 
            System.out.println("queue is full, stop produce!"); 
            try { 
                //current produce thread wait
                wait(); 
            } 
            catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
        //can start to produce , put number into queue 
        try{
        	System.out.println("queue put"+this.bin.toString());
        	System.out.println("Producer queue size"+queue.size());
            queue.put(this.bin);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //notify all monitor threads
        notifyAll(); 
    } 

    /** 
     * Consumer 
     * 
     * @param queue 
     */ 
    public synchronized void consume(BlockingQueue<BigInteger> queue) { 
            //whether whether can start to consume
            while (queue.isEmpty()) { 
                    try { 
                            //current thread wait
                            wait(); 
                    } catch (InterruptedException e) { 
                            e.printStackTrace(); 
                    } 
            } 
            //can consumer
            queue.add(this.bin); 
            System.out.println("Consume queue size"+queue.size()); 
            //notify all monitor threads
            notifyAll(); 
    } 

	public static void main(String[] args) {
		Fibonacci fbi = new Fibonacci();
		fbi.getFnData("206");
		// the queue number is configurable
		BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(10);
		Producer producer = new Producer(queue,fbi);
        Consumer consumer = new Consumer(queue,fbi);
        //starting producer to produce messages in queue
        new Thread(producer).start();
        //starting consumer to consume messages from queue
        new Thread(consumer).start();
        System.out.println("Producer and Consumer has been started");
		System.out.println(fbi.getFnData("206"));
		System.out.println(fbi.getFnData("200"));
		System.out.println(fbi.getFnData("5"));
		System.out.println(fbi.getFnData("207"));
		System.out.println(fbi.getFnData("0"));
		System.out.println(fbi.getFnData("-1"));
		System.out.println(fbi.getFnData("p1"));
	}
}
