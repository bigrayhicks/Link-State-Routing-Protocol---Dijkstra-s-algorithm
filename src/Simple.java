/* This file implements the Djkistra Algorithm and prints the shortest path
 * Simple class takes input from TestGUI and then computes the ConnectionTables for all routers, ehich gets stored in a common repository i.e., connectiontables for TestGUI to access.
 * It also Computes the paths and appends it in a Hash set named pset.
*/
package swing;

import java.util.ArrayList;
import java.util.HashSet;

import beans.ConnectionTable;

public class Simple 
{
	private static Simple simple;
	static ArrayList<ArrayList<Integer>> NextHop = new ArrayList<ArrayList<Integer>>(); //keeps track of the nexthop of a router for a particular destination
	static ArrayList<ArrayList<Integer>> xpre = new ArrayList<ArrayList<Integer>>(); //xpre keeps track of the previous hop of a node which comes as a result from the Djkistra Algorithm
	private static ArrayList<ArrayList<ConnectionTable>> connectionTables = new ArrayList<ArrayList<ConnectionTable>>(); //connectionTables is a collection of all of the routers connection tables 
	private static HashSet<String> pset = new HashSet<String>();	//pset collects all possible paths from the source to destination
	private static StringBuilder temp= new StringBuilder();
	private static ArrayList<ArrayList<Double>> Adj = new ArrayList<ArrayList<Double>>(); 	//Adj is the adjacent matrix whixh comes in as an input from the user
	
	
	private Simple() {
		// TODO Auto-generated constructor stub
	}
	
	//returns an instance of the class Simple
	public static Simple getInstance(){
		if(simple!=null){
			return simple;
		}else{
			return simple = new Simple();
		}
	}
	
	
	//implements the dijkstra algorithm
	public void dijkstra(int s,int d)  
	{
	
		int i=0,j=0,k=0;	//loop itarators
		pset = new HashSet<>();
		ArrayList<ArrayList<Double>> adj = new ArrayList<ArrayList<Double>>();		//Adj is the adjacent matrix which comes in as an input from the user
		ArrayList<ArrayList<Integer>> pre = new ArrayList<ArrayList<Integer>>();		//pre keeps track of the previous hop of a node which comes as a result from the Djkistra Algorithm
		ArrayList<ArrayList<Double>> D_m = new ArrayList<ArrayList<Double>>();		//D_m is the cost of least cost path from node s to m 
		connectionTables = new ArrayList<>();
		NextHop = new ArrayList<>();
		
		adj = Adj;	//makes a local copy of the adjacency matrix
		
		//initializes the arraylist pre and D_m
		for (i=0;i<adj.size();i++)
		{	
			ArrayList<Integer> list = new ArrayList<Integer>();
			ArrayList<Double> t = new ArrayList<Double>();
			for(j=0;j<adj.size();j++)
			{
				list.add(-1);
				t.add(-1.0);
			}
			pre.add(list);
			D_m.add(t);
		}
		
		pre=makepre(pre,adj.size());//initializes the pre list
		
		//calculates the connection tables for all routers
		while(k<adj.size())
		{
			D_m.set(k,Cal(adj,k,pre));		//calculates the minimum cost of node k
			pre=getpre();					// retrieves the pre matrix as calulated by Cal()
			NextHop.add(Build_Next_Hop(k,adj.size(),pre));		//calculates the nexthop of the node k, by lcalling the Build_Next_Hop()
			
			
			System.out.println("Connection Table for Router:"+(k+1));
			System.out.println("Router\t Wt\tNext Hop");

			
			//add the connectiontable of node k to the list connectionTables
			ArrayList<ConnectionTable> tempconnectionTable = new ArrayList<ConnectionTable>();
			for(i=0;i<adj.size();i++)
			{
				ConnectionTable connectionTable = new ConnectionTable();
				connectionTable.setRouterNo(i+1);
				connectionTable.setWeight(D_m.get(k).get(i));
				connectionTable.setNextHop(NextHop.get(k).get(i));
				tempconnectionTable.add(connectionTable);
				System.out.println((i+1)+"\t"+D_m.get(k).get(i)+"\t"+NextHop.get(k).get(i));
			}
			connectionTables.add(tempconnectionTable);
			
			k++;
		}
		
		for (i = 0; i < adj.size(); i++) 
		{
           System.out.print(pre.get(s).get(i) + " \t");
	    }
		
		//if s is not equal to destination then calutale all possible paths
		if(s != d)
			Paths(s-1,d-1,pre,adj,D_m.get(s-1));
	
	}
		
	/**
	 * @return connectionTables
	 */
	public ArrayList<ArrayList<ConnectionTable>>  getConnectionTable() 
	{
		return connectionTables;
	}
	
	/**
	 * @return Adj
	 */
	public ArrayList<ArrayList<Double>> getAdjMatrix() {
		return Adj;
	}
	
	/**
	 * @return the xpre 
	 */
	public static void setpre(ArrayList<ArrayList<Integer>> pre) {
		xpre = pre;
	}
	
	public static ArrayList<ArrayList<Integer>> makepre(ArrayList<ArrayList<Integer>> pre,int x) 
	{
		System.out.println("At makepre");
		for (int i=0;i<x;i++)
		{	
			ArrayList<Integer> list = new ArrayList<Integer>();
			//ArrayList<Double> t = new ArrayList<Double>();
			for(int j=0;j<x;j++)
			{
				list.add(-1);
				//t.add(-1.0);
			}
			pre.add(list);
			//D_m.add(t);
		}
		return pre;
	}
	
