import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;

public class Save {
	
	private String filename;
	private FileWriter fstream;
	Student student = new Student();

	/**
	 * This method prints the information into a csv file using the information given by the main class.
	 * 
	 * , is used to move from column to column
	 * 
	 * lineSeperator is used to move to next row
	 * 
	 * //@param namedFile
	 * //@param table
	 */
	void writeFile(String namedFile, JTable table)
	{
		/*
		 * Allows program to open a file and then write the information to it
		 */
		try
		{
			filename = namedFile;
			fstream = new FileWriter(filename, false); //true tells to append data.

			DefaultTableModel model = (DefaultTableModel)table.getModel();
			int columns = model.getColumnCount();
			
			for(int i = 0; i < model.getRowCount(); i++)
		    {
				for(int n = 0; n < columns; n++){
					String data = (String) model.getValueAt(i, n);
					fstream.write(data);
					if (n != columns - 1)
						fstream.write(",");
				}
				fstream.write(System.lineSeparator());
		    }
		}
		
		catch (IOException e) {
		    System.err.println("Error: " + e.getMessage());
		}
		/*
		 *  Closes the file and flushes the buffer of any remaining instructions to print
		 */
		finally
		{
			try {
				fstream.flush();
				fstream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    fstream = null;
            System.gc();
		}
	}
}
