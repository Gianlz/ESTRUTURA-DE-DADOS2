package EstruturaDeArvore;


import java.util.Queue;
import java.util.LinkedList;

public class ArvoreBinaria implements Arvore {

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
        if (raiz == null) {
            raiz = new Nodo(chave);
            return;
        }

        Nodo atual = raiz;
        Nodo pai;

        while (true) {
            pai = atual;
            if (chave < atual.chave) {
                atual = atual.esq;
                if (atual == null) {
                    pai.esq = new Nodo(chave);
                    return;
                }
            } else if (chave > atual.chave) {
                atual = atual.dir;
                if (atual == null) {
                    pai.dir = new Nodo(chave);
                    return;
                }
            } else {
                return;
            }
        }
    }




    public void mostrarEmOrdem() {
        mostrarOrdenadoCrescente(raiz);
    }

    private void mostrarOrdenadoCrescente(Nodo raiz) {
        if (raiz != null) {
            mostrarOrdenadoCrescente(raiz.esq);
            System.out.print(raiz.chave + " | ");
            mostrarOrdenadoCrescente(raiz.dir);
        }
    }

    public void mostrarEmOrdemDecrescente() {
        mostrarDecrescente(raiz);
    }

    private void mostrarDecrescente(Nodo raiz) {
        if (raiz != null) {
            mostrarOrdenadoCrescente(raiz.dir);
            System.out.print(raiz.chave + " | ");
            mostrarOrdenadoCrescente(raiz.esq);
        }
    }

    public void mostrarPorNivel() {
        if (raiz == null) {
            System.out.println("Árvore vazia !");
            return;
        }

        Queue<Nodo> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            for (int i = 0; i < tamanhoNivel; i++) {
                Nodo nodoAtual = fila.poll();

                if (nodoAtual == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(nodoAtual.chave + " ");
                    fila.add(nodoAtual.esq);
                    fila.add(nodoAtual.dir);
                }
            }
            System.out.println();
        }
    }

    public void remover(int chave) {
        raiz = removerItem(raiz, chave);
    }

    private Nodo removerItem(Nodo raiz, int chave) {
        if (raiz == null) {
            return null;
        }
        if (chave < raiz.chave) {
            raiz.esq = removerItem(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            raiz.dir = removerItem(raiz.dir, chave);
        } else {
            if (raiz.esq == null) {
                return raiz.dir;
            } else if (raiz.dir == null) {
                return raiz.esq;
            } else {
                Nodo sucessor = encontrarSucessor(raiz.dir);
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

    public void mostrarMaiorNumero() {
        mostrarMaior(raiz);
    }

    private void mostrarMaior(Nodo raiz) {
        if (raiz != null) {
            mostrarMaior(raiz.dir);
            if (raiz.dir == null) {
                System.out.println(raiz.chave);
            }

        }
    }

    public void mostrarMenorNumero() {
        mostrarMenor(raiz);
    }

    private void mostrarMenor(Nodo raiz) {
        if (raiz != null) {
            mostrarMenor(raiz.esq);
            if (raiz.esq == null) {
                System.out.println(raiz.chave);
            }

        }
    }

    public void mostrarFolhas() {
        mostrarFolha(raiz);
    }

    private void mostrarFolha(Nodo raiz) {
        if (raiz != null) {
            mostrarFolha(raiz.esq);
            mostrarFolha(raiz.dir);
            if (raiz.esq == null) {
                if (raiz.dir == null) {
                    System.out.println(raiz.chave);
                }
            }
        }
    }

    public void mostrarAncestral() {
        mostrarAncestrais(raiz);
    }

    private void mostrarAncestrais(Nodo raiz) {
        if (raiz != null) {
            mostrarFolha(raiz.esq);
            mostrarFolha(raiz.dir);
            if (raiz.esq == null) {
                if (raiz.dir == null) {
                    System.out.println(raiz.chave);
                }
            }
        }
    }

    public boolean buscar(int chave) {
        return buscarChave(chave);
    }

    private boolean buscarChave(int chave) {
        Nodo atual = raiz;
        while (atual != null) {
            if (chave == atual.chave) return true;
            if (chave < atual.chave) atual = atual.esq;
            else atual = atual.dir;
        }
        return false;
    }


}

