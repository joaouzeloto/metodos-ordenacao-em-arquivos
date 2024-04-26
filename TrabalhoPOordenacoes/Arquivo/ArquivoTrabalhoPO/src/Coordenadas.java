public class Coordenadas
{
    private int n1, n2;
    private Coordenadas prox;

    public int getN1() {
        return n1;
    }

    public Coordenadas(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
        this.prox =null;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public Coordenadas getProx() {
        return prox;
    }

    public void setProx(Coordenadas prox) {
        this.prox = prox;
    }
}
