package com.soccer.web;

public class Test {

	public static void main(String[] args) {
		String tmp = "13245";
		System.out.println(tmp.length());
		System.out.println("2번째 1 : " + (Character.getNumericValue(tmp.charAt(1)) + 1));
		System.out.println("2번째 2 : " + (tmp.charAt(1) + 1));
	}

}
