import java.io.*;

public class Principal
{
    private double calculaCompInsDirOrd(int TL){
        return TL-1;
    }

    private double calculaCompInsDirRev(int TL){
        return ((Math.pow(TL, 2)) + (TL-2)) / 4;
    }

    private double calculaCompInsDirRand(int TL){
        return ((Math.pow(TL, 2)) + (TL-4)) / 4;
    }

    // Calcula movimentações

    private int calculaMovInsDirOrd(int TL){
        return 3 * (TL-1);
    }

    private double calculaMovInsDirRev(int TL){
        return ((Math.pow(TL, 2)) + (9 * TL) - 10) / 4;
    }

    private double calculaMovInsDirRand(int TL){
        return ((Math.pow(TL, 2)) + (3 * TL) - 4) / 2;
    }

    public void geraTabela() throws IOException{

        FileWriter arq = iniciaTabela();

        // Gera arquivos

        Arquivo_Java arqOrd = new Arquivo_Java("arqOrdenado.dat");
        Arquivo_Java arqRand = new Arquivo_Java("arqRandom.dat");
        Arquivo_Java arqRev = new Arquivo_Java("arqReverso.dat");
        Arquivo_Java auxRand = new Arquivo_Java("arqRandomicoAux.dat");
        Arquivo_Java auxRev = new Arquivo_Java("arqReversoAux.dat");


        arqOrd.geraOrdenado();
        arqRev.geraReverso();
        arqRand.geraRandom();


        insertionSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        bubbleSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        selectionSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        binaryInsertionSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        shakeSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        shellSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        combSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        gnomeSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        quickSortPivo(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        mergeSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        quickSortSemPivo(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        coutingSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        radixSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        heapSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        bucketSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        timSort(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);

        mergeSort2(arqOrd,arqRev,arqRand,arq,auxRand,auxRev);


        arq.close();
        exibirTabela();
    }

    public FileWriter iniciaTabela() throws IOException {
        FileWriter arq = new FileWriter("Tabela1.txt");

        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("__________________________________________________________________________________________________________________________________________________________________________________________________________________________%n");
        gravarArq.printf("|Métodos Ordenação | \t\t\tArquivo Ordenado\t\t\t | \t\t\tArquivo em Ordem Reversa\t\t\t | \t\t\tArquivo Randômico\t\t\t |%n");
        gravarArq.printf("_________________________________________________________________________________________________________________________________________________________________________________________________________________________|%n");
        gravarArq.printf("\t\t | Comp. Prog. *| Comp. Equa. #| Mov. Prog. +| Mov. Equa. -| Tempo | Comp. Prog. *| Comp. Equa. #| Mov. Prog. +| Mov. Equa. -| Tempo | Comp. Prog. *| Comp. Equa. #| Mov. Prog. +| Mov. Equa. -| Tempo |%n");
        gravarArq.printf("_________________________________________________________________________________________________________________________________________________________________________________________________________________________|%n");

        return arq;
    }

    private void gravaLinhaTabela(FileWriter arq, String metodo, int compOrd, int compRev, int compRand, int movOrd, int movRev, int movRand, double compEquaOrd, double compEquaRev,
                                  double compEquaRand, double movEquaOrd, double movEquaRev, double movEquaRand,int ttotalOrd, int ttotalRev, int ttotalRand) throws IOException{
        PrintWriter gravarArq = new PrintWriter(arq);

        gravarArq.printf("" + metodo + "    |\t" + String.format("%-6.6s", compOrd) + "\t  |\t" + String.format("%-6.6s", compEquaOrd) + "\t |      " + String.format("%-6.6s", movOrd) + " |      " + String.format("%-2.6s", movEquaOrd) + " |\t" + String.format("%-2.6s", ttotalOrd) + "   |\t    " + String.format("%-6.6s", compRev) + "  |\t  " + String.format("%-6.6s", compEquaRev) + "   |\t " + String.format("%-6.6s", movRev) + "  | \t" + String.format("%-6.6s", movEquaRev) + " |  " + String.format("%-2.6s", ttotalRev) + "   |     " + String.format("%-6.6s", compRand) + "   |\t  " + String.format("%-6.6s", compEquaRand) + "     |\t    " + String.format("%-6.6s", movRand) + " |\t " + String.format("%-6.6s", movEquaRand) + "  |  " + String.format("%-4.6s", ttotalRand) + " |%n");
        gravarArq.printf("_________________________________________________________________________________________________________________________________________________________________________________________________________________________|%n");
    }

    public void exibirTabela() throws FileNotFoundException, IOException{

        FileInputStream stream = new FileInputStream("Tabela1.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String linha = br.readLine();
        while(linha != null) {
            System.out.println(linha);
            linha = br.readLine();
        }
    }

    public void insertionSort(Arquivo_Java arqOrd,Arquivo_Java arqRev, Arquivo_Java arqRand,  FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Insertion");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.insertionSortArq();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.insertionSortArq();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.insertionSortArq();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Inserção Direta", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Insertion");
    }

    public void bubbleSort(Arquivo_Java arqOrd,Arquivo_Java arqRev, Arquivo_Java arqRand,  FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Bubble");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.bubbleSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.bubbleSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.bubbleSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Bubble Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Bubble");
    }

    public void selectionSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Selection");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.selectionSortArq();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.selectionSortArq();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.selectionSortArq();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Selection Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Selection");
    }

