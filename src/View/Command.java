package View;

public abstract class Command {
    private String id, description;

    public Command(String id, String description){
        this.id = id;
        this.description = description;
    }
    public String getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public abstract void execute();


}
