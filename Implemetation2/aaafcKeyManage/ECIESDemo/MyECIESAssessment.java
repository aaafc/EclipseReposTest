package aaafcKeyManage.ECIESDemo;

import java.security.spec.InvalidParameterSpecException;

import aaafcKeyManage.Exception.AssessmentTimeOverflowException;
import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;


/**
 * This is a class for generating an assessment object for ECIES algorithm.
 * @author aaafc
 *
 */
public class MyECIESAssessment {
	
	MyECIESProcessor processor = new MyECIESProcessor();
	
	/**
	 * Calculates time cost for encrypting and decrypting messages by ECIES algorithm.
	 * Key pairs are generated by parameterSpec object. Careful: the method is synchronized by the
	 * object
	 * @param messages
	 * 			String array which contains the messages to be delivered.
	 * @param nSpec
	 * 			parameter array which contains the parameter for each message. 
	 * @param processLoops
	 * 			integer array which contains how many loops for each message.
	 * @return	An long[2][length] array, where 2 indicates encrypting time and decrypting time respectively,
	 * 	length indicates how many messages are encrypted/decrypted.
	 * @throws EncryptProcessFailedException
	 * @throws DecryptProcessFailedException
	 * @throws GenerateKeyFailedException
	 * @throws AssessmentTimeOverflowException
	 * 			Indicates the assessment process takes too many time(in the case, 200000 milliseconds) for calculating
	 * @throws InvalidParameterSpecException
	 */
	public synchronized long[][] encryptAndDecryptProcess(String[] messages, MyAlgorithmParameterSpec[] nSpec,
			int processLoops[])
			throws EncryptProcessFailedException, DecryptProcessFailedException, GenerateKeyFailedException, 
			AssessmentTimeOverflowException, InvalidParameterSpecException{
		long generalSt = System.currentTimeMillis();
		MyMessage tempMessage = null;
		long[][] result = new long[2][messages.length];
		for (int i = 0; i < messages.length; i++) {
			processor.keyManager.generateKey(nSpec[i]);
			for(int j = 0; j < processLoops[i]; j++)
			{
				
				tempMessage = new MyMessage(messages[i]);
				result[0][i] += processor.EncryptMessage(tempMessage, processor.keyManager.privateKeys.size() - 1, nSpec[i]) / processLoops[i];
				result[1][i] += processor.DecryptMessage(tempMessage) / processLoops[i];
				if(System.currentTimeMillis() - generalSt > 500000)
				{
					throw new AssessmentTimeOverflowException();
				}
			}
			processor.keyManager.removeKey();
		}
		return result;
		
	}
	
}
