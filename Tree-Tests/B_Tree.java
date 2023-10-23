package EstruturaDeArvore;

public class B_Tree implements Arvore {
    private static class Nodo {
        int[] chaves;
        int t;
        Nodo[] filhos;
        int n;
        boolean folha;

        public Nodo(int t, boolean folha) {
            this.t = t;
            this.folha = folha;
            this.chaves = new int[2*t - 1];
            this.filhos = new Nodo[2*t];
            this.n = 0;
        }

        private void Percorrer() {
            int i = 0;
            for (i = 0; i < this.n; i++) {
                if (this.folha == false) {
                    this.filhos[i].Percorrer();
                }
                System.out.print(this.chaves[i] + " ");
            }

            if (this.folha == false) {
                this.filhos[i].Percorrer();
            }
        }

        private void divideFilho(int i, Nodo y) {
            Nodo z = new Nodo(y.t, y.folha);
            z.n = t - 1;
            for (int j = 0; j < t-1; j++) {
                z.chaves[j] = y.chaves[j+t];
            }
            if (y.folha == false) {
                for (int j = 0; j < t; j++) {
                    z.filhos[j] = y.filhos[j+t];
                }
            }
            y.n = t - 1;
            for (int j = n; j >= i+1; j--) {
                filhos[j+1] = filhos[j];
            }
            filhos[i+1] = z;
            for (int j = n-1; j >= i; j--) {
                chaves[j+1] = chaves[j];
            }
            chaves[i] = y.chaves[t-1];
            n = n + 1;
        }

        private void inserirImcompleto(int k) {
            int i = n - 1;
            if (folha == true) {
                while (i >= 0 && chaves[i] > k) {
                    chaves[i+1] = chaves[i];
                    i--;
                }
                chaves[i+1] = k;
                n = n + 1;
            } else {
                while (i >= 0 && chaves[i] > k) {
                    i--;
                }
                if (filhos[i+1].n == 2*t - 1) {
                    divideFilho(i+1, filhos[i+1]);
                    if (chaves[i+1] < k) {
                        i++;
                    }
                }
                filhos[i+1].inserirImcompleto(k);
            }
        }

        private void remover(int k) {
            int idx = encontraChave(k);

            if (idx < n && chaves[idx] == k) {
                if (folha) {
                    removeDaFolha(idx);
                } else {
                    removeForaFolha(idx);
                }
            } else {
                boolean flag = ((idx==n)? true : false);

                if (filhos[idx].n < t) {
                    preencher(idx);
                }

                if (flag && idx > n) {
                    filhos[idx-1].remover(k);
                } else {
                    filhos[idx].remover(k);
                }
            }
        }

        private void removeDaFolha(int idx) {

            for (int i=idx+1; i<n; ++i)
                chaves[i-1] = chaves[i];

            n--;
        }

        private void removeForaFolha(int idx) {
            int k = chaves[idx];

            if (filhos[idx].n >= t) {
                int pred = getAnterior(idx);
                chaves[idx] = pred;
                filhos[idx].remover(pred);
            }

            else if (filhos[idx+1].n >= t) {
                int sucessor = getSucessor(idx);
                chaves[idx] = sucessor;
                filhos[idx+1].remover(sucessor);
            }

            else {
                merge(idx);
                filhos[idx].remover(k);
            }
        }

        private void preencher(int idx) {

            if (idx != 0 && filhos[idx-1].n >= t)
                EmprestaAnterior(idx);
            else if (idx != n && filhos[idx+1].n >= t)
                EmprestaProximo(idx);
            else {
                if (idx != n)
                    merge(idx);
                else
                    merge(idx-1);
            }
        }

        private int getAnterior(int idx) {
            Nodo atual = filhos[idx];
            while (!atual.folha)
                atual = atual.filhos[atual.n];
            return atual.chaves[atual.n - 1];
        }

        private int getSucessor(int idx) {
            Nodo atual = filhos[idx + 1];
            while (!atual.folha)
                atual = atual.filhos[0];
            return atual.chaves[0];
        }

