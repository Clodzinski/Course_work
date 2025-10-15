/* Author: Christopher Lodzinski
 * Date: 04/28/2025
 * Purpose: This program was given to us by the professor to fix up some of the coding errors. The program is created with weighted
 * graphs.
 */

package Week12;
import java.io.*;
import java.util.*;

public class Exercise29_06 {
	public static void main(String[] args) throws IOException {
		// Create a weighted tail model and serialize it to a file
	    WeightedTailModel16 model = new WeightedTailModel16();
	    // Save the model to a file
	    try (ObjectOutputStream output = new ObjectOutputStream(
	        new FileOutputStream("WeightedTailModel16.dat"))) {
	    	output.writeObject(model);
	    	}
	    System.out.println("Done");
	    }
	
	public static class WeightedTailModel16 extends TailModel16 implements Serializable {
	    private static final long serialVersionUID = 1L;
	    protected WeightedGraph<Integer>.ShortestPathTree tree;
	    
	    public WeightedTailModel16() {
	    	// Create edges with weights representing number of flips
	    	List<WeightedEdge> edges = getEdges();
	    	// Create weighted graph and compute shortest path tree from target node
	    	WeightedGraph<Integer> graph = new WeightedGraph<>(edges, NUMBER_OF_NODES);
	    	// Obtain a shortest path tree rooted at the target node
	    	tree = graph.getShortestPath(NUMBER_OF_NODES - 1);
	}
	
	private List<WeightedEdge> getEdges() {
		// Creating a list to story edges.
		List<WeightedEdge> edges = new ArrayList<>();
	
		for (int u = 0; u < NUMBER_OF_NODES; u++) {
			for (int k = 0; k < DIMENSION * DIMENSION; k++) {
				char[] node = getNode(u); // Get the node for vertex u
				if (node[k] == 'H') {
					int v = getFlippedNode(node, k);
					int numberOfFlips = getNumberOfFlips(u, v);
	
	    // Add edge (v, u) for a legal move from node u to node v
				  edges.add(new WeightedEdge(v, u, numberOfFlips));
			  	}
		  	}
	  	}
	
	  	return edges;
	}
	
	// Compute how many cells differ
	private static int getNumberOfFlips(int u, int v) {
		char[] node1 = getNode(u);
		char[] node2 = getNode(v);
		int count = 0; // Count the number of different cells
	    for (int i = 0; i < node1.length; i++) {
	    	if (node1[i] != node2[i])
	    		count++;
	    }
	    return count;
	}
	// Get the total flip cost from a node to the target node
	public int getNumberOfFlips(int u) {
		return (int) tree.getCost(u);
		}
	}
	
	static class TailModel16 implements Serializable {
		public final static int DIMENSION = 4;
	   		// 1 << 9 is 512; 1 << 16 is 65536;
		public final static int NUMBER_OF_NODES = 1 << DIMENSION * DIMENSION;
		protected UnweightedGraph<Integer>.SearchTree tree; // Define a tree
	
	/** Construct a model */
	public TailModel16() {
		// Create edges
		List<Edge> edges = getEdges();
	
		// Create a graph
		UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges, NUMBER_OF_NODES);
	
		// Obtain a BSF tree rooted at the target node
		  tree = graph.bfs(NUMBER_OF_NODES - 1);
		}
	
	// Generate legal unweighed moves for each board state
	private List<Edge> getEdges() {
		List<Edge> edges = new ArrayList<Edge>(); // Store
		for (int u = 0; u < NUMBER_OF_NODES; u++) {
			for (int k = 0; k < DIMENSION * DIMENSION; k++) {
				char[] node = getNode(u); // Get the node for vertex u
				if (node[k] == 'H') {
					int v = getFlippedNode(node, k);
					// Add edge (v, u) for a legal move from node u to node v
					edges.add(new Edge(v, u));
				}
			}
		}
		
		return edges;
	}
	
	// Flip a node at a given position and its adjacent cells
	public static int getFlippedNode(char[] node, int position) {
		int row = position / DIMENSION;
		int column = position % DIMENSION;
	
		flipACell(node, row, column);
		flipACell(node, row - 1, column);
		flipACell(node, row + 1, column);
		flipACell(node, row, column - 1);
		flipACell(node, row, column + 1);
		  
		return getIndex(node);
	}
	// Flip a single cell in the board, if within bounds
	public static void flipACell(char[] node, int row, int column) {
		if (row >= 0 && row < DIMENSION && column >= 0 && column < DIMENSION) {
			// Within the boundary
			if (node[row * DIMENSION + column] == 'H')
				node[row * DIMENSION + column] = 'T'; // Flip from H to T
			else
				node[row * DIMENSION + column] = 'H'; // Flip from T to H
			}
		}
	// Convert board configuration to an integer index
	public static int getIndex(char[] node) {
		int result = 0;
		for (int i = 0; i < DIMENSION * DIMENSION; i++)
			if (node[i] == 'T')
				result = result * 2 + 1;
			else
				result = result * 2 + 0;
	
		return result;
		}
	
