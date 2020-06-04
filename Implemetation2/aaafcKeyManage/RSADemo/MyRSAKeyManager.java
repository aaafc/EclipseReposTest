package aaafcKeyManage.RSADemo;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyKeyManager;
import aaafcKeyManage.main.MyProcessor;

/**
 * This is a class for manage the key pairs for RSA algorithm.
 * @author aaafc
 *
 */
public class MyRSAKeyManager implements MyKeyManager{
	
	ArrayList<RSAPrivateKey> privateKeys = new ArrayList<RSAPrivateKey>();
	ArrayList<RSAPublicKey> publicKeys = new ArrayList<RSAPublicKey>();
	
	private KeyPairGenerator keyPairGen;
	
	public MyRSAKeyManager()
	{
		this.init();
	}
	
	
	private void init()
	{
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	/**
	 * Generate a pair of RSA key pair at default parameter.
	 *  The same as generateKey(1024);
	 */
	@Override
	public long generateKey() throws GenerateKeyFailedException {
		return this.generateKey(1024);
	}
	
	/**
	 * Generate a pair of RSA key pair to the last index of the key list.
	 * @param keySize 
	 * 			The size of key pair modulus.
	 * @return	the time of generation in nanosecond.
	 * @throws GenerateKeyFailedException
	 */
	public long generateKey(int keySize) throws GenerateKeyFailedException {
		
		try {
			return generateKey(MyRSAParameterSpec.getInstance(new String[] {"RSA", "" + keySize}));
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new GenerateKeyFailedException();
		}
		
	}
	
	/**
	 * Remove a key pair by a specific index of key list
	 * @param index
	 * 		The index of the target key pair in the list
	 */
	public void removeKey(int index)
	{
		if(privateKeys.size() != publicKeys.size())
			return;
		privateKeys.remove(index);
		publicKeys.remove(index);
	}
	
	/**
	 * Remove a key pair by default parameter.
	 * 		The same as removeKey(list.size() - 1);
	 */
	public void removeKey()
	{
		removeKey(privateKeys.size() - 1);
	}

	@Override
	public String getKeyParam(int keyIndex) {
		return ("KeySize: " + this.privateKeys.get(keyIndex).getModulus().bitLength() + "bits\n"
		+ "Private Key Exp Length: " + this.privateKeys.get(keyIndex).getPrivateExponent().bitLength() + "bits\n"
		+ "Public Key Exp Length: " + this.publicKeys.get(keyIndex).getPublicExponent().bitLength() + "bits\n");
	}


	@Override
	public long generateKey(MyAlgorithmParameterSpec nSpec) throws GenerateKeyFailedException {
		
		if(keyPairGen == null)
			throw new GenerateKeyFailedException();
		int keySize = (Integer)nSpec.getParameter()[1];
		keyPairGen.initialize(keySize);
		
		/*
		 * Generating Key pair. Calculating the time cost.
		 */
		long st = System.nanoTime();
		KeyPair keyPair = keyPairGen.genKeyPair();
		long result = System.nanoTime() - st;
		
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		
		publicKeys.add(publicKey);
		privateKeys.add(privateKey);
		// TODO Auto-generated method stub
		return result;
	}
	
	
	
	
}
