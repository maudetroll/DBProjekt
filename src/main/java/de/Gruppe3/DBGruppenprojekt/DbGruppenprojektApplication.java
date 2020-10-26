package de.Gruppe3.DBGruppenprojekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.vorburger.exec.ManagedProcessException;

@SpringBootApplication
public class DbGruppenprojektApplication {

	public static void main(String[] args) throws ManagedProcessException, InterruptedException {
		SpringApplication.run(DbGruppenprojektApplication.class, args);

		
		MariaDBConnection conn= new MariaDBConnection();
		conn.startDB();
		conn.connectToDatabase();
		
		
		
		// killt die DB
		Thread.sleep(100000);
		conn.stopDB();

	
	

	}

}
