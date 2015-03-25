package a.b.c;

import java.util.Base64;

public class Test {

	public static void main(String[] args) {

		System.out.println(new String(Base64.getEncoder().encode(
				"abcde".getBytes())));

	}

}
