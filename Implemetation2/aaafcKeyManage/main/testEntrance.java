package aaafcKeyManage.main;

import java.lang.reflect.InvocationTargetException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import aaafcKeyManage.AESDemo.MyAESAssessDisplay;
import aaafcKeyManage.AESDemo.MyAESDisplay;
import aaafcKeyManage.AESDemo.MyAESProcessor;
import aaafcKeyManage.DESDemo.MyDESAssessDisplay;
import aaafcKeyManage.DESDemo.MyDESDisplay;
import aaafcKeyManage.DESDemo.MyDESProcessor;
import aaafcKeyManage.ECIESDemo.MyECIESProcessor;
import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.RSADemo.MyRSAAssessDisplay;
import aaafcKeyManage.RSADemo.MyRSADisplay;
import aaafcKeyManage.RSADemo.MyRSAProcessor;
import aaafcKeyManage.SHADemo.MySHAAssessDisplay;
import aaafcKeyManage.SHADemo.MySHADisplay;
import aaafcKeyManage.SHADemo.MySHAProcessor;
import aaafcKeyManage.UI.MainController;

public class testEntrance {
	// Modified text file
	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		int a = 7;
		MyMessage testMessage = new MyMessage("Hello A World!");
		switch (a) {
		case 1:
			MyRSAProcessor mrsar = new MyRSAProcessor();
			// block 1: test RSA encryption and Decryption
			try {
				System.out.println("Generating time cost in Nano: " + mrsar.keyManager.generateKey());
				System.out.println("Generating time cost in Nano: " + mrsar.keyManager.generateKey());

				System.out.println("Encrypting time cost in Nano: " + mrsar.EncryptMessage(testMessage, 0));
				System.out.println(new String(testMessage.getCipherMessage()));
				System.out.println("RSA parameters: " + mrsar.keyManager.getKeyParam(testMessage.getKeyIndex()));
				System.out.println("Decrypting time cost in Nano: " + mrsar.DecryptMessage(testMessage));
				System.out.println(new String(testMessage.getRebuiltMessage()));

				System.out.println("Encrypting time cost in Nano: " + mrsar.EncryptMessage(testMessage, 0));
				System.out.println("Decrypting time cost in Nano: " + mrsar.DecryptMessage(testMessage));
			} catch (GenerateKeyFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EncryptProcessFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DecryptProcessFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		// block 2: test SHA message hashing algorithm
		case 2:
			MySHAProcessor mshar = new MySHAProcessor();
			try {
				System.out.println("Time Cost for SHA in Nano: " + mshar.EncryptMessage(testMessage));
			} catch (EncryptProcessFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			MyDESProcessor mdesr = new MyDESProcessor();
			try {
				mdesr.keyManager.generateKey();
				System.out.println("Time Cost for DES Encrypt in Nano: " + mdesr.EncryptMessage(testMessage));
				System.out.println(
						"msg.AlgoIn: " + testMessage.getAlgorithmIndex() + " msg.KeyIn: " + testMessage.getKeyIndex());
				System.out.println("Time Cost for DES Decrypt in Nano: " + mdesr.DecryptMessage(testMessage));
				System.out.println("Message rebuilt: " + new String(testMessage.getRebuiltMessage()));
			} catch (GenerateKeyFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EncryptProcessFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DecryptProcessFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			// MyRSADisplay mrsad = new MyRSADisplay();
			MyRSAAssessDisplay mrsaassd = MyRSAAssessDisplay.getInstance();
			break;
		case 5:
			MySHADisplay mshad = MySHADisplay.getInstance();
			MySHAAssessDisplay mshaassd = MySHAAssessDisplay.getInstance();
			break;
		case 6:
			MyDESAssessDisplay mdesassd = MyDESAssessDisplay.getInstance();
			break;
		case 7:
			MainController mc = new MainController();
			break;
		case 8:
			try {
				Object[] temp = MyAlgorithmParameterSpec.getInstance("RSA/1234/CBC").getParameter();
				System.out.println(temp[0] + "\n" + temp[1] + "\n" + temp[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 9:
			try {
				MyECIESProcessor meccp = new MyECIESProcessor();
				MyMessage longtestMessage = new MyMessage("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
				meccp.EncryptMessage(longtestMessage);
				meccp.DecryptMessage(longtestMessage);
				System.out.println(new String(longtestMessage.getCipherMessage()));
				System.out.println(new String(longtestMessage.getRebuiltMessage()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		case 10:
			testFunction_2020_3_2.testFunction("ECIES", 10);
//			Enumeration e = ECNamedCurveTable.getNames();
//			while(e.hasMoreElements())
//				System.out.println(e.nextElement().toString());
			break;
		default:
			System.out.println("No Such testEntrance Index.");
			break;
		}
	}

}
