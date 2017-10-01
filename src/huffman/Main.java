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
		 * Tree t = new Tree("file.txt", false);
		 * t.encodeFile("file.txt");
		 * 
		 * 
		 * 
		 * if I have a serialization file:
		 * Tree t = new Tree("serialization.txt", true);
		 * t.decodeFile("binaryEncoding.txt");
		 * 
		 * 
		 * if I then want to serialize:
		 * t.serializeTree("serializetothisfile.txt");
		 * 
		 */
		
		
		
	}
}















