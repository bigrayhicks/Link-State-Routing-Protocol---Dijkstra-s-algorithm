package swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import beans.ConnectionTable;
/**
 * Dialog to show the popup area
 * @author prajakt
 *
 */
public class PopupTextArea extends JDialog {

	private static final long serialVersionUID = 1L;
	private static PopupTextArea popupTextArea;
	private JTextArea textArea;
	/**
	 * Private constuctor
	 * @param frame
	 */
	private PopupTextArea(JFrame frame) {
		super(frame);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		textArea = new JTextArea(5, 25);
		textArea.setSize(800, 600);
		JScrollPane scrollableText = new JScrollPane(textArea);
		scrollableText.setPreferredSize(new Dimension(800, 600));

		add(scrollableText);
		pack();

	}
	/**
	 * Making singleton
	 * @param frame
	 * @return
	 */
	public static PopupTextArea getInstance(JFrame frame) {
		if (popupTextArea != null) {
			return popupTextArea;
		} else {
			return popupTextArea = new PopupTextArea(frame);
		}
	}

	/**
	 * Method to show loaded and current adjacency matrix
	 * 
	 * @param adjMatrix
	 */
	public void showAdjMatrix(ArrayList<ArrayList<Double>> adjMatrix, ArrayList<ArrayList<ConnectionTable>> connectionTables) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < adjMatrix.size(); i++) {
			for (int j = 0; j < adjMatrix.size(); j++) {
				if(adjMatrix.get(i).get(j)==Double.POSITIVE_INFINITY){
					if (i == j)
						builder.append("0\t");
					else
						builder.append("-1\t");
				}else{
					builder.append(adjMatrix.get(i).get(j) + "\t");
				}

			}
			builder.append("\n");
		}
		builder.append("\n-------------------------------Connection Tables-------------------------------------");
		int count = 0;
		for(ArrayList<ConnectionTable> connectionTable:connectionTables){
			count++;
			builder.append("\n--------------------------------------------------------------------\n");
			builder.append("Connection Table for Router: " + count);
			builder.append("\nRouter\t Wt\tNext Hop\n");
			for(ConnectionTable connection:connectionTable){
				builder.append(connection.getRouterNo()+"\t"+connection.getWeight()+"\t"+connection.getNextHop() + "\n");
			}
		}
		//Now printing the connection tables
		textArea.setEditable(false);
		textArea.setText(builder.toString());

	}
	
	/**
	 * method to handle editablematrix
	 * @param adjMatrix
	 */
	public void showEditableMatrix(ArrayList<ArrayList<Double>> adjMatrix){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < adjMatrix.size(); i++) {
			for (int j = 0; j < adjMatrix.size(); j++) {
				if(adjMatrix.get(i).get(j)==Double.POSITIVE_INFINITY){
					if (i == j)
						builder.append("0\t");
					else
						builder.append("-1\t");
				}else{
					builder.append(adjMatrix.get(i).get(j) + "\t");
				}

			}
			builder.append("\n");
		}
		textArea.setEditable(true);
		textArea.setText(builder.toString());
	}

	/**
	 * Method to show loaded and current adjacency matrix
	 * 
	 * @param adjMatrix
	 */
	public void showAdjMatrixWithPath(ArrayList<ArrayList<Double>> adjMatrix, HashSet<String> paths) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < adjMatrix.size(); i++) {
			for (int j = 0; j < adjMatrix.size(); j++) {
				if(adjMatrix.get(i).get(j)==Double.POSITIVE_INFINITY){
					if (i == j)
						builder.append("0\t");
					else
						builder.append("-1\t");
				}else{
					builder.append(adjMatrix.get(i).get(j) + "\t");
				}

			}
			builder.append("\n");
		}
		builder.append("\n-------------------------------Shortest Path-------------------------------------");
		for(String s: paths){
			if(s.contains("->")){
				s = s.substring(0,s.lastIndexOf('-'));
				builder.append("\n "+s+ "\n");
			}
		}
		String text = builder.toString();
		
		//Now printing the connection tables
		textArea.setEditable(false);
		textArea.setText(text);

	}

	
	/**
	 * Method to enable the user to add values to the matrix, and also delete
	 * them
	 * 
	 * @param adjMatrix
	 * @return if the edit was clean
	 */
	public boolean showAddAndEditableMatrix(ArrayList<ArrayList<Double>> adjMatrix) {
		boolean result = false;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= adjMatrix.size(); i++) {
			if (i < adjMatrix.size()) {
				for (int j = 0; j <= adjMatrix.size(); j++) {
					if (j < adjMatrix.size()) {
						if(adjMatrix.get(i).get(j)==Double.POSITIVE_INFINITY){
							if (i == j)
								builder.append("0\t");
							else
								builder.append("-1\t");
						}else{
							builder.append(adjMatrix.get(i).get(j) + "\t");
						}
					} else {
						if (i == j)
							builder.append("0\t");
						else
							builder.append("-1\t");
					}
				}
			} else {
				// Fill the last row with infinity
				for (int j = 0; j <= adjMatrix.size(); j++) {
					if (i == j)
						builder.append("0\t");
					else
						builder.append("-1\t");
				}

			}
			builder.append("\n");
		}
		textArea.setEditable(true);
		textArea.setText(builder.toString());
		return result;
	}
	
	public String getTextAreaText() {
		return textArea.getText();
	}

}
