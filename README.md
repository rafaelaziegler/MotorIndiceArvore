# Motor de Banco de Dados com Indexação em Árvore-B

Projeto desenvolvido em **Java 17 (Maven)** para simular o funcionamento de um **motor de banco de dados** com armazenamento em arquivo binário e **indexação eficiente usando Árvore-B**.

---

## Descrição do Projeto
O sistema cria um pequeno **banco de dados simulado**, no qual:
- Cada produto é gravado em um **arquivo binário** (`produtos.db`) usando registros de **tamanho fixo (100 bytes)**.  
- As posições de cada produto no arquivo são **indexadas** em uma **Árvore-B** que armazena pares `(id → posição no arquivo)`.  
- As consultas de busca utilizam o índice para acessar diretamente o registro no arquivo, simulando uma busca otimizada igual a de bancos reais.

---

## Estrutura das Classes

| Classe | Função Principal |
|--------|------------------|
| **Produto.java** | Define o modelo de produto com `id`, `nome` e `preço`. Implementa métodos `toBytes()` e `fromBytes()` para leitura/escrita binária. |
| **DatabaseFile.java** | Gerencia o arquivo binário `produtos.db` usando `RandomAccessFile`, permitindo escrita e leitura direta por posição. |
| **IndexEntry.java** | Estrutura auxiliar do índice, armazenando o par `(idProduto, posição)` e implementando `Comparable`. |
| **BTreeNode.java** | Representa o nó da Árvore-B, com operações de inserção e divisão de nós. |
| **BTree.java** | Implementa a lógica da Árvore-B (inserção, busca e travessia). |
| **Main.java** | Integra todas as classes, gravando produtos, construindo o índice e realizando buscas rápidas pelo ID. |

---

## Execução

### Requisitos
- **JDK 17+**
- **Apache Maven**

### 🔧 Comandos
Compile e execute o projeto com:
```bash
mvn clean package
mvn exec:java

Exemplo de Saída

Índice B-Tree (em ordem):
 1 3 7 10 18 25
Encontrado via índice → Produto{id=1, nome='SSD NVMe 1TB', preco=399.9}
Encontrado via índice → Produto{id=7, nome='Cabo HDMI 2.1 2m', preco=39.99}
Encontrado via índice → Produto{id=25, nome='Monitor 24" IPS 75Hz', preco=799.0}
ID 99 não encontrado no índice.
