package aaafcKeyManage.SHADemo;

import java.security.spec.InvalidParameterSpecException;

import aaafcKeyManage.Exception.AssessmentTimeOverflowException;
import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessUtil;

/**
 * This is a class which could do assessment process for SHA algorithm.
 * @author aaafc
 *
 */
public class MySHAAssessment {

	private MySHAProcessor processor = null;

	public MySHAAssessment() {
		processor = new MySHAProcessor();
	}

	/**
	 * @deprecated
	 * @param stringLength
	 * @param processLoops
	 * @return
	 * @throws Exception
	 */
	public synchronized long[] hashingProcess(int stringLength, int processLoops) throws Exception {
		MyMessage tempMessage;

		long[] result = new long[processLoops];
		try {
			for (int i = 0; i < processLoops; i++) {
				tempMessage = new MyMessage(new String(MyProcessUtil.randomBytes(stringLength)));
				result[i] = processor.EncryptMessage(tempMessage);
			}
		} catch (Exception e) {
			throw e;
		}
		return result;

	}
	
	/**
	 * @deprecated
	 * @param stringLength
	 * @param processLoops
	 * @return
	 * @throws Exception
	 */
	public long averageHashingProcess(int stringLength, int processLoops)
		throws Exception
	{
		long[] temp = hashingProcess(stringLength, processLoops);
		long result = 0;
		for(int i = 0; i < processLoops; i++)
		{
			result += temp[i];
		}
		
		result /= processLoops;
		return result;
	}

	/**
	 * Calculates time cost for encrypting and decrypting messages by SHA algorithm.
	 * No Keys are needed for SHA. Careful: the method is synchronized by the
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
			for(int j = 0; j < processLoops[i]; j++)
			{
				processor.keyManager.generateKey(nSpec[i]);
				tempMessage = new MyMessage(messages[i]);
				result[0][i] += processor.EncryptMessage(tempMessage, 0, nSpec[i]) / processLoops[i];
				result[1][i] += processor.DecryptMessage(tempMessage) / processLoops[i];
				processor.keyManager.removeKey();
				if(System.currentTimeMillis() - generalSt > 200000)
				{
					throw new AssessmentTimeOverflowException();
				}
			}
		}
		return result;
		
	}

}
