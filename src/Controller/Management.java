package Controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Model.*;

public class Management 
{
    private static final int k_ClassAMaxAirPressure = 31;
    private static final int k_ClassBMaxAirPressure = 33;
    private static final int k_ClassCMaxAirPressure = 26;
    private static final float k_ClassCEngineCapacity = 5;
    private static final float k_FuelClassAEngineCapacity = 10;
    private static final float k_FuelClassBEngineCapacity = 8;
    private static final float k_ClassAMaxBattery = 1.8f;
    private static final float k_ClassBMaxBattery = 1.4f;
    private ShRoom r_ShRoom = new ShRoom();
    private JSONArray jsonsToFile = new JSONArray();
    private JSONArray cJsonsToFile = new JSONArray();
    
    
    public final void addCar(JSONObject i_Car, JSONObject i_Client)
    {
        String plateNumber = (String)i_Car.get("Plate Number");
        String clientPhoneNumber = (String)i_Client.get("Phone");
        String clientName = (String)i_Client.get("Name");
        CarDetails details;
        Car newCar;
   
        details = new CarDetails();
        newCar = this.createNewCar(plateNumber,(String)i_Car.get("Type"),(String)i_Car.get("Engine Type"));
        this.fillDetails(newCar, details,i_Car, i_Client);
        newCar.InsertDetails(details);
        this.r_ShRoom.AddCar(plateNumber, newCar, clientName, clientPhoneNumber);
    }
        
    private final Car createNewCar(String i_PlateNumber, String i_Type ,String i_EngineType) 
    {
        Car newCar;
        newCar = CarFactory.CreateCar(i_PlateNumber, i_Type);
        newCar.CreateEngineAndWheels(i_EngineType);
        return newCar;
    }
    
    private final void fillDetails(Car i_Car, CarDetails i_Details,JSONObject i_JSCar, JSONObject i_JSClient) 
    {
        this.fillBasicInformation(i_Car,i_JSCar);
        i_Details.setCarColor((String)i_JSCar.get("Color"));
        i_Details.setNumberOfDoors(Integer.parseInt((String)i_JSCar.get("NumOfDoors")));
        i_Details.setLicenseType((String)i_JSCar.get("License Type"));
    }
        
    private final void fillBasicInformation(Car i_Car,JSONObject i_JSCar) {
        boolean validEnergy = false;
        boolean validAirPressure = false;
        float currentAirPressure;
        String manufacturerName;
        i_Car.setModel((String)i_JSCar.get("Model"));
        while (!validEnergy) {
            {
            	i_Car.AddEnergy(Float.parseFloat((String)i_JSCar.get("Engine Capacity")));
                validEnergy = true;
            }
            
        }
        
        while (!validAirPressure) 
        {
            	currentAirPressure = Float.parseFloat((String)i_JSCar.get("Wheel Air Pressure"));
            	manufacturerName = (String)i_JSCar.get("Wheel Manufacturer");
                i_Car.InitializeWheelsInfo(manufacturerName, currentAirPressure);
                validAirPressure = true;
       }
        
    }
    
    public final void changeCarService(String i_PlateNumber,String i_ServiceToChange) 
    {    
    	for(Object o : cJsonsToFile) 
    	{
    		if(((JSONObject)o).get("Car#").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Service", i_ServiceToChange);
    		}
    	}
    	UpdateClientsFile();
    }

    
    public final void changeCarAirPressure(String i_PlateNumber) 
    {    
    	Car v = r_ShRoom.GetCar(i_PlateNumber);
    	float MaxAirPressure = v.getWheel().getCurrentAirPressure();
    	String MaxAirPressureString = Float.toString(MaxAirPressure);
    	
    	for(Object o : jsonsToFile) 
    	{
    		if(((JSONObject)o).get("Plate Number").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Wheel Air Pressure", MaxAirPressureString);
    		}
    	}
    	UpdateCarsFile();
    }

	public final void changeCarColor(String i_PlateNumber, String i_newColor) 
	{    
		Car v = r_ShRoom.GetCar(i_PlateNumber);
		for(Object o : jsonsToFile) 
    	{
    		if(((JSONObject)o).get("Plate Number").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Color", i_newColor);
    		}
    	}
		UpdateCarsFile();
	}

