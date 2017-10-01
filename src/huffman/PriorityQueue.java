package huffman;

import java.util.*;

public class PriorityQueue extends Tree {
	
	public ArrayList<Node> objects = new ArrayList<Node>();
	public HashMap<Node, Integer> nodeToPriority = new HashMap<Node, Integer>();
	
	// enqueue node to priority queue
	public void enqueue(Node n, int priority) {
		// add entry to hashmap
		nodeToPriority.put(n, priority);
		
		for (int i = 0; i <= this.objects.size(); i++) {
			if (i == this.objects.size()) {
				this.objects.add(n);
				break;
			} else if (nodeToPriority.get(this.objects.get(i)) > priority) {
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