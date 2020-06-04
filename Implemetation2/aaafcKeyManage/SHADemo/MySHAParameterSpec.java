package aaafcKeyManage.SHADemo;

import aaafcKeyManage.main.MyAlgorithmParameterSpec;

/**
 * The parameter specification for SHA algorithm. <br>
 * Stands as "SHA/($Algorithm_STD)", for example, <br>
 * "SHA/SHA-1" or "SHA/SHA-256"<br><br>
 * by default, set to "SHA/SHA-1"
 * @author aaafc
 */
public class MySHAParameterSpec extends MyAlgorithmParameterSpec {

	private MySHAParameterSpec(Object[] parameterString) {
		super(parameterString);
	}

	public static MySHAParameterSpec getInstance(String[] parameterString) {
		Object temp[] = new Object[2];
		int pos = 0;
		try {
			temp[0] = parameterString[0];
			pos++;
			temp[1] = parameterString[1];
			pos++;
		} catch (ArrayIndexOutOfBoundsException e) {
			for (int i = pos; i < 2; i++) {
				switch (i) {
				case 0:
					temp[0] = "SHA";
					break;
				case 1:
					temp[1] = "SHA-1";
					break;
				default:
					break;
				}

			}
		}
		return new MySHAParameterSpec(temp);

	}
}
