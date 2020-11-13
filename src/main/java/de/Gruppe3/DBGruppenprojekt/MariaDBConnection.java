package de.Gruppe3.DBGruppenprojekt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ch.qos.logback.classic.db.DBHelper;
import ch.qos.logback.core.db.dialect.DBUtil;
import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;

public class MariaDBConnection {
	
	public static final String statement= "INSERT INTO vehicles(PS,Color,brand,price,extraequipment) VALUES (?,?,?,?,?)";
	public MariaDB4jSpringService db= new MariaDB4jSpringService();
	private String url = "jdbc:mariadb://localhost:3306/vehicleDatabase";
	private String user = "root";
	private String password = "";
	private TextFileWriter results;
	
	public MariaDBConnection(TextFileWriter results) {
		this.results= results;
	}
	
	public void connectToDatabase(Vehicle[] vehicle,Connection conn) throws SQLException, IOException {

		insertData(conn, vehicle);
		readDatabase(conn, vehicle);
		if (vehicle.length < 1) {
			updateDatabase(conn, vehicle);
		}
		deleteDatabase(conn);

}
	public void startDB() throws ManagedProcessException {
		
		
		if(db!=null) {
			db.stop();
		}
		db.setDefaultPort(3306);
		db.start();
		db.getDB().createDB("TestDB");
		db.getDB().source("schema.sql");
	}
	
	public void stopDB() {
		db.stop();
	}
	
	public double insertData(Connection conn,Vehicle [] vehicles) throws SQLException, IOException {
		
		double timeDifference=0.0;

		vehicles[0].setExtraEquipment("SL AMG 63");

        for (Vehicle v: vehicles) {
        	PreparedStatement pS= conn.prepareStatement(statement);
        	conn.setAutoCommit(false);
        	pS.setInt(1, v.getPs());
        	pS.setInt(2,v.getColorCode());
        	pS.setString(3, v.getBrand());
        	pS.setInt(4, v.getPrice());
        	pS.setString(5, v.getExtraEquipment());
        
            pS.executeUpdate();
        }
		double beforeExecution= System.nanoTime();
        conn.commit();
        double afterExecution= System.nanoTime();
        timeDifference= timeDifference+ (afterExecution-beforeExecution);
        
        results.appendData("CREATE", vehicles.length, (Double) timeDifference);
		return (Double) timeDifference;
	}
	
	public double readDatabase(Connection conn,Vehicle [] vehicles) throws SQLException, IOException {
		Statement statement= conn.createStatement();

		double beforeExecution= System.nanoTime();
		ResultSet rs=statement.executeQuery( "select * from " + "vehicles"+ " WHERE ExtraEquipment LIKE "+ "'SL AMG 63'");
		double afterExecution= System.nanoTime();
		
		while (rs.next()) {
			if (!rs.getString(6).equals("SL AMG 63")) {
				System.err.println("NICHT GEFUNDEN");
			}
			System.out.println("Eintrag gefunden: "+ rs.getString(6));
		}
		double timeDifference=(Double) afterExecution-beforeExecution;
		results.appendData("READ", vehicles.length, (Double) timeDifference);
		return timeDifference;
	}
	
	public void updateDatabase(Connection conn,Vehicle [] vehicles) throws SQLException {
		
		String statementUpdate= "UPDATE vehicles SET PS = ?, Color = ?, brand = ?, price=?, extraequipment=?";
				
        for (Vehicle v: vehicles) {
        	PreparedStatement pS= conn.prepareStatement(statementUpdate);
        	pS.setInt(1, v.getPs());
        	pS.setInt(2,v.getColorCode());
        	pS.setString(3, v.getBrand());
        	pS.setInt(4, v.getPrice());
        	pS.setString(5, v.getExtraEquipment());
        	conn.setAutoCommit(false);
        	pS.executeUpdate();
        }
        conn.commit();

	}
	
	public void deleteDatabase(Connection conn) throws SQLException {
				
		PreparedStatement dropTable = conn.prepareStatement(
				String.format("DELETE FROM vehicles"));
	    	try {
			dropTable.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public double messureTime(PreparedStatement pS) throws SQLException {
		double timeDifference=0.0;
    	double beforeExecution= System.nanoTime();
        pS.executeUpdate();
        double afterExecution= System.nanoTime();
        timeDifference= timeDifference+ (afterExecution-beforeExecution);
		return timeDifference;
		
	}
	
	public Connection connectToDatabase() throws SQLException {
		return DriverManager.getConnection(url,user,password);
	}


}
