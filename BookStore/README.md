# Catálogo BookStore Enterprise • Padrão DAO & Mapeamento Objeto-Relacional
**Disciplina:** Programação Orientada a Objetos / Engenharia de Software  
**Laboratório:** Aula 29 • Persistência com SQLite, JDBC, DIP e Optional  

---

## 🏛️ Arquitetura do Projeto

O projeto adota os princípios de **Clean Architecture**, **SOLID** (com foco no Princípio da Responsabilidade Única - SRP e no Princípio da Inversão de Dependência - DIP) e o **Padrão DAO (Data Access Object)**:

```
c:\Users\enzo\Desktop\ATV19\
├── .vscode/
│   └── settings.json                  # Configuração para VS Code (Java Project)
├── .classpath                         # Configuração para Eclipse
├── .project                           # Configuração para Eclipse
├── executar.bat                       # Script automatizado de compilação e execução
├── README.md                          # Este documento
├── lib/
│   ├── sqlite-jdbc-3.45.1.0.jar       # Driver JDBC oficial do SQLite
│   ├── slf4j-api-1.7.36.jar           # Abstração de Logging
│   └── slf4j-simple-1.7.36.jar        # Implementação de Logging
└── src/
    └── br/
        └── com/
            └── bookstore/
                ├── model/
                │   └── Livro.java             # Entidade de domínio pura com invariantes blindadas
                ├── factory/
                │   └── ConexaoFactory.java    # Fábrica hermética de conexões SQLite
                ├── dao/
                │   ├── LivroDAO.java          # Contrato desacoplado (Interface)
                │   └── LivroDAOSqlite.java    # Implementação de persistência com PreparedStatement e RowMapper
                └── Main.java                  # Roteiro canônico de testes e validação
```

---

## 🚀 Como Executar

### Opção 1: Via Script `executar.bat`
Basta dar um duplo clique no arquivo `executar.bat` na raiz do projeto ou executá-lo no Prompt de Comando / PowerShell:
```cmd
.\executar.bat
```

### Opção 2: Via Linha de Comando Manual
```cmd
if not exist bin mkdir bin
javac -encoding UTF-8 -cp "lib/*" -d bin src/br/com/bookstore/model/*.java src/br/com/bookstore/factory/*.java src/br/com/bookstore/dao/*.java src/br/com/bookstore/*.java
java -cp "bin;lib/*" br.com.bookstore.Main
```

### Opção 3: No VS Code ou Eclipse
Abra a pasta `c:\Users\enzo\Desktop\ATV19` diretamente no VS Code (com a extensão *Extension Pack for Java*) ou importe como projeto existente no Eclipse. Execute o arquivo `Main.java`.

---

## 📋 Requisitos e Invariantes Atendidos

1. **`br.com.bookstore.model.Livro`**:
   - Atributos privados: `Long id`, `String titulo`, `String autor`, `double preco`.
   - Construtor completo para hidratação (`id, titulo, autor, preco`).
   - Construtor de conveniência de negócio (`titulo, autor, preco`) com `id = null`.
   - Blindagem com `IllegalArgumentException` para:
     - Título nulo ou com comprimento menor que 2 caracteres.
     - Autor nulo ou vazio.
     - Preço menor ou igual a `0.0`.
   - Encapsulamento estrito: apenas getters e o setter `setId(Long id)` para sincronizar a chave gerada.

2. **`br.com.bookstore.dao.LivroDAO`**:
   - Interface pura e desacoplada de JDBC.
   - Métodos: `salvar(Livro)`, `buscarPorId(Long)` retornando `Optional<Livro>` e `listarTodos()`.

3. **`br.com.bookstore.dao.LivroDAOSqlite`**:
   - Inicialização determinística de tabela via `CREATE TABLE IF NOT EXISTS` no construtor.
   - Gerenciamento seguro de recursos via `try-with-resources`.
   - Recuperação da chave gerada com `Statement.RETURN_GENERATED_KEYS` e `stmt.getGeneratedKeys()`.
   - RowMapper isolado e centralizado no método privado `mapearLinha(ResultSet rs)`.

4. **`br.com.bookstore.Main`**:
   - Injeção via interface (`LivroDAO dao = new LivroDAOSqlite()`).
   - Persistência das 3 obras com verificação dos IDs gerados (1, 2 e 3).
   - Consulta funcional com `buscarPorId(2L).ifPresentOrElse(...)`.
   - Consulta de ID inexistente (999L) demonstrando ausência de `NullPointerException`.
   - Relatório tabular completo de `listarTodos()`.
