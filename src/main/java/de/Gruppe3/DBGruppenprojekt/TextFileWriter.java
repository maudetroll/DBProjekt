package de.Gruppe3.DBGruppenprojekt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter {

	public BufferedWriter bufferedWriter;
	
	public void writeData() throws IOException {
		String str = "Zeiten der Crud-Operationen";
		String fileName= "";
		bufferedWriter= new BufferedWriter(new FileWriter(fileName));
		bufferedWriter.write(str);
	}
	
	public void appendData(String data) throws IOException {
		bufferedWriter.newLine();
		bufferedWriter.append(data);
	}
	
	public void closeWriter() throws IOException {
		bufferedWriter.close();
	}
}
