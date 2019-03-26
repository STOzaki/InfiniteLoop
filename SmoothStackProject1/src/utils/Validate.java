package utils;

public class Validate {
	public static boolean checkValidID(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
		//input = scan.nextLine();
		//while(!checkValidID(input);
		//	input = scan.nextLine();
	}
}
