package aaafcKeyManage.main;

import aaafcKeyManage.Exception.DecryptProcessFailedException;
import aaafcKeyManage.Exception.EncryptProcessFailedException;

public interface MyProcessor {
	
	/**
	 * Process a message by specific ParameterSpec.
	 * @param msg
	 * 			The message object to be processed
	 * @param keyIndex TODO
	 * @param nSpec
	 * 			The parameterSpec to be used
	 * @return
	 * 			Time cost in nanoseconds
	 * @throws EncryptProcessFailedException
	 */
	public abstract long EncryptMessage(MyMessage msg, int keyIndex, MyAlgorithmParameterSpec nSpec)
			throws EncryptProcessFailedException;
	
	/**
	 * Process a specific message and store the results back to the object. Uses the last available key.
	 * @param msg
	 * 			The message object to be processed
	 * @return	Time cost in nanoseconds.
	 * @throws EncryptProcessFailedException
	 */
	public abstract long EncryptMessage(MyMessage msg) throws EncryptProcessFailedException;
	
	/**
	 * Process a specific message object and store the results back to the object. Uses a specific key.
	 * @param msg
	 * 			The message object to be processed.
	 * @param KeyIndex
	 * 			The index where specific key object is stored in the list. 
	 * @return  Time cost in nanoseconds.
	 */
	public abstract long EncryptMessage(MyMessage msg, int KeyIndex) throws EncryptProcessFailedException;
	
	/**
	 * DeProcess a specific message object and store the results back to the object.
	 * @param msg
	 * 			The message to be deProcessed.
	 * @return	The time cost in nanoseconds.
	 * @throws DecryptProcessFailedException
	 */
	public abstract long DecryptMessage(MyMessage msg) throws DecryptProcessFailedException;
	
	/**
	 * Specify whether the message is available to be processed or not.
	 * @param msg
	 * 			The message object to be checked.
	 * @return	true for available; false for not available.
	 */
	public abstract boolean isSatisfied(MyMessage msg);
	
}
