package fibonacci.resource;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * Producer
 */
class Producer extends Thread {
	private BlockingQueue<BigInteger> queue; // put data into queue
	private Fibonacci fibonacci; // godown

	Producer(BlockingQueue<BigInteger> queue, Fibonacci fibonacci) {
		this.queue = queue;
		this.fibonacci = fibonacci;
	}

	public void run() {
		// produce product
		fibonacci.produce(queue);
	}
}
