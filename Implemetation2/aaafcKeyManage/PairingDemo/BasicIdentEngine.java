package aaafcKeyManage.PairingDemo;

import java.security.spec.InvalidParameterSpecException;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

/**
 * This is a engine class under the structure of BilinearPairingEngine interface.
 * @author aaafc
 *
 */
public class BasicIdentEngine implements BilinearPairingEngine{
	// define variables for parameters
	// P: Generator Element, s: master-key, Ppub: sP
	// G1: Additive field, G2: multiplicative field, Fq: finite field
	private Element P, s, Ppub;
	private Field G1, G2, Fq;
	
	// define variables for public key(Qid) and secret key(Qs)
	private Element Qid, Qs;
	
	// define variables for transmission
	// r: Random element in Fq, T1: e(Qid, Ppub) ^ r, V:rP
	// T2: e(V, Qs)
	private Element r, T1, V, T2;
	
	// define pairing engine
	private PairingCurveParam curveParam;
	private PairingParameters parameters = null;
	private Pairing pairing;
	
	// define data storage for the engine
	private byte[] plainMessage = null;
	private byte[] cipherMessage;
	private byte[] rebuiltMessage;
	
	// define log file for testing the encrypting and decrypting processes.
	private final String[] logs = {
			"setup", "extract", "encrypt", "decrypt",
			"InitPairing", "EncryptPairing", "DecryptPairing"};
	private long timeLog[] = new long[7];
	
	public BasicIdentEngine()
	{
	}
	
	
	private void setup()
	{
		long st = System.currentTimeMillis();
		pairing = PairingFactory.getPairing(parameters);
		timeLog[4] = System.currentTimeMillis() - st;
		G1 = pairing.getG1();
		G2 = pairing.getGT();
		Fq = pairing.getZr();
		P = G1.newRandomElement().getImmutable();
		s = Fq.newRandomElement().getImmutable(); // generate master-key
		Ppub = P.mulZn(s).getImmutable();
		timeLog[0] = System.currentTimeMillis() - st;
	}
	
	private void extractSecretKey()
	{
		long st = System.currentTimeMillis();
		Qid = G1.newElement().setFromHash("test".getBytes(), 0, 4).getImmutable();
		Qs = Qid.mulZn(s).getImmutable();
		timeLog[1] = System.currentTimeMillis() - st;
	}
	
	private void encrypt()
	{
		long st = System.currentTimeMillis();
		r = Fq.newRandomElement().getImmutable();
		V = P.mulZn(r).getImmutable();
		
		long pairingst = System.currentTimeMillis();
		T1 = pairing.pairing(Ppub, Qid).powZn(r).getImmutable();
		timeLog[5] = System.currentTimeMillis() - st;
		this.cipherMessage = dataModulusAdd(plainMessage, T1.toBytes());
		timeLog[2] = System.currentTimeMillis() - st;
	}
	
	private void decrypt()
	{
		long st = System.currentTimeMillis();
		T2 = pairing.pairing(V, Qs);
		timeLog[6] = System.currentTimeMillis() - st;
		this.rebuiltMessage = dataModulusAdd(cipherMessage, T2.toBytes());
		timeLog[3] = System.currentTimeMillis() - st;
	}
	
	
	/**
	 * A method to do modulus add to two byte arrays.
	 *	that is: result = data + module.
	 *	if data is longer than module, reuse module for several times 
	 *	until data meets the end.<br>
	 *	i.e. data = {0, 0, 0, 0, 0, 0, 0}, module = {2, 3, 4}
	 *    than result = {2, 3, 4, 2, 3, 4, 2}
	 *   <br><br>
	 *  i.e.2 data = {0, 0, 0}, module = {1, 2, 3, 4, 5}
	 *    than result = {1, 2, 3}
	 *    
	 * @param data
	 * 		byte array which seen as the first part of modulus addition.
	 * @param module
	 * 		byte array which seen as the second part of modulus addition.
	 */
	private byte[] dataModulusAdd(byte[] data, byte[] module) 
	{
		int dl = data.length;
		int ml = module.length;
		int rnd = dl / ml;
		int rmn = dl % ml;
		
		byte[] result = new byte[dl];	
		int pointer = 0;
		
		for(int i = 0; i < rnd; i++)
		{
			for(int j = 0; j < ml; j++)
			{
				pointer = j + i * ml;
				result[pointer] = (byte)(data[pointer] ^ module[j]);
			}
		}
		
		for(int j = 0; j < rmn; j++)
		{
			pointer = j + ml * rnd;
			result[pointer] = (byte)(data[pointer] ^ module[j]);
		}
		return result;
	}
	
	@Override
	public void init(PairingParameters parameters, String plainMessage){
		this.curveParam = null;
		this.parameters = parameters;
		this.plainMessage = plainMessage.getBytes();
	}
	
	@Override
	public void init(String path, String plainMessage)
	{
		this.curveParam = null;
		this.parameters = PairingFactory.getPairingParameters(path);
		this.plainMessage = plainMessage.getBytes();
	}
	
	public void init(PairingCurveParam curveParam, String plainMessage)
	{
		this.curveParam = curveParam.duplicate();
		this.parameters = this.curveParam.generatePairingParameters();
		this.plainMessage = plainMessage.getBytes();
	}

	@Override
	public void doProcess() throws Exception {
		if(this.parameters == null || this.plainMessage == null)
			throw new Exception("Pairing Process Failed for null parameter or plainMsg.");
		setup();
		extractSecretKey();
		encrypt();
		decrypt();
	}

	@Override
	public PairingLog extractPairingLog() throws LoggingException{
		if(this.parameters == null || this.plainMessage == null)
			throw new LoggingException("Log Extraction failed: Not initialized.");
		if(this.rebuiltMessage == null)
			throw new LoggingException("Log Extraction failed: Not processed");
		String[] logData = {timeLog[0] + "ms", 
				timeLog[1] + "ms", timeLog[2] + "ms", timeLog[3] + "ms", 
				timeLog[4] + "ms", timeLog[5] + "ms", timeLog[6] + "ms"};
		
		return new PairingLog(this.plainMessage, this.cipherMessage, this.rebuiltMessage,
				this.logs, logData, this.curveParam);
	}
	
}
