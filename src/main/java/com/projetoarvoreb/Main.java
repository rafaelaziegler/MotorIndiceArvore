package com.projetoarvoreb;


import java.io.IOException;
import java.util.List;

/**
 * Fase 3 – Integração: escreve produtos no arquivo, constrói índice em Árvore-B
 * e realiza consultas rápidas por ID (O(log n)) seguidas de leitura direta no arquivo.
 */
public class Main {

    public static void main(String[] args) {
        String caminhoArquivo = "produtos.db";
        DatabaseFile db = new DatabaseFile(caminhoArquivo);

        // Árvore-B com grau mínimo t=3 (suporta até 5 chaves por nó)
        BTree indice = new BTree(3);

        try {
            // Para demonstração, começamos o arquivo limpo
            db.limparArquivo();

            // --- Inserções (simulando "escrita no disco" + "atualização do índice") ---
            List<Produto> produtos = List.of(
                    new Produto(10, "Teclado Mecânico ABNT2", 349.90),
                    new Produto(3,  "Mouse Óptico 1600dpi", 59.90),
                    new Produto(25, "Monitor 24\" IPS 75Hz", 799.00),
                    new Produto(1,  "SSD NVMe 1TB", 399.90),
                    new Produto(18, "Headset Gamer 7.1", 289.00),
                    new Produto(7,  "Cabo HDMI 2.1 2m", 39.99)
            );

            for (Produto p : produtos) {
                long pos = db.write(p);                 // grava registro e obtém ponteiro
                indice.insert(new IndexEntry(p.getId(), pos)); // atualiza índice
            }

            System.out.println("Índice B-Tree (em ordem):");
            indice.traverse();

            // --- Consultas: buscar por ID e ler diretamente no arquivo ---
            consultarProduto(db, indice, 1);
            consultarProduto(db, indice, 7);
            consultarProduto(db, indice, 25);
            consultarProduto(db, indice, 99); // inexistente

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }

    private static void consultarProduto(DatabaseFile db, BTree indice, int id) throws IOException {
        IndexEntry entry = indice.search(id);
        if (entry == null) {
            System.out.println("ID " + id + " não encontrado no índice.");
            return;
        }
        Produto p = db.read(entry.address());
        System.out.println("Encontrado via índice → " + p);
    }
}
