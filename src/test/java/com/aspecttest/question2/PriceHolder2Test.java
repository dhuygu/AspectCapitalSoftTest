package com.aspecttest.question2;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class PriceHolder2Test {

	// that can schedule commands to run after a given delay
	private ScheduledExecutorService executorService;
	private PriceHolder2 ph;

	@Before
	public void setUp() throws Exception {
		executorService = Executors.newScheduledThreadPool(10);
		ph = new PriceHolder2();
	}

	@Test
	public void test_putPrice1() {
		ph.putPrice("a", new BigDecimal(10));
		assertEquals(ph.getPrice("a"), new BigDecimal(10));
	}

	@Test
	public void test_putPrice2() {
		ph.putPrice("a", null);
		assertEquals(ph.getPrice("a"), null);
	}

	@Test
	public void test_getPrice1() {
		BigDecimal result = ph.getPrice("z");
		assertEquals(result, null);
	}

	@Test
	public void test_hasPriceChanged1() {
		ph.putPrice("a", new BigDecimal(10));

		assertEquals(true, ph.hasPriceChanged("a"));

		ph.getPrice("a");

		assertEquals(false, ph.hasPriceChanged("a"));

		ph.putPrice("a", new BigDecimal(10));

		assertEquals(true, ph.hasPriceChanged("a"));

	}

	@Test
	public void test_hasPriceChanged2() {
		Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(10));
		executorService.schedule(worker1, 1, TimeUnit.SECONDS);
		worker1.run();

		Runnable worker2 = () -> ph.putPrice("a", new BigDecimal(12));
		executorService.schedule(worker2, 1, TimeUnit.SECONDS);
		worker2.run();

		assertEquals(true, ph.hasPriceChanged("a"));
	}

	@Test
	public void test_multithreadedPutGet1() {
		Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(
				10)); /**
						 * this is the equivalent of the lambda expression new
						 * Runnable(){
						 * 
						 * @Override public void run(){ ph.putPrice("a", new
						 *           BigDecimal(10)); } };
						 * 
						 **/
		executorService.schedule(worker1, 1, TimeUnit.SECONDS);
		worker1.run();

		Runnable worker2 = () -> ph.putPrice("a", new BigDecimal(12));
		executorService.schedule(worker2, 2, TimeUnit.SECONDS);
		worker2.run();

		assertEquals(new BigDecimal(12), ph.getPrice("a"));
	}

	@Test
	public void test_multithreadedPutGet2() {
		Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(10));

		executorService.schedule(worker1, 1, TimeUnit.SECONDS);
		worker1.run();

		Runnable worker2 = () -> ph.putPrice("a", new BigDecimal(12));
		executorService.schedule(worker2, 2, TimeUnit.SECONDS);
		worker2.run();

		Runnable worker3 = () -> ph.putPrice("a", new BigDecimal(2));
		executorService.schedule(worker3, 3, TimeUnit.SECONDS);
		worker3.run();

		assertEquals(new BigDecimal(2), ph.getPrice("a"));
	}

	@Test
	public void test_multithreadedPutGet3() {
		Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(10));
		executorService.schedule(worker1, 1, TimeUnit.SECONDS);
		worker1.run();

		Runnable worker2 = () -> ph.putPrice("a", new BigDecimal(12));
		executorService.schedule(worker2, 2, TimeUnit.SECONDS);
		worker2.run();

		Runnable worker3 = () -> ph.putPrice("a", new BigDecimal(2));
		executorService.schedule(worker3, 3, TimeUnit.SECONDS);
		worker3.run();

		Runnable worker4 = () -> ph.putPrice("b", new BigDecimal(20));
		executorService.schedule(worker4, 1, TimeUnit.SECONDS);
		worker4.run();

		Runnable worker5 = () -> ph.putPrice("b", new BigDecimal(21));
		executorService.schedule(worker5, 2, TimeUnit.SECONDS);
		worker5.run();

		assertEquals(new BigDecimal(2), ph.getPrice("a"));

		assertEquals(new BigDecimal(21), ph.getPrice("b"));

	}

	@Test
	public void test_multithreadedPutGet4() {

		Runnable worker1 = () -> ph.putPrice("a", new BigDecimal(10));
		executorService.schedule(worker1, 5, TimeUnit.SECONDS);
		worker1.run();


		Runnable worker2 = () -> ph.putPrice("a", new BigDecimal(12));
		executorService.schedule(worker2, 5, TimeUnit.SECONDS);
		worker2.run();

		Runnable worker3 = () -> ph.putPrice("a", new BigDecimal(2));
		executorService.schedule(worker3, 5, TimeUnit.SECONDS);
		worker3.run();

		Runnable worker4 = () -> ph.putPrice("a", new BigDecimal(3));
		executorService.schedule(worker4, 5, TimeUnit.SECONDS);
		worker4.run();

		assertEquals(true, ph.hasPriceChanged("a"));

		Runnable worker5 = () -> ph.putPrice("a", new BigDecimal(16));
		executorService.schedule(worker5, 5, TimeUnit.SECONDS);
		worker5.run();

		Runnable worker6 = () -> ph.putPrice("a", new BigDecimal(25));
		executorService.schedule(worker6, 5, TimeUnit.SECONDS);
		worker6.run();

		Runnable worker7 = () -> ph.putPrice("b", new BigDecimal(20));
		executorService.schedule(worker7, 5, TimeUnit.SECONDS);
		worker7.run();

		Runnable worker8 = () -> ph.putPrice("b", new BigDecimal(21));
		executorService.schedule(worker8, 5, TimeUnit.SECONDS);
		worker8.run();

	}

}
