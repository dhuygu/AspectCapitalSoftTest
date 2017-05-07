package com.aspecttest.question3;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

public class PriceHolder3Test {

	@Test
	public void test() {
		// Parallel among methods in a class
		Class[] cls = { ParallelTest.class };
		JUnitCore.runClasses(ParallelComputer.methods(), cls);
	}

	public static class ParallelTest {

		public static ScheduledExecutorService executorService;
		public static PriceHolder3 ph;

		@BeforeClass
		public static void setUp() throws Exception {
			executorService = Executors.newScheduledThreadPool(15);
			ph = new PriceHolder3();
		}

		@Test
		public void a() {
			Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(10));
			executorService.schedule(worker1, 1, TimeUnit.SECONDS);
			worker1.run();

			assertEquals(true, ph.hasPriceChanged("a"));

			ph.getPrice("a");

			try {
				ph.waitForNextPrice("a");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Test
		public void b() {
			
			Runnable worker1 = () -> ph.putPrice("x", new BigDecimal(60));
			executorService.schedule(worker1, 1, TimeUnit.SECONDS);
			worker1.run();
			
			Runnable worker2 = () -> ph.putPrice("y", new BigDecimal(50));
			executorService.schedule(worker2, 3, TimeUnit.SECONDS);
			worker2.run();
			
			Runnable worker3 = () -> ph.putPrice("z", new BigDecimal(40));
			executorService.schedule(worker3, 5, TimeUnit.SECONDS);
			worker3.run();
			
			assertEquals(new BigDecimal(40), ph.getPrice("z"));
			
			assertEquals(new BigDecimal(50), ph.getPrice("y"));
			
			Runnable worker4 = () -> ph.putPrice("k", new BigDecimal(70));
			executorService.schedule(worker4, 7, TimeUnit.SECONDS);
			worker4.run();

			Runnable worker5 = () -> ph.putPrice("a", new BigDecimal(22));
			executorService.schedule(worker5, 20, TimeUnit.SECONDS);
			worker5.run();

			assertEquals(true, ph.hasPriceChanged("a"));
		}
	}

}
