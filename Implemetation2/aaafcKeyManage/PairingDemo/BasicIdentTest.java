package aaafcKeyManage.PairingDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is a class for Testing the BasicIdent algorithm.
 * The class is set to empty after the encapsulation
 * @author aaafc
 *
 */
public class BasicIdentTest {
//	public static void main(String[] args) throws Exception
//	{
//		int size = 20;
//		String path = "./2020_4_23_test/2020_4_23_String_in";
//		String curvePath = "./2020_4_23_test/2020_4_23_curve_param.txt";
//		String curvePathOut = "./2020_4_23_test/2020_4_23_String_out/3.txt";
//		
//		String[] plainMessages = getTestMessages(path, size);
//		PairingCurveParam[] curveParamArray = getPairingCurveParams(curvePath, size);
//		PairingLog[] logs = new PairingLog[size];
//		
//		BasicIdentEngine temp = new BasicIdentEngine();
//		for(int i = 0; i < size; i++)
//		{
//			temp.init(curveParamArray[i], plainMessages[i]);
//			temp.doProcess();
//			logs[i] = temp.extractPairingLog();
//		}
//		
//		PairingLog.extractLogWithCurveInfo(logs, curvePathOut);
//		
//	}
//	
//	private static String[] getTestMessages(String path, int size)
//	{
//		String[] result = new String[size];
//		Arrays.fill(result, "");
//		try {
//			
//			File f = new File(path);
//			File[] files;
//			files = f.listFiles();
//			byte[] fileContent;
//			for(int i = 0; i < size; i++)
//			{
//
//				fileContent = new byte[(int)files[i].length()];
//		        FileInputStream inputStream = new FileInputStream(files[i]);
//		        inputStream.read(fileContent);
//		        result[i] = new String(fileContent);
//		        inputStream.close();
//			}
//			return result;
//		} catch (Exception e) {
//		    // TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	private static PairingCurveParam[] getPairingCurveParams(String path, int size)
//	{
//		BufferedReader br = null;
//		PairingCurveParam[] array = new PairingCurveParam[size];
//		try {
//			br = new BufferedReader(new FileReader(path));
//			String temp;
//			String[] data;
//			for(int i = 0; i < size; i++)
//			{
//				temp = br.readLine();
//				if(temp.charAt(0) == '#')
//				{
//					i--;
//					continue;
//				}
//				data = temp.split("\t");
//				array[i] = new PairingCurveParam(Integer.parseInt(data[0]),
//						Integer.parseInt(data[1]), Integer.parseInt(data[2]));
//				
//			}
//			
//			return array;
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e2)
//		{
//			e2.printStackTrace();
//		}
//		return null;
//	}
}
