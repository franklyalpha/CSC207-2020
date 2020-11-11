import useCases.RoomManager;

import java.io.*;

public class GatewayRoom {

    public void ser(RoomManager tester){
        try{
            File f = new File("rooms.txt");
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(tester);
            oos.close();
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public RoomManager deser(){
        RoomManager seri;
        try{
            File f = new File("rooms.txt");
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            seri = (RoomManager) oid.readObject();
            oid.close();

        } catch(EOFException eof) {
            seri = new RoomManager();
        } catch(IOException | ClassNotFoundException io){
            System.out.println("Cannot find original file. Will reset all settings. ");
            seri = new RoomManager();
        }
        return seri;

    }
}