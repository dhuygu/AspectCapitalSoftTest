package com.aspecttest.question3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aspecttest.model.Entity;

/**
 * @author duygu
 *
 */
public class NewPriceWaiter implements Runnable {
	private static final Logger logger = LogManager.getLogger(NewPriceWaiter.class.getName());

	private Entity entity;

	public NewPriceWaiter(Entity entity) {
		this.entity = entity;
	}

	public void run() {

		try {
			synchronized (entity) {
				Thread.sleep(200);
				while (!entity.isPriceChanged()) {
					entity.wait();
					Thread.sleep(100);
				}
			}
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.info("Exception occurred: " + entity.getId());
		}
	}

}
