package huffman;

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;

import huffman.Main.TreeType;

public class Tree extends Main {
	
	public int leftBit;
	public int rightBit;
	
	public Node root; // public nodity
	public HashMap<Character, String> binEquiv = new HashMap<Character, String>();	// hashmap of characters to their binary representations as determined by tree
	
	public Formatter f;
	
	// constructor for Tree: given filename of either text or serialization data, and boolean indicating which one it is 
	public Tree(TreeType type, String filename, Boolean fromSerialization) {
		filename = filename.replaceAll("/", fileSeparator);//universal system paths 
		
		// configure tree type
		if (type == TreeType._0LEFT_1RIGHT) {
			this.leftBit = 0;
			this.rightBit = 1;
		} else if (type == TreeType._1LEFT_0RIGHT) {
			this.leftBit = 1;
			this.rightBit = 0;
		}
		
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
					
					// if not standard char
					if (line.length() > 1) {
						// check special cases: ## represents null char and \n represents newline
						if (line.equals("##")) {
							chars.add('\u0000');
						} else if (line.equals("\\n")) {
							chars.add('\n');
						}
					} else {
						chars.add(line.charAt(0));
					}

				} else {
					// add line to total text
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
		
		if (!fromSerialization) {
			// construct a priority based on frequencies in text
			PriorityQueue pq = this.constructPQFromText(text);
			// construct tree from pq
			this.constructFromPQ(pq);
			
		} else {
			// construct tree, given serialization data
			this.constructFromSerialization(chars);
		}
		
		// initialize binequiv hashmap for quick encoding
		this.initializeBinEquiv();
	}

