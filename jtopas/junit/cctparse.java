import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class cctparse {

public static void main(String[] args) throws IOException {
	BufferedReader stack = new BufferedReader(new FileReader("outsuccessFinal.txt"));
	BufferedReader stackfailed = new BufferedReader(new FileReader("outfailFinal.txt"));
	BufferedWriter cct = new BufferedWriter(new FileWriter("cct.csv"));	
	
	ArrayList<String> passed = new ArrayList<String>();
	ArrayList<Integer> passedCount = new ArrayList<Integer>();
	ArrayList<String> failed = new ArrayList<String>();
	ArrayList<Integer> failedCount = new ArrayList<Integer>();
	
	
	String line = new String();
	double allfailed = 0.0;
	double allpassed = 0.0;
	
	while (stack.ready()) {
		line = stack.readLine();
		String[] words = line.split("\\s+");
		if (words.length > 0) {
		allpassed += 1;
		if (words[0].contains("CALL")) {
			if (!passed.contains(words[1])) {
				passed.add(words[1]);
				passedCount.add(new Integer(1));
			}
			else {
				int index = passed.indexOf(words[1]);
				Integer count = (Integer) passedCount.get(index);
				int ncnt = count.intValue()+1;
				passedCount.set(index, new Integer(ncnt));
			}
		}
		}
	}
	stack.close();
	while (stackfailed.ready()) {
		line = stackfailed.readLine();
		String[] words = line.split("\\s+");
		if (words.length > 0) {
		allfailed += 1;
		if (words[0].contains("CALL")) {
			if (!failed.contains(words[1])) {
				failed.add(words[1]);
				failedCount.add(new Integer(1));
			}
			else {
				int index = failed.indexOf(words[1]);
				Integer count = (Integer) failedCount.get(index);
				int ncnt = count.intValue()+1;
				failedCount.set(index, new Integer(ncnt));
			}
		}
		}
	}
	stackfailed.close();
	
	String headings = "Method Name, Passed, Failed, Tarantula, SBI, Jaccard, Ochiai";
	cct.write(headings);
	cct.newLine();
	for (String str : passed) {
		String row = str;
		int i = passed.indexOf(str);
		Integer count = (Integer) passedCount.get(i);
		int cnt = count.intValue();
		int ct = 0;
		row += "," + Integer.toString(cnt);
		if (failed.contains(str)) {
			int j = failed.indexOf(str);
			Integer c = (Integer) failedCount.get(j);
			ct = c.intValue();
			row += "," + Integer.toString(ct);
		}
		else {
			row += "," + ct;
		}
		double tar = 0;
		double och = 0;
		if (ct > 0) {
			tar = (ct/allfailed)/((cnt/allpassed)+(ct/allfailed));	
			och = (double)ct/Math.sqrt(allfailed*(cnt+ct));
		}
		double sbi = ct/(cnt+ct);
		double jac = ct/(allfailed + cnt);
		row += "," + Double.toString(tar);
		row += "," + Double.toString(sbi);
		row += "," + Double.toString(jac);
		row += "," + Double.toString(och);
		cct.write(row);
		cct.newLine();
	}
	
	for (String str : failed) {
		String row = str;
		int i = failed.indexOf(str);
		Integer fcount = (Integer) failedCount.get(i);
		int ct = fcount.intValue();
		int cnt = 0;
		if (!passed.contains(str)) {
			row += "," + Integer.toString(cnt);
			row += "," + Integer.toString(ct);
		
			double tar = 0;
			double och = 0;
			if (ct > 0) {
				tar = (ct/allfailed)/((cnt/allpassed)+(ct/allfailed));	
				och = (double)ct/Math.sqrt(allfailed*(cnt+ct));
			}
			double sbi = ct/(cnt+ct);
			double jac = ct/(allfailed + cnt);
			row += "," + Double.toString(tar);
			row += "," + Double.toString(sbi);
			row += "," + Double.toString(jac);
			row += "," + Double.toString(och);
			cct.write(row);
			cct.newLine();
		}
	}
	
	cct.close();
}
}