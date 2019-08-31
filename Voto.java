
class Voto 
{
    private long id_voto;
    private int n_candidato;
    private String momento;

    public Voto()
    {
        id_voto = 0;
        n_candidato = 0;
        momento = "";
    }

    //

    public setId_Voto(int n)
    {
        if(n != 0) this.id_voto = n;
    }

    public setN_Candidato(int n)
    {
        if(n != 0) this.n_candidato = n;
    }

    public setMomento(String momento)
    {
        this.momento = momento;
    }

    //

    public setId_Voto(int n)
    {
        if(n != 0) this.id_voto = n;
    }

    public setN_Candidato(int n)
    {
        if(n != 0) this.n_candidato = n;
    }

    public setMomento(String momento)
    {
        this.momento = momento;
    }
}