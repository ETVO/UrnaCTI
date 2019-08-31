
class Partido 
{
    private int numero;
    private String nome;
    private String obs;

    public Partido()
    {
        numero = 0;
        nome = "";
        obs = "";
    }

    //

    public setNumero(int n)
    {
        if(n != 0) this.numero = n;
    }

    public setNome(String nome)
    {
        this.nome = nome;
    }

    public setObs(String obs)
    {
        this.obs = obs;
    }

    //

    public getNumero()
    {
        return this.numero;
    }

    public getNome()
    {
        return this.nome;
    }

    public getObs()
    {
        return this.obs;
    }
}