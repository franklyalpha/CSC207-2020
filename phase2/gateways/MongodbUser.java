package gateways;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongodbUser {

    public void startMongodbUser() {

         Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
         mongoLogger.setLevel(Level.SEVERE);

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            MongoDatabase database = mongoClient.getDatabase("csc207");

            try {
                database.createCollection("csc207");
            } catch (MongoCommandException e) {

                database.getCollection("csc207").drop();
            }
        }
    }
}
