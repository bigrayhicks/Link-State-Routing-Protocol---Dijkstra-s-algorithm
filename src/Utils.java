package swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Utils {
	/**
	 * Check if the matrix we got is clean
	 * @param adjMatrix
	 * @return
	 */
	public static boolean isMatixClean(ArrayList<ArrayList<Double>> adjMatrix){
		boolean result = true;
		int columnCount = 0;
		for(int i=0;i<adjMatrix.size();i++){
			columnCount=0;
			for(int j=0;j<adjMatrix.get(i).size();j++){
				//check if lower triangular matrix is equal to upper 
				
				//check for -ve weights
				if(adjMatrix.get(i).get(j) < -1){
					System.out.println("Reached here" + i + "   " + j);
					return false;
				}
				//Check for diagnal 0's
				if(i == j && adjMatrix.get(i).get(j)!=0){
					adjMatrix.get(i).set(j, Double.POSITIVE_INFINITY);
					System.out.println(i + ": " + j + ": " + adjMatrix.get(i).get(j));
					return false;
				}else if(i != j && adjMatrix.get(i).get(j)==0){
					System.out.println(i + ": " + j + ": " + adjMatrix.get(i).get(j));
					return false;
				}
				columnCount++;
				
			}
			//Check for square matrix
			if(adjMatrix.size() !=columnCount){
				System.out.println("Reacehd " + i);
				return false;
			}
		}
		
		
		return result;
	}
	/**
	 * Function to delete a node from the matrix
	 * @param adjMatrix
	 * @param node
	 * @return
	 */
	public static ArrayList<ArrayList<Double>> deleteNode(ArrayList<ArrayList<Double>> adjMatrix,int node){
		for(int i =0;i<adjMatrix.size();i++){
			for(int j = 0;j<adjMatrix.size();j++){
				if(j==node)
					adjMatrix.get(i).remove(j);
			}
		}
		adjMatrix.remove(node);
		return adjMatrix;
	}
	
	/**
	 * Method to load the adjacency matrix from a file
	 * @param Path to look for the adjacency Matrix.
	 * @return the status of the method
	 * @throws FileNotFoundException 
	 */
	public static String loadAdjMatrix(String path) {
		ArrayList<ArrayList<Double>> adjMatrix = new ArrayList<>();
		String result = null;
		try{
			int i=0;
			String line=null;
			File adjfile = new File (path);
			Scanner scan = new Scanner(adjfile);
			
			double value = 0.0;
			
			while(scan.hasNextLine())
			{
				ArrayList<Double> weight = new ArrayList<Double>();
				line=scan.nextLine();
				String delims = " ";
				String[] str1 = line.split(delims);
				//visited.add(0);
				for (i = 0; i < str1.length; i++) 
				{
					try{
						value=Double.parseDouble(str1[i]);
					}catch(NumberFormatException nfe){
						scan.close();
						return result = "There are some non numeric values";
					}
					//We can enter a 
					if(value==-1.0)	//||value==0.0)
						value=Double.POSITIVE_INFINITY;
					else if(value<-1){
						scan.close();
						return "There is a negative value in the matrix";
					}
		            weight.add(value);
				//System.out.println(weight.get(i));
				}
				adjMatrix.add(weight);
				
			}
			scan.close();
			
			for (i = 0; i < adjMatrix.size(); i++) 
			{
		        for (int j = 0; j < adjMatrix.get(i).size(); j++) 
		        {
		           if(adjMatrix.get(i).get(j)==Double.POSITIVE_INFINITY)
		        	   System.out.print('i'+" \t");
		           else
		        	   System.out.print(adjMatrix.get(i).get(j) + " \t");
	
		        }
		        System.out.println();
		    }
			if(isMatixClean(adjMatrix)){
				Simple simple = Simple.getInstance();
				simple.setAdj(adjMatrix);
				result = "success";
			}else{
				result = "There is an error in the matrix";
			}
		}catch(FileNotFoundException fnf){
			result = "The File was not found";
		}
		return result;
		
		
	}
}
