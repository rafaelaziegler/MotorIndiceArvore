package com.projetoarvoreb;

/** Implementação da Árvore-B usando BTreeNode para IndexEntry. */
public class BTree {

    private BTreeNode root;
    private final int t; // grau mínimo

    public BTree(int t) {
        if (t < 2) throw new IllegalArgumentException("t deve ser >= 2");
        this.t = t;
        this.root = null;
    }

    /** Para debug. */
    public void traverse() {
        if (root != null) root.traverse();
        System.out.println();
    }

    /** Busca por chave k e retorna o IndexEntry correspondente. */
    public IndexEntry search(int k) {
        return (root == null) ? null : root.search(k);
    }

    /** Insere um IndexEntry no índice. */
    public void insert(IndexEntry entry) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = entry;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.C[0] = root;
                s.splitChild(0, root);

                int i = 0;
                if (s.keys[0].key() < entry.key()) i++;
                s.C[i].insertNonFull(entry);
                root = s;
            } else {
                root.insertNonFull(entry);
            }
        }
    }
}
