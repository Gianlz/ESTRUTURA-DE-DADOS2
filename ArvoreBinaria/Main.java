package ArvoreBinaria;

public class Main {
    public static void main(String[] args) {
        Arvore arvore = new Arvore();

        arvore.inserir(8);
        arvore.inserir(3);
        arvore.inserir(1);
        arvore.inserir(6);
        arvore.inserir(10);

        // arvore.remover(8);
        // arvore.remover(3);

//        System.out.println("Árvore em ordem:");
//        arvore.mostrarEmOrdem();

//        System.out.println("\nÁrvore por nível:");
//        arvore.mostrarPorNivel();
//
//        System.out.println("\nÁrvore em ordem decrescente:");
//        arvore.mostrarEmDecrescente();
//
//        arvore.mostrarMaiorNumero();
//        arvore.mostrarMenorNumero();
//

//        arvore.mostrarNosFolhas();

//        arvore.mostrarAncestrais(6);
//        arvore.mostrarDescendentes(1);
//        arvore.mostrarSubArvoreDireita(8);
//        arvore.mostrarSubArvoreEsquerda(8);
//

//        arvore.mostrarListaEncadeada();
//

//        arvore.mostrarNumerosPares();
//
//        System.out.println("Nível do nó 8: " + arvore.encontrarNivel(8));
//
//        System.out.println("Altura da árvore: " + arvore.calcularAltura());
//
//        System.out.println("Tamanho da árvore: " + arvore.calcularTamanho());
//
        arvore.inserirNaoRecursivo(26);
//
        System.out.println("\nÁrvore em ordem após inserção não recursiva:");
        arvore.mostrarEmOrdem();
    }
}
