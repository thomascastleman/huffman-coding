package huffman;

import java.util.*;

public class Main {
	
	public int leftBit = 0;
	public int rightBit = 1;

	public static void main(String[] args) {
		
		/*
		 * Projected Use:
		 * 
		 * if I have a plaintext file:
		 * 
		 * Tree t = new Tree("file.txt", false);
		 * t.encodeFile("file.txt");
		 * 
		 * 
		 * 
		 * if I have a serialization file:
		 * 
		 * Tree t = new Tree("serialization.txt", true);
		 * t.decodeFile("binaryEncoding.txt");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * if I then want to serialize:
		 * t.serializeTree("serializetothisfile.txt");
		 * 
		 */
		
		Tree t = new Tree("input.txt", false);
		
		System.out.println(t.binEquiv.get('H'));
		
		
		
		
//		PriorityQueue pq = new PriorityQueue();
//		
//		Node n0 = new Node('a');
//		Node n1 = new Node('b');
//		Node n2 = new Node('c');
//		
//		pq.enqueue(n0,  10);
//		pq.enqueue(n1,  3);
//		pq.enqueue(n2,  1);
//		
//		Tree t = new Tree();
//		t.constructFromPQ(pq);
//		t.initializeBinEquiv();
		
//		Tree t = new Tree();
//		PriorityQueue pq = t.constructPQFromText("this is the text");
//		t.constructFromPQ(pq);
//		t.initializeBinEquiv();
		
		// t.serializeTree("s.txt");
		
//		ArrayList<Character> chars = new ArrayList<Character>();
//		chars.add('\u0000');
//		chars.add('a');
//		chars.add('c');
//		chars.add('b');
//		
//		System.out.println(chars.get(0) == '\u0000');
//		
//		System.out.println(chars);
//		
//		Tree test = new Tree();
//		test.constructFromSerialization(chars);
//		test.initializeBinEquiv();
//		
//		System.out.println(test.binEquiv.get('a'));
//		System.out.println(test.binEquiv.get('b'));
//		System.out.println(test.binEquiv.get('c'));
		
		
		
//		t.serializeTree();
		
		// t.encode("abccba");
		
//		t.decode("1010000011");
		
//		String id = UUID.randomUUID().toString();
//		System.out.println(id);
		
	}
	
	public static void logNode(Node n) {
		System.out.print("\nContent: " + n.content);

	}

}
