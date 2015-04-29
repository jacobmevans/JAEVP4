import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JAEVP4 {

	static class vertex{
		
		int ID;
		edge connectedTo;
		
		public vertex(int ID, edge connected){
			
			this.ID = ID;
			this.connectedTo = connected;
			
		}
		
	}//End of vertex
	
	
	static class edge{
		
		int vertexID;
		edge next;
		int weight;
		
		public edge(int vID, edge edge, int value){
			
			this.vertexID = vID;
			this.next = edge;
			this.weight = value;
			
		}
	}//End of edge
	
	public static class graph{
		
		vertex[] connectedNodes;
		 int sourceVertex;
		public graph(int size, int srcVertex){			
			
			connectedNodes = new vertex[size];
			
			for(int x = 0; x < size; x++){
				createVertex(x, x+1);
			}
			sourceVertex = srcVertex;
		}
		
		public void createVertex(int index, int vertID){
			
			connectedNodes[index] = new vertex(vertID, null);
			
		}
		
		public void createEdge(int vertID, int edgeID, int value){
			
			connectedNodes[vertID - 1].connectedTo = new edge(edgeID,connectedNodes[vertID - 1].connectedTo, value);
			
			
		}
		
		 public void print() {
		        System.out.println();
		        for (int v=0; v < connectedNodes.length; v++) {
		            System.out.print(connectedNodes[v].ID);
		            for (edge nbr=connectedNodes[v].connectedTo; nbr != null;nbr=nbr.next) {
		                System.out.print(" --> " + connectedNodes[nbr.vertexID -1].ID);
		            }
		            System.out.println("\n");
		        }
		    }
		
	}//End of graph

	
	

	public static void main(String[] args) throws IOException{
	
		graph graph = null;
		int index = 0;
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
