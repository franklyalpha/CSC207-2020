public class Room {

    private int id;
    private int capacity;
    private Map schedule;
    public Chatroom Messageboard;

    public Room(int id, int capacity, Chatroom chatroom){
        this.id = id;
        this.capacity = capacity;
        this.schedule = new HashMap<Date, String>();
        this.Messageboard = chatroom;
    }

    public int getId() { return id; }

    public int getCapacity() { return capacity; }

    public void setId(int id) { this.id = id; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void addTalk(){}
}
