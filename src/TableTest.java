import java.io.PrintWriter;

public class TableTest {

	public static void main(String[] args) {
		WordTable table = new WordTable();
		table.add("word", 2);
		table.add("bird", 4);
		table.add("nerd", 0);
		table.add("heard", 2);
		table.add("herd", 1);
		table.add("gird", 2);
		table.add("turd", 3);
		table.add("purred", 2);
		table.add("turd", 1);
		
		
		if(table.contains("heard"))
			System.out.println("Test 1 passed!");
		else
			System.out.println("Test 1 failed!");

		if(table.contains("hard"))
			System.out.println("Test 2 failed!");
		else
			System.out.println("Test 2 passed!");
		
		PrintWriter out = new PrintWriter(System.out);
		table.print(out);
		out.flush();
	}

}
