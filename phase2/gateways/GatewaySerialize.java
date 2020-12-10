package gateways;

import globallyAccessible.CannotSerializeException;
import useCases.*;

import java.sql.Connection;

public class GatewaySerialize {

    public void serialize(AbstractSerializableManager object) {
        try{
            MySQL.writeJavaObject(MySQL.getConnection(), object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AbstractSerializableManager deserialize(String name) throws CannotSerializeException{
        AbstractSerializableManager serialize;
        try{
            Connection conn = MySQL.getConnection();
            switch (name){
                case "events":
                    serialize = (EventManager) MySQL.readJavaObject(conn, "useCases.EventManager");
                    break;
                case "chats":
                    serialize = (MessageRoomManager) MySQL.readJavaObject(conn, "useCases.MessageRoomManager");
                    break;
                case "requests":
                    serialize = (RequestManager) MySQL.readJavaObject(conn, "useCases.RequestManager");
                    break;
                case "rooms":
                    serialize = (RoomManager) MySQL.readJavaObject(conn, "useCases.RoomManager");
                    break;
                default:
                    throw new CannotSerializeException("Cannot deserialize!");
            }
        } catch(Exception e){
            String reset = "Cannot find " + name + ". Will reset all settings.";
            System.out.println(reset);
            serialize = newManager(name);
        }
        return serialize;
    }

    private AbstractSerializableManager newManager(String name) throws CannotSerializeException {
        AbstractSerializableManager serialize;
        switch (name){
            case "events":
                serialize = new EventManager();
                break;
            case "chats":
                serialize = new MessageRoomManager();
                break;
            case "requests":
                serialize = new RequestManager();
                break;
            case "rooms":
                serialize = new RoomManager();
                break;
            default:
                throw new CannotSerializeException("Cannot deserialize!");
        }
        return serialize;
    }

    public void serializeUser(UserManager users){
        try{
            MySQL.writeJavaObject(MySQL.getConnection(), users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserManager deserializeUser(){
        UserManager serialize;
        try{
            serialize = (UserManager) MySQL.readJavaObject(MySQL.getConnection(), "useCases.UserManager");
        } catch(Exception e){
            System.out.println("Cannot find user data. Generating new data...");
            serialize = new UserManager();
            System.out.println("New data generated!");
        }
        return serialize;
    }
}
