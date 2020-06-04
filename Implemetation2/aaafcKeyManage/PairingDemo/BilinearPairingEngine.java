package aaafcKeyManage.PairingDemo;

import it.unisa.dia.gas.jpbc.PairingParameters;

/**
 * This interface is for organize overall structure<br>
 * for Pairing-based algorithms.<br>
 * A successful implementation is shown as below:
 * <p>
 * BilinearPairingEngine example = new A();<br>
 * example.init(param1, plainMessage);<br>
 * example.doProcess();<br>
 * PairingLog log = example.extractPairingLog();<br>
 * <p>
 * And an Object "log" is generated for further extraction.
 * @author aaafc
 *
 */
public interface BilinearPairingEngine {
	
	/**
	 * Initialize the engine by PairingParameters object.
	 * @param parameters
	 * 		A PairingParameters object which defines the curve.
	 * @param plainMessage
	 * 		A String represent the plain message.
	 */
	public void init(PairingParameters parameters, String plainMessage);
	
	/**
	 * Initialize the engine by PairingParameters object.
	 * @param parameterPath
	 * 		A String which defines the path to the file<br>
	 *  	which stores the parameter for the curve.
	 * @param plainMessage
	 * 		A String represent the plain message.
	 */
	public void init(String parameterPath, String plainMessage);
	
	/**
	 * Initialize the engine by PairingParameters object.
	 * @param curveParam
	 * 		A PairingCurveParam object which store the parameters<br>
	 *  to generate a PairingParameters object.
	 * @param plainMessage
	 * 		A String represent the plain message.
	 */
	public void init(PairingCurveParam curveParam, String plainMessage);
	
	/**
	 * Do the whole process for the engine object
	 * @throws Exception
	 */
	public void doProcess() throws Exception;
	
	/**
	 * Extract a PairingLog object which stores the data for the operation.
	 * @return	A PairingLog object
	 * @throws LoggingException
	 */
	public PairingLog extractPairingLog() throws LoggingException;
	
	
	
}
