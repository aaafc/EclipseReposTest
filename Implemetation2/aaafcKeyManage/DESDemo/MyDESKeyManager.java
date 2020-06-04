package aaafcKeyManage.DESDemo;

import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyKeyManager;
import aaafcKeyManage.main.MyProcessUtil;

/**
 * 
 * @author aaafc
 *
 */
public class MyDESKeyManager implements MyKeyManager {

	public ArrayList<SecretKey> keyList = new ArrayList<SecretKey>();

	private SecretKeyFactory keyGen = null;

	public MyDESKeyManager() {
		try {
			keyGen = SecretKeyFactory.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long generateKey() throws GenerateKeyFailedException {
		return generateKey(MyDESParameterSpec.getInstance(new String[] {}));
		// TODO Auto-generated method stub
	}

	@Override
	public String getKeyParam(int keyIndex) {
		return null;
	}

	@Override
	public void removeKey() {
		removeKey(keyList.size() - 1);
		// TODO Auto-generated method stub

	}

	public void removeKey(int index) {
		keyList.remove(index);
	}

	@Override
	public long generateKey(MyAlgorithmParameterSpec nSpec) throws GenerateKeyFailedException {
		try {
			if (keyGen == null || !nSpec.getParameter()[0].equals("DES")) {
				throw new GenerateKeyFailedException();
			}
			DESKeySpec keySpec;

			keySpec = new DESKeySpec(((String)nSpec.getParameter()[3]).getBytes());

			SecretKey k;
			
			
			long st = System.nanoTime();
			k = keyGen.generateSecret(keySpec);
			long result = System.nanoTime() - st;
			
			keyList.add(k);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new GenerateKeyFailedException();
		}

	}

}
