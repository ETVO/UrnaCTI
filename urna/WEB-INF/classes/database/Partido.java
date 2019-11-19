package database;

public class Partido 
{
    private int numero; // PK
    private int num_prev;
    private String nome;

    public Partido()
    {
        numero = 0;
        nome = "";
    }

    //

    public void setNumero(int n)
    {
        if(n != 0) this.numero = n;
    }

    public void setNumPrev(int n)
    {
        if(n != 0) this.num_prev = n;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    //

    public int getNumero()
    {
        return this.numero;
    }

    public int getNumPrev()
    {
        return this.num_prev;
    }

    public String getNome()
    {
        return this.nome;
    }
}
