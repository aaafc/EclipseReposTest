package aaafcKeyManage.AESDemo;

import java.security.spec.InvalidParameterSpecException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import aaafcKeyManage.DESDemo.MyDESKeyManager;
import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessor;

/**
 * 
 * @author aaafc
 *
 */
public class MyAESProcessor implements MyProcessor{
	
	public static final int AlgorithmIndex = 4;
	
	public MyAESKeyManager keyManager = null;
	private Cipher cipher = null;
	
	public MyAESProcessor()
	{
		keyManager = new MyAESKeyManager();
		try {
			this.keyManager.generateKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public long EncryptMessage(MyMessage msg) throws EncryptProcessFailedException {
		return EncryptMessage(msg, keyManager.keyList.size() - 1);
		// TODO Auto-generated method stub
		
	}

	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex) throws EncryptProcessFailedException {
		return EncryptMessage(msg, keyIndex, MyAESParameterSpec.getInstance(new String[] {"AES"}));
	}

	@Override
	public long DecryptMessage(MyMessage msg) throws DecryptProcessFailedException {
		// TODO Auto-generated method stub
		if(!isSatisfied(msg))
			throw new DecryptProcessFailedException();
		try {
			MyAESParameterSpec nSpec = (MyAESParameterSpec)msg.getAlgorithmParameterSpec();
			cipher = Cipher.getInstance(nSpec.getParameter()[0] + "/" 
					+ nSpec.getParameter()[1] + "/" + nSpec.getParameter()[2]);
			
			if(nSpec.getParameter()[1].equals("ECB"))
				cipher.init(Cipher.DECRYPT_MODE, keyManager.keyList.get(msg.getKeyIndex()));
			else
				cipher.init(Cipher.DECRYPT_MODE, keyManager.keyList.get(msg.getKeyIndex()),
					new IvParameterSpec(((String)nSpec.getParameter()[3]).getBytes()));
					
			long st = System.nanoTime();
			byte[] rebuiltMessage = cipher.doFinal(msg.getCipherMessage());
			long result = System.nanoTime() - st;
			
			msg.setRebuiltMessage(rebuiltMessage, result);
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DecryptProcessFailedException();
		}
	}

	@Override
	public boolean isSatisfied(MyMessage msg) {
		if(msg.getAlgorithmIndex() != 0 && msg.getAlgorithmIndex() != AlgorithmIndex)
			return false;
		return true;
	}

	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex, MyAlgorithmParameterSpec nSpec)
			throws EncryptProcessFailedException {
		if(!isSatisfied(msg))
			throw new EncryptProcessFailedException();
		try {
			
			cipher = Cipher.getInstance(nSpec.getParameter()[0] + "/" 
			+ nSpec.getParameter()[1] + "/" + nSpec.getParameter()[2]);
			
			byte[] plainMessage = msg.getPlainMessage().getBytes();
			byte[] cipherMessage;
			if(nSpec.getParameter()[1].equals("ECB"))
				cipher.init(Cipher.ENCRYPT_MODE, keyManager.keyList.get(keyIndex));
			else
				cipher.init(Cipher.ENCRYPT_MODE, keyManager.keyList.get(keyIndex),
					new IvParameterSpec(((String)nSpec.getParameter()[3]).getBytes()));
			long st = System.nanoTime();
			cipherMessage = cipher.doFinal(plainMessage);
			long result = System.nanoTime() - st;
			msg.setCipherMessage(cipherMessage, AlgorithmIndex, keyIndex, result, nSpec);
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptProcessFailedException();
		}
	}
	
	
	
}
