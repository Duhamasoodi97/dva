package file_reader;

import java.util.HashMap;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.LinkedList;

import data.Airport;
import data.ControlTower;
import data.Aeroplane;
import data.Airline;
import data.Flight;
import data.FlightPlan;

public class DataReader {
	
	//return list containing data of airports
	public static HashMap<String, Airport> getAirports() {
		
		HashMap<String, Airport> airportHashMap = new HashMap<>();
		
		try {
			FileReader fileReader = new FileReader("Airports.txt");
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			
			while(line != null) {
				String[] split = line.split("; ");
				airportHashMap.put(split[0],
							new Airport(split[1],
										split[0],
										new ControlTower(split[3], split[2])));
				line = bufferedReader.readLine();
			}
			
			fileReader.close();
			bufferedReader.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return airportHashMap;
	}
	
	//return list containing data of planes
	public static HashMap<String, Aeroplane> getAeroplanes() {
		
		HashMap<String, Aeroplane> planes = new HashMap<String, Aeroplane>();
		
		try {
			FileReader reader = new FileReader("Planes.txt");
			
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = bufferedReader.readLine();
			
			while(line != null) {
				String[] split = line.split("; ");

				planes.put(split[0],
							  new Aeroplane(split[0],
											Double.parseDouble(split[2]),
											split[1],
											Double.parseDouble(split[3])));
				line = bufferedReader.readLine();
			}
			
			reader.close();
			bufferedReader.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return planes;
	}

	//return list containing data of airlines
	public static HashMap<String, Airline> getAirlines() {
		
		HashMap<String, Airline> airlineHashMap = new HashMap<>();
		
		try {
			FileReader fileReader = new FileReader("Airlines.txt");
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			
			while(line != null) {
				String[] airlineSplit = line.split("; ");

				airlineHashMap.put(airlineSplit[0],
							 new Airline(airlineSplit[1],
									 	 airlineSplit[0]));
				
				line = bufferedReader.readLine();
			}
			
			fileReader.close();
			bufferedReader.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return airlineHashMap;
	}
	
	//return list containing data of flights
	public static HashMap<String, Flight> getFlights() {
		
		HashMap<String, Flight> flightHashMap = new HashMap<String, Flight>();
		
		HashMap<String, Aeroplane> planes = getAeroplanes();
		HashMap<String, Airport> ports = getAirports();
		
		try {
			FileReader fileReader = new FileReader("Flights.txt");
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM:dd:yyyy HH:mm");

			while(line != null) {
				String[] split = line.split("; ");
				
				LocalDateTime dateTime = LocalDateTime.parse(split[4] + " " + split[5], formatter);
				
				LinkedList<ControlTower> towers = new LinkedList<ControlTower>();
				for(int i=6; i<split.length; i++) {
					towers.add(ports.get(split[i]).getTower());
				}
				


				flightHashMap.put(split[0],
							new Flight(split[0],
									   planes.get(split[1]),
									   ports.get(split[2]),
									   ports.get(split[3]),
									   dateTime,
									   new FlightPlan(towers)
								   ));
								
				line = bufferedReader.readLine();
			}
			
			fileReader.close();
			bufferedReader.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return flightHashMap;
	}
	
	//this will store the data of flight in a text file
	public static void storeFlights(HashMap<String, Flight> flightHM) {
		try {
			FileWriter fileWriter = new FileWriter("Flights.txt");
			
			DateTimeFormatter f = DateTimeFormatter.ofPattern("MM:dd:yyyy; HH:mm");
			
			HashMap<String, Airport> ports = getAirports();


			flightHM.forEach((s1, fl) -> {
				try{
					String s = s1 + "; "
							 			  + fl.getAeroplane().getModel() + "; "
							 			  + fl.getDeparture().getAirportCode() + "; "
							 			  + fl.getDestination().getAirportCode() + "; "
							 			  + fl.getDateTime().format(f) + "; ";
					
					
					for(ControlTower tower: fl.getPlan().getTowers()) {
						for(Airport port: ports.values()) {
							if(tower.compareTo(port.getTower())) {
								s = s + port.getAirportCode() + "; ";
							}
						}
					}
					
					s = s.trim();
					if(s.endsWith(";")) {
						s = s.substring(0, s.length()-1);
					}
					
					fileWriter.write(s
							 + System.lineSeparator());
				} 
				catch(Exception e) {
					
				}
			});
			
			fileWriter.close();
		}
		catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

}
