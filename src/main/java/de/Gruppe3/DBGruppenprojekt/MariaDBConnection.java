package de.Gruppe3.DBGruppenprojekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import ch.qos.logback.classic.db.DBHelper;
import ch.qos.logback.core.db.dialect.DBUtil;
import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;

public class MariaDBConnection {
	
	public static final String statement= "INSERT INTO vehicles(PS,Color,brand,price) VALUES (?,?,?,?)";
	public MariaDB4jSpringService db= new MariaDB4jSpringService();
	public Vehicle[] vehicles;
	
	public void connectToDatabase() {
        try {
            String url = "jdbc:mariadb://localhost:3306/vehicleDatabase";
            String user = "root";
            String password = "";
            try (Connection conn = DriverManager.getConnection(url,user,password)){
                System.out.println("connected");
                
                System.out.println("*********************************************");
                System.out.println("Datenbank wird erstellt, Create Operation");
                System.out.println("*********************************************");
                double timeToCreate=createDatabase(conn);
                System.out.println("Ergebnis:"+ timeToCreate+ " Nanosekunden");
                System.out.println("");
                System.out.println("*********************************************");
                System.out.println("Datenbank wird gelesen");
                System.out.println("*********************************************");
                System.out.println("");
                System.out.println("*********************************************");
                System.out.println("Datenbank wird geupdatet");
                System.out.println("*********************************************");
                double timeToUpdate=updateDatabase(conn);
                System.out.println("Ergebnis:"+ timeToUpdate+ " Nanosekunden");
                System.out.println("");
                System.out.println("*********************************************");
                System.out.println("Datenbank wird gelöscht");
                System.out.println("*********************************************");
                double timeToDelete=deleteDatabase(conn);
                System.out.println("Ergebnis:"+ timeToDelete+ " Nanosekunden");
                

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
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
	
	public double createDatabase(Connection conn) throws SQLException {
		
		double timeDifference=0.0;
		vehicles = new Vehicle[2000];
		for (int i = 0; i < vehicles.length; i++) {
			vehicles[i] = new Vehicle();
		}
        for (Vehicle v: vehicles) {
        	PreparedStatement pS= conn.prepareStatement(statement);
        	pS.setInt(1, v.getPs());
        	pS.setInt(2,v.getColorCode());
        	pS.setString(3, v.getBrand());
        	pS.setInt(4, v.getPrice());
        	
        	double beforeExecution= System.nanoTime();
            pS.executeUpdate();
            double afterExecution= System.nanoTime();
            timeDifference= timeDifference+ (afterExecution-beforeExecution);
        }
		return (Double) timeDifference;
	}
	
	public double readDatabase(Connection conn) {
		
		return (Double) null;
	}
	
	public double updateDatabase(Connection conn) throws SQLException {
		
		double timeDifference=0.0;
		
		String statementUpdate= "UPDATE vehicles SET PS = ?, Color = ?, brand = ?, price=?";
		
	
		
        for (Vehicle v: vehicles) {
        	PreparedStatement pS= conn.prepareStatement(statementUpdate);
        	pS.setInt(1, v.getPs());
        	pS.setInt(2,v.getColorCode());
        	pS.setString(3, v.getBrand());
        	pS.setInt(4, v.getPrice());
        	
        	double beforeExecution= System.nanoTime();
            pS.executeUpdate();
            double afterExecution= System.nanoTime();
            timeDifference= timeDifference+ (afterExecution-beforeExecution);
        }
		return timeDifference/vehicles.length;
	}
	
	public double deleteDatabase(Connection conn) throws SQLException {
		
		String statementDelete= "DROP TABLE IF EXISTS vehicles";
		
		PreparedStatement dropTable = conn.prepareStatement(
				String.format("DROP TABLE IF EXISTS vehicles"));
	
    	double beforeExecution= System.nanoTime();
    	try {
			dropTable.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
        double afterExecution= System.nanoTime();
		
		return (Double) afterExecution/beforeExecution;
	}
	
	
	public double messureTime(PreparedStatement pS) throws SQLException {
		double timeDifference=0.0;
    	double beforeExecution= System.nanoTime();
        pS.executeUpdate();
        double afterExecution= System.nanoTime();
        timeDifference= timeDifference+ (afterExecution-beforeExecution);
		return timeDifference;
		
	}
}
