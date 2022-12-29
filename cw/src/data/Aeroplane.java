package data;
public class Aeroplane {

    private String model;
    private double speed;
    private String Manufacturer;
    private double consumption;

    
    public Aeroplane(String model, double speed, String Manufacturer, double fuelConsumption) {
    	setModel(model);
    	setSpeed(speed);
    	setManufacturer(Manufacturer);
    	setConsumption(fuelConsumption);
    }
    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
    
    @Override
    public String toString() {
    	return this.getModel();
    }

}
