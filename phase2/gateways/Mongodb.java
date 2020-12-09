package gateways;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mongodb {

    public MongoDatabase database;

    public Mongodb(){

        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            database = mongoClient.getDatabase("csc207");
            MongoCollection<Document> usersCollection = database.getCollection("users");



            try {
                database.createCollection("data");
            } catch (MongoCommandException e) {
                database.getCollection("data").drop();
            }
        }

    }
}
