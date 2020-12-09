//package gateways;
//
//import useCases.MessageRoomManager;
//import useCases.EventManager;
//import useCases.RoomManager;
//import useCases.RequestManager;
//
//import java.io.*;
//
//
//public class GatewaySerialize<T> {
//
//    public void serialize(T object, String filename){
//        try{
//            File f = new File(filename + ".txt");
//            ObjectOutputStream oos = null;
//            OutputStream out = new FileOutputStream(f);
//            oos = new ObjectOutputStream(out);
//            oos.writeObject(object);
//            oos.close();
//        }catch (IOException io){
//            io.printStackTrace();
//        }
//
//    }
//
//    public T deserialize(String filename){
//        try{
//            File f = new File(filename + ".txt");
//            InputStream input = new FileInputStream(f);
//            ObjectInputStream oid = new ObjectInputStream(input);
//            switch (filename) {
//                case "chats": {
//                    MessageRoomManager serialize = (MessageRoomManager) oid.readObject();
//                    break;
//                }
//                case "rooms": {
//                    RoomManager serialize = (RoomManager) oid.readObject();
//                    break;
//                }
//                case "requests": {
//                    RequestManager serialize = (RequestManager) oid.readObject();
//                    break;
//                }
//                case "events": {
//                    EventManager serialize = (EventManager) oid.readObject();
//                    break;
//                }
//                default:
//                    return null;
//            }
//            oid.close();
//
//        } catch(EOFException eof) {
//            serialize = new MessageRoomManager();
//        } catch(IOException | ClassNotFoundException io){
//            String warning = "Cannot find " + filename + "Will reset all settings.";
//            System.out.println(warning);
//            serialize = new MessageRoomManager();
//        }
//        return serialize;
//
//    }
//}