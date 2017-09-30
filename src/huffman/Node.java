package huffman;

public class Node extends Tree {
	
	public char content;		// content of node itself (char)
	public Node leftChild;		// left child in bst
	public Node rightChild;		// right child in bst
	
	public Node(char content_) {
		this.content = content_;
	}
	
	public Node() {
		
	}
}
