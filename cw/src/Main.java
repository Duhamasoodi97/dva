import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.util.*;
import data.*;
import file_reader.DataReader;

public class Main {
	
	public static void runApp() {
		//we are using jpanel in jframe
		JFrame jFrame = new JFrame("Flight Scheduling");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setting the size properties of jframe
		jFrame.setSize(1400,900);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		jFrame.getContentPane().setLayout(new GridBagLayout());


		//we are adding the panels
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());

		HashMap<String, Airline> airlineHashMap = DataReader.getAirlines();
		HashMap<String, Flight> flightHashMap = DataReader.getFlights();
		HashMap<String, Aeroplane> aeroplaneHashMap = DataReader.getAeroplanes();
		HashMap<String, Airport> airportHashMap = DataReader.getAirports();

		
		//listing out flights in jpanel
		JPanel jPanel1 = new JPanel();
		JLabel jLabel = new JLabel("Flights");
		
				String[] columns = {"Flight",
		                "Plane",
		                "Departure",
		                "Destination",
		                "Date",
		                "Time"};
				
				int size = flightHashMap.keySet().size();

				
				Object[][] data = new Object[size][6];
				
		int i=0;		
		for(String s: flightHashMap.keySet()) {
			data[i][0] = flightHashMap.get(s).getCode();
			data[i][1] = flightHashMap.get(s).getAeroplane().getModel();
			data[i][2] = flightHashMap.get(s).getDeparture().getAirportCode();
			data[i][3] = flightHashMap.get(s).getDestination().getAirportCode();
			data[i][4] = flightHashMap.get(s).getDateTime().toString();
			data[i][5] = flightHashMap.get(s).getDateTime().toString();
			i++;
		}
		
		JTable jTable = new JTable(new DefaultTableModel(data, columns));
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.setDefaultEditor(Object.class, null);
		JScrollPane jScrollPane = new JScrollPane(jTable);
				
		jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));
		jPanel1.add(jLabel);
		jPanel1.add(jScrollPane);
		
		
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 0;
		jPanel.add(jPanel1, constraints);
		
		
		//listing out data of flight plan on the click on particular flight
		JPanel jPanel2 = new JPanel();
		JLabel jLabel1 = new JLabel("Flight Plan");
		
				String[] column2 = {""};
				
				Object[][] data2 = new Object[20][1];
		

		JTable jTable1 = new JTable(data2, column2);
		jTable1.getTableHeader().setReorderingAllowed(false);
		jTable1.setEnabled(false);
		JScrollPane jScrollPane1 = new JScrollPane(jTable1);
		jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.PAGE_AXIS));
		jPanel2.add(jLabel1);
		jPanel2.add(jScrollPane1);
		
		constraints.gridwidth=1;
		constraints.gridx = 3;
		constraints.gridy = 0;
		jPanel.add(jPanel2, constraints);
		

		
		
		//listing out data of particular flight in jpanel
		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout(new GridBagLayout());
		
		
		
		JLabel label = new JLabel("Distance");
		JLabel label1 = new JLabel("km");
		JLabel label2 = new JLabel("Time");
		JLabel label3 = new JLabel("Fuel Consumption");
		JLabel label4 = new JLabel("litre");
		JLabel label5 = new JLabel("CO2");
		JLabel label6 = new JLabel("kg");

		JTextArea jTextArea3 = new JTextArea(1,5);
		jTextArea3.setEditable(false);
		JTextArea jTextArea2 = new JTextArea(1,5);
		jTextArea2.setEditable(false);
		JTextArea jTextArea1 = new JTextArea(1,5);
		jTextArea1.setEditable(false);
		JTextArea jTextArea = new JTextArea(1,5);
		jTextArea.setEditable(false);
		
		
		
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		jPanel3.add(label, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		jPanel3.add(jTextArea3, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		jPanel3.add(label1, constraints);
		constraints.gridx=0;
		constraints.gridy=2;
		jPanel3.add(label2,constraints);
		constraints.gridx=0;
		constraints.gridy=3;
		jPanel3.add(jTextArea2, constraints);
		constraints.gridx=0;
		constraints.gridy=4;
		jPanel3.add(label3, constraints);
		constraints.gridx=0;
		constraints.gridy=5;
		jPanel3.add(jTextArea1, constraints);
		constraints.gridx=1;
		constraints.gridy=5;
		jPanel3.add(label4, constraints);
		constraints.gridx=0;
		constraints.gridy=6;
		jPanel3.add(label5, constraints);
		constraints.gridx=0;
		constraints.gridy=7;
		jPanel3.add(jTextArea, constraints);
		constraints.gridx=1;
		constraints.gridy=7;
		jPanel3.add(label6, constraints);
		
		
		constraints.gridx = 4;
		constraints.gridy=0;
		jPanel.add(jPanel3, constraints);
		
		
		// listing data of particular flight in jtable1
		jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if(!event.getValueIsAdjusting()) {
					String code = (String) jTable.getValueAt(jTable.getSelectedRow(), 0);
					
					
					
					//showing data of flight plan in another jtable for particular flight 
					LinkedList<ControlTower> towers = flightHashMap.get(code).getPlan().getTowers();
					int brwsTbl = 0;
					
					for(ControlTower tower: towers) {
						for(Airport port: airportHashMap.values()) {
							if(tower.compareTo(port.getTower())) {
								data2[brwsTbl][0] = port.getAirportCode();
								brwsTbl++;
							}
						}
					}
					for(int brwdt= brwsTbl; brwdt<20;brwdt++) {
						Arrays.fill(data2[brwdt], null);
					}
					jTable1.repaint();
					
					
					//changing data on selecting other flights
					try {
						String distance = flightHashMap.get(code).calculateDistance().toString();
						jTextArea3.setText(distance);
                        String time = flightHashMap.get(code).calculateTime().toString();
						jTextArea2.setText(time);
						String fuel = flightHashMap.get(code).consumption().toString();
						jTextArea1.setText(fuel);
						String co2 = flightHashMap.get(code).emission().toString();
						jTextArea.setText(co2);
					}
					catch (Exception e) {		
					}
					
				}
			}
		});
				
		JPanel jPanel4 = new JPanel();
		jPanel4.setLayout(new GridBagLayout());
		
		
		//adding space to append flight information
		JPanel jPanel5 = new JPanel();
		
		JLabel label7 = new JLabel("Add Flight");
		String[] addColumns = {"Airline",
                "Number",
                "Plane",
                "Departure",
                "Destination",
                "Date",
                "Time"};
		
		Object[][] flightData = new Object[1][7];

		JTable jTable2 = new JTable(flightData, addColumns);
		jTable2.getTableHeader().setReorderingAllowed(false);
		
			TableColumn tableColumn = jTable2.getColumnModel().getColumn(0);
			JComboBox<Airline> jComboBox = new JComboBox<Airline>();
			
			for(Airline line: airlineHashMap.values()) {
				jComboBox.addItem(line);
			}
			tableColumn.setCellEditor(new DefaultCellEditor(jComboBox));
			
			
			TableColumn column = jTable2.getColumnModel().getColumn(2);
			JComboBox<Aeroplane> planes = new JComboBox<Aeroplane>();
			
			for(Aeroplane plane: aeroplaneHashMap.values()) {
				planes.addItem(plane);
			}
			column.setCellEditor(new DefaultCellEditor(planes));
			
			TableColumn tableColumn1 = jTable2.getColumnModel().getColumn(3);
			JComboBox<Airport> airports = new JComboBox<Airport>();
			
			for(Airport airport: airportHashMap.values()) {
				airports.addItem(airport);
			}
			tableColumn1.setCellEditor(new DefaultCellEditor(airports));
			
			TableColumn tableColumn2 = jTable2.getColumnModel().getColumn(4);
			tableColumn2.setCellEditor(new DefaultCellEditor(airports));
		
		JScrollPane scrollPane = new JScrollPane(jTable2);
		scrollPane.setPreferredSize(new Dimension (600, 40));
				
		jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.PAGE_AXIS));
		jPanel5.add(label7);
		jPanel5.add(scrollPane);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		jPanel4.add(jPanel5, constraints);
		
		
		//adding jpanel where one can enter the flight plan
		JPanel jPanel6 = new JPanel();
		
		JLabel label8 = new JLabel("Flight Plan");
		
		String[] columnNames = new String[20];
		for(int j=0; j<20; j++) {
			columnNames[j] = "";
		}
		
		Object[][] addPlanData = new Object[1][20];

		JTable jTable3 = new JTable(addPlanData, columnNames);
		jTable3.getTableHeader().setReorderingAllowed(false);
		
		for(int j=0; j<jTable3.getColumnCount(); j++) {
			TableColumn tableColumn3 = jTable3.getColumnModel().getColumn(j);
			
			tableColumn3.setCellEditor(new DefaultCellEditor(airports));
		}
		
		JScrollPane scrollPane1 = new JScrollPane(jTable3);
		scrollPane1.setPreferredSize(new Dimension (1000, 40));
				
		jPanel6.setLayout(new BoxLayout(jPanel6, BoxLayout.PAGE_AXIS));
		jPanel6.add(label8);
		jPanel6.add(scrollPane1);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		jPanel4.add(jPanel6, constraints);
		
		
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<ControlTower> towers = new LinkedList<ControlTower>();
				for(int i=0; i<20; i++) {
					if(jTable3.getValueAt(0,i) != null)
					towers.add(((Airport) jTable3.getValueAt(0, i)).getTower());
				}
				
				((Airline) jTable2.getValueAt(0, 0)).getAirlineCode();
				
				DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM:dd:yyyy HH:mm");
				LocalDateTime localDateTime = LocalDateTime.parse(jTable2.getValueAt(0, 5) + " " + jTable2.getValueAt(0, 6), pattern);
				
				Flight flight = new Flight(((Airline) jTable2.getValueAt(0, 0)).getAirlineCode() + jTable2.getValueAt(0,1),
												   (Aeroplane) jTable2.getValueAt(0, 2),
												   (Airport) jTable2.getValueAt(0, 3),
												   (Airport) jTable2.getValueAt(0, 4),
												   localDateTime,
												   new FlightPlan(towers));
				
				flightHashMap.put(flight.getCode(), flight);
				
				Object [] d = new Object[6];
				d[0] = flight.getCode();
				d[1] = flightHashMap.get(flight.getCode()).getAeroplane().getModel();
				d[2] = flightHashMap.get(flight.getCode()).getDeparture().getAirportCode();
				d[3] = flightHashMap.get(flight.getCode()).getDestination().getAirportCode();
				d[4] = flightHashMap.get(flight.getCode()).getDateTime().toString();
				d[5] = flightHashMap.get(flight.getCode()).getDateTime().toString();
				((DefaultTableModel) jTable.getModel()).addRow(d);
				jTable.repaint();
				
				DataReader.storeFlights(flightHashMap);
				
				
			}
		});
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		jPanel4.add(addBtn, constraints);
		
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		jFrame.getContentPane().add(jPanel, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		jFrame.getContentPane().add(jPanel4, constraints);
		
		jFrame.setVisible(true);
	}

	public static void main(String[] args) {
		runApp();
	}
}
