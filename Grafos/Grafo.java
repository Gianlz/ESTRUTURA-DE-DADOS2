import java.util.*;

public class Grafo {
    Map<String, Map<String, Integer>> grafo = new HashMap<>();

    public void adicionarVertice(String vertice) {
        grafo.put(vertice, new HashMap<>());
    }

    public void adicionarAresta(String vertice1, String vertice2, int peso) {
        if (vertice1 != null && vertice2 != null) {
            grafo.get(vertice1).put(vertice2, peso);
            grafo.get(vertice2).put(vertice1, peso);
        }
    }

    public void removerVertice(String vertice) {
        Iterator<Map.Entry<String, Map<String, Integer>>> iterator = grafo.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<String, Integer>> entry = iterator.next();
            entry.getValue().remove(vertice);
            if (entry.getKey().equals(vertice)) {
                iterator.remove();
            }
        }
    }

    public boolean isConexo() {
        Set<String> visitados = new HashSet<>();
        String inicio = grafo.keySet().iterator().next();
        dfs(inicio, visitados);
        return visitados.size() == grafo.size();
    }

    private void dfs(String atual, Set<String> visitados) {
        visitados.add(atual);
        for (String vizinho : grafo.get(atual).keySet()) {
            if (!visitados.contains(vizinho)) dfs(vizinho, visitados);
        }
    }

    public boolean isCompleto() {
        int n = grafo.size();
        return grafo.values().stream().allMatch(vizinhos -> vizinhos.size() == n - 1);
    }

    /**
     *
     * @param  origem  o parâmetro de origem para a função
     * @return         um mapa contendo as distâncias da origem para cada vértice no grafo
     */
    public Map<String, OptionalInt> dijkstra(String origem) {
        Map<String, OptionalInt> distancias = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<Map.Entry<String, Integer>> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (String vertice : grafo.keySet()) {
            distancias.put(vertice, OptionalInt.empty());
        }

        distancias.put(origem, OptionalInt.of(0));
        filaPrioridade.add(new AbstractMap.SimpleEntry<>(origem, 0));

        while (!filaPrioridade.isEmpty()) {
            String verticeAtual = filaPrioridade.poll().getKey();

            if (!visitados.contains(verticeAtual)) {
                visitados.add(verticeAtual);

                for (Map.Entry<String, Integer> vizinho : grafo.get(verticeAtual).entrySet()) {
                    String verticeVizinho = vizinho.getKey();
                    int pesoAresta = vizinho.getValue();

                    int novaDistancia = distancias.get(verticeAtual).orElse(0) + pesoAresta;
                    if (novaDistancia < distancias.get(verticeVizinho).orElse(Integer.MAX_VALUE)) {
                        distancias.put(verticeVizinho, OptionalInt.of(novaDistancia));
                        filaPrioridade.add(new AbstractMap.SimpleEntry<>(verticeVizinho, novaDistancia));
                    }
                }
            }
        }

        return distancias;
    }

    public enum TipoGrafo {
        EULERIANO,
        SEMIEULERIANO,
        NAO_EULERIANO
    }


    public TipoGrafo verificarEuleriano() {
        int contagemGrauImpar = 0;

        for (Map<String, Integer> arestas : grafo.values()) {
            if (arestas.size() % 2 != 0) {
                contagemGrauImpar++;
            }
        }

        if (contagemGrauImpar == 0) {
            return TipoGrafo.EULERIANO;
        } else if (contagemGrauImpar == 2) {
            return TipoGrafo.SEMIEULERIANO;
        } else {
            return TipoGrafo.NAO_EULERIANO;
        }
    }



    public enum TipoGrafo1 {
        HAMILTONIANO,
        SEMI_HAMILTONIANO,
        NAO_HAMILTONIANO
    }


    /**
     * Esse método não abrange todos casos, mas todos dentro do possível.
     *
     */

    public TipoGrafo1 verificarHamiltoniano() {
        int n = grafo.size();

        if (n < 3) {
            return TipoGrafo1.NAO_HAMILTONIANO;
        }

        boolean isHamiltoniano = true;
        boolean isSemiHamiltoniano = false;

        for (String vertice : grafo.keySet()) {
            int degree = grafo.get(vertice).size();
            if (degree < n / 2) {
                isHamiltoniano = false;
            }
            if (degree == n - 1) {
                isSemiHamiltoniano = true;
            }
        }

        if (isHamiltoniano) {
            return TipoGrafo1.HAMILTONIANO;
        } else if (isSemiHamiltoniano) {
            return TipoGrafo1.SEMI_HAMILTONIANO;
        } else {
            return TipoGrafo1.NAO_HAMILTONIANO;
        }
    }
}
