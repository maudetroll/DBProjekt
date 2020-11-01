
package de.Gruppe3.DBGruppenprojekt;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class MongoDBConnection {

	private static final String CONNECTION_STRING = "mongodb://%s:%d";

	private MongodExecutable mongodExecutable;
	private MongoTemplate mongoTemplate;

	public void connect() throws UnknownHostException, IOException {
		String ip = "localhost";
		int port = 27017;

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6())).build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
		
		mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
	
		System.out.println("*********************************************");
		System.out.println("CREATE");
		System.out.println("*********************************************");
		double timeToCreate = createEnry();
		System.out.println("Ergebnis:" + timeToCreate + " Nanosekunden");
		System.out.println("");
		System.out.println("*********************************************");
		System.out.println("READ");
		System.out.println("*********************************************");
		double timeToRead = readEntry();
		System.out.println("Ergebnis:" + timeToRead + " Nanosekunden");
		System.out.println("*********************************************");
		System.out.println("UPDATE");
		System.out.println("*********************************************");
		double timeToUpdate = updateEntry();
		System.out.println("Ergebnis:" + timeToUpdate + " Nanosekunden");
		System.out.println("");
		System.out.println("*********************************************");
		System.out.println("DELETE");
		System.out.println("*********************************************");
		double timeToDelete = deleteEntry();
		System.out.println("Ergebnis:" + timeToDelete + " Nanosekunden");
		
	}

	public double createEnry() {
		ArrayList<Vehicles> vehicles = Utils.generateTestData();
		ArrayList<Document> docList = new ArrayList<Document>();
		for (Vehicles vehicle : Utils.generateTestData()) {
			Document doc = new Document();
			doc.append("ps", vehicle.getPs());
			doc.append("colorCode", vehicle.getColorCode());
			doc.append("brand", vehicle.getBrand());
			doc.append("price", vehicle.getPrice());
			docList.add(doc);
		}
		docList.get(55).append("model", "AMG");
		double beforeExecution = System.nanoTime();
		
		mongoTemplate.getCollection("collection").insertMany(docList);
				
		double afterExecution = System.nanoTime();
		
		return (Double) (afterExecution - beforeExecution);
	}

	public double readEntry() {
		MongoCollection<Document> c = mongoTemplate.getCollection("collection");
		double beforeExecution = System.nanoTime();
		DBObject query = new BasicDBObject("_id", 108);
		FindIterable<Document> doc;
		doc = c.find(Filters.eq("model", "AMG"));	
		Document doci = doc.first();
		System.out.println(doci.get("ps"));
		double afterExecution = System.nanoTime();
		
		return (Double) (afterExecution - beforeExecution);
	}

	public double updateEntry() {
	//muss noch gemacht werden
		
		return 0;
	}

	public double deleteEntry() {

		double beforeExecution = System.nanoTime();
		mongoTemplate.dropCollection("collection");
		double afterExecution = System.nanoTime();

		return (Double) afterExecution / beforeExecution;
	}

	public double messureTime(PreparedStatement pS) throws SQLException {
		double timeDifference = 0.0;
		double beforeExecution = System.nanoTime();
		pS.executeUpdate();
		double afterExecution = System.nanoTime();
		timeDifference = timeDifference + (afterExecution - beforeExecution);
		return timeDifference;
	}

}
