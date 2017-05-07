package com.aspecttest.question3;

import java.math.BigDecimal;

public class Runner {

	public static void main(String[] args) {
		PriceHolder3 ph = new PriceHolder3();

		ph.putPrice("a", new BigDecimal(10));
		System.out.println(ph.getPrice("a"));
		ph.putPrice("a", new BigDecimal(12));
		System.out.println(ph.hasPriceChanged("a"));
		ph.putPrice("b", new BigDecimal(2));
		ph.putPrice("a", new BigDecimal(11));
		System.out.println(ph.getPrice("a"));
		System.out.println(ph.getPrice("a"));
		System.out.println(ph.getPrice("b"));
	}

}
