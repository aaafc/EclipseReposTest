package aaafcKeyManage.ECIESDemo;

import org.bouncycastle.jce.ECNamedCurveTable;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import aaafcKeyManage.DESDemo.MyDESDisplay;
import aaafcKeyManage.Exception.EncryptProcessFailedException;
import aaafcKeyManage.Exception.GenerateKeyFailedException;
import aaafcKeyManage.UI.JComboBoxSyn;
import aaafcKeyManage.main.MyAlgorithmParameterSpec;
import aaafcKeyManage.main.MyMessage;
import aaafcKeyManage.main.MyProcessDisplay;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * This is a display object which could generate a frame for ECIES algorithm.
 * @author aaafc
 *
 */
public class MyECIESDisplay extends JFrame implements MyProcessDisplay{
	
	private static MyECIESDisplay theInstance = null;
	public static MyECIESDisplay getInstance()
	{
		if(theInstance == null)
			theInstance = new MyECIESDisplay();
		return theInstance;
	}

	public MyECIESProcessor processor = null;
	public ArrayList<MyMessage> messageStorage = null;
	private MyMessage currentMessage = null;
	private JComboBoxSyn keyPairSelectBox;
	private JComboBoxSyn messageSelectBox;
	private JTextArea plainArea;
	private JTextArea cipherArea;
	private JTextArea keyPairArea;
	private JComboBox standardSet;

	private MyECIESDisplay() {

		if (processor == null)
			processor = new MyECIESProcessor();
		getContentPane().setLayout(null);
		messageStorage = new ArrayList<MyMessage>();

		JScrollPane keyPairPane = new JScrollPane();
		keyPairPane.setBounds(447, 10, 354, 316);
		getContentPane().add(keyPairPane);

		keyPairArea = new JTextArea();
		keyPairArea.setLineWrap(true);
		keyPairArea.setWrapStyleWord(true);
		keyPairPane.setViewportView(keyPairArea);

		JScrollPane plainMessagePane = new JScrollPane();
		plainMessagePane.setBounds(10, 10, 394, 161);
		getContentPane().add(plainMessagePane);

		plainArea = new JTextArea();
		plainArea.setLineWrap(true);
		plainArea.setWrapStyleWord(true);
		plainMessagePane.setViewportView(plainArea);

		JScrollPane cipherMessagePane = new JScrollPane();
		cipherMessagePane.setBounds(10, 200, 394, 180);
		getContentPane().add(cipherMessagePane);

		cipherArea = new JTextArea();
		cipherArea.setLineWrap(true);
		cipherArea.setWrapStyleWord(true);
		cipherMessagePane.setViewportView(cipherArea);

		keyPairSelectBox = new JComboBoxSyn(processor.keyManager.publicKeys);
		keyPairSelectBox.setBounds(573, 408, 228, 43);
		keyPairSelectBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				keyPairArea.setText(e.getItem().toString() + "\n"
						+ processor.keyManager.privateKeys
						.get(processor.keyManager.publicKeys.indexOf(e.getItem())));
			}

		});
		getContentPane().add(keyPairSelectBox);

		messageSelectBox = new JComboBoxSyn(messageStorage);
		messageSelectBox.setBounds(573, 469, 228, 43);
		getContentPane().add(messageSelectBox);

		JLabel keyPair = new JLabel("Key_Store");
		keyPair.setHorizontalTextPosition(JLabel.CENTER);
		keyPair.setFont(new Font("1", Font.BOLD, 16));
		keyPair.setBounds(447, 408, 100, 43);
		getContentPane().add(keyPair);

		JLabel messageSelect = new JLabel("Message_S");
		messageSelect.setHorizontalTextPosition(JLabel.CENTER);
		messageSelect.setFont(new Font("1", Font.BOLD, 16));
		messageSelect.setBounds(447, 469, 100, 43);
		getContentPane().add(messageSelect);

		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(10, 408, 394, 98);
		getContentPane().add(controlPanel);
		controlPanel.setLayout(new GridLayout(2, 2, 15, 15));

		JButton encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((currentMessage == null && !plainArea.getText().equals(""))
						|| (currentMessage != null &&
						!plainArea.getText().equals(currentMessage.getPlainMessage())))
				{
					MyMessage tempMessage = new MyMessage(plainArea.getText());
					messageStorage.add(tempMessage);
					currentMessage = tempMessage;
				}
				if (currentMessage != null) {
					try {
						processor.EncryptMessage(currentMessage, keyPairSelectBox.getSelectedIndex());
						cipherArea.setText(currentMessage.getBase64DecodedCipher());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		encryptButton.setFont(new Font("Arial", Font.BOLD, 16));
		controlPanel.add(encryptButton);

		JButton loadButton = new JButton("Load Msg");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentMessage = (MyMessage) messageSelectBox.getSelectedItem();
				plainArea.setText(currentMessage.getPlainMessage());
				try {
					cipherArea.setText(currentMessage.getAlgorithmIndex() == 0 ? ""
							: currentMessage.getBase64DecodedCipher());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		controlPanel.add(loadButton);
		loadButton.setFont(new Font("Arial", Font.BOLD, 16));

		JButton decryptButton = new JButton("Decrypt");
		decryptButton.setFont(new Font("Arial", Font.BOLD, 16));
		decryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentMessage != null)
					try {
						processor.DecryptMessage(currentMessage);
						cipherArea.setText(new String(currentMessage.getRebuiltMessage()));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		controlPanel.add(decryptButton);

		JButton generateButton = new JButton("Generate");
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						processor.keyManager.generateKey(MyAlgorithmParameterSpec.getInstance("ECIES/STD/" + standardSet.getSelectedItem().toString()));
					} catch (Exception e2)
					{
						throw new GenerateKeyFailedException("ECIES Dis Failed Generate Key: " + e2.getMessage());
					}
					keyPairSelectBox.syn();
					keyPairSelectBox.setSelectedItem(keyPairSelectBox.getItemAt(keyPairSelectBox.getItemCount() - 1));
				} catch (GenerateKeyFailedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		generateButton.setFont(new Font("Arial", Font.BOLD, 16));
		controlPanel.add(generateButton);
		
		JLabel lblStdset = new JLabel("gen_std");
		lblStdset.setHorizontalTextPosition(SwingConstants.CENTER);
		lblStdset.setFont(new Font("Dialog", Font.BOLD, 16));
		lblStdset.setBounds(447, 346, 100, 43);
		getContentPane().add(lblStdset);
		
		standardSet = new JComboBox();
		Enumeration ecTable = ECNamedCurveTable.getNames();
		while(ecTable.hasMoreElements())
			standardSet.addItem(ecTable.nextElement());
		standardSet.setBounds(573, 346, 228, 43);
		getContentPane().add(standardSet);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 550);
		setLocation(200, 100);
		setVisible(true);

	}
}