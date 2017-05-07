package com.aspecttest.question1;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duygu
 *This solution is an altervative to Solution1 if JDK is not 1.8
 *
 */
public class Solution1b {

	/**
	 * 
	 * Number is used because it is the super class of all numeric types. It is
	 * assumed that parameter function name is given as String
	 *
	 * @param f
	 * @param l
	 * @return
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public static <T extends Number> List<T> map(String f, final List<T> l) throws Exception {
		List<T> newList = new ArrayList<T>();
		try {
			// validate list param
			if (l != null && l.size() > 0) {

				// validate function name param
				if (f != null && f.length() > 0) {
					Method method = null;

					method = Solution1b.class.getMethod(f, Number.class);

					for (final T n : l) {
						Object result = method.invoke(null, n);

						if (result instanceof Number) {
							newList.add((T) result);
						}

					}

				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		return newList;
	}

	public static <T extends Number> Number plusOne(T n) {
		/**
		 * It is assumed only double, Integer, Float and Long types are used to
		 * fill the list. This list can be extended for types that extends
		 * Number
		 */
		if (n != null) {
			if (n instanceof Double) {
				return n.doubleValue() + 1D;
			} else if (n instanceof Integer) {
				return n.intValue() + 1;
			} else if (n instanceof Float) {
				return n.floatValue() + 1F;
			} else if (n instanceof Long) {
				return n.longValue() + 1L;
			}
		}

		return null;

	}

}
