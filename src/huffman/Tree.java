package huffman;

import java.util.*;

public class Tree extends Main {
	
	public Node root;
	public HashMap<Character, String> binEquiv = new HashMap<Character, String>();
	
	public Tree(String raw) {
		System.out.println("");
	}
	
	public Tree() {
		
	}
	
	// given a priority queue of nodes, construct a huffman tree
	public void constructFromPQ(PriorityQueue pq) {
		
		Node parent = null;
		
		while (pq.objects.size() > 0) {
			parent = new Node();
			
			// pop 2 nodes off pq
			parent.leftChild = pq.dequeue();
			parent.rightChild = pq.dequeue();
			
			// get their respective priorities
			int leftPriority = parent.leftChild == null ? 0 : pq.nodeToPriority.get(parent.leftChild);
			int rightPriority = parent.rightChild == null ? 0 : pq.nodeToPriority.get(parent.rightChild);
			
			if (pq.objects.size() > 0) {
				// enqueue parent node with sum of priorities
				pq.enqueue(parent,  leftPriority + rightPriority);
			}
		}
		
		// set root to final parent
		this.root = parent;	
	}
	
	public void initializeBinEquiv() {
		dfsForBinEquiv(this.root, "");
	}
	
	public void dfsForBinEquiv(Node n, String currentBinString) {
		// if left child exists
		if (n.leftChild != null) {
			dfsForBinEquiv(n.leftChild, currentBinString + String.valueOf(super.leftBit));
		}
		// if right child exists
		if (n.rightChild != null) {
			dfsForBinEquiv(n.rightChild, currentBinString + String.valueOf(super.rightBit));
		}
		// if char not null
		if ((int) n.content != 0) {
			// add char mapped to current binary string
			binEquiv.put(n.content, currentBinString);
		}
	}
	
	public void encode(String text) {
		
	}
	
	public void decode(String binary) {
		
	}
	
	public void serializeTree() {
		
	}
}
