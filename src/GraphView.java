package swing;

import java.util.ArrayList;
import java.util.HashSet;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
/**
 * View to visualize the graph
 * @author prajakt
 *
 */
public class GraphView {

	/**
	 * Create a graph based on the passed matrid
	 * @param adjMatrix
	 * @param pset
	 */
	public GraphView(ArrayList<ArrayList<Double>> adjMatrix,
			HashSet<String> pset) {
		Graph graph = new SingleGraph("tutorial 1");
		Viewer viewer = graph.display();
		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.setAutoCreate(true);
		graph.setStrict(false);
		for (int i = 0; i < adjMatrix.size(); i++) {
			for (int j = i; j < adjMatrix.get(i).size(); j++) {
				if (adjMatrix.get(i).get(j) > 0
						&& adjMatrix.get(i).get(j) != Double.POSITIVE_INFINITY) {
					
					graph.addEdge("" + i + j, "" + i, "" + j);
				}
				if (adjMatrix.get(i).get(j) != Double.POSITIVE_INFINITY) {
					graph.addNode(String.valueOf(i));
				}
			}
		}
		
		/*
		 * graph.addEdge("BC", "B", "C"); graph.addEdge("CA", "C", "A");
		 * graph.addEdge("AD", "A", "D"); graph.addEdge("DE", "D", "E");
		 * graph.addEdge("DF", "D", "F"); graph.addEdge("EF", "E", "F");
		 */
		String path = null;
		outer: for (String a : pset) {
			if (path == null) {
				path = a;
				break outer;
			}
		}
		System.out.println(path);
		if (path!=null && path.contains("->")) {
			for (String nodes : path.split("->")) {
				int i = Integer.parseInt(nodes) -1;
				graph.getNode(String.valueOf(i)).setAttribute("ui.class", "marked");
			}
			
		}
			for (Node node : graph)
				
				node.addAttribute("ui.label", String.valueOf(Integer.valueOf(node.getId())+1));
		

		/*
		 * for (Node node : graph) { node.addAttribute("ui.label",
		 * node.getId()); }
		 * 
		 * // explore(graph.getNode("A")); }
		 * 
		 * /* public void explore(Node source) { Iterator<? extends Node> k =
		 * source.getBreadthFirstIterator();
		 * 
		 * while (k.hasNext()) { Node next = k.next();
		 * next.setAttribute("ui.class", "marked"); sleep(); } }
		 */

	}

	protected String styleSheet = "node {" + "   fill-color: red;" + "}"
			+ "node.marked {" + "   fill-color: green;" + "}";

	
}