public class Main {
    public static void main(String[] args) {
        Grafo grafoExemplo = new Grafo();
        grafoExemplo.adicionarVertice("A");
        grafoExemplo.adicionarVertice("B");
        grafoExemplo.adicionarVertice("C");
        grafoExemplo.adicionarAresta("A", "B", 1);
        grafoExemplo.adicionarAresta("B", "C", 2);

        System.out.println("Grafo Original:");
        System.out.println(grafoExemplo.grafo);

        // d) Algoritmo de Dijkstra
        System.out.println("\nDistâncias mínimas a partir do vértice A:");
        System.out.println(grafoExemplo.dijkstra("A"));

        // e) Verificar se o grafo é euleriano, semieuleriano ou não euleriano
        System.out.println("\nO grafo é " + grafoExemplo.verificarEuleriano());

        // f) Verificar se o grafo é hamiltoniano, semi-hamiltoniano ou não hamiltoniano
        System.out.println("O grafo é " + grafoExemplo.verificarHamiltoniano());

        // Verificar se o grafo é completo
        System.out.println("O grafo é completo? " + grafoExemplo.isCompleto());

        // Verificar se o grafo é conexo
        System.out.println("O grafo é conexo? " + grafoExemplo.isConexo());

        // Remover um vértice do grafo
        grafoExemplo.removerVertice("C");
        System.out.println("Grafo após remover o vértice C:");
        System.out.println(grafoExemplo.grafo);
    }
}