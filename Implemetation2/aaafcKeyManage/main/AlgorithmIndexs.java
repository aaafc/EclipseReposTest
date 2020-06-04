package aaafcKeyManage.main;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import aaafcKeyManage.AESDemo.MyAESAssessDisplay;
import aaafcKeyManage.AESDemo.MyAESAssessment;
import aaafcKeyManage.AESDemo.MyAESDisplay;
import aaafcKeyManage.AESDemo.MyAESParameterSpec;
import aaafcKeyManage.AESDemo.MyAESProcessor;
import aaafcKeyManage.DESDemo.MyDESAssessDisplay;
import aaafcKeyManage.DESDemo.MyDESAssessment;
import aaafcKeyManage.DESDemo.MyDESDisplay;
import aaafcKeyManage.DESDemo.MyDESParameterSpec;
import aaafcKeyManage.DESDemo.MyDESProcessor;
import aaafcKeyManage.ECIESDemo.MyECIESAssessDisplay;
import aaafcKeyManage.ECIESDemo.MyECIESAssessment;
import aaafcKeyManage.ECIESDemo.MyECIESDisplay;
import aaafcKeyManage.ECIESDemo.MyECIESParameterSpec;
import aaafcKeyManage.ECIESDemo.MyECIESProcessor;
import aaafcKeyManage.PairingDemo.BasicIdentAssessDisplay;
import aaafcKeyManage.RSADemo.MyRSAAssessDisplay;
import aaafcKeyManage.RSADemo.MyRSAAssessment;
import aaafcKeyManage.RSADemo.MyRSADisplay;
import aaafcKeyManage.RSADemo.MyRSAParameterSpec;
import aaafcKeyManage.RSADemo.MyRSAProcessor;
import aaafcKeyManage.SHADemo.MySHAAssessDisplay;
import aaafcKeyManage.SHADemo.MySHAAssessment;
import aaafcKeyManage.SHADemo.MySHADisplay;
import aaafcKeyManage.SHADemo.MySHAParameterSpec;
import aaafcKeyManage.SHADemo.MySHAProcessor;

/**
 * This class is for store the Map cases for the algorithms into a Mapper.
 * 			
 * @author aaafc
 *
 */
public class AlgorithmIndexs {
	
	
	private static HashMap<String, Class> generalAlgorithmAssessDisplayClassMapper;
	private static HashMap<String, Class> generalAlgorithmDisplayClassMapper;
	private static HashMap<String, Class> generalAlgorithmProcessorClassMapper;
	private static HashMap<String, Class> generalAlgorithmAssessmentClassMapper;
	private static HashMap<String, Class> generalAlgorithmParameterSpecClassMapper;
	
	public static ArrayList<String> algorithms;
	
