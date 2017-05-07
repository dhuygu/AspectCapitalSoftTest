package com.aspecttest.question1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.aspecttest.question1.Solution1b;
import com.aspecttest.question1.Solution1a;

public class Runner {

	public static void main(String[] args) throws Exception {
		// Sample how to call Solution1
		List<Integer> l1 = Arrays.asList(1, 2, 3);
		List<Integer> result = Solution1a.map(n -> n + 1, l1);
		ListPrinter.printListElements(result);

		
		//Sample how to call Solution2
		List<Number> l = new ArrayList<Number>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4.2D);
		l.add(5.9F);
		l.add(6L);
		List<Number> resultList = Solution1b.map("plusOne", l);
		// original list
		ListPrinter.printListElements(l);
		// modified list
		ListPrinter.printListElements(resultList);

	}

}
