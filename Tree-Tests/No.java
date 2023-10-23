package EstruturaDeArvore;

public class No {
    int chave;
    No esquerda, direita;

    public No(int chave) {
        this.chave = chave;
        this.esquerda = null;
        this.direita = null;
    }
}