        private int encontraChave(int k) {
            int idx = 0;
            while (idx < n && chaves[idx] < k)
                ++idx;
            return idx;
        }

        private void merge(int idx) {
            Nodo filho = filhos[idx];
            Nodo irmao = filhos[idx+1];

            filho.chaves[t-1] = chaves[idx];

            for (int i=0; i<irmao.n; ++i)
                filho.chaves[i+t] = irmao.chaves[i];

            if (!filho.folha) {
                for(int i=0; i<=irmao.n; ++i)
                    filho.filhos[i+t] = irmao.filhos[i];
            }

            for (int i=idx+1; i<n; ++i)
                chaves[i-1] = chaves[i];

            for (int i=idx+2; i<=n; ++i)
                filhos[i-1] = filhos[i];

            filho.n += irmao.n+1;
            n--;

            irmao = null;
        }

        private void EmprestaAnterior(int idx) {
            Nodo filho = filhos[idx];
            Nodo irmao = filhos[idx-1];

            for (int i=filho.n-1; i>=0; --i)
                filho.chaves[i+1] = filho.chaves[i];

            if (!filho.folha) {
                for(int i=filho.n; i>=0; --i)
                    filho.filhos[i+1] = filho.filhos[i];
            }

            filho.chaves[0] = chaves[idx-1];

            if (!filho.folha)
                filho.filhos[0] = irmao.filhos[irmao.n];

            chaves[idx-1] = irmao.chaves[irmao.n-1];

            filho.n += 1;
            irmao.n -= 1;
        }

        private void EmprestaProximo(int idx) {
            Nodo filho = filhos[idx];
            Nodo irmao = filhos[idx+1];

            filho.chaves[filho.n] = chaves[idx];

            if (!(filho.folha))
                filho.filhos[(filho.n)+1] = irmao.filhos[0];

            chaves[idx] = irmao.chaves[0];

            for (int i=1; i<irmao.n; ++i)
                irmao.chaves[i-1] = irmao.chaves[i];

            if (!irmao.folha) {
                for(int i=1; i<=irmao.n; ++i)
                    irmao.filhos[i-1] = irmao.filhos[i];
            }

            filho.n += 1;
            irmao.n -= 1;
        }

        private boolean buscarChave(int k) {
            int idx = 0;
            while (idx < n && chaves[idx] < k) {
                idx++;
            }
            if (idx < n && chaves[idx] == k) { // Ensure idx is within bounds of array
                return true;
            }
            if (folha) {
                return false;
            }
            return (idx < n) ? filhos[idx].buscarChave(k) : false; // Ensure idx is within bounds of array
        }


    }

    private Nodo root;
    private int t;

    public B_Tree(int t) {
        this.root = null;
        this.t = t;
    }

    public void traverse() {
        if (this.root != null) {
            this.root.Percorrer();
        }
        System.out.println();
    }

    public void inserir(int k) {
        if (this.root == null) {
            this.root = new Nodo(this.t, true);
            this.root.chaves[0] = k;
            this.root.n = 1;
        } else {
            if (this.root.n == 2*this.t - 1) {
                Nodo s = new Nodo(this.t, false);
                s.filhos[0] = this.root;
                s.divideFilho(0, this.root);
                int i = 0;
                if (s.chaves[0] < k) {
                    i++;
                }
                s.filhos[i].inserirImcompleto(k);
                this.root = s;
            } else {
                root.inserirImcompleto(k);
            }
        }
    }

    public void remover(int k) {
        if (root == null) {
            System.out.println("A arvore esta vazia");
            return;
        }

        root.remover(k);

        if (root.n==0) {
            Nodo tmp = root;
            if (root.folha) {
                root = null;
            } else {
                root = root.filhos[0];
            }
        }
    }

    public boolean buscar(int k) {
        if (root == null) {
            return false;
        }
        return root.buscarChave(k);
    }

}