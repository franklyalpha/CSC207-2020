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
            oos.close();
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public UserManager deser(){
        UserManager seri;
        try{
            File f = new File("users.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            seri = (UserManager)oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            seri = new UserManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find original file. Will reset all settings. ");
            seri = new UserManager();
        }
        return seri;

    }
}