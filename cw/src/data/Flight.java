package data;

import exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Flight {

    private String code;
    private Aeroplane aeroplane;
    private Airport departure;
    private Airport destination;
    private LocalDateTime dateTime;
    private FlightPlan plan;
    private Airline airline;

    public Flight(String identifier, 
    			  Aeroplane plane,
    			  Airport departureAirport,
    			  Airport destinationAirport,
    			  LocalDateTime departureDateTime,
    			  FlightPlan flightPlan) {
    	setCode(identifier);
    	setAeroplane(plane);
    	setDeparture(departureAirport);
    	setDestination(destinationAirport);
    	setDateTime(departureDateTime);
    	setPlan(flightPlan);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Aeroplane getAeroplane() {
        return aeroplane;
    }

    public void setAeroplane(Aeroplane aeroplane) {
        this.aeroplane = aeroplane;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public FlightPlan getPlan() {
        return plan;
    }

    public void setPlan(FlightPlan plan) {
        this.plan = plan;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Double calculateDistance() throws NotFoundException {
        double i = 0;
        ControlTower controlTower = this.departure.getTower();
        if (controlTower == null) {
            throw new NotFoundException("Control tower for this flight not found.");
        }
        GPSCoordinate gpsCoordinate = controlTower.getGpsCoordinate();
        LinkedList<ControlTower> controlTowers = this.getPlan().getTowers();
        if (controlTowers.isEmpty()) {
            throw new NotFoundException("Control towers to visit is empty.");
        }
        double inRadian = gpsCoordinate.getLatInRad();
        Double inRadian1 = gpsCoordinate.getLngInRad();

        for (ControlTower tower : controlTowers) {
            GPSCoordinate gpsCoordinate1 = tower.getGpsCoordinate();
            if (gpsCoordinate1 == null) {
                throw new NotFoundException("GPS coordinates not found.");
            }
            double radian = gpsCoordinate1.getLatInRad();
            Double inRadian2 = gpsCoordinate1.getLngInRad();
            double deltaLng = inRadian2 - inRadian1;
            double deltaLat = radian - inRadian;
            double trig = Math.pow(Math.sin(deltaLat / 2), 2.0) + Math.cos(inRadian)
                    * Math.cos(radian) + Math.pow(Math.sin(deltaLng / 2), 2.0);
            
            
            double sqrt = Math.sqrt(trig);
            
            if(sqrt >= 1) {
            	sqrt -=1;
            }

            i += 2 * 6371.00 * Math.asin(sqrt);
            
            inRadian = radian;
            inRadian1 = inRadian2;
        }
        return i;
    }

    public Double calculateTime() throws NotFoundException {
        double v = 0.0;
        Aeroplane aero = this.getAeroplane();
        Double speed = aero.getSpeed();
        FlightPlan plan = this.getPlan();
        ControlTower tower = this.departure.getTower();
        if (tower == null){
            throw new NotFoundException("Departure airport control tower not found.");
        }
        LinkedList<ControlTower> towers = plan.getTowers();
        if (towers.isEmpty()) {
            throw new NotFoundException("Control towers not found.");
        }
        for (int i=0; i<towers.size();i++) {
            Double v1 = tower.distance(towers.get(i));
            if(v1.isNaN()){
            }else{
                v += v1 / speed;
            }
            tower = towers.get(i);
        }
        return v;
    }

    public Double consumption() throws NotFoundException {
        Aeroplane aeroplane = this.getAeroplane();
        Double aeroplaneConsumption = aeroplane.getConsumption();
        Double distance = this.calculateDistance();
        return distance * aeroplaneConsumption / 100;
    }

    public Double emission() throws NotFoundException{
        //kg per litre
        Double EMISSION_FACTOR = 4.09;
        return this.consumption() * EMISSION_FACTOR;
    }
}
