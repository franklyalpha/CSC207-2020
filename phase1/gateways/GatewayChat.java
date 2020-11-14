package gateways;

import useCases.ChatroomManager;

import java.io.*;

public class GatewayChat {

    public void ser(ChatroomManager tester){
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

    public ChatroomManager deserialize(){
        ChatroomManager serialize;
        try{
            File f = new File("chats.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            serialize = (ChatroomManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            serialize = new ChatroomManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find original file. Will reset all settings. ");
            serialize = new ChatroomManager();
        }
        return serialize;

    }
}