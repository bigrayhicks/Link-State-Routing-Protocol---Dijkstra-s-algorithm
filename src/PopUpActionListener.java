package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PopUpActionListener implements ActionListener{

	private static String progress;
	public PopUpActionListener() {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = PopupTextArea.getInstance(null).getTextAreaText();
		String[] strRows = text.split("\n");
		
		//visited.add(0);
		ArrayList<ArrayList<Double>> adjMatrix = new ArrayList<ArrayList<Double>>();
		for(int j=0;j < strRows.length;j++){
			ArrayList<Double> weight = new ArrayList<Double>();
			String[] strColumns = strRows[j].split("\t");
			for (int i = 0; i < strColumns.length; i++) 
			{
				double value = 0;
				try{
					value=Double.parseDouble(strColumns[i].trim());
				}catch(NumberFormatException nfe){
					progress = "There are some non numeric values";

					ProjectFrame testGUI = ProjectFrame.getInstance();
					testGUI.trigger(1);
					return;
				}
				//We can enter a 
				if(value==-1.0)
					value=Double.POSITIVE_INFINITY;
				
	            weight.add(value);
			//System.out.println(weight.get(i));
			}
			adjMatrix.add(weight);
		}
		if(Utils.isMatixClean(adjMatrix)){
			progress = "success";
			Simple simple = Simple.getInstance();
			simple.setAdj(adjMatrix);
			simple.dijkstra(0, 0);
		}else{
			progress = "There is an error in the matrix";
			System.out.println("Matrix not clean");
		}
		ProjectFrame testGUI = ProjectFrame.getInstance();
		testGUI.trigger(1);
	}
	
	public String getProgress(){
		return progress;
	}
	
}
