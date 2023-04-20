package Model;

public class Client 
{
     private String m_Name;
     private String m_ClientPhoneNumber;
     private String m_CarService = "Color Service"; 


     public Client(String i_Name, String i_PhoneNumber)
     {
         m_Name = i_Name;
         m_ClientPhoneNumber = i_PhoneNumber;
     }

     public String getName()
     {
          return m_Name;
     }
     
     public String getCarService() 
     {
    	 return m_CarService;
     }
     
     public void setCarService(String m_CarService) {
		this.m_CarService = m_CarService;
	}

	public String getPhoneNumber() 
     {
    	 return m_ClientPhoneNumber;
     }
}
