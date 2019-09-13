
class Candidato 
{
    private int numero; // PK
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
        switch(cargo)
        {
            case "Deputado Federal": this.cargo = 0;
            case "Senador": this.cargo =  1;
            case "Presidente": this.cargo =  2;
            case "Deputado Estadual": this.cargo =  3;
            case "Governador": this.cargo =  4;
            default: this.cargo =  5;
        }
    }

    //

    public int getNumero()
    {
        return this.numero;
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