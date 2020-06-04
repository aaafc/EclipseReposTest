package aaafcKeyManage.ECIESDemo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;

import aaafcKeyManage.RSADemo.MyRSAAssessDisplay;
import aaafcKeyManage.RSADemo.MyRSAAssessment;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyProcessAssessDisplay;
import aaafcKeyManage.main.MyProcessUtil;

import javax.swing.JComboBox;

/**
 * This is a frame class for Assessment of ECIES algorithm.
 * @author aaafc
 *
 */
public class MyECIESAssessDisplay extends JFrame implements MyProcessAssessDisplay {
	private static MyECIESAssessDisplay theInstance = null;
	public static MyECIESAssessDisplay getInstance()
	{
		if(theInstance == null)
			theInstance = new MyECIESAssessDisplay();
		return theInstance;
	}
	
	private JTextField messageLengthField;
	private JTextField numberOfRoundsField;
	private JTable dataOutputTable;
	
	private DefaultTableModel tableModel;
	private Vector<Vector<String>> tableModelVector;
	private Vector<String> tableColumnVector;
	private JButton doProcess;
	
	private MyECIESAssessment assessment;
	private JComboBox ecStandardBox;
	private JLabel eCStandardLabel;
	
	private MyECIESAssessDisplay() {
		assessment = new MyECIESAssessment();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 450);
		setLocation(200, 100);
		getContentPane().setLayout(null);
		
		JLabel lblMessageL = new JLabel("Message Length");
		lblMessageL.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageL.setFont(new Font("Arial", Font.BOLD, 16));
		lblMessageL.setBounds(8, 201, 145, 40);
		getContentPane().add(lblMessageL);
		
		messageLengthField = new JTextField();
		messageLengthField.setText("100");
		messageLengthField.setFont(new Font("Arial", Font.BOLD, 16));
		messageLengthField.setBounds(163, 202, 66, 40);
		messageLengthField.setHorizontalAlignment(JTextField.RIGHT);
		getContentPane().add(messageLengthField);
		messageLengthField.setColumns(10);
		
		JLabel lblBits_1 = new JLabel("bytes");
		lblBits_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBits_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblBits_1.setBounds(240, 201, 43, 40);
		getContentPane().add(lblBits_1);
		
		JLabel processRoundsLabel = new JLabel("Process Rounds");
		processRoundsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		processRoundsLabel.setFont(new Font("Arial", Font.BOLD, 16));
		processRoundsLabel.setBounds(10, 274, 145, 40);
		getContentPane().add(processRoundsLabel);
		
		numberOfRoundsField = new JTextField();
		numberOfRoundsField.setText("10");
		numberOfRoundsField.setHorizontalAlignment(SwingConstants.RIGHT);
		numberOfRoundsField.setFont(new Font("Arial", Font.BOLD, 16));
		numberOfRoundsField.setColumns(10);
		numberOfRoundsField.setBounds(163, 274, 66, 40);
		getContentPane().add(numberOfRoundsField);
		
		doProcess = new JButton("doProcess");
		doProcess.setFont(new Font("Arial", Font.BOLD, 16));
		doProcess.setBounds(72, 334, 133, 40);
		doProcess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					String[] row = new String[8];
					String std = ecStandardBox.getSelectedItem().toString();
					int messageL = Integer.parseInt(messageLengthField.getText());
					int rounds = Integer.parseInt(numberOfRoundsField.getText());
					long[][] temp = MyECIESAssessDisplay.this.assessment.
							encryptAndDecryptProcess(new String[]{MyProcessUtil.randomString(messageL)}, 
									new MyAlgorithmParameterSpec[] {MyAlgorithmParameterSpec.getInstance("ECIES/STD/" + std)}, 
									new int[] {rounds});
					ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(std);
					ECCurve c = ecSpec.getCurve();
					row[0] = "ECIES/" + std;
					row[1] = c.getOrder().toString();
					row[2] = c.getA().toBigInteger().toString();
					row[3] = c.getB().toBigInteger().toString();
					row[4] = messageL + " bytes";
					row[5] = rounds + " loops";
					row[6] = temp[0][0] + " ns";
					row[7] = temp[1][0] + " ns";
					tableModel.addRow(row);
				}catch(NumberFormatException numForExp)
				{
					numForExp.printStackTrace();
					JOptionPane.showMessageDialog(MyECIESAssessDisplay.this, "Invalid value format.");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(MyECIESAssessDisplay.this, "Exception: " 
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

		tableColumnVector.add("CurveName");
		tableColumnVector.add("CurveOrder");
		tableColumnVector.add("CurveA");
		tableColumnVector.add("CurveB");
		tableColumnVector.add("MessageLength");
		tableColumnVector.add("processLoops");
		tableColumnVector.add("AvgTimeOfEn");
		tableColumnVector.add("AvgTimeOfDe");
		
		tableModel = new DefaultTableModel(tableModelVector, tableColumnVector);
		dataOutputTable = new JTable(tableModel);
		dataOutputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 0; i < tableColumnVector.size(); i++)
		{
			dataOutputTable.getColumnModel().getColumn(i)
				.setPreferredWidth(tableColumnVector.get(i).length() * 10 + 20);
		}
		dataOutputTableScroll.setViewportView(dataOutputTable);
		
		ecStandardBox = new JComboBox();
		ecStandardBox.setBounds(32, 91, 228, 43);
		Enumeration ecTable = ECNamedCurveTable.getNames();
		while(ecTable.hasMoreElements())
			ecStandardBox.addItem(ecTable.nextElement());
		getContentPane().add(ecStandardBox);
		
		eCStandardLabel = new JLabel("ECStandard");
		eCStandardLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		eCStandardLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		eCStandardLabel.setBounds(32, 49, 121, 43);
		getContentPane().add(eCStandardLabel);
		setVisible(true);
	}
}

