package de.Gruppe3.DBGruppenprojekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.vorburger.exec.ManagedProcessException;

@SpringBootApplication
public class DbGruppenprojektApplication implements CommandLineRunner {

	@Autowired
	private MongoDBRepository vehiclesRepository;

	public static void main(String[] args) throws ManagedProcessException, InterruptedException {
		SpringApplication.run(DbGruppenprojektApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ArrayList<Vehicle[]> testData= createTestData();
		Connection conn = DriverManager.getConnection(url,user,password);
		MariaDBConnection mariaDBConn= new MariaDBConnection();
		mariaDBConn.startDB();
		
		for(Vehicle[] v: testData) {
			mariaDBConn.connectToDatabase(v);
		}
		// killt die DB
		Thread.sleep(1000000);
		mariaDBConn.stopDB();
		
		
		
		vehiclesRepository.deleteAll();
		// CRUD Implementierung
		// Create
		double beforeExecution = System.nanoTime();
		vehiclesRepository.save(new Vehicle("1"));
		double afterExecution = System.nanoTime();
		System.out.println("*********************************************");
		System.out.println("CREATE");
		System.out.println("*********************************************");
		System.out.println("Ergebnis:" + (afterExecution - beforeExecution));
				
		// Read
		beforeExecution = System.nanoTime();
		Vehicle testVehicle = vehiclesRepository.findById("1");
		afterExecution = System.nanoTime();
		System.out.println("*********************************************");
		System.out.println("READ");
		System.out.println("*********************************************");
		System.out.println("Ergebnis:" + (afterExecution - beforeExecution));
		
		// Update
		testVehicle.ps = 1000;
		vehiclesRepository.save(testVehicle);

		// Delete
		vehiclesRepository.delete(testVehicle);

		


			
	}
	
	public ArrayList<Vehicle[]> createTestData(){
		// Vehicles erstellen
		ArrayList <Vehicle[]> vehicleList= new ArrayList<>();
		Vehicle[] vehicle = new Vehicle[1];
		for (int i = 0; i < vehicle.length; i++) {
			vehicle[i] = new Vehicle();
		}
		vehicleList.add(vehicle);
		
		Vehicle[] vehiclesLow = new Vehicle[1000];
		for (int i = 0; i < vehiclesLow.length; i++) {
			vehiclesLow[i] = new Vehicle();
		}
		vehicleList.add(vehiclesLow);
		
		Vehicle[] vehiclesMid = new Vehicle[10000];
		for (int i = 0; i < vehiclesMid.length; i++) {
			vehiclesMid[i] = new Vehicle();
		}
		vehicleList.add(vehiclesMid);
		
		Vehicle[] vehiclesHigh = new Vehicle[100000];
		for (int i = 0; i < vehiclesHigh.length; i++) {
			vehiclesHigh[i] = new Vehicle();
		}
		vehicleList.add(vehiclesHigh);
		
		return vehicleList;
	}

	

}
