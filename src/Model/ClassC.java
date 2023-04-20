package Model;

public class ClassC extends Car
{
	private static final int k_NumberOfWheels = 4;
	private static final int k_MaxAirPressure = 26;
	private static final int k_MaxFuel = 0;
	private static final int k_NoneBattery = 0;

    public ClassC(String i_PlateNumber) 
    {
    	super(i_PlateNumber, k_NumberOfWheels);
    }

    public void CreateEngineAndWheels(String i_EnergyType)
    {
        TypeOfEngine(i_EnergyType);
        InitializeWheels(k_MaxAirPressure);
        m_Engine.UpdateMax(k_MaxFuel);
        ((Fuel)m_Engine).setFuelType("Electricity");
    }

    public void UpdateEnergy()
    {
        UpdateCurrentEnergy(k_MaxFuel, k_NoneBattery);
    }
}
