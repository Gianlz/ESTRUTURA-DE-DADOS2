package ArvoreBinaria;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Arvore {
    private static class Nodo {
        private int chave;
        private Nodo dir, esq;

        public Nodo(int item) {
            this.chave = item;
            dir = esq = null;
        }
    }

    Nodo raiz = null;

    public void inserir(int chave) {
        raiz = inserirDado(raiz, chave);
    }

    private Nodo inserirDado(Nodo raiz, int chave) {
        if (raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }
        if (chave < raiz.chave) {
            raiz.esq = inserirDado(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            raiz.dir = inserirDado(raiz.dir, chave);
        }
        return raiz;
    }

    public void mostrarEmOrdem() {
        mostrarOrdenado(raiz);
    }

    private void mostrarOrdenado(Nodo raiz) {
        if (raiz != null) {
            mostrarOrdenado(raiz.esq);
            System.out.print(raiz.chave + "| ");
            mostrarOrdenado(raiz.dir);
        }
    }

    public void mostrarPorNivel() {
        if (raiz == null) {
            System.out.println("Árvore vazia!");
            return;
        }
        Queue<Nodo> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            for (int i = 0; i < tamanhoNivel; i++) {
                Nodo nodoAtual = fila.poll();
                if (nodoAtual != null) {
                    System.out.print(nodoAtual.chave + " ");
                    fila.add(nodoAtual.esq);
                    fila.add(nodoAtual.dir);
                } else {
                    System.out.print("-");
                }
            }
        }
        System.out.println();
    }

    public void mostrarEmDecrescente() {
        mostrarDecrescente(raiz);
        System.out.println();
    }

    private void mostrarDecrescente(Nodo nodo) {
        if (nodo != null) {
            mostrarDecrescente(nodo.dir);
            System.out.print(nodo.chave + "| ");
            mostrarDecrescente(nodo.esq);
        }
    }


    public void remover(int chave) {
        raiz = removerItem(raiz, chave);
    }

    private Nodo removerItem(Nodo raiz, int chave) {
        if (raiz == null) {
            return null; // Nó não encontrado, não faz nada
        }
        if (chave < raiz.chave) {
            raiz.esq = removerItem(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            raiz.dir = removerItem(raiz.dir, chave);
        } else {
            if (raiz.esq == null) {
                return raiz.dir; // Caso sem filho à esquerda
            } else if (raiz.dir == null) {
                return raiz.esq; // Caso sem filho à direita
            } else {
                Nodo sucessor = encontrarSucessor(raiz.dir); // Nó sucessor é o menor da subárvore à direita
                raiz.chave = sucessor.chave;
                raiz.dir = removerItem(raiz.dir, sucessor.chave);
            }
        }
        return raiz;
    }

    private Nodo encontrarSucessor(Nodo nodo) {
        while (nodo.esq != null) {
            nodo = nodo.esq;
        }
        return nodo;
    }

    private Nodo encontrarMaior(Nodo nodo) {
        if (nodo == null) {
            return null;
        }

        while (nodo.dir != null) {
            nodo = nodo.dir;
        }
        return nodo;
    }

    private Nodo encontrarMenor(Nodo nodo) {
        if (nodo == null) {
            return null;
        }

        while (nodo.esq != null) {
            nodo = nodo.esq;
        }
        return nodo;
    }

    public void mostrarMaiorNumero() {
        if (raiz == null) {
            System.out.println("Árvore vazia");
            return;
        }

        Nodo maior = encontrarMaior(raiz);
        System.out.println("Maior número: " + maior.chave);
    }

    public void mostrarMenorNumero() {
        if (raiz == null) {
            System.out.println("Árvore vazia");
            return;
        }

        Nodo menor = encontrarMenor(raiz);
        System.out.println("Menor número: " + menor.chave);
    }





    public void mostrarNosFolhas() {
        System.out.println("As folhas são:");
        mostrarNosFolhas(raiz);
    }

    private void mostrarNosFolhas(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        if (nodo.esq == null && nodo.dir == null) {
            System.out.print(nodo.chave + " | ");
        }
        mostrarNosFolhas(nodo.esq);
        mostrarNosFolhas(nodo.dir);
    }

    public void mostrarAncestrais(int chave) {
        List<Integer> ancestrais = new ArrayList<>();
        if (!encontrarAncestrais(raiz, chave, ancestrais)) {
            System.out.println("Nenhum ancestral");
        } else {
            System.out.println("Os nós ancestrais de " + chave + " são " + ancestrais);
        }
    }

    private boolean encontrarAncestrais(Nodo nodo, int chave, List<Integer> ancestrais) {
        if (nodo == null) {
            return false;
        }
        if (nodo.chave == chave) {
            return true;
        }
        if (encontrarAncestrais(nodo.esq, chave, ancestrais) || encontrarAncestrais(nodo.dir, chave, ancestrais)) {
            ancestrais.add(nodo.chave);
            return true;
        }
        return false;
    }

    public void mostrarDescendentes(int chave) {
        List<Integer> descendentes = new ArrayList<>();
        if (!encontrarEDescendentes(raiz, chave, descendentes)) {
            System.out.println("Nenhum descendente");
        } else {
            System.out.println("Os nós descendentes de " + chave + " são " + descendentes);
        }
    }

    private boolean encontrarEDescendentes(Nodo nodo, int chave, List<Integer> descendentes) {
        if (nodo != null) {
            if (nodo.chave == chave) {
                adicionarDescendentes(nodo, descendentes);
                return true;
            } else {
                return encontrarEDescendentes(nodo.esq, chave, descendentes) ||
                        encontrarEDescendentes(nodo.dir, chave, descendentes);
            }
        }
        return false;
    }

    private void adicionarDescendentes(Nodo nodo, List<Integer> descendentes) {
        if (nodo != null) {
            descendentes.add(nodo.chave);
            adicionarDescendentes(nodo.esq, descendentes);
            adicionarDescendentes(nodo.dir, descendentes);
        }
    }

    public void mostrarSubArvoreDireita(int chave) {
        List<Integer> nos = new ArrayList<>();
        Nodo nodoInicial = encontrarNodo(raiz, chave);
        if (nodoInicial == null || nodoInicial.dir == null) {
            System.out.println("Nenhuma subárvore encontrada");
        } else {
            adicionarNodos(nodoInicial.dir, nos);
            System.out.println("A subárvore direita da chave " + chave + " é " + nos);
        }
    }

    public void mostrarSubArvoreEsquerda(int chave) {
        List<Integer> nos = new ArrayList<>();
        Nodo nodoInicial = encontrarNodo(raiz, chave);
        if (nodoInicial == null || nodoInicial.esq == null) {
            System.out.println("Nenhuma subárvore encontrada");
        } else {
            adicionarNodos(nodoInicial.esq, nos);
            System.out.println("A subárvore esquerda da chave " + chave + " é " + nos);
        }
    }

    private Nodo encontrarNodo(Nodo nodo, int chave) {
        if (nodo == null) {
            return null;
        }
        if (nodo.chave == chave) {
            return nodo;
        }
        Nodo esquerda = encontrarNodo(nodo.esq, chave);
        if (esquerda != null) {
            return esquerda;
        }
        return encontrarNodo(nodo.dir, chave);
    }

    private void adicionarNodos(Nodo nodo, List<Integer> nos) {
        if (nodo != null) {
            nos.add(nodo.chave);
            adicionarNodos(nodo.esq, nos);
            adicionarNodos(nodo.dir, nos);
        }
    }

    public void mostrarListaEncadeada() {
        Nodo currentNode = raiz;
        StringBuilder sb = new StringBuilder();
        transformarEmListaEncadeada(currentNode, sb);
        System.out.println("Lista encadeada: " + sb.toString());
    }

    private void transformarEmListaEncadeada(Nodo currentNode, StringBuilder sb) {
        if (currentNode != null) {
            transformarEmListaEncadeada(currentNode.esq, sb);
            sb.append(currentNode.chave).append(" | ");
            transformarEmListaEncadeada(currentNode.dir, sb);
        }
    }

    public void mostrarNumerosPares() {
        System.out.println("Números pares:");
        mostrarNumerosPares(raiz);
        System.out.println();
    }

    private void mostrarNumerosPares(Nodo nodo) {
        if (nodo != null) {
            mostrarNumerosPares(nodo.esq);
            if (nodo.chave % 2 == 0) {
                System.out.print(nodo.chave + " ");
            }
            mostrarNumerosPares(nodo.dir);
        }
    }


    public int encontrarNivel(int chave) {
        return encontrarNivel(raiz, chave, 1);
    }

    private int encontrarNivel(Nodo currentNode, int chave, int nivel) {
        if (currentNode == null) {
            return -1; // Nó não encontrado
        }

        if (currentNode.chave == chave) {
            return nivel; // Nó encontrado
        }

        int nivelEsquerda = encontrarNivel(currentNode.esq, chave, nivel + 1);
        if (nivelEsquerda != -1) {
            return nivelEsquerda; // Nó encontrado na subárvore esquerda
        }

        int nivelDireita = encontrarNivel(currentNode.dir, chave, nivel + 1);
        if (nivelDireita != -1) {
            return nivelDireita; // Nó encontrado na subárvore direita
        }

        return -1; // Nó não encontrado na árvore
    }

    public int calcularAltura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(Nodo currentNode) {
        if (currentNode == null) {
            return 0; // Caso base: uma árvore vazia tem altura 0
        }

        int alturaEsquerda = calcularAltura(currentNode.esq);
        int alturaDireita = calcularAltura(currentNode.dir);

        return Math.max(alturaEsquerda, alturaDireita) + 1;
    }

    public int calcularTamanho() {
        return calcularTamanho(raiz);
    }

    private int calcularTamanho(Nodo currentNode) {
        if (currentNode == null) {
            return 0; // Caso base: uma árvore vazia tem tamanho 0
        }

        int tamanhoEsquerda = calcularTamanho(currentNode.esq);
        int tamanhoDireita = calcularTamanho(currentNode.dir);

        return tamanhoEsquerda + tamanhoDireita + 1;
    }

    public void inserirNaoRecursivo(int chave) {
        Nodo novoNodo = new Nodo(chave);

        if (raiz == null) {
            raiz = novoNodo; // Se a árvore estiver vazia, o novo nó se torna a raiz
            return;
        }

        Nodo currentNode = raiz;
        while (true) {
            if (chave < currentNode.chave) {
                if (currentNode.esq == null) {
                    currentNode.esq = novoNodo; // Insere o novo nó como filho à esquerda
                    return;
                }
                currentNode = currentNode.esq; // Move para a subárvore esquerda
            } else {
                if (currentNode.dir == null) {
                    currentNode.dir = novoNodo; // Insere o novo nó como filho à direita
                    return;
                }
                currentNode = currentNode.dir; // Move para a subárvore direita
            }
        }
    }
}
