package cz.cvut.run.utils;

public class Utils {

	public static String getString(byte[] input){
		if (input == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(input.length);
		for (int i=0; i<input.length; i++){
			sb.append(input[i] + " ");
		}
		return sb.toString();
	}

	public static int parseByteToInt(byte[] input) {
		if (input == null || input.length == 0){
			return 0;
		}
		int result = 0;
		for (int i= 0; i<input.length; i++){
			result += unsignedToBytes(input[i]) * Math.pow(256, input.length-i-1);
		}
		return result;
	}
	
	
	public static int unsignedToBytes(byte b) {
		 return b & 0xFF;
	}
}
