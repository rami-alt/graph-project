

import java.util.ArrayList;
import java.util.List;
public class MainLab11 {

		public static void main(String[] args) {

			AdjacencyMapGraph<String, String> graph = new AdjacencyMapGraph<>(true);
			
			
			Vertex<String>  SFO= graph.insertVertex("SFO");
			Vertex<String>  LAX= graph.insertVertex("LAX");
			Vertex<String>  ORD= graph.insertVertex("ORD");
			Vertex<String>  DFW= graph.insertVertex("DFW");
			Vertex<String>  BOS= graph.insertVertex("BOS");
			Vertex<String>  JFK= graph.insertVertex("JFK");
			Vertex<String>  MIA= graph.insertVertex("MIA");
			
			
			Edge<String> SW45= graph.insertEdge(JFK, SFO, "SW45");
			graph.insertEdge(BOS, JFK, "NW35");
			graph.insertEdge(LAX, ORD, "UA120");
			graph.insertEdge(ORD, DFW, "UA877");
			graph.insertEdge(DFW, ORD, "DL355");
			graph.insertEdge(DFW, LAX, "AA49");
			graph.insertEdge(JFK, DFW, "AA1387");
			graph.insertEdge(MIA, LAX, "AA411");
			graph.insertEdge(MIA, DFW, "AA523");
			graph.insertEdge(JFK, MIA, "AA903");
			graph.insertEdge(BOS, MIA, "DL247");
			
			System.out.println("-------------print graph----------------");
			System.out.println(graph);
			System.out.println("----------------------------------------");

			System.out.println("in Degreeof DFW:"+graph.inDegree(DFW));
			System.out.println("----------------------------------------");
			System.out.println("out Degreeof DFW:"+graph.outDegree(DFW));
			System.out.println("----------------------------------------");
			
			

	

		}
		}
