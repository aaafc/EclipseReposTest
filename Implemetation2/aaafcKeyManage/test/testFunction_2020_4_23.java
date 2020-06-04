package aaafcKeyManage.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import aaafcKeyManage.AESDemo.MyAESAssessment;
import aaafcKeyManage.DESDemo.MyDESAssessment;
import aaafcKeyManage.ECIESDemo.MyECIESAssessment;
import aaafcKeyManage.Exception.AssessmentTimeOverflowException;
import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.RSADemo.MyRSAAssessment;
import aaafcKeyManage.SHADemo.MySHAAssessment;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;

/**
 * This is a class for testing.
 * @author aaafc
 *
 */
public class testFunction_2020_4_23 {

	private static int testSourceNum;

	private static final String testFilePath = "./2020_5_7_test/";

	public static void testFunction(String algorithm, int size) {

		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}

		testSourceNum = size;
		algorithm = algorithm.toLowerCase();
		MyAESAssessment maesa = new MyAESAssessment();
		MyRSAAssessment mrsaa = new MyRSAAssessment();
		MySHAAssessment mshaa = new MySHAAssessment();
		MyDESAssessment mdesa = new MyDESAssessment();
		MyECIESAssessment meciesa = new MyECIESAssessment();
		String[] testMessages = getTestMessagesWithPath(testSourceNum, testFilePath + "2020_4_28_String_in_4");
		MyAlgorithmParameterSpec[] specArr = getParamSpecWithPath(testSourceNum, testFilePath + "paramSource_2020_4_28_" + algorithm + ".txt");
		int[] processLoops = getProcessLoops(testSourceNum);

		try {
			long[][] result = null;
			switch (algorithm) {
			case "aes":
				result = maesa.encryptAndDecryptProcess(testMessages, specArr, processLoops);
				break;
			case "des":
				result = mdesa.encryptAndDecryptProcess(testMessages, specArr, processLoops);
				break;
			case "sha":
				result = mshaa.encryptAndDecryptProcess(testMessages, specArr, processLoops);
				break;
			case "rsa":
				result = mrsaa.encryptAndDecryptProcess(testMessages, specArr, processLoops);
				break;
			case "ecies":
				result = meciesa.encryptAndDecryptProcess(testMessages, specArr, processLoops);
				break;
			}
			BufferedWriter br;
			br = new BufferedWriter(new FileWriter(
					testFilePath + "stringOut_2020_5_7/" + algorithm + "_" + System.currentTimeMillis() + ".txt"));
			for (int i = 0; i < testSourceNum; i++) {
				br.write(testMessages[i].length() + "\t" + specArr[i].toString() + "\t" + processLoops[i] + "\t"
						+ +result[0][i] + "\t" + result[1][i]);
				br.newLine();
			}
			br.flush();
			br.close();
		} catch (InvalidParameterSpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptProcessFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DecryptProcessFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GenerateKeyFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AssessmentTimeOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String[] getTestMessagesRev(int size) {
		String[] result = new String[size];
		Arrays.fill(result, "");
		try {

			File f = new File(testFilePath + "2020_4_28_String_in");
			File[] files;
			files = f.listFiles();
			byte[] fileContent;
			for (int i = 0; i < size; i++) {

				fileContent = new byte[(int) files[i].length()];
				FileInputStream inputStream = new FileInputStream(files[i]);
				inputStream.read(fileContent);
				result[size - i - 1] = new String(fileContent);
				inputStream.close();
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private static String[] getTestMessages(int size) {
		String[] result = new String[size];
		Arrays.fill(result, "");
		try {

			File f = new File(testFilePath + "2020_4_28_String_in");
			File[] files;
			files = f.listFiles();
			byte[] fileContent;
			for (int i = 0; i < size; i++) {

				fileContent = new byte[(int) files[i].length()];
				FileInputStream inputStream = new FileInputStream(files[i]);
				inputStream.read(fileContent);
				result[i] = new String(fileContent);
				inputStream.close();
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	private static String[] getTestMessagesWithPath(int size, String filePath) {
		String[] result = new String[size];
		Arrays.fill(result, "");
		try {

			File f = new File(filePath);
			File[] files;
			files = f.listFiles();
			byte[] fileContent;
			for (int i = 0; i < size; i++) {

				fileContent = new byte[(int) files[i].length()];
				FileInputStream inputStream = new FileInputStream(files[i]);
				inputStream.read(fileContent);
				result[i] = new String(fileContent);
				inputStream.close();
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private static String[] getTestMessagesOld(int size) {
		String[] result = new String[size];
		Arrays.fill(result, "");
		try {

			File f = new File(testFilePath + "2020_4_28_String_in");
			File[] files;
			files = f.listFiles();
			BufferedReader br;
			String temp;
			for (int i = 0; i < size; i++) {
				br = new BufferedReader(new FileReader(files[i]));
				while ((temp = br.readLine()) != null) {
					result[i] += (temp + "\n");
				}
				br.close();
			}
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static MyAlgorithmParameterSpec[] getParamSpec(int size, String al) {
		MyAlgorithmParameterSpec[] result = new MyAlgorithmParameterSpec[size];
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(testFilePath + "paramSource_2020_3_2_" + al + ".txt"));
			for (int i = 0; i < size; i++) {
				result[i] = MyAlgorithmParameterSpec.getInstance(br.readLine());
			}
			br.close();
			return result;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static MyAlgorithmParameterSpec[] getParamSpecWithPath(int size, String path) {
		MyAlgorithmParameterSpec[] result = new MyAlgorithmParameterSpec[size];
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(path));
			for (int i = 0; i < size; i++) {
				result[i] = MyAlgorithmParameterSpec.getInstance(br.readLine());
			}
			br.close();
			return result;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	private static int[] getProcessLoops(int size) {
		int[] result = new int[size];
		try {
			BufferedReader br = new BufferedReader(new FileReader(testFilePath + "processLoopsSource_2020_3_2.txt"));
			for (int i = 0; i < size; i++) {
				result[i] = Integer.parseInt(br.readLine());
			}
			br.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}