package gateways;

import useCases.MessageRoomManager;

import java.io.*;

public class GatewayChat {

    public void ser(MessageRoomManager tester){
        try{
            File f = new File("chats.txt");
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(tester);
            oos.close();
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public MessageRoomManager deserialize(){
        MessageRoomManager serialize;
        try{
            File f = new File("chats.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            serialize = (MessageRoomManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            serialize = new MessageRoomManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find chat file. Will reset all settings. ");
            serialize = new MessageRoomManager();
        }
        return serialize;

    }
}