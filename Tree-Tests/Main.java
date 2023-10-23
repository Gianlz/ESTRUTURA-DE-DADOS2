package EstruturaDeArvore;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] tamanhosEntrada = { 100000, 1000000 };

        for (int tamanho : tamanhosEntrada) {
            int[] entrada_ordenada = generateSortedInput(tamanho);
            int[] entrada_aleatoria = generateRandomInput(tamanho);

            ArvoreAVL arvoreAVL = new ArvoreAVL();
            testarInsercaoERemocao("AVL Ordenada", tamanho, entrada_ordenada, arvoreAVL);
            testarInsercaoERemocao("AVL Aleatoria", tamanho, entrada_aleatoria, arvoreAVL);

            ArvoreBinaria arvoreBinaria = new ArvoreBinaria();
            testarInsercaoERemocao("Árvore Binária Ordenada", tamanho, entrada_ordenada, arvoreBinaria);
            testarInsercaoERemocao("Árvore Binária Aleatoria", tamanho, entrada_aleatoria, arvoreBinaria);

            B_Tree bTree = new B_Tree(8);
            testarInsercaoERemocao("B-Tree Ordenada", tamanho, entrada_ordenada, bTree);
            testarInsercaoERemocao("B-Tree Aleatoria", tamanho, entrada_aleatoria, bTree);
        }
    }

    public static void testarInsercaoERemocao(String estrutura, int tamanho, int[] entrada, Arvore arvore) {
        // Inserção
        long inicioInsercao = System.nanoTime();
        for (int i = 0; i < tamanho; i++) {
            arvore.inserir(entrada[i]);
        }
        long fimInsercao = System.nanoTime();
        double tempoInsercao = Math.round((fimInsercao - inicioInsercao) / 1000000.0);

        // Remoção
        long inicioRemocao = System.nanoTime();
        for (int i = 0; i < tamanho; i++) {
            arvore.remover(entrada[i]);
        }
        long fimRemocao = System.nanoTime();
        double tempoRemocao = Math.round((fimRemocao - inicioRemocao) / 1000000.0);

        System.out.println("Tempo de execução para " + estrutura + " (Tamanho " + tamanho + "):");
        System.out.println("Inserção: " + tempoInsercao + "ms");
        System.out.println("Remoção: " + tempoRemocao + "ms");
        System.out.println();
    }


    public static int[] generateRandomInput(int size) {
        int[] arr = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(Integer.MAX_VALUE);
        }

        return arr;
    }



    public static int[] generateSortedInput(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
}