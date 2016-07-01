package swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Dialog to delete popup
 * @author prajakt
 *
 */
public class DeletePopUp extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private int noOfNodes;
	
	
	private static final long serialVersionUID = 1L;
	private static DeletePopUp deletePopUp;
	private String selectedItem;
	private JComboBox<String> dropDown;
	private DeletePopUp(JFrame frame,int noOfNodes) {
		super(frame);
		this.noOfNodes = noOfNodes;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		String[] nodes = new String[noOfNodes];
		for(int i =0;i<this.noOfNodes;i++){
			nodes[i] = String.valueOf(i+1);
		}
		dropDown = new JComboBox<>(nodes);
		add(dropDown);
		
	}
	/**
	 * Making singleton
	 * @param frame
	 * @param noOfNodes
	 * @return
	 */
	public static DeletePopUp getInstance(JFrame frame,int noOfNodes){
		if(deletePopUp!=null){
			return deletePopUp;
		}else{
			return deletePopUp = new DeletePopUp(frame,noOfNodes);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		selectedItem = (String) dropDown.getSelectedItem();
		dropDown.removeItem(String.valueOf(noOfNodes--));
		ProjectFrame testGUI = ProjectFrame.getInstance();
		testGUI.trigger(2);
	}
	
	public String getSelectedItem(){
		return selectedItem;
	}
	
	
}
