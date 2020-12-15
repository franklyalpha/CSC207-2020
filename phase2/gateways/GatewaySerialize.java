package gateways;

import globallyAccessible.CannotSerializeException;
import useCases.*;

import java.sql.Connection;

/**
 * The entry to serialize and deserialize java object.
 */
public class GatewaySerialize {

    /**
     * Serialize object with the type AbstractSerializableManager.
     * @param object to serialize
     */
    public void serialize(AbstractSerializableManager object) {
        try{
            SQLServer.writeJavaObject(SQLServer.getConnection(), object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize the object to the actual type with the super class AbstractSerializableManager.
     * @return the actual object with the super class AbstractSerializableManager.
     */
    public AbstractSerializableManager deserialize(String name) throws CannotSerializeException{
        AbstractSerializableManager serialize;
        try{
            Connection conn = SQLServer.getConnection();
            switch (name){
                case "events":
                    serialize = (EventManager) SQLServer.readJavaObject(conn, "useCases.EventManager");
                    break;
                case "chats":
                    serialize = (MessageRoomManager) SQLServer.readJavaObject(conn, "useCases.MessageRoomManager");
                    break;
                case "requests":
                    serialize = (RequestManager) SQLServer.readJavaObject(conn, "useCases.RequestManager");
                    break;
                case "rooms":
                    serialize = (EventRoomManager) SQLServer.readJavaObject(conn, "useCases.EventRoomManager");
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
                serialize = new EventRoomManager();
                break;
            default:
                throw new CannotSerializeException("Cannot deserialize!");
        }
        return serialize;
    }

    /**
     * Serialize object with the type UserManager.
     * @param users to serialize
     */
    public void serializeUser(UserManager users){
        try{
            SQLServer.writeJavaObject(SQLServer.getConnection(), users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize the object to the type UserManager.
     * @return the userManager.
     */
    public UserManager deserializeUser(){
        UserManager serialize;
        try{
            serialize = (UserManager) SQLServer.readJavaObject(SQLServer.getConnection(), "useCases.UserManager");
        } catch(Exception e){
            System.out.println("Cannot find user data. Generating new data...");
            serialize = new UserManager();
            System.out.println("New data generated!");
        }
        return serialize;
    }
}
