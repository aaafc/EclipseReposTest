package aaafcKeyManage.RSADemo;

import aaafcKeyManage.main.MyAlgorithmParameterSpec;

/**
 * The parameter specification for RSA algorithm. <br>
 * Stands as "RSA/($modulus_length)", for example, <br>
 * "RSA/1024" for modulus length of 1024 bits or <br> 
 * "RSA/2048" for modulus length of 2048 bits.<br><br>
 * by default, set to "RSA/1024"
 * @author aaafc
 *
 */
public class MyRSAParameterSpec extends MyAlgorithmParameterSpec {

	private MyRSAParameterSpec(Object[] parameterStringArr) {
		super(parameterStringArr);
	}

	public static MyRSAParameterSpec getInstance(String[] parameterString) {
		Object temp[] = new Object[2];
		int pos = 0;
		try {
			temp[0] = parameterString[0];
			pos++;
			try {
				temp[1] = Integer.parseInt(parameterString[1]);
			} catch (NumberFormatException e) {
				temp[1] = 1024;
			}
			pos++;
		} catch (ArrayIndexOutOfBoundsException e) {
			for(int i = pos; i < 2; i++)
			{
				switch(i)
				{
				case 0:
					temp[0] = "RSA";
					break;
				case 1:
					temp[1] = 1024;
					break;
				default:
					break;
				}
				
			}
		}
		
		return new MyRSAParameterSpec(temp);
	}

}
