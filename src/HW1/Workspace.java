package HW1;//Notes
//An id
// a type
// whether it's avaliable
//


public class Workspace {
    private int id;
    private String type;
    private boolean isAvailable;

    public Workspace(int id, String type, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getType(){
        return type;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    // Setter
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Workspace ID: " + id + ", Type: " + type + ", Available: " + (isAvailable ? "Yes" : "No");
    }

}