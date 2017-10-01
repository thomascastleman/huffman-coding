package huffman;

import java.util.*;
import java.io.*;

public class Tree extends Main {
	
	public Node root; // public nodity
	public HashMap<Character, String> binEquiv = new HashMap<Character, String>();
	
	public Formatter f;
	
	public Tree(String filename, Boolean fromSerialization) {
		filename = filename.replaceAll("/", fileSeparator);//universal system paths 
		
		ArrayList<Character> chars = new ArrayList<Character>();
		String text = "";
		
		// read in file
		String line = null;
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
			int count = 0;
			while ((line = bufferedReader.readLine()) != null) { 
				if (count > 0) {
					text += '\n';
				}
				
				if (fromSerialization) {
					
					if (line.length() > 1) {
						if (line.equals("##")) {
							chars.add('\u0000');
						} else if (line.equals("\\n")) {
							chars.add('\n');
						}
					} else {
						chars.add(line.charAt(0));
					}

				} else {
					text += line;
				}
				count++;
			}

			bufferedReader.close();
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}
		
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
		
		
		
		if (!fromSerialization) {
			PriorityQueue pq = this.constructPQFromText(text);
			
//			for (int i = 0; i < pq.objects.size(); i++) {
//				System.out.println(pq.objects.get(i).content + " " + pq.nodeToPriority.get(pq.objects.get(i)));
//			}
			
			this.constructFromPQ(pq);
			
		} else {
			this.constructFromSerialization(chars);
		}
		
		this.initializeBinEquiv();
	}

	public Tree() {
		
	}
	
	// DEBUG
	public void logBinEquiv() {
		ArrayList<Character> keys = new ArrayList<Character>(this.binEquiv.keySet());
		
		for (int i = 0; i < keys.size(); i++) {
			System.out.println(keys.get(i) + ": " + this.binEquiv.get(keys.get(i)));
		}
	}
	
	// given string of text, construct priority queue based on char frequency
	public PriorityQueue constructPQFromText(String text) {
		HashMap<Character, Integer> charToFrequency = new HashMap<Character, Integer>();
		
		// map characters to frequencies in text
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (charToFrequency.get(c) != null) {
				charToFrequency.put(c, charToFrequency.get(c) + 1);
			} else {
				charToFrequency.put(c, 1);
			}
		}
		
		PriorityQueue pq = new PriorityQueue();
		
		// all keys in hashmap
		ArrayList<Character> chars = new ArrayList<Character>(charToFrequency.keySet());
		
		// create node for each character, enqueue with frequency as priority
		for (int i = 0; i < chars.size(); i++) {
			Node n = new Node(chars.get(i));
			pq.enqueue(n, charToFrequency.get(chars.get(i)));
		}
		
		return pq;
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
	
	public void encodeFile(String readFrom, String writeTo) {
		readFrom = readFrom.replaceAll("/", fileSeparator);//universal system paths 
		
		String total = "";
		
		String line = null;
		try {
			FileReader fileReader = new FileReader(readFrom);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) { 
				total += line + '\n';
			}

			bufferedReader.close();
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + readFrom + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + readFrom + "'");
		}
		
		this.encode(total, writeTo);
	}
	
	public void decodeFile(String readFrom, String writeTo) {
		readFrom = readFrom.replaceAll("/", fileSeparator);//universal system paths 
		
		String total = "";
		
		String line = null;
		try {
			FileReader fileReader = new FileReader(readFrom);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) { 
				System.out.println(line);
				total += line + '\n';
			}

			bufferedReader.close();
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + readFrom + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + readFrom + "'");
		}
		
		this.decode(total, writeTo);
		
	}
	
	// encode a string of text using already existing hashmap from chars to binary
	public void encode(String text, String filename) {
		filename = filename.replaceAll("/", fileSeparator);//universal system paths 
		
		try {
			f = new Formatter(filename);
		} catch (Exception e) {
			System.out.println("Error creating file (Tree.encode)");
		}
		
		for (int i = 0; i < text.length(); i++) {
			
			f.format("%s", binEquiv.get(text.charAt(i)));
			
		}
		
		f.close();
		
	}
	
	public void decode(String binary, String filename) {
		filename = filename.replaceAll("/", fileSeparator);//universal system paths 
		
		try {
			f = new Formatter(filename);
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
	
	// serialize tree to given file
	public void serializeTree(String writeTo) {
		writeTo = writeTo.replaceAll("/", fileSeparator); //universal system paths 
		
		try {
			
			BufferedWriter file = new BufferedWriter(new FileWriter(writeTo));
		
			ArrayList<Node> q = new ArrayList<Node>();
			q.add(this.root);
			
			while (q.size() > 0) {
				Node current = q.get(0);
				q.remove(0);
				
				if (current.leftChild != null) {

					if (current.leftChild.content == '\u0000') {
						file.write("##");
					} else if (current.leftChild.content == '\n') {
						file.write("\\n");
					} else {
						file.write(current.leftChild.content);
					}
					file.write('\n');
					
					q.add(current.leftChild);
				}
				if (current.rightChild != null) {

					if (current.rightChild.content == '\u0000') {
						file.write("##");
					} else if (current.rightChild.content == '\n') {
						file.write("\\n");
					} else {
						file.write(current.rightChild.content);
					}
					
					file.write('\n');

					q.add(current.rightChild);
				}
			}
			
			file.close();
			
			
		} catch (IOException e) {
			System.out.println("Error writing to file (Tree.serializeTree)");
		}
	}
	
	// construct tree from array of chars from serialization file
	public void constructFromSerialization(ArrayList<Character> lines) {
		ArrayList<Node> needChildren = new ArrayList<Node>();
		this.root = new Node();
		needChildren.add(this.root);
		
		for (int i = 0; i < lines.size() - 1; i += 2) {
			Node parent = needChildren.get(0);
			needChildren.remove(0);
			
			// left child
			if (lines.get(i) == '\u0000') {
				Node lChild = new Node();
				parent.leftChild = lChild;
				needChildren.add(lChild);
			} else {
				parent.leftChild = new Node(lines.get(i));
			}
			
			// right child
			if (lines.get(i + 1) == '\u0000') {
				Node rChild = new Node();
				parent.rightChild = rChild;
				needChildren.add(rChild);
			} else {
				parent.rightChild = new Node(lines.get(i + 1));
			}
		}
	}
}

















