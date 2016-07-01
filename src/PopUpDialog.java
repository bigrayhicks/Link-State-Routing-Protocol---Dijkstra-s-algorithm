package swing;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PopUpDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PopUpDialog(String message,JFrame jFrame) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		JLabel label = new JLabel(message);
		add(label);
		pack();
	}
}
