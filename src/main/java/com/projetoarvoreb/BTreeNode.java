package com.projetoarvoreb;

/**
 * Nó de Árvore-B adaptado para armazenar IndexEntry.
 * Implementação clássica baseada em CLRS, usando mínimo grau t.
 */
class BTreeNode {

    final int t;                // grau mínimo
    int n;                      // número atual de chaves
    boolean leaf;               // é folha?
    IndexEntry[] keys;          // tamanho máximo 2t-1
    BTreeNode[] C;              // filhos (2t)

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new IndexEntry[2 * t - 1];
        this.C = new BTreeNode[2 * t];
        this.n = 0;
    }

    /** Percorre e imprime as chaves (debug). */
    void traverse() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf) C[i].traverse();
            System.out.print(" " + keys[i].key());
        }
        if (!leaf) C[i].traverse();
    }

    /** Busca por uma key k e retorna o IndexEntry (ou null). */
    IndexEntry search(int k) {
        int i = 0;
        while (i < n && k > keys[i].key()) i++;
        if (i < n && k == keys[i].key()) return keys[i];
        if (leaf) return null;
        return C[i].search(k);
    }

    /** Insere uma nova entrada em um nó não cheio. */
    void insertNonFull(IndexEntry entry) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && keys[i].key() > entry.key()) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = entry;
            n = n + 1;
        } else {
            while (i >= 0 && keys[i].key() > entry.key()) i--;
            i++;
            if (C[i].n == 2 * t - 1) {
                splitChild(i, C[i]);
                if (keys[i].key() < entry.key()) i++;
            }
            C[i].insertNonFull(entry);
        }
    }

    /** Divide o filho y (cheio) deste nó. */
    void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        // move t-1 chaves de y para z
        for (int j = 0; j < t - 1; j++) z.keys[j] = y.keys[j + t];

        // se não for folha, move os filhos
        if (!y.leaf) for (int j = 0; j < t; j++) z.C[j] = y.C[j + t];

        y.n = t - 1;

        // abre espaço para novo filho
        for (int j = n; j >= i + 1; j--) C[j + 1] = C[j];
        C[i + 1] = z;

        // move chaves para abrir espaço ao mediano
        for (int j = n - 1; j >= i; j--) keys[j + 1] = keys[j];
        keys[i] = y.keys[t - 1];  // sobe mediana

        n = n + 1;
    }
}
