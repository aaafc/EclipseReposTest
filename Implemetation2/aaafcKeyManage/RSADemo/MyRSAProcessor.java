package aaafcKeyManage.RSADemo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessor;

/**
 * This is a class to generate an process object for RSA algorithm.
 * @author aaafc
 *
 */
public class MyRSAProcessor implements MyProcessor{
	
	public static final int AlgorithmIndex = 1;
	
	public MyRSAKeyManager keyManager = null;
	
	private Cipher cipher = null;
	
	public MyRSAProcessor()
	{
		this.keyManager = new MyRSAKeyManager();
		try {
			cipher = Cipher.getInstance("RSA");
			this.keyManager.generateKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex) throws EncryptProcessFailedException{
		// TODO Auto-generated method stub
				try {
					return this.EncryptMessage(msg, keyIndex, null);
				} catch (Exception e)
				{
					e.printStackTrace();
					throw new EncryptProcessFailedException();
				}
				
	}

	@Override
	public long EncryptMessage(MyMessage msg) throws EncryptProcessFailedException {
		return this.EncryptMessage(msg, this.keyManager.privateKeys.size() - 1, null);
	}
	
	@Override
	public long DecryptMessage(MyMessage msg) throws DecryptProcessFailedException {
		if(!isSatisfied(msg))
			throw new DecryptProcessFailedException();
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, keyManager.privateKeys.get(msg.getKeyIndex()));
			
			byte[] data = msg.getCipherMessage();
			byte[] rebuiltMessage = new byte[0];
			byte[] tempRebuiltMessage;
			int byteLimit = ((int)keyManager.publicKeys.get(msg.getKeyIndex()).getModulus().bitLength() - 1) / 8 + 1;
			int loops = (data.length - 1) / byteLimit + 1;
			long result = 0;
			
			for(int i = 0; i < loops; i++)
			{
				long st = System.nanoTime();
				tempRebuiltMessage = cipher.doFinal(data, i * byteLimit,
						i == (loops - 1) ? (data.length - i * byteLimit) : byteLimit);
				result += System.nanoTime() - st;
				rebuiltMessage = mergeByteArray(rebuiltMessage, tempRebuiltMessage);
			}
			
			msg.setRebuiltMessage(rebuiltMessage, result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isSatisfied(MyMessage msg) {
		if(this.keyManager == null || this.cipher == null)
			return false;
		if(msg.getAlgorithmIndex() != AlgorithmIndex && msg.getAlgorithmIndex() != 0)
			return false;
		if(this.keyManager.privateKeys.isEmpty() || this.keyManager.publicKeys.isEmpty())
			return false;
		return true;
	}

	
	
	private static byte[] mergeByteArray(byte[] first, byte[] latter)
	{
		byte[] result = new byte[first.length + latter.length];
		for(int i = 0; i < first.length; i++)
			result[i] = first[i];
		for(int i = 0; i < latter.length; i++)
			result[i + first.length] = latter[i];
		return result;
	}


	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex, MyAlgorithmParameterSpec nSpec)
			throws EncryptProcessFailedException {
		if(!isSatisfied(msg))
		{
			throw new EncryptProcessFailedException();
		}
		
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, keyManager.publicKeys.get(keyIndex));
			
			byte[] data = msg.getPlainMessage().getBytes();
			byte[] cipherMessage = new byte[0];
			byte[] tempCipherMessage;
			int byteLimit = ((int)keyManager.publicKeys.get(keyIndex).getModulus().bitLength() - 1) / 8 - 10;
			int loops = (data.length - 1) / byteLimit + 1;
			long result = 0;
			//Encrypt the message. Calculate the time cost.
			for(int i = 0; i < loops; i++)
			{
				long st = System.nanoTime();
				tempCipherMessage = cipher.doFinal(data, i * byteLimit,
						i == (loops - 1) ? (data.length - i * byteLimit) : byteLimit);
				result += System.nanoTime() - st;
				cipherMessage = mergeByteArray(cipherMessage, tempCipherMessage);
			}
			//Store the Cipher, index for the algorithm and the index
			//  for the key pair into MyMessage instance.
			msg.setCipherMessage(cipherMessage, AlgorithmIndex, keyIndex,
					result, null);
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
