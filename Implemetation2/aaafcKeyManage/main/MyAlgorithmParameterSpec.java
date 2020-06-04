package aaafcKeyManage.main;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

import aaafcKeyManage.Exception.InvalidParameterFormatException;

/**
 * This is a class for managing the parameters for different algorithms.
 * 
 * @author lenovo
 *
 */
public abstract class MyAlgorithmParameterSpec {
	
	private Object[] parameter;
	
	public MyAlgorithmParameterSpec(Object[] nParameter) {
		parameter = nParameter;
	}
	
	/**
	 * This is a class for generating a parameter specification for one algorithm.
	 * @param NParameterString
	 * 			The string indicates the algorithm and the parameter it uses. Values are separated by "/"
	 * 		and form a single String. i.e. "RSA/2048" indicates its a parameter for RSA with 2048 bit of modulus size.
	 * 		If the String is not fully configured, default values are added. i.e. For DES algorithm, 4 parameters 
	 * 		are required, configured as "DES/$mode/$Padding/$initialVector". If "DES" is sent, originally it will
	 * 		fill up as "DES/ECB/NoPadding/a1b2c3d4" for the common usage. However, at least the name of the algorithm is 
	 * 		required. 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static MyAlgorithmParameterSpec getInstance(String NParameterString)
		throws NoSuchAlgorithmException, IllegalAccessException, IllegalArgumentException,
		InvocationTargetException, NoSuchMethodException, SecurityException
	{
		String[] temp = NParameterString.split("/");
		if(AlgorithmIndexs.getAlgorithmIndex(temp[0]) == -1)
			throw new NoSuchAlgorithmException("Creating Parameter Spec failed: No such algorithm");
		return (MyAlgorithmParameterSpec)AlgorithmIndexs.getAlgorithmParameterSpecClass(temp[0])
				.getMethod("getInstance", new Class[] {java.lang.String.class.arrayType()}).invoke(null, new Object[] {temp});
	}
	
	
	public Object[] getParameter(){
		return parameter;
	}
	
	public String toString()
	{
		String result = "";
		result += parameter[0] == null ? "" : parameter[0];
		for(int i = 1; i < parameter.length; i++)
			if(parameter[i] != null)
				result += "/" + parameter[i];
		return result;
	}

}
