package huffman;

import java.util.*;

public class Main {
	
	public int leftBit = 0;
	public int rightBit = 1;
	
	public static String fileSeparator = System.getProperty("file.separator");

	public static void main(String[] args) {		
		if (fileSeparator != "/") {
			fileSeparator += "\\";
		}
		
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
		
		System.out.println("Creating tree from input");
		
		Tree t = new Tree("textFiles/input.txt", false);
		
		t.logBinEquiv();
		
//		t.encodeFile("textFiles/input.txt", "textFiles/encoded.txt");
//		t.decodeFile("textFiles/encoded.txt", "textFiles/decoded.txt");
		
		t.serializeTree("textFiles/ser.txt");
		
		
		System.out.println("Creating other tree from serialization");
		Tree otherTree = new Tree("textFiles/ser.txt", true);
		otherTree.logBinEquiv();
		
		
	}
	
	public static void logNode(Node n) {
		System.out.print("\nContent: " + n.content);

	}

}















