package de.Gruppe3.DBGruppenprojekt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TextFileWriter {

	public BufferedWriter bufferedWriter;
	public Resource resource = new ClassPathResource("result.txt");
	
	public TextFileWriter() throws IOException {
		String header = "Zeiten der CRUD-Operationen";
		String mariaDB = "Zeiten der MongoDB";
		if(!resource.isFile()) {
			resource.getFile().createNewFile();
		}
		bufferedWriter= new BufferedWriter(new FileWriter(resource.getFile()));
		bufferedWriter.write(header);
		bufferedWriter.newLine();
		bufferedWriter.write(mariaDB);
	}
	
	public void appendData(String operation, int length, double time) throws IOException {
		bufferedWriter.newLine();
		bufferedWriter.append(operation+ " Anzahl: "+length+ " Dauer: "+ time +" Nanosekunden");
	}
	
	public void closeWriter() throws IOException {
		bufferedWriter.close();
		System.out.println("Hier ist die Datei gespeichert "+ resource.getFile().toURI().toString());
	}
	
	public void writeHeaderMariaDB() throws IOException {
		String mariaDB= "Zeiten der MariaDB";
		bufferedWriter.append("\n");
		bufferedWriter.append(mariaDB);
	}
}
