package gateways;

import useCases.UserManager;

import java.io.*;

public class GatewayUser {

    public void ser(UserManager tester){
        try{
            File f = new File("users.txt");
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(tester);
            // TODO: apply mysql

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
