package gateways;

import useCases.RequestManager;

import java.io.*;

public class GatewayRequest {

    public void ser(RequestManager tester){
        try{
            File f = new File("requests.txt");
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(tester);
            oos.close();
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public RequestManager deserialize(){
        RequestManager serialize;
        try{
            File f = new File("requests.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            serialize = (RequestManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            serialize = new RequestManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find request file. Will reset all settings. ");
            serialize = new RequestManager();
        }
        return serialize;

    }
}