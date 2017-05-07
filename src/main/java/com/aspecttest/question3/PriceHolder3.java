package com.aspecttest.question3;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aspecttest.model.Entity;

/**
 * @author duygu PriceHolder class is final in the document. I didnt want to
 *         change it so I didnt subclassed the PriceHolder3 from PriceHolder2
 *         class. I didnt used it as a field in this class not to increase the
 *         number of puclic methods of the Priceholder class. It is coded like
 *         an independent code.
 *
 */

public final class PriceHolder3 {

	private static final Logger logger = LogManager.getLogger(PriceHolder3.class.getName());

	/**
	 * null values and keys are not allowed allows concurrent modification of
	 * the Map from several threads without the need to block them
	 * ConcurrentHashMap locks only the bucket
	 */
	private ConcurrentHashMap<String, Entity> priceMap;

	/**
	 * it is impossible to be sure that any thread will try to put the a new
	 * value for the same entity into the map before the first threads finished
	 * its work. Therefore it is better to lock all putPrice method
	 */
	private final Lock newEntityLock = new ReentrantLock();
	private final Lock updateEntityLock = new ReentrantLock();

	public PriceHolder3() {
		this.priceMap = new ConcurrentHashMap<String, Entity>();
	}

	/** Called when a price ‘p’ is received for an entity ‘e’ */
	public void putPrice(String e, BigDecimal p) {

		if (priceMap.get(e) != null) {
			updatePrice(e, p);
		} else {
			createNew(e, p);
		}

	}

	private void createNew(String e, BigDecimal p) {
		newEntityLock.lock();
		if (priceMap.get(e) == null) {
			Entity entity = new Entity(e, p);
			priceMap.put(e, entity);
			newEntityLock.unlock();
			logger.info("New entity creation finished: {} - {} *** {}", e, p, priceMap.toString());
		} else {
			newEntityLock.unlock();
			logger.info("It is thought that it is new creation but now it is going to be updated:{} - {} *** {}", e, p,
					priceMap.toString());
			updatePrice(e, p);
		}
	}

	private void updatePrice(String e, BigDecimal p) {
		Entity entity = null;
		try {
			updateEntityLock.lock();
			entity = priceMap.get(e);
			entity.setPrice(p);
			entity.setPriceChanged(true);
			priceMap.put(e, entity);
			logger.info("Entity update ended:{} - {} *** {}", e, p, priceMap.toString());
		} finally {
			updateEntityLock.unlock();
			entity.notifyAll();
		}
		

	}

	/** Called to get the latest price for entity ‘e’ */
	public BigDecimal getPrice(String e) {
		BigDecimal result = null;
		if (priceMap.get(e) != null) {
			Entity entity = priceMap.get(e);
			result = entity.getPrice();
			if (entity.isPriceChanged()) {
				setAsRead(entity);
			}
		}

		logger.info("getPrice is called for:{} *** {} ", e, priceMap.toString());
		return result;

	}

	private void setAsRead(Entity entity) {

		/**
		 * the situation may change until entering this block so double check is
		 * done
		 **/
		synchronized (entity) {
			if (entity.isPriceChanged()) {
				// because the new value is read.
				entity.setPriceChanged(false);
			}
		}

	}

	/**
	 * Called to determine if the price for entity ‘e’ has changed since the
	 * last call to getPrice(e).
	 */
	public boolean hasPriceChanged(String e) {
		boolean result = false;

		if (priceMap.get(e) != null) {
			Entity entity = priceMap.get(e);
			result = entity.isPriceChanged();
		}

		return result;
	}

	/**
	 * Returns the next price for entity ‘e’. If the price has changed since the
	 * last call to getPrice() or waitForNextPrice(), it returns immediately
	 * that price. Otherwise it blocks until the next price change for entity
	 * ‘e’.
	 * 
	 * @throws ExecutionException
	 */
	public BigDecimal waitForNextPrice(String e) throws InterruptedException, ExecutionException {
		BigDecimal result = null;
		Entity entity = priceMap.get(e);
		if (entity != null) {
			if (!hasPriceChanged(e)) {
				logger.info("Going to wait until the entity is updated: {} ", entity);
				NewPriceWaiter waiter = new NewPriceWaiter(entity);
				waiter.run();
				logger.info("Waitig process finished. Entity is updated: {}", entity);
			}
			result = getPrice(e);
		}

		return result;
	}

}
