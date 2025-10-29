package com.projetoarvoreb;

/**
 * Par (key, address) usado pelo índice.
 * key: ID do produto
 * address: posição (ponteiro) no arquivo produtos.db
 */
public class IndexEntry implements Comparable<IndexEntry> {

    private final int key;
    private final long address;

    public IndexEntry(int key, long address) {
        this.key = key;
        this.address = address;
    }

    public int key() { return key; }
    public long address() { return address; }

    @Override
    public int compareTo(IndexEntry o) {
        return Integer.compare(this.key, o.key);
    }

    @Override
    public String toString() {
        return "(" + key + " -> " + address + ")";
    }
}
