package huffman;

import java.util.*;

public class Main {
	
	public enum TreeType{_0LEFT_1RIGHT, _1LEFT_0RIGHT};
	public static String fileSeparator = System.getProperty("file.separator");	// get system file separator

	public static void main(String[] args) {		
		if (fileSeparator != "/") {
			fileSeparator += "\\";
		}
		
		// construct tree off of text, encode text, and serialize tree
		Tree tree = new Tree(TreeType._0LEFT_1RIGHT, "textFiles/input.txt", false);
		tree.encodeFile("textFiles/input.txt", "textFiles/encoded.txt");
		tree.serializeTree("textFiles/serialization.txt");
		
		// construct new tree off of serialization of previous tree, decode text
		Tree newTree = new Tree(TreeType._0LEFT_1RIGHT, "textFiles/serialization.txt", true);
		tree.decodeFile("textFiles/encoded.txt", "textFiles/decoded.txt");
		
	}
}