package data;
public class Airport {

    private String airportName;
    private String airportCode;
    private ControlTower tower;
    
    public Airport(String name, String code, ControlTower controlTower) {
    	setAirportName(name);
    	setAirportCode(code);
    	setTower(controlTower);
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public ControlTower getTower() {
        return tower;
    }

    public void setTower(ControlTower tower) {
        this.tower = tower;
    }
    
    @Override
    public String toString() {
    	return this.getAirportCode();
    }

}
