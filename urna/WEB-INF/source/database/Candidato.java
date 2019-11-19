
public class Candidato 
{
    private int numero; // PK
    private int num_prev;
    private int n_partido; // FK
    private String nome;
    private int cargo;

    public Candidato()
    {
        numero = 0;
        n_partido = 0;
        nome = "";
        cargo = 5;
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

    public void setN_Partido(int n)
    {
        if(n != 0) this.n_partido = n;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setCargo(int n)
    {
        this.cargo = n;
    }

    public void setCargoString(String cargo)
    {
        if("Deputado Federal".equals(cargo)) this.cargo = 0;
        else if("Senador".equals(cargo)) this.cargo =  1;
        else if("Presidente".equals(cargo)) this.cargo =  2;
        else if("Deputado Estadual".equals(cargo)) this.cargo =  3;
        else if("Governador".equals(cargo)) this.cargo =  4;
        else this.cargo =  -1;
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

    public int getN_Partido()
    {
        return this.n_partido;
    }

    public String getNome()
    {
        return this.nome;
    }

    public int getCargo()
    {
        return this.cargo;
    }

    public String getCargoString()
    {
        switch(this.cargo)
        {
            case 0: return "Deputado Federal";
            case 1: return "Senador";
            case 2: return "Presidente";
            case 3: return "Deputado Estadual";
            case 4: return "Governador";
            default: return "";
        }
    }
}
