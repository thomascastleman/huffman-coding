package huffman;

public class Main {
	
	public int leftBit = 0;
	public int rightBit = 1;

	public static void main(String[] args) {
		
		PriorityQueue pq = new PriorityQueue();
		
		Node n0 = new Node('a');
		Node n1 = new Node('b');
		Node n2 = new Node('c');
		
		pq.enqueue(n0,  10);
		pq.enqueue(n1,  3);
		pq.enqueue(n2,  1);
		
		Tree t = new Tree();
		t.constructFromPQ(pq);
		t.initializeBinEquiv();
		
		// t.encode("abccba");
		
		t.decode("1010000011");
		
		
//		
//		logNode(t.root);
//		
//		System.out.print("\nleft: ");
//		logNode(t.root.leftChild);
//		System.out.print("\nright: ");
//		logNode(t.root.rightChild);
//		
//		System.out.print("\nleft: ");
//		logNode(t.root.leftChild.leftChild);
//		System.out.print("\nright: ");
//		logNode(t.root.leftChild.rightChild);
		
		
		

	}
	
	public static void logNode(Node n) {
		System.out.print("\nContent: " + n.content);

	}

}
