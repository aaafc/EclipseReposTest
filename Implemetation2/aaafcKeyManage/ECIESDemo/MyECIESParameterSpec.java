package aaafcKeyManage.ECIESDemo;

import aaafcKeyManage.main.MyAlgorithmParameterSpec;

/**
 * 
 * @author aaafc
 *
 */
public class MyECIESParameterSpec extends MyAlgorithmParameterSpec {

	public MyECIESParameterSpec(Object[] nParameter) {
		super(nParameter);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param parameterString
	 * @return
	 */
	public static MyECIESParameterSpec getInstance(String[] parameterString) {
		Object temp[] = new Object[3];
		int pos = 0;
		int lim = parameterString.length;
		if(lim < 3);
		else if (parameterString[0].equals("ECIES")) {
			if (parameterString[1].equals("STD")) {
				temp[0] = parameterString[0];
				for(pos = 1; pos < lim; pos++)
					temp[pos] = parameterString[pos];
			} else if (parameterString[1].equals("COS")) {
					
			}
		}

		if (pos > 1 && temp[1].equals("COS"))
			pos = 1;

		switch (pos) {
		case 0:
			temp[0] = "ECIES";
		case 1:
			temp[1] = "STD";
		case 2:
			temp[2] = "prime192v1";		// set to default
		default:
			break;
		}

		return new MyECIESParameterSpec(temp);
	}
}
