package Model;

public class CarFactory
{
    public static Car CreateCar(String i_PlateNumber, String i_CarType)
    {
        Car newCar = null;

        if (i_CarType.equals("ClassA"))
        {
            newCar = new ClassA(i_PlateNumber);
        }
        else if (i_CarType.equals("ClassB"))
        {
            newCar = new ClassB(i_PlateNumber);
        }
        else if (i_CarType.equals("ClassC"))
        {
            newCar = new ClassC(i_PlateNumber);
        }

        return newCar;
    }
}
