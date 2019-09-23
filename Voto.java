import java.text.DateFormat;

class Voto 
{
    private long id_voto; // PK
    private int n_candidato; // FK
    private String momento;

    public Voto()
    {
        id_voto = 0;
        n_candidato = 0;
        momento = "";
    }

    public Voto(long id_voto, int n_candidato, String momento)
    {
        setVoto(id_voto, n_candidato, momento);
    }

    public void setVoto(long id_voto, int n_candidato, String momento)
    {
        setId_Voto(id_voto);
        setN_Candidato(n_candidato);
        setMomento(momento);
    }

    //

    public void setId_Voto(long n)
    {
        if(n != 0) this.id_voto = n;
    }

    public void setN_Candidato(int n)
    {
        if(n != 0) this.n_candidato = n;
    }

    public void setMomento(String momento)
    {
        this.momento = momento;
    }

    //

    public long getId_Voto()
    {
        return this.id_voto;
    }

    public int getN_Candidato()
    {
        return this.n_candidato;
    }

    public String getMomento()
    {
        return this.momento;
    }

    public String getMomentoDate()
    {
        try {
            DateFormat df = DateFormat.getDateInstance();
            return df.parse(this.momento).toString();
        }
        catch (Exception e) {
            return this.momento;
        }
    }
}