	// empty constructor
	public Tree() {
		
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
			parent = new Node(); // create parent node
			
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
			dfsForBinEquiv(n.leftChild, currentBinString + String.valueOf(this.leftBit));
		}
		// if right child exists
		if (n.rightChild != null) {
			dfsForBinEquiv(n.rightChild, currentBinString + String.valueOf(this.rightBit));
		}
		// if char not null
		if ((int) n.content != 0) {
			// add char mapped to current binary string
			binEquiv.put(n.content, currentBinString);
		}
	}
	
	// given file to read text from, encode to another file
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

	// given file to read binary from, decode to text into another file
	public void decodeFile(String readFrom, String writeTo) {
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
		
		this.decode(total, writeTo);
		
	}
	
	// encode a string of text to a file using hashmap from chars to binary
	public void encode(String text, String filename) {
		filename = filename.replaceAll("/", fileSeparator);//universal system paths 
		
		try {
			f = new Formatter(filename);
		} catch (Exception e) {
			System.out.println("Error creating file (Tree.encode)");
		}
		
		// for each character, write binary representation to new file
		for (int i = 0; i < text.length(); i++) {
			f.format("%s", binEquiv.get(text.charAt(i)));
		}
		
		f.close();
		
	}
	
	// decode a string of binary by tracing down tree and recording leaf node content
	public void decode(String binary, String filename) {
		filename = filename.replaceAll("/", fileSeparator);//universal system paths 
		
		try {
			f = new Formatter(filename);
		} catch (Exception e) {
			System.out.println("Error creating file (Tree.decode)");
		}
		
		Node current = this.root;
		
		for (int i = 0; i < binary.length(); i++) {
			// if bit is 1
			if (binary.charAt(i) == '\u0031') {
				// move to left or right child according to tree rules
				if (this.leftBit == 1) {
					current = current.leftChild;
				} else {
					current = current.rightChild;
				}
			// if bit is 0
			} else if (binary.charAt(i) == '\u0030') {
				// move to left or right child according to tree rules
				if (this.leftBit == 0) {
					current = current.leftChild;
				} else {
					current = current.rightChild;
				}
			}
			
			// if current node is leaf
			if (current.rightChild == null && current.leftChild == null) {
				// write content to file
				f.format("%c", current.content);
				// reset at root
				current = this.root;
			}
		}
		
		f.close();
	}
	
	// serialize tree to given file using BFS
	public void serializeTree(String writeTo) {
		writeTo = writeTo.replaceAll("/", fileSeparator); //universal system paths 
		
		try {
			
			BufferedWriter file = new BufferedWriter(new FileWriter(writeTo));
		
			ArrayList<Node> q = new ArrayList<Node>();
			q.add(this.root);
			
			while (q.size() > 0) {
				// pop node from queue
				Node current = q.get(0);
				q.remove(0);
				
				if (current.leftChild != null) {

					// write left child content
					if (current.leftChild.content == '\u0000') {
						file.write("##");
					} else if (current.leftChild.content == '\n') {
						file.write("\\n");
					} else {
						file.write(current.leftChild.content);
					}
					file.write('\n');
					
					// add to queue
					q.add(current.leftChild);
				}
				if (current.rightChild != null) {

					// write right child content
					if (current.rightChild.content == '\u0000') {
						file.write("##");
					} else if (current.rightChild.content == '\n') {
						file.write("\\n");
					} else {
						file.write(current.rightChild.content);
					}
					file.write('\n');

					// add to queue
					q.add(current.rightChild);
				}
			}
			
			file.close();
			
			
		} catch (IOException e) {
			System.out.println("Error writing to file (Tree.serializeTree)");
		}
	}
	
	// construct tree from array of chars from serialization file
	public void constructFromSerialization(ArrayList<Character> chars) {
		ArrayList<Node> needChildren = new ArrayList<Node>();	// array of which body nodes have yet to be given children
		this.root = new Node();
		needChildren.add(this.root);
		
		// for char in serialization data
		for (int i = 0; i < chars.size() - 1; i += 2) {
			// pop parent from needchildren
			Node parent = needChildren.get(0);
			needChildren.remove(0);
			
			// left child
			if (chars.get(i) == '\u0000') {
				Node lChild = new Node();
				parent.leftChild = lChild;
				needChildren.add(lChild);
			} else {
				parent.leftChild = new Node(chars.get(i));
			}
			
			// right child
			if (chars.get(i + 1) == '\u0000') {
				Node rChild = new Node();
				parent.rightChild = rChild;
				needChildren.add(rChild);
			} else {
				parent.rightChild = new Node(chars.get(i + 1));
			}
		}
	}
	
	// given files containing original text and binary encoding from this tree, calculate percent compression
	public double getPercentCompression(String originalFile, String encodedFile) {
		//universal system paths 
		originalFile = originalFile.replaceAll("/", fileSeparator);
		encodedFile = encodedFile.replaceAll("/", fileSeparator);
		
		// read encoded file
		String line = null;
		String total = "";
		try {
			FileReader fileReader = new FileReader(encodedFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				total += line;
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + encodedFile + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + encodedFile + "'");
		}
		
		int compressedBits = total.length();
		
		// read original file
		total = "";
		try {
			FileReader fileReader = new FileReader(originalFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				total += line + '\n';
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + originalFile + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + originalFile + "'");
		}
		
		String unicode = this.encodeToUnicode(total);
		int rawBits = unicode.length();
		
		double percentCompression = (1.0 - ((double) compressedBits / (double) rawBits)) * 100.0;
		
		return percentCompression;
	}
	
	// encode a string in standard UTF-8
	public String encodeToUnicode(String text) {
		
		String unicode_encoding = "";
		for (int i = 0; i < text.length(); i++) {
			
			byte[] bytes = String.valueOf(text.charAt(i)).getBytes(Charset.forName("UTF-8"));
			
			for (byte b : bytes) {
				
				String str = Integer.toString(b, 2);
				String binString;
				
				// add extra 0s if necessary
				if (str.length() < 8) {
					binString = "";
					for (int j = 0; j < 8 - str.length(); j++) {
						binString += "0";
					}
					binString += str;
				} else {
					binString = str;
				}

				// add to unicode encoding
				unicode_encoding += binString;
			}
		}
		
		return unicode_encoding;
	}
	
	public void writeUnicodeToFile(String readFrom, String writeTo) {
		//universal system paths
		readFrom = readFrom.replaceAll("/", fileSeparator);
		writeTo = writeTo.replaceAll("/", fileSeparator);
		
		String line = null;
		String total = "";
		try {
			FileReader fileReader = new FileReader(readFrom);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				total += line;
			}
			bufferedReader.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + readFrom + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + readFrom + "'");
		}
		
		String encoded = this.encodeToUnicode(total);
		
		try {
			f = new Formatter(writeTo);
			f.format("%s", encoded);
			f.close();
			
		} catch (Exception e) {
			System.out.println("Error creating file (Tree.encode)");
		}
	}
}