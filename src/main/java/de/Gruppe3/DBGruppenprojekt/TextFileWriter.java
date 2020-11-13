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
		if(!resource.isFile()) {
			resource.getFile().createNewFile();
		}
		bufferedWriter= new BufferedWriter(new FileWriter(resource.getFile()));
		bufferedWriter.write(header);
	}
	
	public void appendData(String data) throws IOException {
		bufferedWriter.newLine();
		bufferedWriter.append(data);
	}
	
	public void closeWriter() throws IOException {
		bufferedWriter.close();
		System.out.println("Hier ist die Datei gespeichert "+ resource.getFile().toURI().toString());
	}
}
