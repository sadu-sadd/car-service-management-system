package Model;

public class Electric extends EnergySource
{
	private float m_MaxBatteryTime;
    private float m_CurrentBatteryTime;

    public float getM_MaxBatteryTime()
    {
		return m_MaxBatteryTime;
	}

	public void setM_MaxBatteryTime(float m_MaxBatteryTime) 
	{
		this.m_MaxBatteryTime = m_MaxBatteryTime;
	}

	public float getM_CurrentBatteryTime() 
	{
		return m_CurrentBatteryTime;
	}

	public void setM_CurrentBatteryTime(float m_CurrentBatteryTime) 
	{
		this.m_CurrentBatteryTime = m_CurrentBatteryTime;
	}
	
	public boolean AddEnergy(float i_EnergyToAdd)
    {
        boolean isValid = false;

        if (m_CurrentBatteryTime + i_EnergyToAdd <= m_MaxBatteryTime)
        {
            m_CurrentBatteryTime += i_EnergyToAdd;
            isValid = true;
        }
        
        return isValid;
    }

    public String GetDetails()
    {
        return String.format("The current battery is: %.2f", CalculateCurrentEnergy(m_MaxBatteryTime, m_CurrentBatteryTime));
    }

    public void UpdateMax(float i_MaxEnergy)
    {
        m_MaxBatteryTime = i_MaxEnergy;
    }
}
