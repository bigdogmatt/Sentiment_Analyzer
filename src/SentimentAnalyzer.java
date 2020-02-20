import java.util.Scanner;
import java.io.*;

public class SentimentAnalyzer 
{

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		Scanner stopWords = null;

		while(stopWords == null)
		{
			try 
			{ //asks for stop word file and creates a scanner for that txt file
				System.out.print("Enter a stop word file: ");
				String stopFile = in.nextLine();
				stopWords = new Scanner(new File(stopFile));
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("Invalid file name.");
			}
		}


		Scanner reviews = null;
		String reviewFile = null;
		while(reviews == null)
		{
			try 
			{ //asks for review file and creates a scanner for that txt file
				System.out.print("Enter a review file: ");
				reviewFile = in.nextLine();
				reviews = new Scanner(new File(reviewFile));
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("Invalid file name.");
			}
		}
		WordTable stopWordsTable = new WordTable (); //wordTable is created that will hold the words from the stop words file
		WordTable reviewTable = new WordTable (); //wordTable is created that will hold the words from the review file 
		
		//the stop words do not need partitioned since every word is legal and on its own line, therefore it can just be added to stopWordsTable
		while(stopWords.hasNext()) 
		{
			String stopWord = stopWords.next();
			stopWordsTable.add(stopWord, 0); //Even though the stop words do not have scores add() requires one, therefore the 0 is just a filler
		}
		stopWords.close();	//close the stopWord file since it is no longer needed
		
		//Since the words in the review file are not already separated and we need to partition the words, clean them and check if they are a stop word.
		//As long as reviews is not empty, the loop will run through and store the letters, apostrophe, or a hyphen into word. 
		//If there is a character other than the three mentioned above, it will not be included and a new word will start.
		while(reviews.hasNext()) 
		{
			int score = reviews.nextInt();
			String line = reviews.nextLine().toLowerCase();
			String word = "";
			for(int i = 0; i<line.length(); i++) 
			{
				char letter = line.charAt(i);
				if((letter>='a' && letter<='z') || letter=='-' || letter=='\'') 
				{
					word += line.charAt(i);

				}
				else 
				{
					word = clean(word);
					if(word.length()>0 && !stopWordsTable.contains(word))
						reviewTable.add(word, score);
					word = ""; //the word is returned to an empty string so that it is not added again after the for loop
				}
			}
			//this code repeats outside of the for loop to catch the words that are at the end of the line with no forbidden characters 
			//behind them, meaning the would not have already been added to stopWordsTable
			word = clean(word); 
			if(word.length()>0 && !stopWordsTable.contains(word))
				reviewTable.add(word, score);
		}
		reviews.close(); //close the stopWord file since it is no longer needed

		try 
		{ //creates a file to hold statistics for the words in reviews
			PrintWriter out = new PrintWriter(reviewFile + ".words");
			reviewTable.print(out);
			out.close();
			System.out.println("Word statistics written to file " + reviewFile + ".words");
			System.out.println("");
		} 
		catch (FileNotFoundException e) 
		{
		}

		String answer = "yes";
		while(!answer.equals("no"))
		{ //will continue to ask the question until the users types no
			System.out.print("Would you like to analyze a phrase? (yes/no) ");
			answer = in.nextLine();
			//If the user wants to analyze a phrase, they will type it in and it will be partitioned it to words. 
			//The score for each word will be looked up and averaged to get the rating.
			if(answer.equals("yes")) 
			{ 
				double score = 0;
				int count = 0;
				System.out.print("Enter phrase: ");
				String line = in.nextLine(); //the phrase is partitioned in the same way as the review file
				String word = "";
				for(int i = 0; i<line.length(); i++)
				{
					char letter = line.charAt(i);
					if((letter>='a' && letter<='z') || letter=='-' || letter=='\'')
					{
						word += line.charAt(i);
					}
					else
					{
						word = clean(word);
						if(word.length()>0 && !stopWordsTable.contains(word))
						{
							score += reviewTable.getScore(word);
							count ++;
						}
						word = "";
					}
				}

				word = clean(word);
				//if the cleaned word is not a stop word, it's score will be retrieved and the count will go up
				if(word.length()>0 && !stopWordsTable.contains(word))
				{
					score += reviewTable.getScore(word);
					count ++;
				}

				double rating = score/count;
				
               //As long as the count is greater than 0, the rating will be printed with only 3 digits after the decimal 
			   // and will indicate if the number is positive, negative, or neutral
				if(count == 0) 
				{ 
					System.out.println("Your phrase contained no words that affect sentiment.");
				}
				else if(rating>2.0) 
				{
					System.out.format("Score: %.3f\n", rating);
					System.out.println("Your phrase was positive.");
				}
				else if(rating<2.0) 
				{
					System.out.format("Score: %.3f\n", rating);
					System.out.println("Your phrase was negative.");
				}
				else 
				{
					System.out.format("Score: %.3f\n", rating);
					System.out.println("Your phrase was perfectly neutral.");
				}
			}
		}
	}

	public static String clean(String word) 
	{ //cleans word by creating a substring without the excess " ' " and " - " at the beginning and end of the word
		while(word.length()>0 && (word.charAt(0) == '-' ||word.charAt(0) == '\'')) 
		{ //if the first character is a " ' " or " - ", word will become a substring  
			word = word.substring(1, word.length());		//of everything except the first character and will loop through until it finds a letter
		}
		while(word.length()>0 && (word.charAt(word.length()-1) == '-' ||word.charAt(word.length()-1) == '\'')) 
		{
			word = word.substring(0, word.length()-1);   //if the last character is a " ' " or " - ", word will become a substring of everything except
		}												 // the last character and will loop through until it finds a letter
		return word;
	}
}