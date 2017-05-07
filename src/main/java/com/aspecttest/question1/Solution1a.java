package com.aspecttest.question1;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author duygu
 * This solution can be applicable if JDK version is 1.8 or higher
 *
 */
public class Solution1a {
	
	public static <T extends Number, R> List<R> map(Function<T, R> plusOne, List<T> list) {
        return (List<R>) list.stream().map(plusOne).collect(Collectors.toList());
    }

}
