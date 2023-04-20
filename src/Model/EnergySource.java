package Model;

public abstract class EnergySource
{
  
	public final float k_MinEnergy = 0;

    public float CalculateCurrentEnergy(float i_MaxEnergy, float i_CurrentEnergy)
    {
        return i_CurrentEnergy;
    }

    public abstract void UpdateMax(float i_MaxEnergy);

    public abstract boolean AddEnergy(float i_EnergyToAdd); 
    
    public abstract String GetDetails();
}
