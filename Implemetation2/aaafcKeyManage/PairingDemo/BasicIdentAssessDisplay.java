package aaafcKeyManage.PairingDemo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;

import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyProcessAssessDisplay;
import aaafcKeyManage.main.MyProcessUtil;

/**
 * This is a frame class for generating assessment frame for BasicIdent algorithm.
 * @author aaafc
 *
 */
public class BasicIdentAssessDisplay extends JFrame implements MyProcessAssessDisplay{
	private static BasicIdentAssessDisplay theInstance = null;
	public static BasicIdentAssessDisplay getInstance()
	{
		if(theInstance == null)
			theInstance = new BasicIdentAssessDisplay();
		return theInstance;
	}
	
	private JTextField messageLengthField;
	private JTable dataOutputTable;
	
	private DefaultTableModel tableModel;
	private Vector<Vector<String>> tableModelVector;
	private Vector<String> tableColumnVector;
	private JButton doProcess;
	
	private JComboBox<String> ecStandardBox;
	private JLabel eCStandardLabel;
	private JTextField curveParam1;
	private JTextField curveParam2;
	private JLabel param1Label;
	private JLabel param2Label;
	
	private BasicIdentAssessDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 450);
		setLocation(200, 100);
		getContentPane().setLayout(null);
		
		JLabel lblMessageL = new JLabel("Message Length");
		lblMessageL.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageL.setFont(new Font("Arial", Font.BOLD, 16));
		lblMessageL.setBounds(10, 245, 145, 40);
		getContentPane().add(lblMessageL);
		
		messageLengthField = new JTextField();
		messageLengthField.setText("100");
		messageLengthField.setFont(new Font("Arial", Font.BOLD, 16));
		messageLengthField.setBounds(165, 246, 66, 40);
		messageLengthField.setHorizontalAlignment(JTextField.RIGHT);
		getContentPane().add(messageLengthField);
		messageLengthField.setColumns(10);
		
		JLabel lblBits_1 = new JLabel("bytes");
		lblBits_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBits_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblBits_1.setBounds(241, 245, 43, 40);
		getContentPane().add(lblBits_1);
		
		doProcess = new JButton("doProcess");
		doProcess.setFont(new Font("Arial", Font.BOLD, 16));
		doProcess.setBounds(72, 334, 133, 40);
		doProcess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					String[] row = new String[12];
					int std = ecStandardBox.getSelectedIndex() + 1;
					std = std / 3 + std;
					int messageL = Integer.parseInt(messageLengthField.getText());
					BasicIdentEngine bie = new BasicIdentEngine();
					PairingCurveParam curveParam = new PairingCurveParam(std, 
							Integer.parseInt(curveParam1.getText()), 
							Integer.parseInt(curveParam2.getText()));
					bie.init(curveParam, MyProcessUtil.randomString(messageL));
					bie.doProcess();
					PairingLog log = bie.extractPairingLog();
					String[] logData = log.getLogData();
					row[0] = "BasicIdent";
					row[1] = curveParam.getCurveTypeName();
					row[2] = curveParam.getCurveParam()[0] + "";
					row[3] = curveParam.getCurveParam()[1] + "";
					row[4] = messageL + " bytes";
					row[5] = logData[0];
					row[6] = logData[1];
					row[7] = logData[2];
					row[8] = logData[3];
					row[9] = logData[4];
					row[10] = logData[5];
					row[11] = logData[6];
					tableModel.addRow(row);
				}catch(NumberFormatException numForExp)
				{
					numForExp.printStackTrace();
					JOptionPane.showMessageDialog(BasicIdentAssessDisplay.this, "Invalid value format.");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(BasicIdentAssessDisplay.this, "Exception: " 
					+ e1.getClass().toString().substring(6) + " is found");
				}
			}
		});
		getContentPane().add(doProcess);
		
		JScrollPane dataOutputTableScroll = new JScrollPane();
		dataOutputTableScroll.setBounds(342, 10, 334, 364);
		getContentPane().add(dataOutputTableScroll);
		
		
		tableModelVector = new Vector<Vector<String>>();
		tableColumnVector = new Vector<String>();

		tableColumnVector.add("AlgorithmName");
		tableColumnVector.add("CurveTypeName");
		tableColumnVector.add("CurveParam1");
		tableColumnVector.add("CurveParam2");
		tableColumnVector.add("MessageLength");
		tableColumnVector.add("setup");
		tableColumnVector.add("extract");
		tableColumnVector.add("encrypt");
		tableColumnVector.add("decrypt");
		tableColumnVector.add("InitPairing");
		tableColumnVector.add("EncryptPairing");
		tableColumnVector.add("DecryptPairing");
		
		tableModel = new DefaultTableModel(tableModelVector, tableColumnVector);
		dataOutputTable = new JTable(tableModel);
		dataOutputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 0; i < tableColumnVector.size(); i++)
		{
			dataOutputTable.getColumnModel().getColumn(i)
				.setPreferredWidth(tableColumnVector.get(i).length() * 10 + 20);
		}
		dataOutputTableScroll.setViewportView(dataOutputTable);
		
		ecStandardBox = new JComboBox<String>();
		ecStandardBox.setFont(new Font("Arial", Font.BOLD, 16));
		ecStandardBox.setBounds(32, 91, 228, 43);
		ecStandardBox.addItem("Curve Type A");
		ecStandardBox.addItem("Curve Type A1");
		ecStandardBox.addItem("Curve Type E");
		ecStandardBox.addItem("Curve Type F");
		getContentPane().add(ecStandardBox);
		
		eCStandardLabel = new JLabel("ECStandard");
		eCStandardLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		eCStandardLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		eCStandardLabel.setBounds(32, 49, 121, 43);
		getContentPane().add(eCStandardLabel);
		
		curveParam1 = new JTextField();
		curveParam1.setFont(new Font("Arial", Font.BOLD, 16));
		curveParam1.setBounds(32, 174, 90, 40);
		getContentPane().add(curveParam1);
		curveParam1.setColumns(10);
		
		curveParam2 = new JTextField();
		curveParam2.setFont(new Font("Arial", Font.BOLD, 16));
		curveParam2.setColumns(10);
		curveParam2.setBounds(170, 174, 90, 40);
		getContentPane().add(curveParam2);
		
		param1Label = new JLabel("param1");
		param1Label.setHorizontalAlignment(SwingConstants.CENTER);
		param1Label.setFont(new Font("Arial", Font.BOLD, 16));
		param1Label.setBounds(32, 140, 90, 28);
		getContentPane().add(param1Label);
		
		param2Label = new JLabel("param2");
		param2Label.setHorizontalAlignment(SwingConstants.CENTER);
		param2Label.setFont(new Font("Arial", Font.BOLD, 16));
		param2Label.setBounds(170, 140, 90, 28);
		getContentPane().add(param2Label);
		setVisible(true);
	}
}
