package com.projetoarvoreb;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Encapsula a interação com o arquivo binário produtos.db
 * usando RandomAccessFile para acesso aleatório.
 */
public class DatabaseFile {

    private final File arquivo;

    public DatabaseFile(String caminho) {
        this.arquivo = new File(caminho);
    }

    /** Apaga o conteúdo do arquivo (útil para demonstração). */
    public void limparArquivo() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "rw")) {
            raf.setLength(0);
        }
    }

    /**
     * Escreve um Produto no final do arquivo e retorna a posição (ponteiro)
     * do início do registro.
     */
    public long write(Produto p) throws IOException {
        byte[] bytes = p.toBytes();
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "rw")) {
            long pos = raf.length();      // posição atual do fim
            raf.seek(pos);                // vai para o fim
            raf.write(bytes);             // escreve 100 bytes
            return pos;                   // retorna ponteiro
        }
    }

    /**
     * Lê um Produto a partir de uma posição (ponteiro) previamente obtida.
     */
    public Produto read(long position) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(arquivo, "r")) {
            raf.seek(position);
            byte[] bytes = new byte[Produto.REGISTRO_BYTES];
            int lidos = raf.read(bytes);
            if (lidos != Produto.REGISTRO_BYTES) {
                throw new IOException("Não foi possível ler o registro completo em " + position);
            }
            return Produto.fromBytes(bytes);
        }
    }
}
