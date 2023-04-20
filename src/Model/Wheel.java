package Model;

public class Wheel
{
	private final float r_MaxAirPressure;
    public static final int k_MinAirPressure = 0;
    private String m_Manufacturer;
    private float m_CurrentAirPressure;

    public Wheel(float i_MaxAirPressure)
    {
        r_MaxAirPressure = i_MaxAirPressure;
        m_CurrentAirPressure = 0;
    }
    
    public String getManufacturer() {
		return m_Manufacturer;
	}

	public void setManufacturer(String m_Manufacturer) {
		this.m_Manufacturer = m_Manufacturer;
	}

	public float getCurrentAirPressure() {
		return m_CurrentAirPressure;
	}

	public void setCurrentAirPressure(float m_CurrentAirPressure) {
		this.m_CurrentAirPressure = m_CurrentAirPressure;
	}

	public boolean Inflate(float i_ToAdd)
    {
        boolean isAdded = false;

        if ((m_CurrentAirPressure + i_ToAdd) <= r_MaxAirPressure)
        {
            m_CurrentAirPressure += i_ToAdd;
            isAdded = true;
        }
       return isAdded;
    }

    public float MissAirPressure()
    {
        return r_MaxAirPressure - m_CurrentAirPressure;
    }
}
