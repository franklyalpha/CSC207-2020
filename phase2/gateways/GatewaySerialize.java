package gateways;

import globallyAccessible.CannotSerializeException;
import useCases.*;

import java.io.*;

public class GatewaySerialize {

    public void serialize(AbstractSerializableManager object){
        String filename;
        try{
            if (object instanceof EventManager){
                filename = "events.txt";
            } else if (object instanceof MessageRoomManager){
                filename = "chats.txt";
            } else if(object instanceof RequestManager){
                filename = "requests.txt";
            } else if (object instanceof RoomManager){
                filename = "rooms.txt";
            } else {
                throw new CannotSerializeException("Cannot serialize!");
            }
            File f = new File(filename);
            ObjectOutputStream oos = null;
            OutputStream out = new FileOutputStream(f);
            oos = new ObjectOutputStream(out);
            oos.writeObject(object);
            // TODO: apply mysql

            oos.close();
        }catch (IOException | CannotSerializeException io){
            io.printStackTrace();
        }

    }

    public AbstractSerializableManager deserialize(String filename) throws CannotSerializeException{
        AbstractSerializableManager serialize;
        try{
            File f = new File(filename);
            InputStream input = new FileInputStream(f);
            ObjectInputStream oid = new ObjectInputStream(input);
            switch (filename){
                case "events.txt":
                    serialize = (EventManager) oid.readObject();
                    break;
                case "chats.txt":
                    serialize = (MessageRoomManager) oid.readObject();
                    break;
                case "requests.txt":
                    serialize = (RequestManager) oid.readObject();
                    break;
                case "rooms.txt":
                    serialize = (RoomManager) oid.readObject();
                    break;
                default:
                    throw new CannotSerializeException("Cannot deserialize!");
            }
            oid.close();
        } catch(EOFException eof) {
            serialize = newManager(filename);
        } catch(IOException | ClassNotFoundException io){
            String reset = "Cannot find " + filename + ". Will reset all settings.";
            System.out.println(reset);
            serialize = newManager(filename);
        }
        return serialize;
    }

    private AbstractSerializableManager newManager(String filename) throws CannotSerializeException {
        AbstractSerializableManager serialize;
        switch (filename){
            case "events.txt":
                serialize = new EventManager();
                break;
            case "chats.txt":
                serialize = new MessageRoomManager();
                break;
            case "requests.txt":
                serialize = new RequestManager();
                break;
            case "rooms.txt":
                serialize = new RoomManager();
                break;
            default:
                throw new CannotSerializeException("Cannot deserialize!");
        }
        return serialize;
    }

}
