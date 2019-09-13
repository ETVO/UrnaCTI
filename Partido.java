
class Partido 
{
    private int numero; // PK
    private String nome;
    private String obs;

    public Partido()
    {
        numero = 0;
        nome = "";
        obs = "";
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

    public void setObs(String obs)
    {
        this.obs = obs;
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

    public String getObs()
    {
        return this.obs;
    }
}