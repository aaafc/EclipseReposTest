package aaafcKeyManage.UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import aaafcKeyManage.DESDemo.MyDESDisplay;
import aaafcKeyManage.main.AlgorithmIndexs;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 * This class is the overall testing frame which could 
 * select from the algorithms and whether do process or assessment.
 * @author aaafc
 *
 */
public class MainController extends JFrame{
	private JComboBox<String> comboBox;
	public MainController() {
		setSize(400, 300);
		setLocation(300, 200);
		getContentPane().setLayout(null);
		
		comboBox = new JComboBox(AlgorithmIndexs.algorithms.toArray())  ;
		comboBox.setFont(new Font("Arial", Font.BOLD, 16));
		comboBox.setBounds(83, 50, 219, 50);
		getContentPane().add(comboBox);
		
		JButton processButton = new JButton("Process");
		processButton.setFont(new Font("Arial", Font.BOLD, 16));
		processButton.setBounds(37, 144, 140, 50);
		processButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
				String algorithm = comboBox.getSelectedItem().toString();
				JFrame f = (JFrame)AlgorithmIndexs.getAlgorithmDisplayClass(algorithm)
						.getMethod("getInstance", new Class[] {}).invoke(null, new Object[]{});
				f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				f.setVisible(true);
				}catch(Exception exp)
				{
					JOptionPane.showMessageDialog(MainController.this,
							exp.getClass().toString().substring(6) + ": " + exp.getMessage());
				}
				
			}
			
		});
		getContentPane().add(processButton);
		
		JButton assessButton = new JButton("Assessment");
		assessButton.setFont(new Font("Arial", Font.BOLD, 16));
		assessButton.setBounds(210, 144, 140, 50);
		assessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					String algorithm = comboBox.getSelectedItem().toString();
					JFrame f = (JFrame)AlgorithmIndexs.getAlgorithmAssessDisplayClass(algorithm)
							.getMethod("getInstance", new Class[] {}).invoke(null, new Object[]{});
					f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					f.setVisible(true);
					}catch(Exception exp)
					{
						JOptionPane.showMessageDialog(MainController.this,
								exp.getClass().toString().substring(6)+ ": " + exp.getMessage());
					}
			}
			
		});
		getContentPane().add(assessButton);
		
		
		
		
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}




