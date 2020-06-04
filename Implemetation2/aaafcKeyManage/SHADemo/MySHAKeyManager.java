package aaafcKeyManage.SHADemo;

import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyKeyManager;

/**
 * This is the key manager class for SHA algorithm.
 * This class does nothing for SHA-2 standard doesn't require secure keys.
 * @author lenovo
 *
 */
public class MySHAKeyManager implements MyKeyManager{

	/**
	 * This method does nothing 
	 * because SHA-2 standard doesn't require secure keys.
	 */
	@Override
	public long generateKey() throws GenerateKeyFailedException {
		
		return -1;
	}

	/**
	 * This method does nothing
	 * because SHA-2 standard doesn't require secure keys.
	 */
	@Override
	public String getKeyParam(int keyIndex) {
		// TODO Auto-generated method stub
		return "No Key Param for SHA";
	}

	@Override
	public void removeKey() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long generateKey(MyAlgorithmParameterSpec nSpec) throws GenerateKeyFailedException {
		// TODO Auto-generated method stub
		return 0;
	}

}