	// Convert integer index back to board configuration
	public static char[] getNode(int index) {
		char[] result = new char[DIMENSION * DIMENSION];
	
		for (int i = 0; i < DIMENSION * DIMENSION; i++) {
			int digit = index % 2;
			if (digit == 0)
				result[DIMENSION * DIMENSION - 1 - i] = 'H';
			else
				result[DIMENSION * DIMENSION - 1 - i] = 'T';
			index = index / 2;
		}	
	
		return result;
	}
	
	// Represents an edge in the unweighed graph
	public static class Edge implements Serializable{
		int u, v;
		public Edge(int u, int v) {
			this.u =u;
			this.v = v;
		}
	}
	
	// represents a weighed edge for the weighed model
	public static class WeightedEdge extends Edge{
		double weight;
		
		public WeightedEdge(int u, int v, double weight) {
			super(u, v);
			this.weight = weight;
		}
	}
	
	// Unweighed graph structure using adjacency list
	public static class UnweightedGraph<T> implements Serializable {
        protected List<List<Integer>> neighbors;

        public UnweightedGraph(List<Edge> edges, int numberOfVertices) {
            neighbors = new ArrayList<>();
            for (int i = 0; i < numberOfVertices; i++) {
                neighbors.add(new ArrayList<>());
            }
            for (Edge e : edges) {
                neighbors.get(e.u).add(e.v);
            }
        }

        // Perform BFS and return the search tree
        public SearchTree bfs(int v) {
            List<Integer> searchOrder = new ArrayList<>();
            int[] parent = new int[neighbors.size()];
            Arrays.fill(parent, -1);
            boolean[] isVisited = new boolean[neighbors.size()];

            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(v);
            isVisited[v] = true;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                searchOrder.add(u);
                for (int w : neighbors.get(u)) {
                    if (!isVisited[w]) {
                        queue.add(w);
                        parent[w] = u;
                        isVisited[w] = true;
                    }
                }
            }

            return new SearchTree(v, parent, searchOrder);
        }
        
        // Structure to store search tree from BFS
        public class SearchTree implements Serializable {
            private final int root;
            private final int[] parent;
            private final List<Integer> searchOrder;

            public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
                this.root = root;
                this.parent = parent;
                this.searchOrder = searchOrder;
            }

            public int getParent(int v) {
                return parent[v];
            }

            public List<Integer> getPath(int index) {
                List<Integer> path = new ArrayList<>();
                while (index != -1) {
                    path.add(index);
                    index = parent[index];
                }
                return path;
            }
        }
    }
    
	// Weighed graph with algorithm for shortest path
	public static class WeightedGraph<T> implements Serializable{
		private final List<List<WeightedEdge>> adjList;
		public WeightedGraph(List<WeightedEdge> edges, int numVertices) {
			adjList = new ArrayList<>();
			for (int i = 0; i < numVertices; i++) {
				adjList.add(new ArrayList<>());
			}
			for (WeightedEdge e: edges) {
				adjList.get(e.u).add(e);
			}
		}
		
		// Algorithm for shortest path
		public ShortestPathTree getShortestPath(int source) {
            double[] cost = new double[adjList.size()];
            Arrays.fill(cost, Double.MAX_VALUE);
            cost[source] = 0;

            int[] parent = new int[adjList.size()];
            Arrays.fill(parent, -1);

            PriorityQueue<Entry> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.cost));
            pq.add(new Entry(source, 0));

            while (!pq.isEmpty()) {
                Entry current = pq.poll();
                if (current.cost > cost[current.vertex]) continue;

                for (WeightedEdge e : adjList.get(current.vertex)) {
                    if (cost[e.v] > cost[current.vertex] + e.weight) {
                        cost[e.v] = cost[current.vertex] + e.weight;
                        parent[e.v] = current.vertex;
                        pq.add(new Entry(e.v, cost[e.v]));
                    }
                }
            }
            return new ShortestPathTree(source, parent, cost);
	}
		// Internal class to represent a node in the priority queue 
		private static class Entry {
	            int vertex;
	            double cost;

	            Entry(int vertex, double cost) {
	                this.vertex = vertex;
	                this.cost = cost;
	            }
	        }
		
		// Result of algorithm 
		public class ShortestPathTree implements Serializable{
			 private final int source;
			 private final int[] parent;
			 private final double[] cost;
			 
			 public ShortestPathTree(int source, int[] parent, double[] cost) {
				 this.source = source;
				 this.parent = parent;
				 this.cost = cost;
			 }
			 public double getCost(int v) {
				 return cost[v];
			 }	 
			 
			 public List<Integer> getPath(int v){
				 List<Integer> path = new ArrayList<>();
				 while (v != -1) {
					 path.add(v);
					 v = parent[v];
				 }
				 return path;
			 }
			 }
		 }
		
	//Utility method to print a board configuration 
	    public static void printNode(char[] node) {
	    	for (int i = 0; i < DIMENSION * DIMENSION; i++)
	    		if (i % DIMENSION != DIMENSION - 1)
	    			System.out.print(node[i]);
	    		else
	    			System.out.println(node[i]);
	    	
	    	System.out.println();
	    	}
	  	}
	}