package aaafcKeyManage.ECIESDemo;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.EllipticCurve;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.main.AlgorithmIndexs;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessor;

/**
 * 
 * @author aaafc
 *
 */
public class MyECIESProcessor implements MyProcessor {

	
	private static final int AlgorithmIndex = 5;
	private Cipher cipher;
	public MyECIESKeyManager keyManager;
	
	public MyECIESProcessor()
	{
		keyManager = new MyECIESKeyManager();
		try {
			keyManager.generateKey();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex, MyAlgorithmParameterSpec nSpec)
			throws EncryptProcessFailedException {
		// TODO Auto-generated method stub
		if(!isSatisfied(msg))
		{
			throw new EncryptProcessFailedException();
		}
		try {
			cipher = Cipher.getInstance("ECIES", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, keyManager.publicKeys.get(keyIndex));
			byte[] plainMessage = msg.getPlainMessage().getBytes();
			byte[] cipherMessage;
			
			long st = System.nanoTime();
			cipherMessage = cipher.doFinal(plainMessage);
			long result = System.nanoTime() - st;
			
			msg.setCipherMessage(cipherMessage, AlgorithmIndex, keyIndex, result, nSpec);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EncryptProcessFailedException("In ECIES En: " + e.getMessage());
		}
		
	}

	@Override
	public long EncryptMessage(MyMessage msg) throws EncryptProcessFailedException {
		return EncryptMessage(msg, this.keyManager.privateKeys.size() - 1, null);
	}

	@Override
	public long EncryptMessage(MyMessage msg, int KeyIndex) throws EncryptProcessFailedException {
		return EncryptMessage(msg, KeyIndex, null);
	}

	@Override
	public long DecryptMessage(MyMessage msg) throws DecryptProcessFailedException {
		if(!isSatisfied(msg))
		{
			throw new DecryptProcessFailedException("In ECIES De: Not Satisfied."
					+ " This may because of the message is already been processed by another algorithm.");
		}
		try {
			cipher = Cipher.getInstance("ECIES", "BC");
			cipher.init(Cipher.DECRYPT_MODE, keyManager.privateKeys.get(msg.getKeyIndex()));
			byte[] cipherMessage = msg.getCipherMessage();
			byte[] rebuiltMessage;
			
			long st = System.nanoTime();
			rebuiltMessage = cipher.doFinal(cipherMessage);
			long result = System.nanoTime() - st;
			
			msg.setRebuiltMessage(rebuiltMessage, result);
			
			
			return result;
		} catch (Exception e) {
			throw new DecryptProcessFailedException("In ECIES De: " + e.getMessage());
		}
	}

	@Override
	public boolean isSatisfied(MyMessage msg) {
		if(msg.getAlgorithmIndex() != AlgorithmIndex && msg.getAlgorithmIndex() != 0)
			return false;
		// TODO Auto-generated method stub
		return true;
	}
}
