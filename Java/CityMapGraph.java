/* Author: Christopher Lodzinski
 * Date: 04/29/2025
 * Purpose: This program will create a simple map of cities using java collections and implement basic operations like 
 * adding cities and road and displaying connections.
 */

package Week11;
import java.util.*;

public class CityMapGraph {
	// Create graph for cities and roads.
	private Map<String, ArrayList<String>> graph;
	
	// Method for creating graph in form of a hashmap
	public CityMapGraph() {
		graph = new HashMap<>();    
	}
	
	// Method to adding city to the graph.
	public void addCity(String city) {
		graph.putIfAbsent(city, new ArrayList<>());
	}
	
	// Method to adding road to the graph by connecting two cities.
    public void addRoad(String city1, String city2) {  
    	
    	graph.get(city1).add(city2);
    	graph.get(city2).add(city1);    
    }
    
    // Method to removing city from the graph
    public void removeCity(String city) {
    	if (graph.containsKey(city)) {
    		for (String neighbor : graph.get(city)) {
    			graph.get(neighbor).remove(city);
    		}
    		graph.remove(city);
    	}
    }
	
    // Method to removing road that connects two cities.
    public void removeRoad(String city1, String city2) {
    	if (graph.containsKey(city1) && graph.containsKey(city2)){
    		graph.get(city1).remove(city2);
    		graph.get(city2).remove(city1);
    	}
    }
    
    // Method to chech if two cities are connected with a road
    public boolean isConnected(String city1, String city2) {       
    	return graph.getOrDefault(city1, new ArrayList<>()).contains(city2);   
    }
     
    // Method to print the graph to the user
    public void printGraph() {     
    	for (String city : graph.keySet()) {        
    		System.out.println(city + " -> " + graph.get(city));      
    	}  
    }
    
    public static void main(String[] args) {        
    	// Creating the graph for the cities
    	CityMapGraph map = new CityMapGraph();
    	
    	// Adding 4 cities to the graph
    	map.addCity("A");        
    	map.addCity("B");       
    	map.addCity("C");
    	map.addCity("D");
    	
    	// Adding roads to connect certain cities
    	map.addRoad("A", "B");    
    	map.addRoad("B", "C");
    	map.addRoad("A", "D");
    	
    	// Displaying the graph to users.
    	map.printGraph();      
    	
    	// Displaying if two cities are connected to each other.
    	System.out.println("A connected to C? " + map.isConnected("A", "C"));   
    	System.out.println("A connected to B? " + map.isConnected("A", "B"));
    	
    	// Removing roads from city A and city D
    	map.removeRoad("A", "D");
    	System.out.println("\nAfter removing raod A -> D: ");
    	map.printGraph();
    	
    	// Removing city C from the graph
    	map.removeCity("C");
    	System.out.println("\nAfter removing city C: ");
    	map.printGraph();
    	
    }
    
}
