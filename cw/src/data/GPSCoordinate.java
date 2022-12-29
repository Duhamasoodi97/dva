package data;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPSCoordinate {
    private String latitude;
    private String longitude;
    private final Pattern DMSLATPATTERN =
            Pattern.compile("(-?)([0-9]{1,3})�([0-5]?[0-9])'([0-5]?[0-9])\\.([0-9]{0,4})\\\"([NS])");
    private final Pattern DMSLNGPATTERN =
            Pattern.compile("(-?)([0-9]{1,3})�([0-5]?[0-9])'([0-5]?[0-9])\\.([0-9]{0,4})\\\"([EW])");

    public GPSCoordinate(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public double lngIndegree(){
        Matcher m = DMSLNGPATTERN.matcher(this.longitude.trim());
        if (m.matches()){
            double toDouble = convertToDouble(m);
            if ((Math.abs(toDouble) > 180)) {
                throw new NumberFormatException("Invalid longitude");
            }
            return toDouble;
        }else {
            throw new NumberFormatException("Malformed DMS coordiniates");
        }
    }

    public double latinDegree(){
        Matcher m = DMSLATPATTERN.matcher(this.latitude.trim());
        if (m.matches()){
        	
            double latitude = convertToDouble(m);
            if ((Math.abs(latitude) > 180)) {
                throw new NumberFormatException("Invalid latitude");
            }
            return latitude;
        }else {
        	//Maybe show these malformed coordinates?
            throw new NumberFormatException("Malformed DMS coordiniates");
        }
    }

    public double getLatInRad(){
        return this.latinDegree() * (Math.PI / 180);
    }

    public double getLngInRad(){
        return this.lngIndegree() * (Math.PI / 180);
    }

    private double convertToDouble(Matcher m){
        int i1 = "".equals(m.group(1)) ? 1 : -1;
        double v = Double.parseDouble(m.group(2));
        double v1 = Double.parseDouble(m.group(3));
        double v2 = Double.parseDouble(m.group(4));
        int i = "NE".contains(m.group(5)) ? 1 : -1;

        return i1 * i * (v + v1 / 60 + v2 / 3600 );
    }
}
