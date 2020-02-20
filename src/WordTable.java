import java.io.*;
public class WordTable 
{
	/*
	 * WordTable constructor. 
	 * A WordTable has an array of wordTress called table.
	 */
	public WordTable() 
	{
		for(int i = 0; i < table.length; i++)
			table[i] = new WordTree();	
	}
	
	private WordTree[] table = new WordTree[26];
	
	
	/*
	 * A new method called add that takes a word as a string and a score as an int.
	 * This method gets the position in the hash table based on the first letter in the word.
	 * The wordTree method, add, is then called passing the given word and score.
	 */
	public void add(String word, int score) 
	{
		int position = (word.charAt(0) - 'a');
		table[position].add(word, score);	
	}
	
	/*
	 * A new method called getScore that takes a word as a string and returns its score as an int.
	 * This method gets the position in the hash table based on the first letter in the word.
	 * The wordTree method, getScore, is then called on the word tree in the hash table passing the given word.
	 */
	public double getScore(String word) 
	{
		int position = (word.charAt(0) - 'a');
		return(table[position].getScore(word));
	}
	
	/*
	 * A new method called contains that takes a word as a string and returns a boolean to tell if the word is in the table.
	 * This method gets the position in the hash table based on the first letter in the word.
	 * The wordTree method, contains, is then called on the word tree in the hash table passing the given word.
	 */
	public boolean contains(String word) 
	{
		int position = (word.charAt(0) - 'a');
		return(table[position].contains(word));
	}
	
	/*
	 * A new method called print that takes a PrintWriter and prints out the 
	 * hash table and all its wordTrees in order.
	 */
	public void print(PrintWriter out) 
	{
		for(int i = 0; i < table.length; i++)
			table[i].print(out);
	}
}

