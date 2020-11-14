package gateways;

import useCases.ActivityManager;

import java.io.*;

public class GatewayActivity {

    public void ser(ActivityManager tester){
        try{
            File f = new File("activities.txt");
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(tester);
            oos.close();
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public ActivityManager deserialize(){
        ActivityManager serialize;
        try{
            File f = new File("activities.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            serialize = (ActivityManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            serialize = new ActivityManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find original file. Will reset all settings. ");
            serialize = new ActivityManager();
        }
        return serialize;

    }
}