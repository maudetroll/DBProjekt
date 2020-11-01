package de.Gruppe3.DBGruppenprojekt;

import java.io.IOException;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.vorburger.exec.ManagedProcessException;

@SpringBootApplication
public class DbGruppenprojektApplication {

	public static void main(String[] args) throws ManagedProcessException, InterruptedException {
		SpringApplication.run(DbGruppenprojektApplication.class, args);

		
	//	MariaDBConnection mariaDBConn= new MariaDBConnection();
	//	mariaDBConn.startDB();
	//	mariaDBConn.connectToDatabase();
		
		
		
		// killt die DB
//		Thread.sleep(100000);
	//	mariaDBConn.stopDB();

		MongoDBConnection md = new MongoDBConnection();
		try {
			md.connect();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
