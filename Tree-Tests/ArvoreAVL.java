package EstruturaDeArvore;
public class ArvoreAVL implements Arvore{

    private static class Nodo {
        private int dado;
        private int contagem;
        private int altd, alte;
        private Nodo dir, esq;

        public Nodo(int dado) {
            this.dado = dado;
            this.contagem = 1;
            dir = esq = null;
            altd = alte = 0;
        }
    }

    Nodo raiz;

    public ArvoreAVL() {
        raiz = null;
    }

    public void inserir(int dado) {
        Nodo atual = raiz;

        while (true) {
            if (atual == null) {
                break;
            }
            if (dado < atual.dado) {
                atual = atual.esq;
            } else if (dado > atual.dado) {
                atual = atual.dir;
            } else {
                // Chave duplicada, incrementar contagem
                atual.contagem++;
                break;
            }
        }


        // Atualizar alturas e balanceamento
        atual = raiz;
        while (atual != null) {
            atual.alte = altura(atual.esq);
            atual.altd = altura(atual.dir);
            atual = balanceamento(atual);
        }
    }


    private Nodo balanceamento(Nodo raiz) {
        int fb = raiz.altd - raiz.alte;
        int fbSubArv;
        if (fb == 2) {
            fbSubArv = raiz.dir.altd - raiz.dir.alte;
            if (fbSubArv >= 0) {

                raiz = rotacao_esquerda(raiz);
            } else {
                raiz.dir = rotacao_direita(raiz.dir);
                raiz = rotacao_esquerda(raiz);
            }
        } else if (fb == -2) {
            fbSubArv = raiz.esq.altd - raiz.esq.alte;
            if (fbSubArv <= 0) {
                raiz = rotacao_direita(raiz);
            } else {
                raiz.esq = rotacao_esquerda(raiz.esq);
                raiz = rotacao_direita(raiz);
            }
        }
        return raiz;
    }

    private Nodo rotacao_esquerda(Nodo raiz) {
        Nodo aux1, aux2;
        aux1 = raiz.dir;
        aux2 = aux1.esq;
        raiz.dir = aux2;
        aux1.esq = raiz;
        if (raiz.dir == null) {
            raiz.altd = 0;
        } else if (raiz.dir.alte > raiz.dir.altd) {
            raiz.altd = raiz.dir.alte + 1;


        } else {
            raiz.altd = raiz.dir.altd + 1;
        }
        if (aux1.esq.alte > aux1.esq.altd) {
            aux1.alte = aux1.esq.alte + 1;
        } else {
            aux1.alte = aux1.esq.altd + 1;
        }
        return aux1;
    }

    private Nodo rotacao_direita(Nodo raiz) {
        Nodo aux1, aux2;
        aux1 = raiz.esq;
        aux2 = aux1.dir;
        raiz.esq = aux2;
        aux1.dir = raiz;
        if (raiz.esq == null) {
            raiz.alte = 0;
        } else if (raiz.esq.alte > raiz.esq.altd) {
            raiz.alte = raiz.esq.alte + 1;
        } else {
            raiz.alte = raiz.esq.altd + 1;
        }
        if (aux1.dir.alte > aux1.dir.altd) {
            aux1.altd = aux1.dir.alte + 1;
        } else {


            aux1.altd = aux1.dir.altd + 1;
        }
        return aux1;
    }

    public void mostrarEmOrdem() {
        mostrandoOrdenado(raiz);
    }

    private void mostrandoOrdenado(Nodo raiz) {
        if (raiz != null) {
            mostrandoOrdenado(raiz.esq);
            for (int i = 0; i < raiz.contagem; i++) {
                System.out.println(raiz.dado);
            }
            mostrandoOrdenado(raiz.dir);
        }
    }

    public void remover(int dado) {
        raiz = removerNodo(raiz, dado);
    }

    private Nodo removerNodo(Nodo raiz, int dado) {
        if (raiz == null) {
            return null;
        }

        if (dado < raiz.dado) {
            raiz.esq = removerNodo(raiz.esq, dado);
        } else if (dado > raiz.dado) {
            raiz.dir = removerNodo(raiz.dir, dado);
        } else {
            // Chave encontrada
            if (raiz.contagem > 1) {
                // Se há mais de uma ocorrência, apenas decrementar a contagem
                raiz.contagem--;
            } else {
                // Se há apenas uma ocorrência, remover o nó
                if (raiz.esq == null) {
                    return raiz.dir;
                } else if (raiz.dir == null) {
                    return raiz.esq;
                }
                Nodo temp = menorNodo(raiz.dir);
                raiz.dado = temp.dado;
                raiz.contagem = temp.contagem;
                raiz.dir = removerNodo(raiz.dir, temp.dado);
            }
        }

        // Atualizar alturas e balanceamento
        raiz.alte = altura(raiz.esq);
        raiz.altd = altura(raiz.dir);
        raiz = balanceamento(raiz);

        return raiz;
    }

    private Nodo menorNodo(Nodo nodo) {
        Nodo atual = nodo;
        while (atual.esq != null) {
            atual = atual.esq;
        }
        return atual;
    }

    private int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(altura(nodo.esq), altura(nodo.dir));
    }

    public boolean VerificarAVL() {
        return VerificarAVL(raiz);
    }

    private boolean VerificarAVL(Nodo raiz) {
        if (raiz == null) {
            return true;
        }

        int fb = raiz.altd - raiz.alte;

        if (Math.abs(fb) > 1) {
            return false;
        }

        return VerificarAVL(raiz.esq) && VerificarAVL(raiz.dir);
    }

    public int ContarPrimo() {
        return Contar(raiz);
    }

    private int Contar(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }

        int contagem = 0;

        if (Primo(raiz.dado)) {
            contagem++;
        }

        contagem += Contar(raiz.esq);
        contagem += Contar(raiz.dir);

        return contagem;
    }

    private boolean Primo(int num) {
        if (num <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public void mostrarNivel(int nivel) {
        mostrarNivel(raiz, nivel, 1);
    }

    private void mostrarNivel(Nodo raiz, int nivel, int nivelAtual) {
        if (raiz == null) {
            return;
        }

        if (nivelAtual == nivel) {
            System.out.print(raiz.dado+ " | ");
        } else if (nivelAtual < nivel) {
            mostrarNivel(raiz.esq, nivel, nivelAtual + 1);
            mostrarNivel(raiz.dir, nivel, nivelAtual + 1);
        }
    }

    public int somaImpar() {
        return somaImpar(raiz, 1);
    }

    private int somaImpar(Nodo raiz, int nivelAtual) {
        if (raiz == null) {
            return 0;
        }

        int soma = 0;

        if (nivelAtual % 2 != 0) {
            soma += raiz.dado;
        }

        soma += somaImpar(raiz.esq, nivelAtual + 1);
        soma += somaImpar(raiz.dir, nivelAtual + 1);

        return soma;
    }

    public boolean buscar(int dado) {
        return buscarDado(raiz, dado);
    }

    private boolean buscarDado(Nodo raiz, int dado) {
        if (raiz == null) {
            return false;
        }
        if (dado == raiz.dado) {
            return true;
        }
        if (dado < raiz.dado) {
            return buscarDado(raiz.esq, dado);
        }
        return buscarDado(raiz.dir, dado);
    }


}

