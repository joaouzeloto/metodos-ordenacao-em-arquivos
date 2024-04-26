import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Arquivo_Java {
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comparacao,movimentacao;

    public Arquivo_Java(String nomearquivo)
    {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
            this.comparacao = this.movimentacao = 0;
        } catch (IOException e)
        { }
    }

    public void truncate(long pos) //desloca eof
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        { }
    }

    //semelhante ao feof() da linguagem C
    //verifica se o ponteiro esta no <EOF> do arquivo
    public boolean eof()
    {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException e)
        { }
        return (retorno);
    }

    //insere um Registro no final do arquivo, passado por par metro
    public void inserirRegNoFinal(Registro reg) throws IOException {
        seekArq(filesize());//ultimo byte
        reg.gravaNoArq(arquivo);
    }

    public int filesize() throws IOException {
        return (int) (arquivo.length()/Registro.length());
    }

    public void exibirArq()
    {
        int i;
        Registro aux = new Registro();
        seekArq(0);
        i = 0;
        while (!this.eof())
        {
            System.out.println("Posicao " + i);
            aux.leDoArq(arquivo);
            aux.exibirReg();
            i++;
        }
    }

    public void exibirUmRegistro(int pos)
    {
        Registro aux = new Registro();
        seekArq(pos);
        System.out.println("Posicao " + pos);
        aux.leDoArq(arquivo);
        aux.exibirReg();
    }

    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        { }
    }

    public void leArq() throws IOException {
        int codigo, idade;
        String nome;
        codigo = Entrada.leInteger("Digite o c digo");
        while (codigo != 0)
        {
            nome = Entrada.leString("Digite o nome");
            idade = Entrada.leInteger("Digite a idade");
            inserirRegNoFinal(new Registro(codigo, nome, idade));
            codigo = Entrada.leInteger("Digite o c digo");
        }
    }


    //.............................................................................
    //métodos de ordenação

    public int getComparacao() {
        return comparacao;
    }

    public void setComparacao(int comparacao) {
        this.comparacao = comparacao;
    }

    public int getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(int movimentacao) {
        this.movimentacao = movimentacao;
    }

    public void insertionSortArq() throws IOException
    {
        int aux,comp,tl=filesize();
        Registro auxR = new Registro(), compR = new Registro();
        aux = 1;
        while(aux<tl)
        {
            seekArq(aux);
            auxR.leDoArq(arquivo);
            comp = aux;
            if(comp-1>=0)
            {
                seekArq(comp-1);
                compR.leDoArq(arquivo);
            }
            comparacao++;
            while (comp-1>=0&&compR.getCodigo()>auxR.getCodigo())
            {
                comparacao++;
                movimentacao++;
                seekArq(comp);
                compR.gravaNoArq(arquivo);
                comp--;
                if(comp-1>=0)
                {
                    seekArq(comp-1);
                    compR.leDoArq(arquivo);
                }
            }
            movimentacao++;
            seekArq(comp);
            auxR.gravaNoArq(arquivo);
            aux++;
        }
    }

    public void insetionSortBinFile() throws IOException {
        int tl=1,aux=0,comp,pos;
        Registro auxR = new Registro(), compR = new Registro(), posR = new Registro();

        while (aux<filesize())
        {
            seekArq(aux);
            auxR.leDoArq(arquivo);
            pos = buscaBinFile(auxR,tl);
            comp = aux;
            comparacao++;
            while (comp-1>=0&&comp!=pos)
            {
                comparacao++;
                movimentacao++;
                movimentacao++;
                seekArq(comp-1);
                compR.leDoArq(arquivo);
                seekArq(comp);
                compR.gravaNoArq(arquivo);
                comp = comp - 1;
            }
            movimentacao++;
            seekArq(pos);
            auxR.gravaNoArq(arquivo);
            aux++;
            tl++;
        }
    }

    public void selectionSortArq() throws IOException {
        int tam = filesize();
        for(int i=0; i< tam;i++)
        {
            seekArq(i);
            Registro menor = new Registro();
            menor.leDoArq(arquivo);
            int pos = i;
            for(int j = i+1; j< tam;j++)
            {
                seekArq(j);
                Registro aux = new Registro();
                aux.leDoArq(arquivo);
                comparacao++;
                if(aux.getCodigo()<menor.getCodigo())
                {
                    pos = j;
                    menor = aux;
                }
            }
            if(i!=pos)
            {
                movimentacao++;
                movimentacao++;
                seekArq(i);
                Registro aux2 = new Registro();
                aux2.leDoArq(arquivo);

                seekArq(i);
                menor.gravaNoArq(arquivo);

                seekArq(pos);
                aux2.gravaNoArq(arquivo);
            }
        }

    }

    private int buscaBinFile(Registro aux, int tl)
    {
        int ini = 0, fim = tl-1, meio = fim/2;
        Registro reg = new Registro();
        seekArq(meio);
        reg.leDoArq(arquivo);
        comparacao++;
        while (ini<fim&&aux.getCodigo()!=reg.getCodigo())
        {
            comparacao++;
            comparacao++;
            if(reg.getCodigo()<aux.getCodigo())
                ini = meio + 1;
            else
                fim = meio-1;
            meio = (ini+fim)/2;
            seekArq(meio);
            reg.leDoArq(arquivo);
        }
        comparacao++;
        if(reg.getCodigo()<aux.getCodigo())
            return meio+1;
        return meio;
    }

    public void shakeSortFile() throws IOException {
        int inicio = 0, fim = filesize();
        Registro aux1 = new Registro(), aux2 = new Registro();
        while (inicio<fim)
        {
            for(int i = inicio;i<fim-1;i++)
            {
                seekArq(i);
                aux1.leDoArq(arquivo);
                seekArq(i+1);
                aux2.leDoArq(arquivo);
                comparacao++;
                if (aux1.getCodigo()>aux2.getCodigo())
                {
                    movimentacao++;
                    movimentacao++;
                    seekArq(i+1);
                    aux1.gravaNoArq(arquivo);
                    seekArq(i);
                    aux2.gravaNoArq(arquivo);
                }
            }
            fim--;
            for(int i = fim;i>inicio;i--)
            {
                seekArq(i);
                aux1.leDoArq(arquivo);
                seekArq(i-1);
                aux2.leDoArq(arquivo);
                comparacao++;
                if (aux1.getCodigo()<aux2.getCodigo())
                {
                    movimentacao++;
                    movimentacao++;
                    seekArq(i-1);
                    aux1.gravaNoArq(arquivo);
                    seekArq(i);
                    aux2.gravaNoArq(arquivo);
                }
            }
            inicio++;
        }
    }

    public void bubbleSortFile() throws IOException
    {
        int tl = filesize();
        Registro aux1=new Registro(),aux2=new Registro();
        while (tl>0)
        {
            for(int i=0;i<tl-1;i++)
            {
                seekArq(i);
                aux1.leDoArq(arquivo);
                seekArq(i+1);
                aux2.leDoArq(arquivo);
                comparacao++;
                if(aux1.getCodigo()>aux2.getCodigo())
                {
                    movimentacao++;
                    movimentacao++;
                    seekArq(i+1);
                    aux1.gravaNoArq(arquivo);
                    seekArq(i);
                    aux2.gravaNoArq(arquivo);
                }
            }
            tl--;
        }
    }

    public void shellSortFile() throws IOException {
        int tl = filesize(), dist=1;
        Registro aux1 = new Registro(), aux2 = new Registro();
        while (dist<tl)
            dist = dist * 3 +1;
        dist = dist/3;
        while (dist>0)
        {
            for(int i=dist;i<tl;i++)
            {
                seekArq(i);
                aux1.leDoArq(arquivo);
                int j = i;
                if(j-dist>=0)
                {
                    seekArq(j-dist);
                    aux2.leDoArq(arquivo);
                }
                comparacao++;
                while (j-dist>=0&&aux1.getCodigo()<aux2.getCodigo())
                {
                    comparacao++;
                    movimentacao++;
                    seekArq(j);
                    aux2.gravaNoArq(arquivo);
                    j = j - dist;
                    if(j-dist>=0)
                    {
                        seekArq(j-dist);
                        aux2.leDoArq(arquivo);
                    }
                }
                movimentacao++;
                seekArq(j);
                aux1.gravaNoArq(arquivo);
            }
            dist = dist/3;
        }
    }

    public void heapSortFile() throws IOException {
        int pai,fe,fd,maior,tl=filesize();
        Registro paiR = new Registro(), feR = new Registro(), fdR = new Registro(), maiorR = new Registro();
        while (tl>1)
        {
            pai = tl/2-1;
            while (pai>=0)
            {
                seekArq(pai);
                paiR.leDoArq(arquivo);
                fe = pai*2+1;
                fd = fe+1;
                seekArq(fe);
                feR.leDoArq(arquivo);
                seekArq(fd);
                fdR.leDoArq(arquivo);
                maior = fe;
                comparacao++;
                if(fd<tl&&fdR.getCodigo()>feR.getCodigo())
                    maior = fd;
                seekArq(maior);
                maiorR.leDoArq(arquivo);
                comparacao++;
                if(maiorR.getCodigo()>paiR.getCodigo())
                {
                    movimentacao++;
                    movimentacao++;
                    seekArq(maior);
                    paiR.gravaNoArq(arquivo);
                    seekArq(pai);
                    maiorR.gravaNoArq(arquivo);
                }
                pai--;
            }
            movimentacao++;
            movimentacao++;
            seekArq(0);
            paiR.leDoArq(arquivo);
            seekArq(tl-1);
            fdR.leDoArq(arquivo);
            seekArq(0);
            fdR.gravaNoArq(arquivo);
            seekArq(tl-1);
            paiR.gravaNoArq(arquivo);
            tl--;
        }

    }


    public void combSortFile() throws IOException {
        Registro aux = new Registro(),comp = new Registro();
        int gap, tam = filesize();
        gap = (int) (tam/1.3);
        for( ;gap>0;gap= (int) (gap/1.3))
        {
            seekArq(0);
            aux.leDoArq(arquivo);
            for(int i=0;i+gap<tam;i++)
            {
                seekArq(i+gap);
                comp.leDoArq(arquivo);
                comparacao++;
                if(aux.getCodigo()<comp.getCodigo())
                {
                    movimentacao++;
                    movimentacao++;
                    seekArq(i);
                    comp.gravaNoArq(arquivo);
                    seekArq(i+gap);
                    aux.gravaNoArq(arquivo);
                }
                seekArq(i+1);
                aux.leDoArq(arquivo);
            }
        }
    }


    public void gnomeSortFile() throws IOException {
        Registro aux = new Registro(), comp= new Registro(), ant = new Registro();
        int n=0, marcar, tam = filesize();
        while(n<tam)
        {
            seekArq(n);
            aux.leDoArq(arquivo);
            seekArq(n+1);
            comp.leDoArq(arquivo);
            comparacao++;
            if(aux.getCodigo()> comp.getCodigo())
            {
                movimentacao++;
                movimentacao++;
                seekArq(n+1);
                aux.gravaNoArq(arquivo);
                seekArq(n);
                comp.gravaNoArq(arquivo);
            }
            marcar = n + 1;
            seekArq(n);
            aux.leDoArq(arquivo);
            boolean flag = true;
            while ((n-1)>=0&&flag)
            {
                flag = false;
                seekArq(n-1);
                ant.leDoArq(arquivo);
                comparacao++;
                if(aux.getCodigo()< ant.getCodigo())
                {
                    movimentacao++;
                    movimentacao++;
                    seekArq(n);
                    ant.gravaNoArq(arquivo);
                    seekArq(n-1);
                    aux.gravaNoArq(arquivo);
                    flag = true;
                }
                n--;
                seekArq(n);
                aux.leDoArq(arquivo);
            }
            n =marcar;
        }
    }

    public void quickSPFile() throws IOException {
        quickSP(0,filesize()-1);
    }

    public void quickSP(int inicio,int fim)
    {
        int i = inicio, j = fim;
        Registro aux1 = new Registro(), aux2 = new Registro(), aux3 = new Registro();
        while (i<j)
        {
            boolean flag = true;
            while (i<j&&flag)
            {
                flag = false;
                seekArq(i);
                aux1.leDoArq(arquivo);
                seekArq(j);
                aux2.leDoArq(arquivo);
                comparacao++;
                if(aux1.getCodigo()<=aux2.getCodigo())
                {
                    i++;
                    flag = true;
                }
            }
            movimentacao++;
            movimentacao++;
            seekArq(i);
            aux1.leDoArq(arquivo);
            seekArq(j);
            aux2.leDoArq(arquivo);
            seekArq(i);
            aux2.gravaNoArq(arquivo);
            seekArq(j);
            aux1.gravaNoArq(arquivo);
            flag = true;
            while (i<j&&flag)
            {
                flag = false;
                seekArq(i);
                aux1.leDoArq(arquivo);
                seekArq(j);
                aux2.leDoArq(arquivo);
                comparacao++;
                if(aux2.getCodigo()>=aux1.getCodigo())
                {
                    j--;
                    flag = true;
                }
            }
            movimentacao++;
            movimentacao++;
            seekArq(i);
            aux1.leDoArq(arquivo);
            seekArq(j);
            aux2.leDoArq(arquivo);
            seekArq(i);
            aux2.gravaNoArq(arquivo);
            seekArq(j);
            aux1.gravaNoArq(arquivo);
        }
        if(inicio<i-1)
            quickSP(inicio,i-1);
        if(j+1<fim)
            quickSP(j+1, fim);
    }

    public void criarDuplicata(RandomAccessFile dup, int tam) throws IOException {
         final int tf = 20;
         int codigo, tl = tf, idade;
         char nome[] = new char[tf];

         for(int i = 0;i<tam;i++)
         {
             codigo = 0;
             idade = 10;
             dup.writeInt(codigo);
             dup.writeInt(idade);
             dup.writeInt(tl);
             for (int j = 0; j < tf; j++)
             {
                 dup.writeChar(nome[j]);
             }
         }
    }

    private void fusao(Arquivo_Java dup,int ini1, int fim1, int ini2, int fim2)
    {
        Registro aux1 = new Registro(), aux2 = new Registro();
        int a=0,i = ini1, j=ini2;
        while (i<=fim1&&j<=fim2)
        {
            seekArq(i);
            aux1.leDoArq(arquivo);
            seekArq(j);
            aux2.leDoArq(arquivo);
            comparacao++;
            if(aux1.getCodigo()<aux2.getCodigo())
            {
                movimentacao++;
                dup.seekArq(a);
                aux1.gravaNoArq(dup.arquivo);
                a++;
                i++;
            }
            else
            {
                movimentacao++;
                dup.seekArq(a);
                aux2.gravaNoArq(dup.arquivo);
                a++;
                j++;
            }
        }
        while (i<=fim1)
        {
            movimentacao++;
            seekArq(i);
            aux1.leDoArq(arquivo);
            dup.seekArq(a);
            aux1.gravaNoArq(dup.arquivo);
            i++;
            a++;
        }
        while (j<=fim2)
        {
            movimentacao++;
            seekArq(j);
            aux2.leDoArq(arquivo);
            dup.seekArq(a);
            aux2.gravaNoArq(dup.arquivo);
            j++;
            a++;
        }
        for(int k=0;k<a;k++)
        {
            movimentacao++;
            dup.seekArq(k);
            aux1.leDoArq(dup.arquivo);
            seekArq(k+ini1);
            aux1.gravaNoArq(arquivo);
        }
    }

    private void mergeS(Arquivo_Java dup, int esq, int dir)
    {
        int meio;
        if(esq<dir)
        {
            meio = (esq+dir)/2;
            mergeS(dup,esq,meio);
            mergeS(dup,meio+1,dir);
            fusao(dup,esq,meio,meio+1,dir);
        }
    }


    public void mergeSortFile() throws IOException
    {
        int tam = filesize();
        RandomAccessFile duplicata = new RandomAccessFile("duplicata.txt","rw");
        criarDuplicata(duplicata,tam);
        duplicata.close();
        Arquivo_Java dup = new Arquivo_Java("duplicata.txt");
        mergeS(dup,0,tam-1);
    }

    

    public void executa() throws IOException {
        leArq();
        exibirArq();
    }

    private void quickP(int ini, int fim)
    {
        int i=ini, j=fim, pivo=(ini+fim)/2;
        Registro iR = new Registro(), jR = new Registro(), pivoR = new Registro();
        while (i<j)
        {
            seekArq(pivo);
            pivoR.leDoArq(arquivo);
            seekArq(i);
            iR.leDoArq(arquivo);
            comparacao++;
            while (iR.getCodigo()<pivoR.getCodigo())
            {
                comparacao++;
                i++;
                seekArq(i);
                iR.leDoArq(arquivo);
            }
            seekArq(pivo);
            pivoR.leDoArq(arquivo);
            seekArq(j);
            jR.leDoArq(arquivo);
            comparacao++;
            while (jR.getCodigo()>pivoR.getCodigo())
            {
                comparacao++;
                j--;
                seekArq(j);
                jR.leDoArq(arquivo);
            }
            if(i<=j)
            {
                movimentacao++;
                movimentacao++;
                seekArq(i);
                iR.leDoArq(arquivo);
                seekArq(j);
                jR.leDoArq(arquivo);
                seekArq(i);
                jR.gravaNoArq(arquivo);
                seekArq(j);
                iR.gravaNoArq(arquivo);
                i++;
                j--;
            }
            if(ini<j)
                quickP(ini,j);
            if(i<fim)
                quickP(i,fim);
        }
    }

    public void quickSortPivo() throws IOException {
        quickP(0,filesize()-1);
    }

    public void particao(Arquivo_Java a, Arquivo_Java b) throws IOException
    {
        Registro origem = new Registro();
        for(int i=0;i<filesize()/2;i++)
        {
            seekArq(i);
            origem.leDoArq(arquivo);
            a.seekArq(i);
            origem.gravaNoArq(a.arquivo);
            seekArq(i+filesize()/2);
            origem.leDoArq(arquivo);
            b.seekArq(i);
            origem.gravaNoArq(b.arquivo);
        }
    }

    public void fusao1(Arquivo_Java a, Arquivo_Java b, int seq) throws IOException {
        int k,j,i;
        int lock = seq;
        Registro aux1 = new Registro(), aux2 = new Registro();
        k = j = i = 0;

        while (k<filesize())
        {
            while (i<seq&&j<seq)
            {
                a.seekArq(i);
                aux1.leDoArq(a.arquivo);
                b.seekArq(j);
                aux2.leDoArq(b.arquivo);
                comparacao++;
                if(aux1.getCodigo()<aux2.getCodigo())
                {
                    movimentacao++;
                    seekArq(k++);
                    aux1.gravaNoArq(arquivo);
                    i++;
                }
                else
                {
                    movimentacao++;
                    seekArq(k++);
                    aux2.gravaNoArq(arquivo);
                    j++;
                }
            }
            while (i<seq)
            {
                movimentacao++;
                a.seekArq(i++);
                aux1.leDoArq(a.arquivo);
                seekArq(k++);
                aux1.gravaNoArq(arquivo);
            }
            while (j<seq)
            {
                movimentacao++;
                b.seekArq(j++);
                aux2.leDoArq(b.arquivo);
                seekArq(k++);
                aux2.gravaNoArq(arquivo);
            }
            seq = seq + lock;
        }
    }


    public void mergeSortFile1() throws IOException
    {
        Arquivo_Java aux1 = new Arquivo_Java("aux1.txt"), aux2 = new Arquivo_Java("aux2.txt");
        int seq = 1;
        aux1.truncate(0);
        aux2.truncate(0);
        while (seq<filesize())
        {
            particao(aux1,aux2);
            fusao1(aux1,aux2,seq);
            seq = seq * 2;
        }
        aux1.arquivo.close();
        aux2.arquivo.close();
        File aux3 = new File("aux1.txt");
        File aux4 = new File("aux2.txt");
        aux3.delete();
        aux4.delete();
    }

    public int achaMaior()
    {
        int maior;
        Registro aux = new Registro();
        seekArq(0);
        aux.leDoArq(arquivo);
        maior = aux.getCodigo();
        while (!eof())
        {
            aux.leDoArq(arquivo);
            if (aux.getCodigo()>maior)
                maior = aux.getCodigo();
        }
        return maior;
    }

    public int achaMenor()
    {
        int menor;
        Registro aux = new Registro();
        seekArq(0);
        aux.leDoArq(arquivo);
        menor = aux.getCodigo();
        while (!eof())
        {
            aux.leDoArq(arquivo);
            if (aux.getCodigo()<menor)
                menor = aux.getCodigo();
        }
        return menor;
    }


    public void bucketSort() throws IOException {
        int qtdeBaldes=1,maior,menor;
        Registro auxReg = new Registro();
        qtdeBaldes = (filesize()/2)-filesize()/4-1;
        maior = achaMaior();
        menor = achaMenor();
        int frequencia = (maior-menor)/2-1;
        Arquivo_Java[]buckets =  new Arquivo_Java[qtdeBaldes];
        Fila aux = new Fila();
        for (int i=0;i<qtdeBaldes;i++)
            buckets[i] = new Arquivo_Java("aux"+i+".txt");
        for (int i=0;i<qtdeBaldes;i=i+frequencia+1)
            aux.inserir(i,i+frequencia);
        seekArq(0);
        while (!eof())
        {
            Coordenadas cordAux = aux.getInicio();
            auxReg.leDoArq(arquivo);
            int i;
            comparacao++;
            for (i=0;auxReg.getCodigo()<cordAux.getN1()&&auxReg.getCodigo()>cordAux.getN2();i++,comparacao++)
                cordAux = cordAux.getProx();
            movimentacao++;
            auxReg.gravaNoArq(buckets[i].arquivo);
        }
        for (int i=0;i<qtdeBaldes;i++)
            buckets[i].bubbleSortFile();
        seekArq(0);
        for(int i=0;i<qtdeBaldes;i++)
        {
            buckets[i].seekArq(0);
            while (!buckets[i].eof())
            {
                movimentacao++;
                auxReg.leDoArq(buckets[i].arquivo);
                auxReg.gravaNoArq(arquivo);
            }
        }
        for (int i=0;i<qtdeBaldes;i++)
            buckets[i].arquivo.close();

        File[] arquivos = new File[qtdeBaldes];
        for (int i=0;i<qtdeBaldes;i++)
            arquivos[i] = new File("aux"+i+".txt");
        for(int i=0;i<qtdeBaldes;i++)
            arquivos[i].delete();
    }

    public void radixSortFile() throws IOException {
        int divisor=1, resto, result, cont;
        Registro aux1 = new Registro(), aux2 = new Registro();
        Arquivo_Java[] arquivos = new Arquivo_Java[10];
        for (int i=0;i<10;i++)
            arquivos[i] = new Arquivo_Java("aux"+i+".txt");
        cont =0;

        while(cont<filesize())
        {
            for (int i=0;i<10;i++)
                arquivos[i].seekArq(0);
            for (int i=0;i<10;i++)
                arquivos[i].truncate(0);
            cont =0;
            for (int j = 0;j<filesize();j++)
            {
                seekArq(j);
                aux1.leDoArq(arquivo);
                result = aux1.getCodigo()/divisor;
                resto = result%10;
                if(result==0)
                    cont++;
                aux1.gravaNoArq(arquivos[resto].arquivo);
                movimentacao++;
            }
            divisor = divisor * 10;
            seekArq(0);
            if(cont<filesize())
            {
                for (int i=0;i<10;i++)
                {
                    arquivos[i].seekArq(0);
                    while (!arquivos[i].eof())
                    {
                        movimentacao++;
                        aux1.leDoArq(arquivos[i].arquivo);
                        aux1.gravaNoArq(arquivo);
                    }
                }
            }
        }
        for (int i=0;i<10;i++)
            arquivos[i].arquivo.close();
        File[] arqs = new File[10];
        for (int i=0;i<10;i++)
            arqs[i] = new File("aux"+i+".txt");
        for (int i=0;i<10;i++)
            arqs[i].delete();
    }

    public void countingSortFile() throws IOException {
        int maior;
        Registro aux = new Registro(0,"nome",10), auxC = new Registro();
        Arquivo_Java b = new Arquivo_Java("b.txt"), c = new Arquivo_Java("c.txt");
        maior = achaMaior();
        c.truncate(maior);
        c.seekArq(0);
        while (!c.eof())
            aux.gravaNoArq(c.arquivo);
        seekArq(0);
        while (!eof())
        {
            movimentacao++;
            aux.leDoArq(arquivo);
            c.seekArq(aux.getCodigo()-1);
            auxC.leDoArq(c.arquivo);
            auxC.setCodigo(auxC.getCodigo()+1);
            c.seekArq(aux.getCodigo()-1);
            auxC.gravaNoArq(c.arquivo);
        }
        for (int i=1;i<c.filesize();i++)
        {
            movimentacao++;
            c.seekArq(i-1);
            aux.leDoArq(c.arquivo);
            c.seekArq(i);
            auxC.leDoArq(c.arquivo);
            auxC.setCodigo(auxC.getCodigo()+aux.getCodigo());
            c.seekArq(i);
            auxC.gravaNoArq(c.arquivo);
        }
        b.truncate(filesize());
        int j = filesize()-1;
        while (j>=0)
        {
            movimentacao++;
            movimentacao++;
            seekArq(j);
            aux.leDoArq(arquivo);
            c.seekArq(aux.getCodigo()-1);
            auxC.leDoArq(c.arquivo);
            b.seekArq(auxC.getCodigo()-1);
            aux.gravaNoArq(b.arquivo);
            auxC.setCodigo(auxC.getCodigo()-1);
            c.seekArq(aux.getCodigo()-1);
            auxC.gravaNoArq(c.arquivo);
            j--;
        }
        seekArq(0);
        b.seekArq(0);
        while (!b.eof())
        {
            movimentacao++;
            aux.leDoArq(b.arquivo);
            aux.gravaNoArq(arquivo);
        }

        c.arquivo.close();
        b.arquivo.close();
        File bx = new File("b.txt"), cx = new File("c.txt");
        bx.delete();
        cx.delete();
    }

    private void insertionSortTim(int ini, int fim)
    {
        int n = ini +1, comp;
        Registro nR = new Registro(), compR = new Registro(), compRAux = new Registro();
        while (n<fim)
        {
            seekArq(n);
            nR.leDoArq(arquivo);
            comp = n;
            seekArq(comp);
            compR.leDoArq(arquivo);
            if(ini<comp)
            {
                seekArq(comp-1);
                compRAux.leDoArq(arquivo);
            }
            comparacao++;
            while (ini<comp&&nR.getCodigo()<compRAux.getCodigo())
            {
                comparacao++;
                movimentacao++;
                movimentacao++;
                seekArq(comp-1);
                compR.gravaNoArq(arquivo);
                seekArq(comp);
                compRAux.gravaNoArq(arquivo);
                comp--;
                seekArq(comp);
                compR.leDoArq(arquivo);
                if(ini<comp)
                {
                    seekArq(comp-1);
                    compRAux.leDoArq(arquivo);
                }
            }
            movimentacao++;
            seekArq(comp);
            nR.gravaNoArq(arquivo);
            n++;
        }
    }

    private void fusaoTim(int ini1, int fim1, int ini2, int fim2) throws IOException {
        Arquivo_Java aux = new Arquivo_Java("auxTim.dat");
        aux.truncate(0);
        int i = ini1, j = ini2, k =0;
        Registro iR = new Registro(), jR = new Registro();
        aux.seekArq(0);
        while (i<=fim1&&j<=fim2)
        {
            seekArq(i);
            iR.leDoArq(arquivo);
            seekArq(j);
            jR.leDoArq(arquivo);
            comparacao++;
            if(iR.getCodigo()<jR.getCodigo())
            {
                movimentacao++;
                iR.gravaNoArq(aux.arquivo);
                i++;
                k++;
            }
            else
            {
                movimentacao++;
                jR.gravaNoArq(aux.arquivo);
                j++;
                k++;
            }
        }
        while (i<=fim1)
        {
            movimentacao++;
            seekArq(i);
            iR.leDoArq(arquivo);
            aux.seekArq(k++);
            iR.gravaNoArq(aux.arquivo);
            i++;
        }
        while (j<=fim2)
        {
            movimentacao++;
            seekArq(j);
            jR.leDoArq(arquivo);
            aux.seekArq(k++);
            jR.gravaNoArq(aux.arquivo);
            j++;
        }
        for(int a = 0;a<k;a++)
        {
            movimentacao++;
            aux.seekArq(a);
            iR.leDoArq(aux.arquivo);
            seekArq(a+ini1);
            iR.gravaNoArq(arquivo);
        }
        aux.arquivo.close();
        File auxFile = new File("auxTim.dat");
        auxFile.delete();
    }

    public void timSortFile() throws IOException {
        int div = 32;
        int tam = filesize();
        for(int i =0;i<tam;i += div)
        {
            if(i+div<tam)
                insertionSortTim(i,i+div);
            else
                insertionSortTim(i,tam);
        }


        for(int i = div;i<tam;i = i*2)
        {
            for (int j = 0;j<tam;j+=i*2)
            {
                int meio = j + i;
                int fim;
                if(meio+i<=tam)
                    fim = meio + i;
                else
                    fim = tam;
                if(meio<fim)
                    fusaoTim(j,meio,meio+1,fim);
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void zeraComp() {
        this.comparacao = 0;
    }

    public void zeraMov() {
        this.movimentacao = 0;
    }

    public void copiaArquivo(Arquivo_Java auxRev)
    {
        Registro aux = new Registro();
        auxRev.seekArq(0);
        auxRev.truncate(0);
        seekArq(0);
        while (!eof())
        {
            aux.leDoArq(arquivo);
            aux.gravaNoArq(auxRev.arquivo);
        }
    }

    public void geraOrdenado()
    {
        truncate(0);
        seekArq(0);
        Registro reg = new Registro(0,"nome",20);
        for(int i=0;i<1024;i++)
        {
            reg.setCodigo(i+1);
            reg.gravaNoArq(arquivo);
        }
    }
    public void geraReverso()
    {
        truncate(0);
        seekArq(0);
        Registro reg = new Registro(0,"nome",20);
        for(int i=0,j=1023;i<1024;i++,j--)
        {
            reg.setCodigo(j+1);
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraRandom()
    {
        truncate(0);
        seekArq(0);
        Registro reg = new Registro(0,"nome",20);
        for(int i=0;i<1024;i++)
        {
            reg.setCodigo((int) (Math.random() * 1500) + 1);
            reg.gravaNoArq(arquivo);
        }
    }
}
