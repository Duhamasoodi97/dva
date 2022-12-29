package data;

import exception.NotFoundException;

public final class ControlTower {
    private GPSCoordinate gpsCoordinate;

    public ControlTower(GPSCoordinate coord) {
        gpsCoordinate.setLongitude(coord.getLongitude());
        gpsCoordinate.setLatitude(coord.getLatitude());
    }

    public ControlTower(String lng, String lat) {
        gpsCoordinate = new GPSCoordinate(lng, lat);
    }

    public GPSCoordinate getGpsCoordinate() {
        return gpsCoordinate;
    }

    public void setGpsCoordinate(GPSCoordinate coord) {
        gpsCoordinate.setLongitude(coord.getLongitude());
        gpsCoordinate.setLatitude(coord.getLatitude());
    }

    public void setCoordinates(String lng, String lat) {
        gpsCoordinate.setLongitude(lng);
        gpsCoordinate.setLatitude(lat);
    }

    public double distance(ControlTower tower) throws NotFoundException {
        GPSCoordinate coordinate = this.getGpsCoordinate();
        double radian = coordinate.getLatInRad();
        double radian1 = coordinate.getLngInRad();
        GPSCoordinate gpsCoord1 = tower.getGpsCoordinate();
        if (gpsCoord1 == null){
            throw new NotFoundException("GPS coordinates not found.");
        }
        Double latInRad1 = gpsCoord1.getLatInRad();
        Double lngInRad = gpsCoord1.getLngInRad();
        double deltaLongitude = lngInRad - radian1;
        double deltaLatitude = latInRad1 - radian;
        double trig = Math.pow(Math.sin(deltaLatitude / 2), 2.0) + Math.cos(radian)
                * Math.cos(latInRad1) + Math.pow(Math.sin(deltaLongitude / 2), 2.0);

        return  2 * 6371.00 * Math.asin(Math.sqrt(trig));
    }
    
    public boolean compareTo(ControlTower tower) {
    	
    	if(tower.getGpsCoordinate()
    						.getLatitude()
    						.equals(this.getGpsCoordinate().getLatitude())
    	   &&
    	   tower.getGpsCoordinate()
    	   					.getLongitude()
    	   					.equals(this.getGpsCoordinate().getLongitude())) {
    			
    		return true;
    	}
    	return false;
    }
}
