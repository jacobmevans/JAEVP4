import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Scanner;

public class JAEVP4 {

	static class Vertex implements Comparable<Vertex>{
		
		int ID;													//Used to identify the vertex.
		ArrayList<Edge> connections = new ArrayList<Edge>();	//Used to store all connecting edges to the vertex.
		double cost;												//Used to hold the cost of using the vertex. (Dijkstras)
		Vertex previous;										//Used to store the previous vertex. (Used in printing Dijkstras.)
		int leader;												//Used to keep track of head of unionFind data structure.
		int numConnected = 0;									//Used to determine which side to union in the union function.
		
		public Vertex(int ID, Edge connected){
			
			this.ID = ID;
			this.cost = Double.POSITIVE_INFINITY;
			
		}

		@Override
		public int compareTo(Vertex that) { //Override compareTo function so that it can be used for priority queue of Vertices.
			
			return Double.compare(this.cost, that.cost);
		}
		
		
		public static Comparator<Vertex> visitedSort = new Comparator<Vertex>() {	//Comparator used to sort the ArrayList of visited vertices.
			
			 public int compare(Vertex v1, Vertex v2)
			    {
				 		 
			        if (v1.ID < v2.ID){
			            return -1;
			        }else if (v1.ID > v2.ID){
			            return 1;
			        }else{
			        	return 0;
			        }
			    }
		};
		
		
	}//End of Vertex
	
	
	static class Edge{
		
		int startVertex;	//Used to store the starting vertex.
		int tarVertex;		//Used to store the target vertex.
		int weight;			//Used to store the weight of the edge.
		
		
		public Edge(int sID,int vID,int value){	//Constructor
			
			this.startVertex = sID;
			this.tarVertex = vID;
			this.weight = value;
			
		}
		
		public static Comparator<Edge> edgeSort = new Comparator<Edge>() { //Comparator used to sort edges.
			
			 public int compare(Edge edge1, Edge edge2)
			    {
			        if (edge1.weight < edge2.weight)
			            return -1;
			        if (edge1.weight > edge2.weight)
			            return 1;
			        return 0;
			    }
			
		};
		
		public static Comparator<Edge> minTreeSort = new Comparator<Edge>() {	//Comparator used to sort the edges of the minTree.
			
			 public int compare(Edge edge1, Edge edge2)
			    {
				 
				 if(edge1.startVertex > edge1.tarVertex){
					 
					 int temp = edge1.startVertex;
					 edge1.startVertex = edge1.tarVertex;
					 edge1.tarVertex = temp;
					 
				 }
				 
			        if (edge1.startVertex < edge2.startVertex)
			            return -1;
			        if (edge1.startVertex > edge2.startVertex)
			            return 1;
			        return 0;
			    }
			
		};
		
		public static Comparator<Edge> minTreeSortTarget = new Comparator<Edge>() {	//Second comparator used to sort the target vertices of the minTree.
			
			 public int compare(Edge edge1, Edge edge2)
			    {
				 if(edge1.startVertex == edge2.startVertex){		 
			        if (edge1.tarVertex < edge2.tarVertex)
			            return -1;
			        if (edge1.tarVertex > edge2.tarVertex)
			            return 1;
				 }
			        return 0;
			    }
			
		};
		
	}//End of edge
	
	
	public static class Graph{
		
		Vertex[] connectedNodes;									//Primary array of vertices in the graph.
		 int sourceVertex;											//Stores source vertex.
		ArrayList<Vertex> visited = new ArrayList<Vertex>();		//ArrayList used to store the visited vertices for Dijkstra.
		ArrayList<Edge> minSpanTree = new ArrayList<Edge>();		//ArrayList used to store the edges used to create the minTree.
		ArrayList<Edge> edges = new ArrayList<Edge>();				//ArrayList used to store the edges for Kruskal.
		PriorityQueue<Vertex> que = new PriorityQueue<Vertex>();	//Priority queue used for Dijkstra.
		 
