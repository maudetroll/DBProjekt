package de.Gruppe3.DBGruppenprojekt;

import java.io.IOException;
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
	private Vehicle testVehicle;
	public TextFileWriter results;

	public static void main(String[] args) throws ManagedProcessException, InterruptedException {
		SpringApplication.run(DbGruppenprojektApplication.class, args);
	}
	ArrayList<Vehicle[]> testData ;
	@Override
	public void run(String... args) throws Exception {
		testData = createTestData();
		results= new TextFileWriter();
		deleteAll();

		// CRUD Implementierung
		crude();

		// Test with 1000
		create(testData.get(1));
		read(testData.get(1));
		deleteAll();

		// Test with 10000
		create(testData.get(2));
		read(testData.get(2));
		deleteAll();

		// Test with 10000
		create(testData.get(3));
		read(testData.get(3));
		deleteAll();

		// MariaDB
		results.writeHeaderMariaDB();
		MariaDBConnection mariaDBConn = new MariaDBConnection(results);
		mariaDBConn.startDB();
		Connection conn = mariaDBConn.connectToDatabase();

		for (Vehicle[] v : testData) {
			mariaDBConn.connectToDatabase(v, conn);
		}

		results.closeWriter();

		mariaDBConn.stopDB();
		

	}

	public void crude() throws IOException {
		create(testData.get(0));
		read(testData.get(0));
		update();
		delete();
		deleteAll();
	}

	public void deleteAll() {
		vehiclesRepository.deleteAll();
	}

	public void create(Vehicle[] vehicles) throws IOException {
		
		double beforeExecution = System.nanoTime();
		vehiclesRepository.save(new Vehicle("1"));
		double afterExecution = System.nanoTime();
		results.appendData("CREATE", vehicles.length, (Double) afterExecution - beforeExecution);
	}

	public void read(Vehicle[] vehicles) throws IOException {
		double beforeExecution = System.nanoTime();
		testVehicle = vehiclesRepository.findById("1");
		double afterExecution = System.nanoTime();
		results.appendData("READ", vehicles.length, (Double) afterExecution - beforeExecution);
	}

	public void update() {
		testVehicle.ps = 1000;
		vehiclesRepository.save(testVehicle);
	}

	public void delete() {
		vehiclesRepository.delete(testVehicle);
	}

	public ArrayList<Vehicle[]> createTestData() {
		// Vehicles erstellen
		ArrayList<Vehicle[]> vehicleList = new ArrayList<>();
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
