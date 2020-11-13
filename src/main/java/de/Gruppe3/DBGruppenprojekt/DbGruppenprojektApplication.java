package de.Gruppe3.DBGruppenprojekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.vorburger.exec.ManagedProcessException;

@SpringBootApplication
public class DbGruppenprojektApplication implements CommandLineRunner {

	@Autowired
	private MongoDBRepository vehiclesRepository;
	private Vehicle testVehicle;

	public static void main(String[] args) throws ManagedProcessException, InterruptedException {
		SpringApplication.run(DbGruppenprojektApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Vehicles erstellen
		Vehicle[] vehicles = new Vehicle[2000];
		for (int i = 0; i < vehicles.length; i++) {
			vehicles[i] = new Vehicle();
		}
		//deleteAll
		delete();
		
		// CRUD Implementierung
		// Create
		create();
				
		// Read
		read();
		
		// Update
		update();

		// Delete
		deleteSelectedEntry();

		
		 MariaDBConnection mariaDBConn= new MariaDBConnection();
		 mariaDBConn.startDB();
		 mariaDBConn.connectToDatabase();

		// killt die DB
			Thread.sleep(1000000);
		 mariaDBConn.stopDB();

			
	}
	
	
	
	
	
	
	public void delete() {
		vehiclesRepository.deleteAll();
	}
	
	public void create() {
		double beforeExecution = System.nanoTime();
		vehiclesRepository.save(new Vehicle("1"));
		double afterExecution = System.nanoTime();
		System.out.println("*********************************************");
		System.out.println("CREATE");
		System.out.println("*********************************************");
		System.out.println("Ergebnis:" + (afterExecution - beforeExecution));
	}
	
	public void read() {
		double beforeExecution = System.nanoTime();
		testVehicle = vehiclesRepository.findById("1");
		double afterExecution = System.nanoTime();
		System.out.println("*********************************************");
		System.out.println("READ");
		System.out.println("*********************************************");
		System.out.println("Ergebnis:" + (afterExecution - beforeExecution));
	}
	
	public void update() {
		testVehicle.ps = 1000;
		vehiclesRepository.save(testVehicle);
	}
	
	public void deleteSelectedEntry() {
		vehiclesRepository.delete(testVehicle);
	}
	
	
	

	

}