		public Graph(int size, int srcVertex){	//Constructor		
			
			connectedNodes = new Vertex[size];
			
			for(int x = 0; x < size; x++){
				createVertex(x, x+1);
			}
			sourceVertex = srcVertex;
		}
		
		public void createVertex(int index, int vertID){
			
			connectedNodes[index] = new Vertex(vertID, null);
			
		}
		
		public void createEdge(int vertID, int edgeID, int value){
			
			Edge newEdge = new Edge(vertID, edgeID, value);
			Edge newEdge2 = new Edge(edgeID, vertID, value);
			
			connectedNodes[vertID - 1].connections.add(newEdge);
			connectedNodes[edgeID - 1].connections.add(newEdge2);
			
			edges.add(newEdge);
			
			
		}
		
		//Function used in unionFind to find the root of the current vertex in the graph.
		public Vertex find(Vertex toFind){
						
			if(toFind.leader == toFind.ID){
				return toFind;
			}else{
				return connectedNodes[toFind.leader - 1];
			}
		 
		}
		
		//Function used in unionFind to union two vertices if the have different roots to avoid cycles.
		public boolean union(Vertex v1, Vertex v2){
			
			Vertex firstRoot = find(v1);
			Vertex secondRoot = find(v2);
		
			if(firstRoot.numConnected < secondRoot.numConnected){	//If second tree is larger than first, let second be the new root.
					
					firstRoot.leader = secondRoot.leader;
					secondRoot.numConnected++;
					return true;
					
			}else{	//Else if first tree is larger than second, let first be the new root. (Default case if trees are equal.)
					
				if(secondRoot.leader != firstRoot.leader){	//Check if duplicate attempt at a union, do not union again if so.
					
					secondRoot.leader = firstRoot.leader;
					firstRoot.numConnected++;
					
					return true;
				}
			}	
			return false;
	    }//End of union();

		 //Print used to display the results of the use of Dijkstra's algorithm.
		 public void printDijkstra(){
			
			 Dijkstra(connectedNodes[sourceVertex - 1]);	//Run Dijkstra's before printing.
			 Collections.sort(visited, Vertex.visitedSort);	//Sort the visited set for display purposes.
			 ArrayList<Integer> tempArray;					//ArrayList used to sort Vertex.previous for display purposes.
			 
			 for(Vertex v : visited){	//For all vertices in visited.
				 
				 System.out.print(sourceVertex + " ");	//Print the source vertex.
				 Vertex temp = v;						//Temporary variable used in storing previous values into tempArray.
				 tempArray = new ArrayList<Integer>();	//Reset contents of tempArray upon each new vertex.
				 
					 while(temp.previous != null && temp.previous.ID != sourceVertex){	//Loop through and get all previous vertices.
						 tempArray.add(temp.previous.ID);
						 //System.out.print(temp.previous.ID + " ");
						 temp = temp.previous;
					 }
					 Collections.reverse(tempArray);	//Sort previous vertices before printing.
					 for(int k = 0; k < tempArray.size(); k++){	//Print previous vertices.
						System.out.print(tempArray.get(k) + " ");
					 }
					 System.out.print(v.ID + " " + (int)v.cost);	//Print target and cost to get there.
					 System.out.println();
			 }
		 }
		 
		 //Print used to display the results of the use of Kruskal's algorithm.
		 public void printKruskal(){
			 
			 Kruskal();													//Run Kruskal's before printing.
			 Collections.sort(minSpanTree, Edge.minTreeSort);			//Sort the minSpanTree by swapping start and target nodes if needed and then sorting by the first node.
			 Collections.sort(minSpanTree, Edge.minTreeSortTarget);		//Sort again used to sort by the value of the second node.
			 System.out.println();
			 
			 for(int i = 0; i < minSpanTree.size(); i++){
				 
				 Edge printEdge = minSpanTree.get(i);
				 System.out.println(printEdge.startVertex + " " + printEdge.tarVertex);
				
			 }
			 System.out.println("Minimal spanning tree length = " + calcMinTree());		//Print the cost of the minimal spanning tree. 
		 }
		 
