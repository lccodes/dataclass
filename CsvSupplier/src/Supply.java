import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Supply {
	private static final String CSVSPLIT = ",";
	
	public static void main(String[] args) {
		String csvFile = "C:\\Users\\lcamery\\Downloads\\CollegeScorecard_Raw_Data\\CollegeScorecard_Raw_Data\\MERGED2013_PP.csv";
		BufferedReader br = null;
		String line = "";
		StringBuilder outCsv = new StringBuilder();
		Set<String> columns = new HashSet<String>();
		boolean help = false;
		if (args.length == 0) {
			System.out.println("usage: <column1> ... <columnN> || -help ");
		} else if (args.length == 1 && args[0].equals("-help")) {
			help = true;
		} else {
			for (String a : args) {
				columns.add(a);
			}
		}

		try {
			Set<Integer> accept = new HashSet<Integer>();
			if (columns.contains("UNITID")) {
				accept.add(0);
				outCsv.append("UNITID,");
				columns.remove("UNITID");
			}
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			if (line == null) {
				System.out.println("Malformed main CSV");
				return;
			}
			String[] parts = line.split(CSVSPLIT);
			for (int i = 0; i < parts.length; i++) {
				if (columns.contains(parts[i])) {
					outCsv.append(parts[i] + ",");
					accept.add(i);
					columns.remove(parts[i]);
				}
				if (help) {
					System.out.println(parts[i]);
				}
			}
			outCsv.append("\n");
			
			if (help) {
				return;
			} else if (!columns.isEmpty()) {
				System.out.println("WARNING: The following columns were not found " + columns);
			}
			
			while ((line = br.readLine()) != null) {
				parts = line.split(CSVSPLIT);
				for (int i = 0; i < parts.length; i++) {
					if (accept.contains(i)) {
						outCsv.append(parts[i] + ",");
					}
				}
				outCsv.append("\n");
			}

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("myselections.csv"));
				bw.write(outCsv.toString());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
