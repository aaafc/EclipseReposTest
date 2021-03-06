package aaafcKeyManage.PairingDemo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is a class for storing the log files for Pairing engine.
 * @author aaafc
 *
 */
public class PairingLog {
	
	private byte[] plainMessage;
	private byte[] cipherMessage;
	private byte[] rebuiltMessage;
	
	// define log file for testing the encrypting and decrypting processes.
	private String[] logs;
	private String[] logData;
	
	private PairingCurveParam curveParam;
	
	public PairingLog(byte[] pm, byte[] cm, byte[] rm,
			String[] logs, String[] logData, PairingCurveParam curveParam)
	{
		this.plainMessage = pm;
		this.cipherMessage = cm;
		this.rebuiltMessage = rm;
		this.logs = logs;
		this.logData = logData;
		this.curveParam = curveParam;
	}
	
	public String[] getLogNames()
	{
		return logs.clone();
	}
	
	public String[] getLogData()
	{
		return logData.clone();
	}
	
	/**
	 * Extract logs from PairingLog objects to a specific path.
	 * @param pairinglogs
	 * 		An array store the PairingLog objects to be extracted.
	 * @param path
	 * 		A String represent the path to be extracted.
	 * @throws LoggingException
	 */
	public static void extractLog(PairingLog[] pairinglogs, String path) 
			throws LoggingException
	{
		File f = new File(path);
		if(f.exists())
			throw new LoggingException("Exception in logging: File already exists!");
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			for(int i = 0; i < pairinglogs.length; i++)
			{
				if(pairinglogs[i] == null)
					continue;
				for(int j = 0; j < pairinglogs[i].logs.length; j++)
				{
					bw.write(pairinglogs[i].logs[j] + "\t");
				}
				bw.newLine();
				for(int j = 0; j < pairinglogs[i].logs.length; j++)
				{
					bw.write(pairinglogs[i].logData[j] + "\t");	
				}
				bw.newLine();
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Extract logs from PairingLog objects to a specific path, along with 
	 * the parameters of the curve. <br>
	 * Only an engine generated by PairingCurveParam object could extract curve info.
	 * @param pairinglogs
	 * 		An array store the PairingLog objects to be extracted.
	 * @param path
	 * 		A String represent the path to be extracted.
	 * @throws LoggingException
	 */
	public static void extractLogWithCurveInfo(PairingLog[] pairinglogs, String path)
		throws LoggingException
	{
		File f = new File(path);
		if(f.exists())
			throw new LoggingException("Exception in logging: File already exists!");
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			for(int i = 0; i < pairinglogs.length; i++)
			{
				if(pairinglogs[i] == null)
					continue;
				if(pairinglogs[i].curveParam != null)
					bw.write("Type\t" + pairinglogs[i].curveParam.CurveParamCharacteristics[0]
							+ "\t" + pairinglogs[i].curveParam.CurveParamCharacteristics[1] + "\t");
				for(int j = 0; j < pairinglogs[i].logs.length; j++)
				{
					bw.write(pairinglogs[i].logs[j] + "\t");
				}
				bw.newLine();
				if(pairinglogs[i].curveParam != null)
					bw.write(pairinglogs[i].curveParam.getCurveTypeName() + "\t" 
							+ pairinglogs[i].curveParam.CurveGenerateParams[0]
							+ "\t" + pairinglogs[i].curveParam.CurveGenerateParams[1] + "\t");
				for(int j = 0; j < pairinglogs[i].logs.length; j++)
				{
					bw.write(pairinglogs[i].logData[j] + "\t");
				}
				bw.newLine();
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LoggingException(e.getMessage());
		}
		
	}
	
}
