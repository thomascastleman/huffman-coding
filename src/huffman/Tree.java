package huffman;

import java.util.*;

public class Tree extends Main {
	
	public Node root;
	public HashMap<Character, String> binEquiv = new HashMap<Character, String>();
	
	public Formatter f;
	
	public Tree(String filename, Boolean fromSerialization) {
		/*
		 * if not from serialization:
		 * 		format file to single string
		 * 		pq = constructPQFromText(text)
		 * 		constructFromPQ(pq)
		 * 
		 * if from serialization:
		 * 		maybe format? depends on serialization
		 * 		constructFromSerialization( ? )
		 * 
		 * 
		 * then, initializeBinEquiv()
		 * 
		 */
	}
	
	public Tree() {
		
	}
	
	// given string of text, construct priority queue based on char frequency
//	public PriorityQueue constructPQFromText(String text) {
//		
//	}
	
	// construct tree from serialization
	public void constructFromSerialization(String text) {
		
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
	
	// initialize the hashmap of characters to binary representations
	public void initializeBinEquiv() {
		dfsForBinEquiv(this.root, "");
	}
	
	// DFS tree and populate binEquiv hashmap
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
	
	// encode a string of text using already existing hashmap from chars to binary
	public void encode(String text) {
		
		try {
			f = new Formatter("binary.txt");
		} catch (Exception e) {
			System.out.println("Error creating file (Tree.encode)");
		}
		
		for (int i = 0; i < text.length(); i++) {
			
			f.format("%s", binEquiv.get(text.charAt(i)));
			
		}
		
		f.close();
		
	}
	
	public void decode(String binary) {
		
		try {
			f = new Formatter("decoded.txt");
		} catch (Exception e) {
			System.out.println("Error creating file (Tree.decode)");
		}
		
		Node current = this.root;
		
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) - '0' == 1) {
				if (super.leftBit == 1) {
					current = current.leftChild;
				} else {
					current = current.rightChild;
				}
			} else if (binary.charAt(i) - '0' == 0) {
				if (super.leftBit == 0) {
					current = current.leftChild;
				} else {
					current = current.rightChild;
				}
			}
			
			if (current.rightChild == null && current.leftChild == null) {
				f.format("%c", current.content);
				current = this.root;
			}
		}
		
		f.close();
	}
	
	public void serializeTree() {
		
	}
}
