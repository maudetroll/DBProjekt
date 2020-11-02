package de.Gruppe3.DBGruppenprojekt;

import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.client.MongoClient;
import de.Gruppe3.DBGruppenprojekt.mongodb.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.vorburger.exec.ManagedProcessException;

@SpringBootApplication
public class DbGruppenprojektApplication implements CommandLineRunner {

	@Autowired
	private VehiclesRepository vehiclesRepository;

	public static void main(String[] args) throws ManagedProcessException, InterruptedException {
		SpringApplication.run(DbGruppenprojektApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		vehiclesRepository.deleteAll();
		//CRUD Implementierung
		//Create
		vehiclesRepository.save(new Vehicles("1"));

		//Read
		Vehicles testVehicle = vehiclesRepository.findById("1");

		//Update
		testVehicle.ps = 1000;
		vehiclesRepository.save(testVehicle);

		//Delete
		vehiclesRepository.delete(testVehicle);


		//Performance Messung
		//Alle Vehicles droppen
		vehiclesRepository.deleteAll();

		//Vehicles erstellen
		Vehicles[] vehicles = new Vehicles[2000];
		for (int i = 0; i < vehicles.length; i++) {
			vehicles[i] = new Vehicles();
		}

		long startTime = System.nanoTime();

		for (Vehicles vehicle : vehicles) {
			vehiclesRepository.save(vehicle);
		}

		long endTime = System.nanoTime() - startTime;

		System.out.println("MongoDB hat "+ vehicles.length + " Einträge in " + endTime + " Nanosekunden angelegt");

		startTime = System.nanoTime();
		vehiclesRepository.findAll();
		endTime = System.nanoTime() - startTime;

		System.out.println("MongoDB hat "+ vehicles.length + " Einträge in " + endTime + " Nanosekunden ausgelesen");
	}


	//	MariaDBConnection mariaDBConn= new MariaDBConnection();
	//	mariaDBConn.startDB();
	//	mariaDBConn.connectToDatabase();
		
		
		
		// killt die DB
//		Thread.sleep(100000);
	//	mariaDBConn.stopDB();

//		MongoDBConnection md = new MongoDBConnection();
//		try {
//			md.connect();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	



}
