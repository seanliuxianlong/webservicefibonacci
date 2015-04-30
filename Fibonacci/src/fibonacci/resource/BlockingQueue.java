package fibonacci.resource;

public class BlockingQueue {
	int item;
	boolean available = false;

	public synchronized void put(int value) {
		while (available == true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		item = value;
		available = true;
		notifyAll();
	}

	/**
	 * get value from queue
	 * 
	 * @return
	 */
	public synchronized int get() {
		while (available == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		available = false;
		notifyAll();
		return item;
	}
}