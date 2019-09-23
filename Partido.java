
class Partido 
{
    private int numero; // PK
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

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    //

    public int getNumero()
    {
        return this.numero;
    }

    public String getNome()
    {
        return this.nome;
    }
}