package aaafcKeyManage.DESDemo;

import aaafcKeyManage.main.MyAlgorithmParameterSpec;

/**
 * 
 * @author aaafc
 *
 */
public class MyDESParameterSpec extends MyAlgorithmParameterSpec{
	
	private MyDESParameterSpec(Object[] parameterString) {
		super(parameterString);
	}
	
	/**
	 * This is a method to transform a String array into an object array which could be 
	 * loaded to the corresponding algorithm processor.
	 * @param parameterString 
	 * 	The String array contains the parameters for DES algorithm 
	 * @return
	 */
	public static MyDESParameterSpec getInstance(String[] parameterString)
	{
		Object[] temp = new Object[4];
		int pos = 0;
		try {
			for(; pos < 4; pos++)
				temp[pos] = parameterString[pos];
		} catch(ArrayIndexOutOfBoundsException e)
		{
			for(; pos < 4; pos++)
				switch(pos)
				{
				case 0:
					temp[pos] = "DES";
					break;
				case 1:
					temp[pos] = "ECB";
					break;
				case 2:
					temp[pos] = "PKCS5Padding";
					break;
				case 3:
					temp[pos] = "a1b2c3d4";
					break;
				}
		}
		return new MyDESParameterSpec(temp);
	}
	
}
