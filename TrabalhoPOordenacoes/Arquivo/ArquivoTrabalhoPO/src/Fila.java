public class Fila
{
    private Coordenadas inicio, fim;

    public Fila(){this.inicio = null; this.fim = null;}

    public void inserir(int a,int b)
    {
        Coordenadas novo = new Coordenadas(a,b);
        if(inicio==null)
            inicio = fim = novo;
        else
        {
            fim.setProx(novo);
            fim = novo;
        }
    }

    public Coordenadas retirar()
    {
        Coordenadas aux = inicio;
        inicio = inicio.getProx();
        return aux;
    }

    public boolean empty()
    {
        return inicio == null;
    }

    public int get1()
    {
        return inicio.getN1();
    }

    public Coordenadas getInicio() {
        return inicio;
    }

    public void setInicio(Coordenadas inicio) {
        this.inicio = inicio;
    }

    public Coordenadas getFim() {
        return fim;
    }

    public void setFim(Coordenadas fim) {
        this.fim = fim;
    }

    public int get2()
    {
        return inicio.getN2();
    }
}
