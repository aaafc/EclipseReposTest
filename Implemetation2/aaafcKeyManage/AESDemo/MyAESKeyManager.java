package aaafcKeyManage.AESDemo;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyKeyManager;
import aaafcKeyManage.main.MyProcessUtil;

/**
 * 
 * @author aaafc
 *
 */
public class MyAESKeyManager implements MyKeyManager{
	
	public ArrayList<SecretKey> keyList = null;
	
	private KeyGenerator keyGen = null;
	
	
	public MyAESKeyManager()
	{
		keyList = new ArrayList<SecretKey>();
		try {
			keyGen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long generateKey() throws GenerateKeyFailedException {
		if (keyGen == null) {
			throw new GenerateKeyFailedException();
		}

		byte[] keySeed = MyProcessUtil.randomBytes(16);
		SecureRandom sr = new SecureRandom(keySeed);
		try {
			
			keyGen.init(128, sr);
			
			SecretKey k;
			long st = System.nanoTime();
			k = keyGen.generateKey();
			long result = System.nanoTime() - st;
			
			keyList.add(k);
			
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new GenerateKeyFailedException();
		}
		
	}

	@Override
	public String getKeyParam(int keyIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeKey() {
		// TODO Auto-generated method stub
		
	}
	
	public void removeKey(int keyIndex) {
		this.keyList.remove(keyIndex);
		
	}

	@Override
	public long generateKey(MyAlgorithmParameterSpec nSpec) throws GenerateKeyFailedException {
		// TODO Auto-generated method stub
		return generateKey();
	}
	
}
