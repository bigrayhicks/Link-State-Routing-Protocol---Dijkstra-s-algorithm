/* This file Finds all possible shortest paths from a source to destination as received from the Simple.java file
 */
package swing;

import java.util.ArrayList;
import java.util.HashSet;

public class MultiPath {
	private static HashSet<String> pset;
	
	public static HashSet<String> allShortestPaths(int source, int destination,ArrayList<ArrayList<Double>> adjMatrix) 
	{
		pset = new HashSet<>();
		double[] distance = new double[adjMatrix.size()]; 	//previous vertex in shortest path(note:Can be multiple vertex)
		ArrayList<ArrayList<Integer>> prevs = new ArrayList<ArrayList<Integer>>();	//array of visited nodes, initially all vertices are unvisited.
		double[] visited = new double[adjMatrix.size()];

		// Initialize the values
		for (int i = 0; i < adjMatrix.size(); i++) 
		{
			distance[i] = Double.POSITIVE_INFINITY;
			visited[i] = 0;
			prevs.add(new ArrayList<Integer>());
		}
                
		distance[source] = 0;      //distance from source to source is 0
		
        //initilaise the given source as current and mark it as visited
		int currrent = source;
		visited[currrent] = 1;
		double min = Double.POSITIVE_INFINITY;
                
        //path calculation
		while (visited[destination] == 0) 
		{
			min = Double.POSITIVE_INFINITY;		                        //minimum value uptil till now
			double m = -1;

			for (int i = 0; i < adjMatrix.size(); i++) 
			{                    
				double d;     //tentative distance from source to current vertex i
			
				if (adjMatrix.get(currrent).get(i) == Double.POSITIVE_INFINITY) 
				{
					d = Double.POSITIVE_INFINITY;
				}
				else 
				{
					d = distance[currrent] + adjMatrix.get(currrent).get(i);      //distence calulation
				}
                                
                //if vertex i is not visited yet 
				if (visited[i] == 0) 
				{
                    //And if newly calculated distance is shorter than previous distance
					if (d < distance[i]) 
					{
						distance[i] = d;//A shorter path is found from source to vertex i   
						prevs.get(i).clear();//delete the previous vertex of i from previous matrix
						prevs.get(i).add(currrent); //add the previous vertex of newly found path 
					}
                                        
                    else if (d == distance[i]) /*Or else newly calculated distanc is equal to previous distance,
                    							than a multiple path is found and add the previous vertex of this path also to prevs matrix*/
                    {
                    	prevs.get(i).add(currrent);
					}

					if (min > distance[i]) /*the neighbour vertex of current which has minimum distance 
						 						to source will be become the next current*/ 
					{
						min = distance[i];
						m = i;
					}
				}
			}
		    
			//if all unvisited vertex are infinity that is the case of disconnected vertex
			if(min == Double.POSITIVE_INFINITY)
				break;
			
			currrent = (int)m;
			visited[currrent] = 1;
		}
        
		//if target is not reachable
		if (min == Double.POSITIVE_INFINITY) 
		{
			pset.add("No Path");
			return pset;
		}
        else 	//else print all found path from source o destination
        {
			ArrayList<Integer> paths = new ArrayList<>();
			printAllPaths(source, destination, prevs, paths,adjMatrix);
		}

		return pset;
	}
    
	//funtion to backtrack and print all possible shortest path
	private static String printAllPaths(int source, int destination,ArrayList<ArrayList<Integer>> prevs, ArrayList<Integer> paths,ArrayList<ArrayList<Double>> adjMatrix) 
	{
		String response = "";          //check size condition for loop
		
		if (paths.size() > adjMatrix.size())
		{
			return "Loop bigger that number of nodes";
		}
		if (source == destination) 
		{
			paths.add(source+1); 	//print all paths in reverse direction in which the vertexs pushe to the vector path
			for (int i = paths.size() - 1; i >= 0; i--)
			{
				response += paths.get(i)+"->";
			}
			pset.add(response);
		}
        
		//recursively calculate the all the paths
		for (int i = 0; i < prevs.get(destination).size(); i++)
		{
			int size = paths.size();
			paths.add(destination+1);
			printAllPaths(source, prevs.get(destination).get(i), prevs, paths, adjMatrix);
			paths = resize(paths, size);
		}
		return response;
	}
	
	//to resize the path list back to the size 
	private static ArrayList<Integer> resize(ArrayList<Integer> adjMatrix, int size)
	{
		ArrayList<Integer> a = new ArrayList<>();
		for(int i=0;i<adjMatrix.size() && i<size;i++)
		{
			a.add(adjMatrix.get(i));
		}
		return a;
	}
}
