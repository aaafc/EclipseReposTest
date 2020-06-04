package aaafcKeyManage.RSADemo;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import aaafcKeyManage.DESDemo.MyDESDisplay;
import aaafcKeyManage.main.MyProcessAssessDisplay;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 * This is a frams class for generating a assessment frame for RSA algorithm.
 * @author aaafc
 *
 */
public class MyRSAAssessDisplay extends JFrame implements MyProcessAssessDisplay{
	
	private static MyRSAAssessDisplay theInstance = null;
	public static MyRSAAssessDisplay getInstance()
	{
		if(theInstance == null)
			theInstance = new MyRSAAssessDisplay();
		return theInstance;
	}
	
	private JTextField messageLengthField;
	private JLabel labelBits;
	private JTextField numberOfRoundsField;
	private JTable dataOutputTable;
	
	private DefaultTableModel tableModel;
	private Vector<Vector<String>> tableModelVector;
	private Vector<String> tableColumnVector;
	private JScrollBar scrollBar;
	private JButton doProcess;
	
	private MyRSAAssessment assessment;
	
	private MyRSAAssessDisplay() {
		assessment = new MyRSAAssessment();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 450);
		setLocation(200, 100);
		getContentPane().setLayout(null);
		
		scrollBar = new JScrollBar();
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar.setVisibleAmount(10);	
		scrollBar.setMaximum(2058);
		scrollBar.setMinimum(966);
		scrollBar.setValue(1024);
		scrollBar.setBounds(29, 101, 210, 25);
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				labelBits.setText(e.getValue() + " bits");
			}
		});
		getContentPane().add(scrollBar);
		
		JLabel keyLengthText = new JLabel("key Length");
		keyLengthText.setFont(new Font("Arial", Font.BOLD, 16));
		keyLengthText.setBounds(29, 51, 100, 40);
		keyLengthText.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(keyLengthText);
		
		labelBits = new JLabel("1024 bits");
		labelBits.setHorizontalAlignment(SwingConstants.CENTER);
		labelBits.setFont(new Font("Arial", Font.BOLD, 16));
		labelBits.setBounds(139, 51, 100, 40);
		getContentPane().add(labelBits);
		
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
					String[] row = new String[5];
					int keySize = scrollBar.getValue();
					int messageL = Integer.parseInt(messageLengthField.getText());
					int rounds = Integer.parseInt(numberOfRoundsField.getText());
					long[] temp = MyRSAAssessDisplay.this.assessment.
							averageEncryptAndDecryptProcess(messageL, keySize, rounds);
					row[0] = keySize + " bits";
					row[1] = messageL + " bytes";
					row[2] = rounds + " loops";
					row[3] = temp[0] + " ns";
					row[4] = temp[1] + " ns";
					tableModel.addRow(row);
				}catch(NumberFormatException numForExp)
				{
					numForExp.printStackTrace();
					JOptionPane.showMessageDialog(MyRSAAssessDisplay.this, "Invalid value format.");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(MyRSAAssessDisplay.this, "Exception: " 
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
		tableColumnVector.add("keySize");
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
		setVisible(true);
	}
}
