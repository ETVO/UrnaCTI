
class Candidato 
{
    private int numero;
    private int n_partido;
    private String nome;
    private int cargo;

    public Candidato()
    {
        numero = 0;
        n_partido = 0;
        nome = "";
        cargo = 0;
    }

    //

    public setNumero(int n)
    {
        if(n != 0) this.numero = n;
    }

    public setN_Partido(int n)
    {
        if(n != 0) this.n_partido = n;
    }

    public setNome(String nome)
    {
        this.nome = nome;
    }

    public setCargo(int n)
    {
        this.cargo = n;
    }

    //

    public getNumero()
    {
        return this.numero;
    }

    public getN_Partido()
    {
        return this.n_partido;
    }

    public getNome()
    {
        return this.nome;
    }

    public getCargo()
    {
        return this.cargo;
    }
}