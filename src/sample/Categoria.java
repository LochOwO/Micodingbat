package sample;

public class Categoria
{
    int num;
    String categoria, descripcion;

    public Categoria(int num, String categoria, String descripcion)
    {
        this.num = num;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
}