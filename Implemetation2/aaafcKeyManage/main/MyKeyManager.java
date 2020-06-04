package aaafcKeyManage.main;

import aaafcKeyManage.Exception.GenerateKeyFailedException;

public interface MyKeyManager {
	
	public abstract long generateKey(MyAlgorithmParameterSpec nSpec) throws GenerateKeyFailedException;
	
	public abstract long generateKey() throws GenerateKeyFailedException;
	
	public abstract String getKeyParam(int keyIndex);
	
	public abstract void removeKey();
	
}

