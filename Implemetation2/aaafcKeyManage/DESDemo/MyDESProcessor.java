package aaafcKeyManage.DESDemo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.RSADemo.MyRSAParameterSpec;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessor;

/**
 * 
 * @author aaafc
 *
 */
public class MyDESProcessor implements MyProcessor {

	public static final int AlgorithmIndex = 3;

	public MyDESKeyManager keyManager = null;
	private Cipher cipher = null;

	public MyDESProcessor() {
		keyManager = new MyDESKeyManager();
		try {
			keyManager.generateKey();
		} catch (GenerateKeyFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long EncryptMessage(MyMessage msg) throws EncryptProcessFailedException {
		return this.EncryptMessage(msg, this.keyManager.keyList.size() - 1);
	}

	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex) throws EncryptProcessFailedException {
		MyDESParameterSpec nSpec = MyDESParameterSpec.getInstance(new String[] {"DES"});
		return EncryptMessage(msg, keyIndex, nSpec);
	}

	@Override
	public long DecryptMessage(MyMessage msg) throws DecryptProcessFailedException {
		if (!isSatisfied(msg))
			throw new DecryptProcessFailedException();
		try {
			MyDESParameterSpec nSpec = (MyDESParameterSpec)msg.getAlgorithmParameterSpec();
			cipher = Cipher.getInstance(nSpec.getParameter()[0] + "/" +
					nSpec.getParameter()[1] + "/" + nSpec.getParameter()[2]);
			// System.out.println("This is msg.ge: " + msg.getKeyIndex());
			if(nSpec.getParameter()[1].equals("ECB"))
				cipher.init(Cipher.DECRYPT_MODE, keyManager.keyList.get(msg.getKeyIndex()));
			else
				cipher.init(Cipher.DECRYPT_MODE, keyManager.keyList.get(msg.getKeyIndex()),
					new IvParameterSpec(((String)nSpec.getParameter()[3]).getBytes()));
			// cipher.update(msg.getCipherMessage());

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
		if (msg.getAlgorithmIndex() != AlgorithmIndex && msg.getAlgorithmIndex() != 0)
			return false;
		return true;
	}

	@Override
	public long EncryptMessage(MyMessage msg, int keyIndex, MyAlgorithmParameterSpec nSpec)
			throws EncryptProcessFailedException {
		try {
			if (!isSatisfied(msg) || !nSpec.getParameter()[0].equals("DES"))
				throw new EncryptProcessFailedException();
			
			//generate a cipher object determined by parameterSpec
			cipher = Cipher.getInstance(nSpec.getParameter()[0] + "/"
						+ nSpec.getParameter()[1] + "/" + nSpec.getParameter()[2]);
			
			// System.out.println("This is keyIn: " + KeyIndex);
			if(nSpec.getParameter()[1].equals("ECB"))
				cipher.init(Cipher.ENCRYPT_MODE, keyManager.keyList.get(keyIndex));
			else
				cipher.init(Cipher.ENCRYPT_MODE, keyManager.keyList.get(keyIndex),
					new IvParameterSpec(((String)nSpec.getParameter()[3]).getBytes()));

			// cipher.update(msg.getPlainMessage().getBytes());

			byte[] cipherMessage;

			byte[] data = msg.getPlainMessage().getBytes();

			// Process the plain message and calculate the time cost.
			long st = System.nanoTime();
			cipherMessage = cipher.doFinal(data);
			long result = System.nanoTime() - st;

			msg.setCipherMessage(cipherMessage, AlgorithmIndex, keyIndex, result, nSpec);

			return result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EncryptProcessFailedException();
		}
	}

}