	public static ArrayList<ArrayList<Integer>> getpre() 
	{
		return xpre;
	}
	
	/**
	 * @return the D_m for a partcular node and calculates the pre of the nodes as well 
	 */
	public static ArrayList<Double> Cal(ArrayList<ArrayList<Double>> adj, int r, ArrayList<ArrayList<Integer>> pre)
	{
		int i=0,j=0,k=0,c=0,flag=0;		//loop itarators
		
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList <Double> D_m = new ArrayList <Double>();	
		
		double min=Double.POSITIVE_INFINITY;
		
		//initialize the D_m and visited lists
		for (i=0;i<adj.size();i++)
		{	
			D_m.add(min);
			visited.add(0);
		}
		D_m.set(r,0.0);	//set weight 0 for node r
		visited.set(r, 1);	//set visited 1 for node r
		
		
		//calculates the next hop node with minimum weight connected to the source node r
		for (i=0;i<adj.size();i++)
		{
			if(D_m.get(i)>adj.get(r).get(i) && r!=i)
			{	
				D_m.set(i, adj.get(r).get(i));
				pre.get(r).set(i, r);
				if(min>D_m.get(i))
				{
					min=D_m.get(i);
					k=i;		//k is the index of the node with minimum weight
				}
			}
		}
		visited.set(k, 1);	//make node k as visited
		
		pre.get(r).set(k, r);	//set the previous hop for k as r
		
		
		while(flag!=adj.size())		//iterate loop till all nodes get visited
		{
			flag=0;
			min=Double.POSITIVE_INFINITY;			//initialize min as infinity
			for(i=0;i<adj.size();i++)
			{
				if (D_m.get(i)>D_m.get(k)+adj.get(k).get(i) && visited.get(i)==0)		//calculate the weights for the nodes adjacennt to node k  
				{
					D_m.set(i, D_m.get(k)+adj.get(k).get(i)); 
					pre.get(r).set(i,k);
				}
		
				if(min>=D_m.get(i) && visited.get(i)==0)			//find a non visited node with the minimum weight
				{
					min=D_m.get(i);
					c=i;				//c is the index of the non-visited node with minimum weight
				}
				//System.out.println(":"+k+" "+min);
			
				if(visited.get(i)==1)		//calculate the number of nodes visited
					flag++;
			
			}
			k=c;		//set k as c
			visited.set(k, 1);		//set the kth node as visited
			
		}

		setpre(pre);
		
		return D_m;			//return the D_m matrix
	}

	
	/**
	 * @return the NextHops for a partcular node r  
	 */
	public static ArrayList<Integer> Build_Next_Hop(int r,int size,ArrayList<ArrayList<Integer>> pre)
	{
		int i=0,j=0;
		ArrayList<Integer> Con=new ArrayList<Integer>();
		System.out.println("In BuildNextHop");
		
		for(j=0;j<size;j++)
		{
			if(r==j || pre.get(r).get(j)==-1 )			// if source reached or the node has no previous hop then set NextHop as -1
				Con.add(-1);
			else
			{
			i=j;
			while(pre.get(r).get(i)!=r)			//iterate for the ith node till the end is met which is -1
			{
				i=pre.get(r).get(i);			// find the the previous hop for node i and set i as the new index
			}
			Con.add(i+1);		//add the nexthop of node r for destination j as i+1
			}
		}
		return Con;			//return NextHop list for node r
	}
	
	
	/**
	 * Print all possible paths for a source s and a destination d for a topology adj with the previous hops as tpre  
	 */
	public static void Paths(int s, int d,ArrayList<ArrayList<Integer>> tpre,ArrayList<ArrayList<Double>> adj,ArrayList <Double> D_m )
	{
		int flag=0;
		
		pset = new HashSet<>();
		temp=new StringBuilder();
		
		for(int i=0;i<adj.size();i++)
		{
			if(NextHop.get(s).get(i)==-1)
			{
				flag++;
			}
		}
		
		if(NextHop.get(s).get(d)==-1 || flag==adj.size())		//if there is no nexthop from the source to that paticular destination or it is a disjoint node 
		{
			System.out.println("No Path");
			pset.add("No Path");
		}
		else
		{
		PrintPath(s,d,tpre);			//print the shortest path with shortest hops
		pset.add(temp.toString());
		pset = MultiPath.allShortestPaths(s, d, adj);			//collect a set of all possible paths from s to d with equal weights 
													//pset is a collection of all possible paths from a source to destination
		
		System.out.println("Hset"+pset);
		}
	}
	
	
	public static void PrintPath(int s,int d,ArrayList<ArrayList<Integer>> tpre)
	{
		if (tpre.get(s).get(d)==-1)
		{
			System.out.print("Path:"+(s+1));
			temp.append(String.valueOf(s+1));			//if the source is met then append the source index
		}
		else
		{	
			PrintPath(s,tpre.get(s).get(d),tpre);			//call the PrintPath recursively with the new destination as the previous hop of d
			System.out.print((d+1));
			temp.append(String.valueOf(d+1));			// append d to the path list
		}	
		temp.append("->");
	}
	
	
	/**
	 * @param adj the adj to set
	 */
	public void setAdj(ArrayList<ArrayList<Double>> adj) {
		Adj = adj;
	}

	/**
	 * @return the pset
	 */
	public HashSet<String> getPset() {
		return pset;
	}
	
	public void resetPath(){
		pset = new HashSet<>();
	}
	
}
