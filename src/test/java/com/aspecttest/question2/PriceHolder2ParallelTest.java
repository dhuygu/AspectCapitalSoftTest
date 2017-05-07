package com.aspecttest.question2;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

import com.aspecttest.question3.PriceHolder3;

public class PriceHolder2ParallelTest {
	
	@Test
	public void test() {
		// Parallel among methods in a class
		Class[] cls = { ParallelTest.class };
		JUnitCore.runClasses(ParallelComputer.methods(), cls);
	}

	public static class ParallelTest {

		public static ScheduledExecutorService executorService;
		public static PriceHolder2 ph;

		@BeforeClass
		public static void setUp() throws Exception {
			executorService = Executors.newScheduledThreadPool(10);
			ph = new PriceHolder2();
		}

		@Test
		public void a() {
			Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(10));
			executorService.schedule(worker1, 1, TimeUnit.SECONDS);
			worker1.run();

			assertEquals(true, ph.hasPriceChanged("a"));

			ph.getPrice("a");

			Runnable worker2 = () -> ph.putPrice("x", new BigDecimal(90));
			executorService.schedule(worker2, 8, TimeUnit.SECONDS);
			worker2.run();
			
			assertEquals(new BigDecimal(90), ph.getPrice("a"));
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
			executorService.schedule(worker3, 4, TimeUnit.SECONDS);
			worker3.run();
			
			assertEquals(new BigDecimal(40), ph.getPrice("z"));
			
			assertEquals(new BigDecimal(50), ph.getPrice("y"));
			
			Runnable worker4 = () -> ph.putPrice("y", new BigDecimal(70));
			executorService.schedule(worker4, 5, TimeUnit.SECONDS);
			worker4.run();

			Runnable worker5 = () -> ph.putPrice("a", new BigDecimal(22));
			executorService.schedule(worker5, 6, TimeUnit.SECONDS);
			worker5.run();
			
			assertEquals(new BigDecimal(22), ph.getPrice("a"));

		}
	}

}