		 //Function that uses Dijkstra's algorithm on the graph.
		 public void Dijkstra(Vertex src){
				
				src.cost = 0;	//Set cost of source to 0.
				que.add(src);	//Add source to the Priority Queue.
				
				while(!que.isEmpty()){	//While the queue is not empty.
					
					Vertex vertex = que.poll();	//Get and remove the minimum value from the priority queue and store in vertex.
					
				
					for(Edge e : vertex.connections){	//For all edges that are connected to the vertex.
						
						Vertex v = connectedNodes[e.tarVertex - 1];	//Set variable v to the connected vertex.
						if(!visited.contains(v)){
							
							int weight = e.weight;						//Get the weight of the edge.
							int newDistance = (int)vertex.cost + weight;		//Calculate the new distance.
							
							if(newDistance < v.cost){					//Compare old distance vs. the new distance, if smaller relax it it.
								que.remove(v);
								v.cost = newDistance;
								v.previous = vertex;
								que.add(v);
								
							}
						}
						
					}
					visited.add(vertex);		//Add the vertex the visited set.
									
				}
					
			}
		 
		 
		 //Function that uses Kruskal's algorithm on the graph.
		 public void Kruskal(){
			 	
			 minSpanTree = new ArrayList<Edge>();	//Create a new ArrayList of edges for minSpanTree.
			 
			 for(Vertex v : connectedNodes){	//Create a disjoint set of each vertex in the graph.
				 v.leader = v.ID;			
			 }
			 
			 Collections.sort(edges, Edge.edgeSort);	//Sort the edges by weight.
			 
			 for(int x = 0; x < edges.size(); x++){	//For every edge in the set of edges.
				 
				Vertex vertexOne = connectedNodes[edges.get(x).startVertex - 1];	
				Vertex vertexTwo = connectedNodes[edges.get(x).tarVertex - 1];
				
				if(union(vertexOne, vertexTwo)){	//Attempt to union the two vertices, if successful add the edge to the minSpanTree, otherwise don't.
					minSpanTree.add(edges.get(x));
				}
			 } 
		 }
		 
		 //Function used the calculate the cost of the minimal spanning tree.
		 public int calcMinTree(){
			 
			 int minTree = 0;
			 
			 for (int i = 0; i < minSpanTree.size(); i++){
				 
				 minTree += minSpanTree.get(i).weight;
				 
			 }
			 
			 return minTree;
		 }
		 
	}//End of graph

	

	public static void main(String[] args) throws IOException{
	
		Graph graph = null;	//Create a new graph.
		int srcVertex;		//Variable used to hold the source vertex.
		//Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(new File("test.txt"));
		String line = "";	//Used to hold the line of input.
		boolean done = false;
		
		while(!done){
			
			line = sc.nextLine();
			String[] tokens = line.split(" ");
			
			if(tokens.length == 2){
				
				int size = Integer.parseInt(tokens[0]);
				srcVertex = Integer.parseInt(tokens[1]);
				
				graph = new Graph(size, srcVertex);		//Create a new graph of size size and with the source vertex srcVertex.
				
			}else{
			
				int vertID = Integer.parseInt(tokens[0]);
				
				if(vertID == 0){
					done = true;
					sc.close();
					continue;
				}
				
				int connectedNode = Integer.parseInt(tokens[1]);
				int weight = Integer.parseInt(tokens[2]);
				
				graph.createEdge(vertID, connectedNode, weight);
			}
			
		}
		graph.printDijkstra();	//Run and print Dijkstra's on the graph.
		graph.printKruskal();	//Run and print Kruskal's on the graph.
				
	}//End of main
}//End of file
