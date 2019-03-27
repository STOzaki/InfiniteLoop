package utils;

import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Validate {
	
	public static boolean checkValidNum(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}
		catch(InputMismatchException e) {
			return false;
		}
	}
	
	public static boolean checkValidPhoneNum(String input) {
		
		String regex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
		boolean result = Pattern.matches(regex, input);
		
		return result ? true : false;
	}
}