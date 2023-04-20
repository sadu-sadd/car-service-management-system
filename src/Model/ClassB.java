package Model;

public class ClassB extends Car
{
	private static final int k_NumberOfWheels = 4;
    private static final float k_MaxAirPressure = 40;
    private static final float k_MaxFuel = 10;
    private static final float k_MaxBatteryTime = 1.4f;

    public ClassB(String i_PlateNumber) 
    {
    	super(i_PlateNumber, k_NumberOfWheels);
    }

    public void CreateEngineAndWheels(String i_EnergyType)
    {
        TypeOfEngine(i_EnergyType);
        InitializeWheels(k_MaxAirPressure);
        if (m_Engine instanceof Fuel)
        {
            m_Engine.UpdateMax(k_MaxFuel);
            ((Fuel)m_Engine).setFuelType("Diesel");
        }
        else
        {
            m_Engine.UpdateMax(k_MaxBatteryTime);
        }
    }

   public void UpdateEnergy()
    {
	   UpdateCurrentEnergy(k_MaxFuel, k_MaxBatteryTime);
    }
}
