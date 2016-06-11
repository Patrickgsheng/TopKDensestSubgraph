package charikar;

import java.util.ArrayList;
import java.util.HashSet;

import main.DensestSubgraph;
import output.Output;
import struct.DegreeMap;
import struct.NodeMap;

public class Charikar implements DensestSubgraph{
	
	public Charikar() {
	}
	public ArrayList<Output> getDensest(DegreeMap degreeMap,NodeMap nodeMap) {
		int numNodes = nodeMap.getNumNodes();
		int numEdges = nodeMap.getNumEdges();
		
		double density = numEdges/(double)numNodes;
		ArrayList<String> densest = new ArrayList<String>();
		
		
		int i = 0 ;
		while(i < degreeMap.capacity){
			HashSet<String> temp = degreeMap.map.get(i);
			
			if(temp.size() == 0) {
				i++;
			}
			else { 
				String element= "";
				for(String str:temp) {
					element = str;
					break;
				}
				temp.remove(element);
				
				//System.out.println(element);
				
				densest.add(element);
				//System.out.println(element + " " + nodeMap.getDegree(element));
				
				
				HashSet<String> neighbors;
				
				if (nodeMap.getNeighbors(element) == null)
					neighbors = new HashSet<String>();
				else 
					neighbors = new HashSet<String>(nodeMap.getNeighbors(element));
				
				if(neighbors.size() > 0 ) {
					for(String neighbor:neighbors) {
						//System.out.println(element+ " " + neighbor);
						nodeMap.removeEdge(element, neighbor);
						nodeMap.removeEdge(neighbor, element);
						numEdges--;
					
						int nodeDegree = nodeMap.getDegree(neighbor);
						degreeMap.decremnetDegree(nodeDegree+1, neighbor);
						if(nodeDegree < i) {
							i=nodeDegree;
						}	
					}
				}
				numNodes--;
				if(numNodes == 0) {
					ArrayList<Output> outputArray = new ArrayList<Output>();
					Output output = new Output();
					output.density = density;
					output.size = densest.size();
					output.nodes = densest;
					outputArray.add(output);
					return outputArray;
				}
				
				double newDensity = numEdges/(double)numNodes;
				if(newDensity >= density) {
					density = newDensity;
					densest = new ArrayList<String>();
				}
			}	
		}
		ArrayList<Output> outputArray = new ArrayList<Output>();
		Output output = new Output();
		output.density = density;
		output.size = densest.size();
		output.nodes = densest;
		outputArray.add(output);
		return outputArray;
		
	}
}