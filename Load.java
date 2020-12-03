import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class Load {

	private Student[] studArray;
	public String fileName;

	public Student[] loadRoster() {
//LinkedList<Student> students = new LinkedList<>();
		
		JButton open = new JButton();
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Select CSV FILE");
	//	fc.setFileSelectionMode("JFileChooser.") possibly make only csv files selectable
		if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION)
		{
			//stuff if approved file
		}
		//System.out.println(fc.getSelectedFile().getAbsolutePath());

		String path = fc.getSelectedFile().getAbsolutePath();
		fileName = fc.getSelectedFile().getName();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			try {
				BufferedReader iterator = new BufferedReader(new FileReader(path));

			int lineCount = 0;

			while(iterator.readLine() != null)
				lineCount++;

			String line = null;
			int n = 0;
			studArray = new Student[lineCount];
			while((line = reader.readLine()) != null) {
				String inputData[] = line.split(",");
				//int count = inputData.length;
				//int numItems = count/6;
				int i = 0;
//				int n = 0;
				// while(i < numItems) {
				Student newStudent = new Student();
				newStudent.setID(inputData[i]);
				newStudent.setFirst(inputData[i+1]);
				newStudent.setLast(inputData[i+2]);
				newStudent.setProg(inputData[i+3]);
				newStudent.setLevel(inputData[i+4]);
				newStudent.setASUrite(inputData[i+5]);

				studArray[n] = newStudent;
				n++;
			}
			/*if ((line = reader.readLine()) != null) {
				Scanner scan = new Scanner(line).useDelimiter(",");
				while (scan.hasNext()) {
					// System.out.println(scan.next());
					count++;
					System.out.println(count);
				}
				scan.close();

				System.out.println(line);
			}*/
			/*String input = null;
			try {
				BufferedReader inputData = new BufferedReader(new FileReader(path));
				studArray = new Student[count%6];

				while ((input = inputData.readLine()) != null) {
					Scanner scan2 = new Scanner(input).useDelimiter(",");
					for(int i = 0; i < count%6; i++) {
						Student newStudent = new Student();
						newStudent.setID(scan2.next());
						newStudent.setFirst(scan2.next());
						newStudent.setLast(scan2.next());
						newStudent.setProg(scan2.next());
						newStudent.setLevel(scan2.next());
						newStudent.setASUrite(scan2.next());

						studArray[i] = newStudent;
					}
					scan2.close();
					//return studArray;
				}*/
			}catch(FileNotFoundException a) {
				System.out.println("File not found.");
			}catch(IOException a) {
				System.out.println("Error.");
			}
		}catch(FileNotFoundException a) {
			 System.out.println("File not found.");
		}
		return studArray;
	}
}
