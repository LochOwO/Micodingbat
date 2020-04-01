package sample;

public class Problema
{
    int num, numCategoria;
    String nombre, descripcion, parametros, programa;

    public Problema(int num, int numCategoria, String nombre, String descripcion, String parametros, String programa)
    {
        this.num = num;
        this.numCategoria = numCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.parametros = parametros;
        this.programa = programa;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public int getNumCategoria()
    {
        return numCategoria;
    }

    public void setNumCategoria(int numCategoria)
    {
        this.numCategoria = numCategoria;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getParametros()
    {
        return parametros;
    }

    public void setParametros(String parametros)
    {
        this.parametros = parametros;
    }

    public String getPrograma()
    {
        return programa;
    }

    public void setPrograma(String programa)
    {
        this.programa = programa;
    }
}