package aaafcKeyManage.AESDemo;

import aaafcKeyManage.DESDemo.MyDESParameterSpec;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;

/**
 * 
 * @author aaafc
 *
 */
public class MyAESParameterSpec extends MyAlgorithmParameterSpec{

	public MyAESParameterSpec(Object[] nParameter) {
		super(nParameter);
		// TODO Auto-generated constructor stub
	}
	
	public static MyAESParameterSpec getInstance(String[] parameterString) {
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
					temp[pos] = "AES";
					break;
				case 1:
					temp[pos] = "ECB";
					break;
				case 2:
					temp[pos] = "PKCS5Padding";
					break;
				case 3:
					temp[pos] = "a1b2c3d4e5f6g7h8";
					break;
				}
		}
		return new MyAESParameterSpec(temp);
		
	}
	
}
