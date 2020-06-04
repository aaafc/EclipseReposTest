package aaafcKeyManage.main;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;

public class MyProcessUtil {
	
	
	public static byte[] randomBytes(int length)
	{
		byte[] temp = new byte[length];
		new Random(System.currentTimeMillis()).nextBytes(temp);
		return temp;
	}
	
	public static String randomString(int length)
	{
		return new String(randomBytes(length));
	}
	
	public static String byteToHexStr(byte[] array)
	{
		String result = "";
		for(int i = 0; i < array.length; i++)
			result += byteToHexStr(array[i]) + " ";
		return result;
	}
	
	public static String byteToHexStr(byte src)
	{
		int upper = (src & 240) >>> 4;
		int lower = src & (byte)15;
		String result = "";
		switch(upper)
		{
		case 10:
			result += "A";
			break;
		case 11:
			result += "B";
			break;
		case 12:
			result += "C";
			break;
		case 13:
			result += "D";
			break;
		case 14:
			result += "E";
			break;
		case 15:
			result += "F";
			break;
		default:
			result += upper;
			break;
		}
		switch(lower)
		{
		case 10:
			result += "A";
			break;
		case 11:
			result += "B";
			break;
		case 12:
			result += "C";
			break;
		case 13:
			result += "D";
			break;
		case 14:
			result += "E";
			break;
		case 15:
			result += "F";
			break;
		default:
			result += lower;
			break;
		}
		return result;
	}
	
}
