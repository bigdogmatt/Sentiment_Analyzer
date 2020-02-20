import java.io.*;

public class WordTree 
{
	/*
	 * A node has a word, a score, a count, a left node, 
	 * a right node, and a boolean that tells if the node
	 * is a black node or red node.
	 */
	private static class Node 
	{
		public String word;
		public int score;
		public int count;
		public boolean black;
		public Node left;
		public Node right;
	}	
	
	/*
	 * A wordTree has a node called root that is the starting node of the tree
	 */
	private Node root = null;
	
	public void add(String word, int score) 
	{
		root = add(root, word, score);
		root.black = true;
	}
	
	/*
	 * A new method called add that takes a node, a word as a string, and a score as an int.
	 * This method adds the given node and score to the wordTree with the given node as the root.
	 * If the word is already int the tree, it add the score and increments the count of that word.  
	 */
	private static Node add(Node node, String word, int score)
	{
		if(node == null)
		{
			node = new Node();
			node.word = word;
			node.score = score;
			node.count = 1;
		}
		
		else if(word.compareTo(node.word) < 0)
			node.left = add(node.left, word, score);
		
		
		else if(word.compareTo(node.word) > 0)
			node.right = add(node.right, word, score);
		
		else
		{
			node.score += score;
			node.count++;
		}
		
		if(isRed(node.right) && !isRed(node.left))
			node = rotateLeft(node);
		
		if(isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		
		if(isRed(node.left) && isRed(node.right))
			flipColors(node);
		
		return node;
	}

	public boolean contains(String word) 
	{
		return contains(root, word);
	}
	
	/*
	 * A new method called contains that takes a node and a word as a string. 
	 * This method searches for a word in a tree using the given node as the root.
	 * If found, this method returns true, if not found, it returns false.
	 */
	private static boolean contains(Node node, String word) 
	{
		if(node == null)
			return false;
	
		else if(node.word.equals(word))
			return true;
		
		else if(word.compareTo(node.word) < 0)
			return contains(node.left, word);
		
		else
			return contains(node.right, word);
	}
	
	
	public double getScore(String word) 
	{
		return getScore(root, word);
	}
	
	/*
	 * A new method called getScore that takes a node and a word as a string. 
	 * This method searches for the given word and returns the score of that 
	 * word using the given node as the root of the tree.
	 */
	private static double getScore(Node node, String word) 
	{
		if(node == null)
			return 2.0;
		
		else if(node.word.equals(word))
			return node.score/(double)node.count;
		
		else if(word.compareTo(node.word) < 0)
			return getScore(node.left, word);
		
		else
			return getScore(node.right, word);
	}

	public void print( PrintWriter out ) 
	{
		print(root, out);
	}
	
	/*
	 * A new method that takes a node and a PrintWriter and
	 * prints out the subtree in order using the given node
	 * as the root.
	 */
	private static void print(Node node, PrintWriter out) 
	{
		if(node != null)
		{
			print(node.left, out);
			out.print(node.word + "\t");
			out.print(node.score + "\t");
			out.println(node.count);
			print(node.right, out);
		}
	}
	
	/*
	 * A new method that takes a node and performs a left rotation on it and returns the new root
	 * of that subtree.
	 */
	private static Node rotateLeft(Node h)
	{
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.black = h.black;
		h.black = false;
		return x;
	}
	
	/*
	 * A new method that takes a node and performs a right rotation on it and returns the new root
	 * of that subtree.
	 */
	private static Node rotateRight(Node h)
	{
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.black = h.black;
		h.black = false;
		return x;
	}
	
	/*
	 * A new method that flips the color of a given node from red to black or black to red.
	 */
	private static void flipColors(Node h)
	{
		h.black = false;
		h.left.black = true;
		h.right.black = true;
	}
	
	/*
	 * A new method that returns true if the node is a red node and false if a black node
	 */
	private static boolean isRed(Node h)
	{
		if (h == null) 
			return false;
		
		return !h.black;
	}
}
