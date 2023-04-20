package Model;

public class CarDetails 
{
	private String m_CarColor;
    private int m_NumberOfDoors;
    private String m_LicenseType;
    
    public String getCarColor() 
    {
		return m_CarColor;
	}
	
    public void setCarColor(String m_CarColor) 
    {
		this.m_CarColor = m_CarColor;
	}
	
    public int getNumberOfDoors() 
    {
		return m_NumberOfDoors;
	}
	
    public void setNumberOfDoors(int m_NumberOfDoors) 
    {
		this.m_NumberOfDoors = m_NumberOfDoors;
	}
	
    public String getLicenseType() 
    {
		return m_LicenseType;
	}
	
    public void setLicenseType(String m_LicenseType) 
    {
		this.m_LicenseType = m_LicenseType;
	}
}
