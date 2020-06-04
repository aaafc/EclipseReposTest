package aaafcKeyManage.PairingDemo;

import java.util.HashMap;

import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a1.TypeA1CurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.e.TypeECurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.f.TypeFCurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pbc.curve.PBCTypeDCurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pbc.curve.PBCTypeGCurveGenerator;

/**
 * This is a class which could store the parameters for generating a <br>
 * PairingParameters object.
 * @author aaafc
 *
 */
public class PairingCurveParam {
	
	public static final int CurveTypeA = 1;
	public static final int CurveTypeA1 = 2;
//	public static final int CurveTypeD = 3;
	public static final int CurveTypeE = 4;
	public static final int CurveTypeF = 5;
//	public static final int CurveTypeG = 6;
	private static HashMap<Integer, String> CurveTypeName;
	
	static {
		CurveTypeName = new HashMap<Integer, String>();
		CurveTypeName.put(CurveTypeA, "Type A");
		CurveTypeName.put(CurveTypeA1, "Type A1");
//		CurveTypeName.put(CurveTypeD, "Type D");
		CurveTypeName.put(CurveTypeE, "Type E");
		CurveTypeName.put(CurveTypeF, "Type F");
//		CurveTypeName.put(CurveTypeG, "Type G");
	}
	
	public int CurveType;
	public int[] CurveGenerateParams = new int[2];
	public String[] CurveParamCharacteristics = new String[2];
	
	/**
	 * The constructor resemble three integers to define the parameters for <br>
	 * the PairingParameters Object.
	 * @param TypeNum
	 * 		This is an object which defines the data for 
	 * @param p1
	 * 		
	 * @param p2
	 */
	public PairingCurveParam(int TypeNum, int p1, int p2)
	{
		if(TypeNum > 6 || TypeNum < 1)
			TypeNum = 1;
		if(TypeNum == 3 || TypeNum == 6)
			TypeNum = 1;
		CurveType = TypeNum;
		CurveGenerateParams[0] = p1;
		CurveGenerateParams[1] = p2;
	}
	
	public PairingParameters generatePairingParameters()
	{
		PairingParametersGenerator ppg = null;
		switch(CurveType)
		{
		case 1:
			ppg = new TypeACurveGenerator(CurveGenerateParams[0], CurveGenerateParams[1]);
			CurveParamCharacteristics[0] = "rbits";
			CurveParamCharacteristics[1] = "qbits";
			break;
		case 2:
			ppg = new TypeA1CurveGenerator(CurveGenerateParams[0], CurveGenerateParams[1]);
			CurveParamCharacteristics[0] = "primes";
			CurveParamCharacteristics[1] = "pBitLen";
			break;
//		case 3:
//			ppg = new PBCTypeDCurveGenerator(CurveGenerateParams[0]);
//			CurveParamCharacteristics[0] = "discrim";
//			CurveParamCharacteristics[1] = "empty";
//			break;
		case 4:
			ppg = new TypeECurveGenerator(CurveGenerateParams[0], CurveGenerateParams[1]);
			CurveParamCharacteristics[0] = "rbits";
			CurveParamCharacteristics[1] = "qbits";
			break;
		case 5:
			ppg = new TypeFCurveGenerator(CurveGenerateParams[0]);
			CurveParamCharacteristics[0] = "rbits";
			CurveParamCharacteristics[1] = "empty";
			break;
//		case 6:
//			ppg = new PBCTypeGCurveGenerator(CurveGenerateParams[0]);
//			CurveParamCharacteristics[0] = "discrim";
//			CurveParamCharacteristics[1] = "empty";
//			break;
			default: 
		}
		
		return ppg.generate();
	}
	
	public String getCurveTypeName()
	{
		return this.CurveTypeName.get(CurveType);
	}
	
	public int[] getCurveParam()
	{
		return this.CurveGenerateParams;
	}
	
	public PairingCurveParam duplicate()
	{
		return new PairingCurveParam(this.CurveType, this.CurveGenerateParams[0], 
				this.CurveGenerateParams[1]);
	}
}
