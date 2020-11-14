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

    public ChatroomManager deser(){
        ChatroomManager seri;
        try{
            File f = new File("chats.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            seri = (ChatroomManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            seri = new ChatroomManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find chat file. Will reset all settings. ");
            seri = new ChatroomManager();
        }
        return seri;

    }
}