	static{
		generalAlgorithmAssessDisplayClassMapper = new HashMap<String, Class>();
		generalAlgorithmDisplayClassMapper = new HashMap<String, Class>();
		generalAlgorithmProcessorClassMapper = new HashMap<String, Class>();
		generalAlgorithmAssessmentClassMapper = new HashMap<String, Class>();
		generalAlgorithmParameterSpecClassMapper = new HashMap<String, Class>();
		algorithms = new ArrayList<String>();
		
		algorithms.add("RSA");
		generalAlgorithmAssessDisplayClassMapper.put("RSA", MyRSAAssessDisplay.class);
		generalAlgorithmDisplayClassMapper.put("RSA", MyRSADisplay.class);
		generalAlgorithmProcessorClassMapper.put("RSA", MyRSAProcessor.class);
		generalAlgorithmAssessmentClassMapper.put("RSA", MyRSAAssessment.class);
		generalAlgorithmParameterSpecClassMapper.put("RSA", MyRSAParameterSpec.class);
		
		
		algorithms.add("SHA");
		generalAlgorithmAssessDisplayClassMapper.put("SHA", MySHAAssessDisplay.class);
		generalAlgorithmDisplayClassMapper.put("SHA", MySHADisplay.class);
		generalAlgorithmProcessorClassMapper.put("SHA", MySHAProcessor.class);
		generalAlgorithmAssessmentClassMapper.put("SHA", MySHAAssessment.class);
		generalAlgorithmParameterSpecClassMapper.put("SHA", MySHAParameterSpec.class);
		
		
		algorithms.add("DES");
		generalAlgorithmAssessDisplayClassMapper.put("DES", MyDESAssessDisplay.class);
		generalAlgorithmDisplayClassMapper.put("DES", MyDESDisplay.class);
		generalAlgorithmProcessorClassMapper.put("DES", MyDESProcessor.class);
		generalAlgorithmAssessmentClassMapper.put("DES", MyDESAssessment.class);
		generalAlgorithmParameterSpecClassMapper.put("DES", MyDESParameterSpec.class);
		
		algorithms.add("AES");
		generalAlgorithmAssessDisplayClassMapper.put("AES", MyAESAssessDisplay.class);
		generalAlgorithmDisplayClassMapper.put("AES", MyAESDisplay.class);
		generalAlgorithmProcessorClassMapper.put("AES", MyAESProcessor.class);
		generalAlgorithmAssessmentClassMapper.put("AES", MyAESAssessment.class);
		generalAlgorithmParameterSpecClassMapper.put("AES", MyAESParameterSpec.class);
	
		algorithms.add("ECIES");
		generalAlgorithmAssessDisplayClassMapper.put("ECIES", MyECIESAssessDisplay.class);
		generalAlgorithmDisplayClassMapper.put("ECIES", MyECIESDisplay.class);
		generalAlgorithmProcessorClassMapper.put("ECIES", MyECIESProcessor.class);
		generalAlgorithmAssessmentClassMapper.put("ECIES", MyECIESAssessment.class);
		generalAlgorithmParameterSpecClassMapper.put("ECIES", MyECIESParameterSpec.class);
		
		algorithms.add("BASICIDENT");
		generalAlgorithmAssessDisplayClassMapper.put("BASICIDENT", BasicIdentAssessDisplay.class);
		
	}
	
	
	public static int getAlgorithmIndex(String algorithm) throws NoSuchAlgorithmException
	{
		String in = algorithm.toUpperCase();
		int i = algorithms.indexOf(in);
		if(i == -1)
			throw new NoSuchAlgorithmException("aaafc's API can not find algorithm in the nameList: " + algorithm);
		return (i + 1);
	}
	
	public static Class getAlgorithmDisplayClass(String algorithm) throws NoSuchAlgorithmException{
		String in = algorithm.toUpperCase();
		Class result = generalAlgorithmDisplayClassMapper.get(in);
		if(result == null)
			throw new NoSuchAlgorithmException("aaafc's API can not find algorithm in the DisplayMapper: " + algorithm);
		return result;
	}
	
	public static Class getAlgorithmAssessDisplayClass(String algorithm) throws NoSuchAlgorithmException{
		String in = algorithm.toUpperCase();
		Class result = generalAlgorithmAssessDisplayClassMapper.get(in);
		if(result == null)
			throw new NoSuchAlgorithmException("aaafc's API can not find algorithm in the AssessDisplayMapper: " + algorithm);
		return result;
	}
	
	public static Class getAlgorithmProcessorClass(String algorithm) throws NoSuchAlgorithmException{
		String in = algorithm.toUpperCase();
		Class result = generalAlgorithmProcessorClassMapper.get(in);
		if(result == null)
			throw new NoSuchAlgorithmException("aaafc's API can not find algorithm in the ProcessorMapper");
		return result;
	}
	
	public static Class getAlgorithmAssessmentClass(String algorithm) throws NoSuchAlgorithmException{
		String in = algorithm.toUpperCase();
		Class result = generalAlgorithmAssessmentClassMapper.get(in);
		if(result == null)
			throw new NoSuchAlgorithmException("aaafc's API can not find algorithm in the AssessmentMapper");
		return result;
	}
	
	public static Class getAlgorithmParameterSpecClass(String algorithm) throws NoSuchAlgorithmException{
		String in = algorithm.toUpperCase();
		Class result = generalAlgorithmParameterSpecClassMapper.get(in);
		if(result == null)
			throw new NoSuchAlgorithmException("aaafc's API can not find algorithm in the AssessmentMapper");
		return result;
	}
	
	
	
}
