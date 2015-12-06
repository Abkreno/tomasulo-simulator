package nan.tomasulo.registers;

public class Register 
{
    private short data;

    public Register(short data) 
    {
        this.data = data;
    }

    public short getData() 
    {
        return this.data;
    }

    public void setData(short data) 
    {
        this.data = data;
    }
}
