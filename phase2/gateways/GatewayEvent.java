package gateways;

import useCases.EventManager;

import java.io.*;

public class GatewayEvent {

    public void ser(EventManager tester){
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

    public EventManager deserialize(){
        EventManager serialize;
        try{
            File f = new File("activities.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            serialize = (EventManager) oid.readObject();
            oid.close();
        } catch(EOFException eof) {
            serialize = new EventManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find activity file. Will reset all settings. ");
            serialize = new EventManager();
        }
        return serialize;
    }
}