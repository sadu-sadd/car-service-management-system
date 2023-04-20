package Model;

import java.util.ArrayList;

public abstract class Car
{
	private final String r_PlateNumber;
    private final ArrayList<Wheel> r_Wheels;
    private final int r_WheelArrSize;
    private String m_ModelName;
    private float m_CurrentEnergy;
    private String m_CarColor;
    private int m_NumberOfDoors;
    private String m_LicenseType;
    protected EnergySource m_Engine;
    
    public Car(String i_PlateNumber, int i_NumberOfWheels)
    {
        r_PlateNumber = i_PlateNumber;
        r_WheelArrSize = i_NumberOfWheels;
        r_Wheels = new ArrayList<Wheel>(i_NumberOfWheels);
    }

    public Wheel getWheel()
    {
    	return r_Wheels.get(0);
    }
    
    public String getPlateNumber() 
    {
		return r_PlateNumber;
	}

	public EnergySource getEngine()
	{
		return m_Engine;
	}

	public String getModel() 
    {
    	return m_ModelName;
    }
	
	public void setModel(String i_Model)
	{
		this.m_ModelName=i_Model;
	}
    
    public float getCurrentEnergy() 
    {
		return m_CurrentEnergy;
	}

	public void setCurrentEnergy(float m_CurrentEnergy) 
	{
		this.m_CurrentEnergy = m_CurrentEnergy;
	}

	public boolean AddEnergy(float i_EnergyToAdd)
    {
        return m_Engine.AddEnergy(i_EnergyToAdd);
        
    }

   public void UpdateCurrentEnergy(float i_MaxFuel, float i_MaxBatteryTime)
    {
        if (m_Engine instanceof Fuel)
        {
            setCurrentEnergy(((Fuel)m_Engine).getCurrentFuel());
        }
        else
        {
            setCurrentEnergy(((Electric)m_Engine).getM_CurrentBatteryTime());
        }
    }

    protected void TypeOfEngine(String i_EnergyType)
    {
        if (i_EnergyType.equals("Fuel"))
        {
            m_Engine = new Fuel();
        }
        else
        {
            m_Engine = new Electric();
        }
    }

    public void InitializeWheels(float i_MaxAirPressure)
    {
        for (int i = 0; i < r_WheelArrSize; i++)
        {
            r_Wheels.add(new Wheel(i_MaxAirPressure));
        }
    }

    public void InitializeWheelsInfo(String i_Manufacturer, float i_CurrentAirPressure)
    {
        for(Wheel w : r_Wheels)
        {
            w.Inflate(i_CurrentAirPressure);
            w.setManufacturer(i_Manufacturer);
        }
    }

    public String GetWheelsDetails()
    {
        StringBuilder info = new StringBuilder();
        String currentWheel = "";

        currentWheel = String.format("Manufacturer: %s" + System.lineSeparator() + "Current air pressure: %.2f.",r_Wheels.get(0).getManufacturer(),r_Wheels.get(0).getCurrentAirPressure());
        info.append(currentWheel);

        return info.toString();
    }

    public String GetEngineDetails()
    {
        StringBuilder info = new StringBuilder();
        String engine;

        info.append(System.lineSeparator());
        engine = m_Engine.GetDetails();
        info.append(engine);

        return info.toString();
    }

    public void InflateToMax()
    {
        for (Wheel w : r_Wheels)
        {
            w.Inflate(w.MissAirPressure());
        }
    }
    
    
    public void InsertDetails(CarDetails i_Details) 
    {
    	m_CarColor = i_Details.getCarColor();
    	m_LicenseType = i_Details.getLicenseType();
    	m_NumberOfDoors = i_Details.getNumberOfDoors();
    }
    
    public String getCarColor() {
		return m_CarColor;
	}

	public int getNumberOfDoors() {
		return m_NumberOfDoors;
	}

	public String getLicenseType() {
		return m_LicenseType;
	}
	
    public String detailsToString() 
    {
    	return String.format("Color: %s" + System.lineSeparator() + 
    						"Number Of Doors: %d" + System.lineSeparator() + 
    						"License Type: %s",
    						m_CarColor,
    						m_NumberOfDoors,
    						m_LicenseType);
    }

	public abstract void CreateEngineAndWheels(String i_EnergyType);

    public abstract void UpdateEnergy();
}