	public final void washCar(String i_PlateNumber) 
	{    
		Car v = r_ShRoom.GetCar(i_PlateNumber);
		for(Object o : jsonsToFile) 
    	{
    		if(((JSONObject)o).get("Plate Number").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).put("Washed", "Yes");
    		}
    	}
		UpdateCarsFile();
	}
    
    public final void changeCarEnergy(String i_PlateNumber) 
    {    
    	Car v = r_ShRoom.GetCar(i_PlateNumber);
    	float EnergyCap = v.getCurrentEnergy();
    	String EnergyCapString = Float.toString(EnergyCap);
    	
    	for(Object o : jsonsToFile) 
    	{
    		if(((JSONObject)o).get("Plate Number").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Engine Capacity", EnergyCapString);
    		}
    	}
    	UpdateCarsFile();
    }
    
    public final void inflateWheelsToMax(String i_PlateNumber) 
    {
        this.r_ShRoom.InflateCarWheelsToMax(i_PlateNumber);  
    }
    
    public String getEnergyType(String i_PlateNumber) 
    {
    	Car car = r_ShRoom.GetCar(i_PlateNumber);
    	EnergySource engine = car.getEngine();
    	
    	if(engine instanceof Fuel)
    	{
    		return "Fuel";
    	}
    	else 
    	{
    		return "Electric";
    	}
    }
    
    public final boolean refuelCar(String i_PlateNumber, String i_FuelToAdd) 
    {
        float fuelToAdd = Float.parseFloat(i_FuelToAdd);
        
        return this.r_ShRoom.RefuelCar(i_PlateNumber, fuelToAdd);
        
        
    }
    
    public final boolean chargeCar(String i_PlateNumber, String i_BatteryToAdd) {
        
        float timeToAdd = Float.parseFloat(i_BatteryToAdd);
        return this.r_ShRoom.ChargeCar(i_PlateNumber, timeToAdd);
    }
    
    public final String getClientFullDetails(String i_PlateNumber) 
    {
		try
		{
			FileReader reader1 = new FileReader("Cars.json");
			BufferedReader buffer1 = new BufferedReader(reader1);
			JSONParser parser1 = new JSONParser();
			String currentJSONString1  = "";
			FileReader reader2 = new FileReader("Clients.json");
			BufferedReader buffer2 = new BufferedReader(reader2);
			JSONParser parser2 = new JSONParser();
			String currentJSONString2  = "";

			while((currentJSONString1 = buffer1.readLine()) != null && (currentJSONString2 = buffer2.readLine()) != null ) 
			{
				JSONObject car = (JSONObject)parser1.parse(currentJSONString1);
				JSONObject client = (JSONObject)parser2.parse(currentJSONString2);
				if(car.get("Plate Number").equals(i_PlateNumber) && client.get("Car#").equals(i_PlateNumber))
			    {	
					String result = String.format("Plate number: %s" + System.lineSeparator() + 
        						"Model: %s" + System.lineSeparator() + 
								"Color: %s" + System.lineSeparator() +
								System.lineSeparator() +
        						"Client name: %s" + System.lineSeparator() + 
        						"Client Phone Number: %s" + System.lineSeparator() +
								System.lineSeparator() + 
        						"Service: %s" + System.lineSeparator() +
								"Washed: %s" + System.lineSeparator() +
        						"Wheel Manufacturer: %s" + System.lineSeparator() +
								"Wheel Air Pressure: %s" + System.lineSeparator() +
								"NumOfWheels: %s" + System.lineSeparator() +
								System.lineSeparator() +
								"Engine Type: %s" + System.lineSeparator() +
        						"Engine Capacity: %s" + System.lineSeparator() +
								"Fuel Type: %s", 
        						car.get("Plate Number"), 
        						car.get("Model"),
								car.get("Color"),
        						client.get("Name"),
        						client.get("Phone"),
        						client.get("Service"),
								car.get("Washed"),
        						car.get("Wheel Manufacturer"),
        						car.get("Wheel Air Pressure"),
								car.get("NumOfWheels"),
								car.get("Engine Type"),
        						car.get("Engine Capacity"),
								car.get("Fuel Type"));
					return result;
					//return (String)car.get("New Color");
			    }
			}
			
		}
		catch (FileNotFoundException e1) 
		{
            e1.printStackTrace();
        }
		catch (IOException e1) 
		{
            e1.printStackTrace();
        }
		catch (org.json.simple.parser.ParseException e1)
		{
			e1.printStackTrace();
		}
		return "None";
        //return this.r_ShRoom.GetClientCarDetails(i_PlateNumber);
    }
    
    public final String getCarFullDetails(String i_PlateNumber) 
    {
        return this.r_ShRoom.GetCarDetails(i_PlateNumber);
    }
    
    private void UpdateClientsFile() 
    {
    	try (FileWriter file = new FileWriter("Clients.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : cJsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    private void UpdateCarsFile() 
    {
    	try (FileWriter file = new FileWriter("Cars.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : jsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public void intializeJSONArray()
    {
    	try 
    	{	
	    	FileReader reader = new FileReader("Cars.json");
			BufferedReader buffer = new BufferedReader(reader);
			JSONParser parser = new JSONParser();
			String currentJSONString  = "";
	
			while((currentJSONString = buffer.readLine()) != null ) 
			{
				JSONObject car = (JSONObject)parser.parse(currentJSONString);
				jsonsToFile.add(car);
			}
			
			reader = new FileReader("Clients.json");
			buffer = new BufferedReader(reader);
			while((currentJSONString = buffer.readLine()) != null ) 
			{
				JSONObject client = (JSONObject)parser.parse(currentJSONString);
				cJsonsToFile.add(client);
			}
    	}
    	catch (FileNotFoundException e1) 
		{
            e1.printStackTrace();
        }
		catch (IOException e1) 
		{
            e1.printStackTrace();
        }
		catch (org.json.simple.parser.ParseException e1)
		{
			e1.printStackTrace();
		}
    }
    
    public void AddCarToFile(JSONObject i_Car)
    {
    	jsonsToFile.add(i_Car);

    	try (FileWriter file = new FileWriter("Cars.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : jsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public void AddClientToFile(JSONObject i_Client)
    {
    	cJsonsToFile.add(i_Client);

    	try (FileWriter file = new FileWriter("Clients.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : cJsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public String CheckAirPressureValidation(String i_CarType, String i_AirPressureToAdd)
    {
    	String isValid="";
    	int airPressure = Integer.parseInt(i_AirPressureToAdd);
    	if((i_CarType.equals("ClassA"))&&(airPressure > k_ClassAMaxAirPressure))
    	{
    		isValid=Integer.toString(k_ClassAMaxAirPressure);
    	}
    	if((i_CarType.equals("ClassB"))&&(airPressure > k_ClassBMaxAirPressure))
    	{
    		isValid=Integer.toString(k_ClassBMaxAirPressure);
    	}
    	if((i_CarType.equals("ClassC"))&&(airPressure > k_ClassCMaxAirPressure))
    	{
    		isValid=Integer.toString(k_ClassCMaxAirPressure);
    	}
    	
    	return isValid;
    }
    
    public String CheckEnergyValidation(String i_CarType, String i_EngineType, String i_EnergyToAdd)
    {
    	float energyToAdd = Float.parseFloat(i_EnergyToAdd);
    	String isValid= "";
    	
    	if(i_CarType.equals("ClassC") && energyToAdd > k_ClassCEngineCapacity)
    	{
    		isValid = Float.toString(k_ClassCEngineCapacity);
    	}
    	
    	if(i_CarType.equals("ClassA"))
    	{
    		if(i_EngineType.equals("Fuel") && energyToAdd > k_FuelClassAEngineCapacity)
    		{
    			isValid = Float.toString(k_FuelClassAEngineCapacity);
    		}
    		
    		if(i_EngineType.equals("Electric") && energyToAdd > k_ClassAMaxBattery)
    		{
    			isValid = Float.toString(k_ClassAMaxBattery);
    		}
    		
    	}
    	
    	if(i_CarType.equals("ClassB"))
    	{
    		if(i_EngineType.equals("Fuel") && energyToAdd > k_FuelClassBEngineCapacity)
    		{
    			isValid = Float.toString(k_FuelClassBEngineCapacity);
    		}
    		
    		if(i_EngineType.equals("Electric") && energyToAdd > k_ClassBMaxBattery)
    		{
    			isValid = Float.toString( k_ClassBMaxBattery);
    		}
    		
    	}
    	
    	return isValid;
    }
    
    public JTable GenerateStringReport(String i_Service)
    {
    	ArrayList<String> relevantPN = new ArrayList<String>();    	
    	String[] columnNames = {"index",
    			"Plate number",
    			"Engine type",
    			"Car type",
    			"Color",
    			"Model",
    			"Engine Capacity",
    			"Wheel Manufacturer",
    			"Wheel Air Pressure",
    			"License Type",
    			"Num Of Wheels",
    			"Num Of Doors",
    			"Fuel Type"};
    	
    	int i = 0;
    	for(Object o : cJsonsToFile) 
    	{
    		if(((JSONObject)o).get("Service").equals(i_Service))
    		{
    			i++;
    			relevantPN.add((String)((JSONObject)o).get("Car#"));
    		}
    	}
    	
    	String[][] rows = new String[i+1][13];
		
		int j = 0;
		
		for(String s: columnNames)
		{
			rows[0][j] = s;
			j++;
		}
		
		int k=0;
		j=1;
    	for(Object o : jsonsToFile) 
    	{
    		if(!relevantPN.isEmpty())
    		{
    			if(relevantPN.get(k).equals((String)((JSONObject)o).get("Plate Number"))) 
	    		{
    				rows[j][0] = Integer.toString(k+1);
    				rows[j][1] = (String)((JSONObject)o).get("Plate Number");
    				rows[j][2] = (String)((JSONObject)o).get("Engine Type");
    				rows[j][3] =(String)((JSONObject)o).get("Type");
    				rows[j][4] =(String)((JSONObject)o).get("Color");
    				rows[j][5] =(String)((JSONObject)o).get("Model");
    				rows[j][6] =(String)((JSONObject)o).get("Engine Capacity");
    				rows[j][7] =(String)((JSONObject)o).get("Wheel Manufacturer");
    				rows[j][8] =(String)((JSONObject)o).get("Wheel Air Pressure");
    				rows[j][9] =(String)((JSONObject)o).get("License Type");
    				rows[j][10] =(String)((JSONObject)o).get("NumOfWheels");
    				rows[j][11] =(String)((JSONObject)o).get("NumOfDoors");
    				rows[j][12] =(String)((JSONObject)o).get("Fuel Type");
    				
    				j++;	
    				k++;
	    			if(j >= (relevantPN.size()+1)) 
	    			{
	    				break;
	    			}
	    		}
    		}
    	}
    	
    	JTable table = new JTable(rows,columnNames);
    	return table;
    }
    
    public void initializeShRoomDictionaries() 
	{
		try 
    	{	
	    	FileReader vReader = new FileReader("Cars.json");
			BufferedReader vBuffer = new BufferedReader(vReader);
			FileReader cReader = new FileReader("Clients.json");
			BufferedReader cBuffer = new BufferedReader(cReader);
			JSONParser parser = new JSONParser();
			String currentCarJSONString  = "";
			String currentClientJSONString  = "";
	
			while((currentCarJSONString = vBuffer.readLine()) != null && ((currentClientJSONString = cBuffer.readLine()) != null )) 
			{
				JSONObject car = (JSONObject)parser.parse(currentCarJSONString);
				JSONObject client = (JSONObject)parser.parse(currentClientJSONString);
				addCar(car, client);	
			}
    	}
    	catch (FileNotFoundException e1) 
		{
            e1.printStackTrace();
        }
		catch (IOException e1) 
		{
            e1.printStackTrace();
        }
		catch (org.json.simple.parser.ParseException e1)
		{
			e1.printStackTrace();
		}
	}
}
