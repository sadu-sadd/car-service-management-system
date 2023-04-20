package Model;

public class Fuel extends EnergySource
{

    private float m_CurrentFuel;
    private float m_MaxFuel;
    private String m_FuelType;

    public float getCurrentFuel() {
		return m_CurrentFuel;
	}

	public void setCurrentFuel(float m_CurrentFuel) {
		this.m_CurrentFuel = m_CurrentFuel;
	}

	public String getFuelType() {
		return m_FuelType;
	}

	public void setFuelType(String i_FuelType) {
		this.m_FuelType = i_FuelType;
	}

    public void Refuel(float i_RefuelToAdd, String i_FuelType)
    {
        if (m_FuelType != i_FuelType)
        {
        	return;
        }

        AddEnergy(i_RefuelToAdd);
    }

    public boolean AddEnergy(float i_EnergyToAdd)
    {
        boolean isValid = false;
        if (m_CurrentFuel + i_EnergyToAdd <= m_MaxFuel)
        {
            m_CurrentFuel += i_EnergyToAdd;
            isValid = true;
        }
        return isValid;
    }
    
    public String GetDetails()
    {
        String message;
        message = String.format("Fuel type is: %s" + System.lineSeparator() + "Current fuel is: %.2f" + System.lineSeparator(), m_FuelType, CalculateCurrentEnergy(m_MaxFuel, m_CurrentFuel));
        return message;
    }

    public void UpdateMax(float i_MaxEnergy)
    {
        m_MaxFuel = i_MaxEnergy;
    }
}
