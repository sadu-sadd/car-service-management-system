package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShRoom 
{
    private final Map<String, Car> r_ShRoomCars;
    private final Map<String, Client> r_CarClients;

    public ShRoom()
    {
        r_ShRoomCars = new HashMap<String, Car>();
        r_CarClients = new HashMap<String, Client>();
    }
    
	public static <T> T as(Object o, Class<T> tClass) 
	{
	    //return tClass.isInstance(o) ? (T) o : null;
        if (tClass.isInstance(o)) {
			return tClass.cast(o);
		} else {
			return null;
		}
	}

	public Car GetCar(String i_PlateNumber)
	{
		Car result = r_ShRoomCars.get(i_PlateNumber);
		return result;
	}
	
    public void AddCar(String i_PlateNumber, Car i_Car, String i_ClientName, String i_ClientPhoneNumber)
    {
        Client carClient = new Client(i_ClientName, i_ClientPhoneNumber);
        if (r_ShRoomCars.get(i_PlateNumber) == null)
        {
            r_ShRoomCars.put(i_PlateNumber, i_Car);
            r_CarClients.put(i_PlateNumber, carClient);
        }
        else
        {
        	return;
        }
    }

    public String GetClientCarDetails(String i_PlateNumber)
    {
        Car car;
        Client client;
        String result, engineDetails, wheelsDetails, modelDetails;

        if (!IsCarExists(i_PlateNumber))
        {
           return "No Car with this plate number";
        }

        car = r_ShRoomCars.get(i_PlateNumber);
        client = r_CarClients.get(i_PlateNumber);
        engineDetails = car.GetEngineDetails();
        wheelsDetails = car.GetWheelsDetails();
        modelDetails = car.getModel();
        result = String.format("Plate number: %s" + System.lineSeparator() + System.lineSeparator() + "Model: %s." + System.lineSeparator() + System.lineSeparator() + 
        		"Client name: %s " + System.lineSeparator() + System.lineSeparator() + "Client Phone Number: %s" + System.lineSeparator() + System.lineSeparator() + "Service: %s." + System.lineSeparator() + System.lineSeparator() +
        		"Wheel details:" + System.lineSeparator() + "%s" + System.lineSeparator() + System.lineSeparator() + "Engine details: %s", i_PlateNumber,
        		modelDetails,client.getName(),client.getPhoneNumber(),client.getCarService(),wheelsDetails,engineDetails);

        return result;
    }
    
    public String GetCarDetails(String i_PlateNumber)
    {
        Car car;
        Client client;
        String result, engineDetails, wheelsDetails, modelDetails;

        if (!IsCarExists(i_PlateNumber))
        {
           return null;
        }

        car = r_ShRoomCars.get(i_PlateNumber);
        client = r_CarClients.get(i_PlateNumber);
        engineDetails = car.GetEngineDetails();
        wheelsDetails = car.GetWheelsDetails();
        modelDetails = car.getModel();
        
        result = String.format("Plate number: %s" + System.lineSeparator() + 
        						"Model: %s." + System.lineSeparator() + 
        						"Client name: %s " + System.lineSeparator() + 
        						"Client Phone Number: %s" + System.lineSeparator() + 
        						"%s" + System.lineSeparator() + 
        						"Service: %s." + System.lineSeparator() +
        						"Wheel details:" + System.lineSeparator() + "%s" + System.lineSeparator() + 
        						"Engine details: %s", 
        						i_PlateNumber, 
        						modelDetails,
        						client.getName(),
        						client.getPhoneNumber(),
        						car.detailsToString(),
        						client.getCarService(),
        						wheelsDetails,
        						engineDetails);

        return result;
    }

    public List<String> GetCarsDetailsByService(String i_Service, boolean i_AllCars)
    {
        List<String> details = new ArrayList<String>();

        for(Map.Entry<String, Client> entry : r_CarClients.entrySet())
        {
            if (i_AllCars || i_Service == ((Client) entry).getCarService())
            {
                details.add(entry.getKey());
            }
        }

        return details;
    }

    public void ChangeService(String i_PlateNumber, String i_NewService)
    {
        boolean check = r_ShRoomCars.containsKey(i_PlateNumber) == true ? true : false;
        if (check)
        {
            r_CarClients.get(i_PlateNumber).setCarService(i_NewService);
        }
       
    }

    public void InflateCarWheelsToMax(String i_PlateNumber)
    {
        r_ShRoomCars.get(i_PlateNumber).InflateToMax();
    }

    public boolean RefuelCar(String i_PlateNumber, float i_FuelToAdd)
    {
    	boolean result = false;
    	if(r_ShRoomCars.get(i_PlateNumber).AddEnergy(i_FuelToAdd))
    	{
    		r_ShRoomCars.get(i_PlateNumber).UpdateEnergy();
    		result = true;
    	}
    	
    	return result;
    }

    public boolean ChargeCar(String i_PlateNumber, float i_BatteryTimeToAdd)
    {
        i_BatteryTimeToAdd = ((int)((i_BatteryTimeToAdd / 60) * 10)) / 10.0f; 
        
        boolean result = false;
        
        if( r_ShRoomCars.get(i_PlateNumber).AddEnergy(i_BatteryTimeToAdd))
        {
        	r_ShRoomCars.get(i_PlateNumber).UpdateEnergy();
        	result = true;
        }
       
        return result;
    }

    public Boolean IsCarExists(String i_PlateNumber)
    {
        return r_ShRoomCars.containsKey(i_PlateNumber);
    }

	public Map<String, Car> GetCarMap() {
		return r_ShRoomCars;
	}

}

