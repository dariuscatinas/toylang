package View;

import Controller.Controller;

public class View {
    private Controller c;

    public View(Controller c){
        this.c = c;
    }
    public void executeAllStep(){
        c.executeAllStep();
    }
}