    public void binaryInsertionSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Binary Insertion");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.insetionSortBinFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;
        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.insetionSortBinFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.insetionSortBinFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Binary Insertion Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Binary Insertion");
    }

    public void shakeSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Shake");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.shakeSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;
        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.shakeSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.shakeSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Shake Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Shake");
    }

    public void shellSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Shell");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.shellSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;
        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.shellSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.shellSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Shell Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Shell");
    }

    public void combSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Comb");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.combSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;
        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.combSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.combSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Comb Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Comb");
    }

    public void gnomeSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Gnome");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.gnomeSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;
        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.gnomeSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.gnomeSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Gnome Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Gnome");
    }

    public void quickSortPivo(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Quick Pivo");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.quickSortPivo();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;
        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.quickSortPivo();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.quickSortPivo();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Quick Sort Pivo", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Quick Pivo");
    }

    public void mergeSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Merge 1");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.mergeSortFile1();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.mergeSortFile1();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.mergeSortFile1();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Merge Sort 1", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Merge 1");
    }

    public void quickSortSemPivo(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Quick Sem Pivo");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.quickSPFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        // arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.quickSPFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.quickSPFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Quick Sort Sem Pivo", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Quick Sem Pivo");
    }

    public void coutingSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Couting");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.countingSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.countingSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.countingSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Couting Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Couting");
    }

    public void radixSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Radix");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.radixSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.radixSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.radixSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Radix Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Radix");
    }

    private void heapSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Heap");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.heapSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.heapSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.heapSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Heap Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Heap");
    }
    public void bucketSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Bucket");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.bucketSort();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.bucketSort();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.bucketSort();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Bucket Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Bucket");
    }
    public void timSort(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Tim");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.timSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.timSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.timSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Tim Sort", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Tim");
    }

    public void mergeSort2(Arquivo_Java arqOrd, Arquivo_Java arqRev, Arquivo_Java arqRand, FileWriter arq, Arquivo_Java auxRand, Arquivo_Java auxRev) throws IOException {
        System.out.println("Inicio Merge 2");
        long tini, tfim;
        int compRand = 0, compRev = 0, compOrd = 0, movRand = 0, movRev = 0, movOrd = 0, ttotalRand = 0, ttotalRev = 0, ttotalOrd = 0;
        // Ordenada
        arqOrd.zeraComp();
        arqOrd.zeraMov();

        tini = System.currentTimeMillis();
        arqOrd.mergeSortFile();
        tfim = System.currentTimeMillis();
        ttotalOrd = (int)(tfim - tini) / 1000;

        //arqOrd.exibir();

        compOrd = arqOrd.getComparacao();
        movOrd = arqOrd.getMovimentacao();

        // Reversa
        arqRev.copiaArquivo(auxRev);
        auxRev.zeraComp();
        auxRev.zeraMov();

        tini = System.currentTimeMillis();
        auxRev.mergeSortFile();
        tfim = System.currentTimeMillis();
        ttotalRev = (int)(tfim - tini) / 1000;

        compRev = auxRev.getComparacao();
        movRev = auxRev.getMovimentacao();

        //auxRev.exibir();

        // Randômica
        arqRand.copiaArquivo(auxRand);
        auxRand.zeraComp();
        auxRand.zeraMov();

        tini = System.currentTimeMillis();
        auxRand.mergeSortFile();
        tfim = System.currentTimeMillis();
        ttotalRand = (int)(tfim - tini) / 1000;

        compRand = auxRand.getComparacao();
        movRand = auxRand.getMovimentacao();

        //auxRand.exibir();

        gravaLinhaTabela(arq, "Merge Sort 2", compOrd, compRev, compRand, movOrd, movRev, movRand, 0, 0,
                0, 0, 0, 0,
                ttotalOrd, ttotalRev, ttotalRand);
        System.out.println("Fim Merge 2");
    }
}