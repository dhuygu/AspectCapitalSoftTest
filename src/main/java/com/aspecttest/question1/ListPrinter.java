package com.aspecttest.question1;

import java.util.List;

public class ListPrinter {

	public static <T extends Number> void printListElements(List<T> list) {
		for (T element : list) {
			System.out.print(element);
			System.out.print(" ");
		}
		System.out.println();
	}

}
