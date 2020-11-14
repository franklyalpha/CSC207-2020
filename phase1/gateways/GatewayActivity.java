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

    public ActivityManager deser(){
        ActivityManager seri;
        try{
            File f = new File("activities.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            seri = (ActivityManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            seri = new ActivityManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find activity file. Will reset all settings. ");
            seri = new ActivityManager();
        }
        return seri;

    }
}