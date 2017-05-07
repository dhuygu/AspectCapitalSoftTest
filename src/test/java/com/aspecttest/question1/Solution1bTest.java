package com.aspecttest.question1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class Solution1bTest {

	private static List<Number> l;

	@BeforeClass
	public static void setUp() throws Exception {
		l = new ArrayList<Number>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4.2D);
		l.add(5.9F);
		l.add(6L);
	}

	@Test
	public void testPlusOne_float() {
		Number result = Solution1b.plusOne(4.5F);
		assertEquals(result, 5.5F);
	}

	@Test
	public void testPlusOne_double() {
		Number result = Solution1b.plusOne(3.4D);
		assertEquals(result, 4.4D);
	}

	@Test
	public void testPlusOne_long() {
		Number result = Solution1b.plusOne(21L);
		assertEquals(result, 22L);
	}

	@Test
	public void testPlusOne_int() {
		Number result = Solution1b.plusOne(1);
		assertEquals(result, 2);
	}

	@Test
	public void testPlusOne_null() {
		Number result = Solution1b.plusOne(null);
		assertEquals(result, null);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testMap_noSuchMethod() throws Exception {
		Solution1b.map("test", l);
	}

	@Test
	public void testMap_emptyList() throws Exception {
		List<Number> emptyList = new ArrayList<Number>();
		List<Number> result = Solution1b.map("test", emptyList);
		assertThat(result.size(), equalTo(0));
	}
	

	@Test
	public void testMap_nullList() throws Exception {
		List<Number> result = Solution1b.map("test", null);
		assertThat(result.size(), equalTo(0));
	}
	
	
	@Test
	public void testMap_originalListRemainsSame() throws Exception {
		String before = l.toString();
		Solution1b.map("plusOne", l);
		String after = l.toString();
		assertThat(before, equalTo(after));
	}
	
	@Test
	public void testMap_numberOfElementsAfterRunning() throws Exception {
		List<Number> result = Solution1b.map("plusOne", l);
		assertThat(result.size(), equalTo(result.size()));
	}
	
	@Test
	public void testMap_increasedOne() throws Exception {
		List<Number> result = Solution1b.map("plusOne", l);
		Integer n = (Integer) l.get(0) + 1;
		Integer calculatedN = (Integer) result.get(0); //2
		assertEquals(n, calculatedN);
	}

}
