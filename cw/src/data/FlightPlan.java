package data;
import java.util.LinkedList;

public class FlightPlan {

    private LinkedList<ControlTower> towers;
    
    public FlightPlan(LinkedList<ControlTower> controlTowers) {
    	setTowers(controlTowers);
    }

    public LinkedList<ControlTower> getTowers() {
        return towers;
    }

    public void setTowers(LinkedList<ControlTower> towers) {
        this.towers = towers;
    }

}