package gateways;

import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import useCases.UserManager;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.*;

public class GatewayUser {

    public void ser(UserManager tester, MongoDatabase database){
        try{
            File f = new File("users.txt");
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(tester);

            // SEND File f to DATABASE
            Document users = new Document("_id", new ObjectId());
            users.append("users", f);
            MongoCollection<Document> usersCollection = database.getCollection("users");
//            usersCollection.insertOne(users);
            oos.close();
        }catch (IOException io){
            io.printStackTrace();

        }

    }

    public UserManager deserialize(){
        UserManager serialize;
        try{
            File f = new File("users.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            serialize = (UserManager)oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            serialize = new UserManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find user file. Will reset all settings. ");
            serialize = new UserManager();
        }
        return serialize;

    }
}
