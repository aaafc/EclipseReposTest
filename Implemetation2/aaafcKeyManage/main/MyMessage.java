package aaafcKeyManage.main;
import java.security.Key;
import java.util.Base64;

public class MyMessage {
	
	
	private String plainMessage;
	private byte[] cipherMessage = null;
	private byte[] rebuiltMessage = null;
	private int AlgorithmIndex;
	private int decryptKeyIndex;
	private MyAlgorithmParameterSpec parameter = null;
	private long enTimeLog;
	private long deTimeLog;
	
	public MyMessage(String s)
	{
		this.plainMessage = s;
		this.AlgorithmIndex = 0;
	}
	
//	public MyMessage(byte[] NplainMessage)
//	{
//		this.plainMessage = NplainMessage.clone();
//	}
//	
	public String getPlainMessage()
	{
		return this.plainMessage;
	}
	
	public String toString()
	{
		return "Message Attributes: Plain-\"" + this.plainMessage + "\" Cipher-" 
				+ this.getCipherMessageString();
	}
	
	public byte[] getCipherMessage()
	{
		return this.cipherMessage;
	}
 	
	public String getCipherMessageString()
	{
		return (this.cipherMessage == null ? "null" : ("\"" + new String(this.cipherMessage) + "\""));
	}
	
	public void setCipherMessage(byte[] cipherMsg, int algorithmIndex, int keyIndex, long enT, MyAlgorithmParameterSpec nSpec)
	{
		
		this.cipherMessage = cipherMsg.clone();
		this.AlgorithmIndex = algorithmIndex;
		this.decryptKeyIndex = keyIndex;
		this.enTimeLog = enT;
		setAlgorithmParameterSpec(nSpec);
	}
	
	public String getBase64DecodedCipher()
	{
		return "Formatted In Base64: " + Base64.getEncoder().encodeToString(cipherMessage);
	}
	
	public void setRebuiltMessage(byte[] rebuiltMsg, long deT)
	{
		this.rebuiltMessage = rebuiltMsg.clone();
		this.deTimeLog = deT;
	}
	
	public byte[] getRebuiltMessage() {
		return this.rebuiltMessage;
	}
	
	
	public long getEnTimeLog()
	{
		return this.enTimeLog;
	}
	
	public long getDeTimeLog()
	{
		return this.deTimeLog;
	}
	
	public int getAlgorithmIndex()
	{
		return this.AlgorithmIndex;
	}
	
	public int getKeyIndex()
	{
		return this.decryptKeyIndex;
	}
	
	public void setAlgorithmParameterSpec(MyAlgorithmParameterSpec nSpec)
	{
		this.parameter = nSpec;
	}
	
	public MyAlgorithmParameterSpec getAlgorithmParameterSpec()
	{
		return this.parameter;
	}
}
