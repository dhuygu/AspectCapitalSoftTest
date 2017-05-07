package com.aspecttest.question1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Solution1aTest {

	@Test
	public void testMap_emptyList() {
		List<Integer> emptyList = new ArrayList<Integer>();
		List<Integer> result = Solution1a.map(n -> n + 1, emptyList);
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void testMap_increasedOne_integer() throws Exception {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);

		List<Integer> result = Solution1a.map(n -> n + 1, l);
		assertThat(result, is(Arrays.asList(2, 3, 4)));
	}


}
