package fibonacci.resource;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {
	private BlockingQueue<BigInteger> queue; // put data into queue
	private Fibonacci fibonacci; // godown

	Consumer(BlockingQueue queue, Fibonacci fibonacci) {
		this.queue = queue;
		this.fibonacci = fibonacci;
	}

	public void run() {
		System.out.println("Producer queue size"+queue.size());
		fibonacci.consume(queue);
	}
}