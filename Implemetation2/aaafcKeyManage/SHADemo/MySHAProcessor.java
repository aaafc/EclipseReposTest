package aaafcKeyManage.SHADemo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessor;

/**
 * This is a class for generating an object processing SHA algorithms.
 * @author aaafc
 *
 */
public class MySHAProcessor implements MyProcessor{

	public static final int AlgorithmIndex = 2;
	
	MySHAKeyManager keyManager;
	MessageDigest msgDigest = null;
	
	public MySHAProcessor()
	{
		keyManager = new MySHAKeyManager();
	}
	
	@Override
	public long EncryptMessage(MyMessage msg) throws EncryptProcessFailedException {
		return EncryptMessage(msg, 0, MySHAParameterSpec.getInstance(new String[] {"SHA", "SHA-1"}));
	}
	
	@Override
	public long EncryptMessage(MyMessage msg, int KeyIndex) throws EncryptProcessFailedException {
		return this.EncryptMessage(msg);
	}

	@Override
	public long DecryptMessage(MyMessage msg) throws DecryptProcessFailedException {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public boolean isSatisfied(MyMessage msg) {
		if(msg.getAlgorithmIndex() != AlgorithmIndex && msg.getAlgorithmIndex() != 0)
			return false;
		return true;
	}
	
	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex, MyAlgorithmParameterSpec nSpec)
			throws EncryptProcessFailedException {
		if(!isSatisfied(msg) || !nSpec.getParameter()[0].equals("SHA"))
			throw new EncryptProcessFailedException();
		
		try {
			msgDigest = MessageDigest.getInstance((String)nSpec.getParameter()[1]);
			msgDigest.update(msg.getPlainMessage().getBytes());
			
			byte[] cipherMsg;
			//Finally do the message digest operation
			long st = System.nanoTime();
			cipherMsg = msgDigest.digest();
			long result = System.nanoTime() - st;
			
			//Store the result back to MyMessage object
			msg.setCipherMessage(cipherMsg, AlgorithmIndex, 0, result, nSpec);
			
			return result;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	

}
