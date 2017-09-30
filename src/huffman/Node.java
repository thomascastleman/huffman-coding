package huffman;

public class Node extends Tree {
	
	public char content;		// content of node itself (char)
	public Node leftChild;		// left child in bst
	public Node rightChild;		// right child in bst
	
	public int priority;		// frequency of content (used only for priority queue)
	
	public Node(char content_, int priority_) {
		this.content = content_;
		this.priority = priority_;
	}
}
