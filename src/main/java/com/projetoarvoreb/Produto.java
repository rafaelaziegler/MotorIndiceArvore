package com.projetoarvoreb;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Registro de tamanho fixo = 100 bytes
 *  - id:    4 bytes (int)
 *  - nome: 88 bytes (String codificada em ISO-8859-1, preenchida com espaços)
 *  - preco: 8 bytes (double)
 */
public class Produto {

    public static final int REGISTRO_BYTES = 100;
    private static final int NOME_BYTES = 88;
    private static final Charset CODIF = StandardCharsets.ISO_8859_1;

    private int id;
    private String nome;
    private double preco;

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = Objects.requireNonNullElse(nome, "");
        this.preco = preco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }

    public void setNome(String nome) { this.nome = Objects.requireNonNullElse(nome, ""); }
    public void setPreco(double preco) { this.preco = preco; }

    /** Converte o objeto para array de bytes de tamanho fixo (100 bytes). */
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(REGISTRO_BYTES);

        // id (4)
        buffer.putInt(id);

        // nome (88) – codifica, corta/padding para tamanho fixo
        byte[] nomeBytes = nome.getBytes(CODIF);
        if (nomeBytes.length > NOME_BYTES) {
            // trunca se ultrapassar
            buffer.put(nomeBytes, 0, NOME_BYTES);
        } else {
            buffer.put(nomeBytes);
            // completa com espaços (0x20) até 88 bytes
            for (int i = nomeBytes.length; i < NOME_BYTES; i++) buffer.put((byte) 0x20);
        }

        // preco (8)
        buffer.putDouble(preco);

        return buffer.array();
    }

    /** Reconstrói um Produto a partir de 100 bytes. */
    public static Produto fromBytes(byte[] bytes) {
        if (bytes == null || bytes.length != REGISTRO_BYTES) {
            throw new IllegalArgumentException("Registro inválido: esperado " + REGISTRO_BYTES + " bytes.");
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int id = buffer.getInt();

        byte[] nomeBytes = new byte[NOME_BYTES];
        buffer.get(nomeBytes);
        String nome = new String(nomeBytes, CODIF).trim();

        double preco = buffer.getDouble();
        return new Produto(id, nome, preco);
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', preco=" + preco + "}";
    }
}
