
	package de.Gruppe3.DBGruppenprojekt;

	import static org.junit.Assert.assertThat;

	import java.util.Collection;

	import org.bson.Document;
	import org.junit.Assert;
	import org.junit.jupiter.api.AfterEach;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.DisplayName;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.SpringBootVersion;
	import org.springframework.data.mongodb.core.MongoTemplate;


	import com.mongodb.BasicDBObjectBuilder;
	import com.mongodb.DBObject;
	import com.mongodb.client.MongoClient;
	import com.mongodb.client.MongoClients;
	import com.mongodb.client.MongoCollection;

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
		 
		    @AfterEach
		    void clean() {
		        mongodExecutable.stop();
		    }
		 
		    @BeforeEach
		    void setup() throws Exception {
		        String ip = "localhost";
		        int port = 27017;
		 
		        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
		            .net(new Net(ip, port, Network.localhostIsIPv6()))
		            .build();
		 
		        MongodStarter starter = MongodStarter.getDefaultInstance();
		        mongodExecutable = starter.prepare(mongodConfig);
		        mongodExecutable.start();
		        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
		    }
		 
		    @DisplayName("given object to save"
		        + " when save object using MongoDB template"
		        + " then object is saved")
		    @Test
		    void test() throws Exception {
		        // given
		        DBObject objectToSave = BasicDBObjectBuilder.start()
		            .add("key", "value")
		            .add("2", "Backfisch")
		            .add("3", "Backfisch2")
		            .add("4", "Backfisch3")
		            .get();
		       
		        DBObject objectToSave2 = BasicDBObjectBuilder.start()
			            .add("Fish", "Backfisch")
			            .get();
		        
		        // when
		        mongoTemplate.save(objectToSave, "collection");
		        mongoTemplate.save(objectToSave2, "collection");
		        MongoCollection<Document> c = mongoTemplate.getCollection("collection");
		        System.out.println("Alle Docis" + c.countDocuments());
		       
		    }
		
		
	}

