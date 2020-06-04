package aaafcKeyManage.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Tool object
 * The object is a sub class of JComboBox, 
 * which use an ArrayList object to initialize.
 * @author aaafc
 *
 */
public class JComboBoxSyn extends JComboBox {

	private List source = null;
	private DefaultComboBoxModel model = null;

	public JComboBoxSyn(ArrayList list) {
		Vector modelVector = new Vector(list);
		source = list;
		
		this.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				syn();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
		model = new DefaultComboBoxModel(modelVector);
		this.setModel(model);
	}
	
	/**
	 * Synchronize the data from the ArrayList to the ComboBox.
	 */
	public void syn()
	{
		if(source == null || model == null)
			return;	
		model.removeAllElements();
		model.addAll(source);
	}

}
