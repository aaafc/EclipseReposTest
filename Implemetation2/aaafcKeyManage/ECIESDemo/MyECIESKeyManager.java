package aaafcKeyManage.ECIESDemo;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;

import javax.security.auth.DestroyFailedException;

import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyKeyManager;

/**
 * 
 * @author aaafc
 *
 */
public class MyECIESKeyManager implements MyKeyManager{
	
	public ArrayList<ECPublicKey> publicKeys = new ArrayList<ECPublicKey>();
	public ArrayList<ECPrivateKey> privateKeys = new ArrayList<ECPrivateKey>();
	public ArrayList<ECParameterSpec> ecSpecs = new ArrayList<ECParameterSpec>();
	
	
	@Override
	public long generateKey(MyAlgorithmParameterSpec nSpec) throws GenerateKeyFailedException {
		// TODO Auto-generated method stub
		if(!nSpec.getParameter()[0].equals("ECIES"))
			throw new GenerateKeyFailedException();
		ECParameterSpec ecSpec = null;
		if(nSpec.getParameter()[1].equals("STD"))
			ecSpec = ECNamedCurveTable.getParameterSpec((String)nSpec.getParameter()[2]);
		else if(nSpec.getParameter()[1].equals("COS")) {
			throw new GenerateKeyFailedException("Sorry, CUSTOM parameters for EC algorithm is not currently implemented.");
		}
		else
			throw new GenerateKeyFailedException();
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
			keyGen.initialize(ecSpec);
			ecSpecs.add(ecSpec);
			
			long st = System.nanoTime();
			
			KeyPair kp = keyGen.generateKeyPair();
			
			long result = System.nanoTime() - st;
			
			publicKeys.add((ECPublicKey)kp.getPublic());
			privateKeys.add((ECPrivateKey)kp.getPrivate());
			
			return result;
			
		} catch (Exception e) {
			throw new GenerateKeyFailedException(e.getMessage());
		}
	}

	@Override
	public long generateKey() throws GenerateKeyFailedException {
		// TODO Auto-generated method stub
		return generateKey(MyECIESParameterSpec.getInstance(new String[] {}));
	}
	
	@Override
	public String getKeyParam(int keyIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeKey() {
		// TODO Auto-generated method stub
		ecSpecs.remove(ecSpecs.size() - 1);
		publicKeys.remove(publicKeys.size() - 1);
		privateKeys.remove(privateKeys.size() - 1);
		
	}

}
