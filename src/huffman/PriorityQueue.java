package huffman;

import java.util.*;

public class PriorityQueue extends Tree {
	
	public ArrayList<Node> objects = new ArrayList<Node>();
	
	// enqueue node to priority queue
	public void enqueue(Node n) {
		for (int i = 0; i <= this.objects.size(); i++) {
			if (i == this.objects.size()) {
				this.objects.add(n);
				break;
			} else if (this.objects.get(i).priority > n.priority) {
				this.objects.add(i, n);
				break;
			}
		}
	}
	
	// dequeue node from priority queue
	public Node dequeue() {
		if (this.objects.size() > 0) {
			Node n = this.objects.get(0);
			this.objects.remove(0);
			
			return n;
		} else {
			return null;
		}
	}
}
