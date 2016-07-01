package swing;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * The main frame for the gui
 * @author prajakt
 * @version 1.0
 */
public class ProjectFrame {
	public final static boolean RIGHT_TO_LEFT = false;
	private static final String TITLE = "CS 542 Project";
	private JFrame frame;
	private File file;
	private Simple simple;
	private JButton addButton;
	private JButton delButton;
	private JButton submit;
	private PopupTextArea popupTextArea;
	private int noOfNodes;
	private JComboBox<String> source;
	private JComboBox<String> destination;
	private JButton viewButton;
	private JButton editButton;
	private JButton viewGraph;
	private JButton getShortestPath;
	private static ProjectFrame projectFrame;
	private DeletePopUp deletePopUp;
	private int heightOfFrame;
	
	/**
	 * The constuctor is private to make it a singleton instance
	 */
	private ProjectFrame() {
		simple = Simple.getInstance();
	}
	/**
	 * Making it singleton
	 * @return
	 */
	public static ProjectFrame getInstance() {
		if (projectFrame != null) {
			return projectFrame;
		} else {
			return projectFrame = new ProjectFrame();
		}
	}
	/**
	 * Method to initiate the creation of a frame 
	 */
	private void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame("Gui 1");
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		frame.setPreferredSize(new Dimension(screenSize.width/2, heightOfFrame = screenSize.height/2));
		frame.getContentPane().add(getPanel());
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * Make the main panel
	 * @return the main panel that is to be added to the frame
	 */
	public JPanel getPanel() {
		submit = new JButton("Submit");
		JPanel panel = new JPanel();
		addButton = new JButton("Add");
		Listener listener = new Listener();
		addButton.addActionListener(listener);
		
		delButton = new JButton("Del");
		delButton.addActionListener(listener);
		viewButton = new JButton("View Adj Matrix & Conn Table");
		viewButton.addActionListener(listener);
		editButton = new JButton("Edit");
		editButton.addActionListener(listener);
		getShortestPath = new JButton("Shortest Path");
		getShortestPath.addActionListener(listener);
		viewGraph = new JButton("View Graph");
		viewButton.addActionListener(listener);
		//Setting dynamic height to all the buttons
		addButton.setPreferredSize(new Dimension(0, heightOfFrame/8));
		delButton.setPreferredSize(new Dimension(0, heightOfFrame/8));
		viewButton.setPreferredSize(new Dimension(0, heightOfFrame/8));
		editButton.setPreferredSize(new Dimension(0, heightOfFrame/8));
		getShortestPath.setPreferredSize(new Dimension(0, heightOfFrame/8));
		viewGraph.setPreferredSize(new Dimension(0, heightOfFrame/8));
		//Adding action listener for showing a graph
		viewGraph.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<ArrayList<Double>> adjMatrix = simple.getAdjMatrix();
				if(adjMatrix!=null && adjMatrix.size()>0){
					new GraphView(adjMatrix,simple.getPset());
				}
				
			}
		});
		DesignGridLayout layout = new DesignGridLayout(panel);	
		
		layout.row().grid().add(getSourceAndDestinationPanel()).add(new JPanel());
		layout.row().grid().add(getShortestPath).add(viewGraph);
		layout.row().grid().add(addButton).add(delButton);
		layout.row().grid().add(viewButton).add(editButton);
		final JFileChooser fileChooser = new JFileChooser();
		JButton openButton = new JButton("Open a File...");
		openButton.setPreferredSize(new Dimension(0, heightOfFrame/8));
		//To open a file dialog
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(frame);
				//Read the file and call dijkstra if the file is clean
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						file = fileChooser.getSelectedFile();

						String response = Utils.loadAdjMatrix(file
								.getAbsolutePath());
						
						if ("success".equals(response)) {
							ArrayList<ArrayList<Double>> adjMatrix = simple
									.getAdjMatrix();

							simple.dijkstra(0, 0);
							String[] values = new String[adjMatrix.size()];
							noOfNodes = values.length;
							source.removeAllItems();
							destination.removeAllItems();
							for (int i = 0; i < adjMatrix.size(); i++) {
								values[i] = String.valueOf(i + 1);
								source.addItem(String.valueOf(i + 1));
								destination.addItem(String.valueOf(i + 1));
							}

						} else {
							PopUpDialog popUp = new PopUpDialog(response, frame);
							popUp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							popUp.setVisible(true);
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		layout.row().grid().add(openButton);

		return panel;
	}

	/**
	 * Method to create panel for source and destination
	 * @return panel containing UI for source and destination
	 */
	private JComponent getSourceAndDestinationPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		JLabel sourceLabel = new JLabel("Source");
		sourceLabel.setPreferredSize(new Dimension(0, heightOfFrame/8));
		source = new JComboBox<String>();
		source.setPreferredSize(new Dimension(0, heightOfFrame/8));
		JLabel destinationLabel = new JLabel("Destination");
		destination = new JComboBox<String>();
		destinationLabel.setPreferredSize(new Dimension(0, heightOfFrame/8));
		destination.setPreferredSize(new Dimension(0, heightOfFrame/8));
		panel.add(sourceLabel);
		panel.add(source);
		panel.add(destinationLabel);
		panel.add(destination);
		return panel;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				getInstance().createAndShowGUI();

			}
		});
	}

	/**
	 * 
	 * Class to listen to button pressed events
	 * 
	 */
	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Remove listeners placed on submit button
			for(ActionListener act : submit.getActionListeners()) {
			    submit.removeActionListener(act);
			}
			//Handing for the add button
			if (e.getSource() == addButton) {
				ArrayList<ArrayList<Double>> adjMatrix = simple.getAdjMatrix();
				if (adjMatrix != null && adjMatrix.size() > 0) {
					popupTextArea = PopupTextArea.getInstance(frame);
					popupTextArea.showAddAndEditableMatrix(adjMatrix);
					popupTextArea
							.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					popupTextArea.setVisible(true);
					if(submit.getParent()!=popupTextArea)
						popupTextArea.add(submit);
					submit.addActionListener(new PopUpActionListener());
					popupTextArea.pack();
					
				}
			} else if (e.getSource() == delButton) {
				if (noOfNodes > 0) {
					deletePopUp =  DeletePopUp.getInstance(frame, noOfNodes);
					if(submit.getParent()!=deletePopUp)
						deletePopUp.add(submit);
					
						submit.addActionListener(deletePopUp);
					deletePopUp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					deletePopUp.setVisible(true);
					deletePopUp.pack();
				}
			} else if (e.getSource() == viewButton) {
				ArrayList<ArrayList<Double>> adjMatrix = simple.getAdjMatrix();
				if (adjMatrix != null && adjMatrix.size() > 0) {
					popupTextArea = PopupTextArea
							.getInstance(frame);
					popupTextArea.showAdjMatrix(adjMatrix,
							simple.getConnectionTable());
					popupTextArea
							.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					popupTextArea.remove(submit);
					popupTextArea.setVisible(true);
				}
			} else if (e.getSource() == editButton) {
				System.out.println("Reached edit");
				ArrayList<ArrayList<Double>> adjMatrix = simple.getAdjMatrix();
				if (adjMatrix != null && adjMatrix.size() > 0) {
					popupTextArea = PopupTextArea
							.getInstance(frame);
					popupTextArea.showEditableMatrix(adjMatrix);
					if(submit.getParent()!=popupTextArea)
						popupTextArea.add(submit);
					submit.addActionListener(new PopUpActionListener());
					
					popupTextArea
							.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					popupTextArea.setVisible(true);
					popupTextArea.pack();
				}
			} else if (e.getSource() == getShortestPath) {
				ArrayList<ArrayList<Double>> adjMatrix = simple.getAdjMatrix();
				if (adjMatrix.size() > 0) {
					String sourceString = (String) source.getSelectedItem();
					String destinationString = (String) destination
							.getSelectedItem();
					
					if (sourceString.equals(destinationString)) {
						Window parentWindow = SwingUtilities.windowForComponent(delButton);
						JDialog dialog = new JDialog(parentWindow);
		                dialog.setLocationRelativeTo(delButton);
		                dialog.setModal(true);
		                dialog.setLayout(new GridLayout(0,1));
		                dialog.add(new JLabel("Source and destination are same"));
		                dialog.pack();
		                dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		                dialog.setVisible(true);
					} else {
						simple.dijkstra(Integer.valueOf(sourceString),
								Integer.valueOf(destinationString));
						HashSet<String> pset = simple.getPset();
						popupTextArea = PopupTextArea.getInstance(frame);
						popupTextArea.showAdjMatrixWithPath(adjMatrix, pset);
						popupTextArea
						.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							popupTextArea.remove(submit);
							System.out.println("Reached in getParent");
						
						popupTextArea.setVisible(true);
					}
				}
			}

		}
	}

	/**
	 * Triggers given by the pop up window
	 * @param i to define the source of the trigger
	 */
	public void trigger(int i) {
		switch (i) {
		//This is called for adding and editing
		case 1:
			PopUpActionListener popUpActionListener = new PopUpActionListener();
			popupTextArea = PopupTextArea.getInstance(frame);
			popupTextArea.remove(submit);
			String progress = popUpActionListener.getProgress();
			if ("success".equals(progress)) {
				
				popupTextArea.dispose();
				
			} else {
				popupTextArea.dispose();
				PopUpDialog popUp = new PopUpDialog(progress, frame);
				popUp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				popUp.setVisible(true);
			}
			break;
		//this is called when delete is processed
		case 2:
			String selectedItem = deletePopUp.getSelectedItem();
			deletePopUp.dispose();
			int a = Integer.valueOf(selectedItem);
			System.out.println("Selected Node " + a--);
			ArrayList<ArrayList<Double>> adjMatrix = Utils.deleteNode(
					simple.getAdjMatrix(), a);
			System.out.println("Size after deleting" + adjMatrix.size());
			simple.setAdj(adjMatrix);
			break;
		default:
			break;
		}
		//Reset the pset
		simple.resetPath();
		
		//Refresh the source and destinations
		source.removeAllItems();
		destination.removeAllItems();
		noOfNodes = simple.getAdjMatrix().size();
		for (int j = 0; j < noOfNodes; j++) {
			source.addItem(String.valueOf(j+1));
			destination.addItem(String.valueOf(j + 1));
		}

	}

}
