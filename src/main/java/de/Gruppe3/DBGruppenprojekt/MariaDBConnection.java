package de.Gruppe3.DBGruppenprojekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import ch.qos.logback.classic.db.DBHelper;
import ch.qos.logback.core.db.dialect.DBUtil;
import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;

public class MariaDBConnection {
	
	public static final String statement= "INSERT INTO vehicles(PS,Color,brand,price) VALUES (?,?,?,?)";
	public MariaDB4jSpringService db= new MariaDB4jSpringService();
	public void connectToDatabase() {
        try {
            String url1 = "jdbc:mariadb://localhost:3306/vehicleDatabase";
            String user = "root";
            String password = "";
            try (Connection con = DriverManager.getConnection(url1,user,password)){
                System.out.println("connected");
                
                for (Vehicles v: Utils.generateTestData()) {
                	PreparedStatement pS= con.prepareStatement(statement);
                	pS.setInt(1, v.getPs());
                	pS.setInt(2,v.getColorCode());
                	pS.setString(3, v.getBrand());
                	pS.setInt(4, v.getPrice());
                	
                    pS.executeUpdate();
                }
                
                
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
	
	
}
