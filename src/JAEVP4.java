import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class JAEVP4 {

	static class Vertex implements Comparable<Vertex>{
		
		int ID;
		ArrayList<edge> connections = new ArrayList();
		int distance;
		Vertex previous;
		
		public Vertex(int ID, edge connected){
			
			this.ID = ID;
			this.distance = Integer.MAX_VALUE;
			
		}

		@Override
		public int compareTo(Vertex that) {
			
			if(this == that){
				return 0;
			}else if(this.ID < that.ID){
				return -1;
			}else if(this.ID > that.ID){
				return 1;
			}
			
			return 0;
		}
		
	}//End of Vertex
	
	
	static class edge{
		
		int VertexID;
		int weight;
		
		public edge(int vID,int value){
			
			this.VertexID = vID;
			this.weight = value;
			
		}
	}//End of edge
	
	public static class graph{
		
		Vertex[] connectedNodes;
		 int sourceVertex;
		public graph(int size, int srcVertex){			
			
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
			
			connectedNodes[vertID - 1].connections.add(new edge(edgeID, value));
			
			
		}
		
		 public void print() {
		        System.out.println();
		        for (int v=0; v < connectedNodes.length; v++) {
		            System.out.print(connectedNodes[v].ID);
		            for (int i = 0; i < connectedNodes[v].connections.size(); i++) {
		            	 System.out.println("\n");
		                System.out.print(" ----> " + connectedNodes[v].connections.get(i).VertexID);
		                System.out.print("(" + connectedNodes[v].connections.get(i).weight + ")");
		            }
		            System.out.println("\n");
		        }
		    }
		 
		 
		 public void calcDistance(Vertex src){
				
				ArrayList<Vertex> unvisited = new ArrayList();
				ArrayList<Vertex> visited = new ArrayList();
				
				
				src.distance = 0;
				unvisited.add(src);
				
				PriorityQueue<Vertex> que = new PriorityQueue<Vertex>(); 
				que.add(src);
				
				while(!que.isEmpty()){
					
					Vertex temp = que.poll();
					visited.add(temp);
					
					for(int i = 1; i <= connectedNodes.length; i++){
						if(!visited.contains(i)){
							if(connectedNodes[i].distance != Integer.MAX_VALUE){
								
								int newDistance = connectedNodes[i].distance + connectedNodes[i].connections.get(i).weight;
								
								if(newDistance < connectedNodes[i].distance)
								
								
							}
						}
					}
					
				}
				
			}
		
	}//End of graph

	public static class Dijkstra{
		
		
		
	}
	

	public static void main(String[] args) throws IOException{
	
		graph graph = null;
	
		//Scanner sc = new Scanner(System.in);
		String line = "";
		
		
		
		try(BufferedReader br = new BufferedReader(new FileReader("test.txt"))){
			while((line = br.readLine()) != null){
				
				String[] tokens = line.split(" ");
				
				if(tokens.length == 2){
					
					int size = Integer.parseInt(tokens[0]);
					int srcVertex = Integer.parseInt(tokens[1]);
					
					graph = new graph(size, srcVertex);
					
				}else{
				
					int vertID = Integer.parseInt(tokens[0]);
					
					if(vertID == 0){
						continue;
					}
					
					int connectedNode = Integer.parseInt(tokens[1]);
					int weight = Integer.parseInt(tokens[2]);
					
					graph.createEdge(vertID, connectedNode, weight);
				}
				
			}
			graph.print();
		}			
	}//End of main
}//End of file
