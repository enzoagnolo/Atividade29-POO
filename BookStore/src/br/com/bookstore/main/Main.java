package br.com.bookstore.main;

import br.com.bookstore.dao.LivroDAO;
import br.com.bookstore.dao.LivroDAOSqlite;
import br.com.bookstore.model.Livro;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File db = new File("bookstore.db");
        if (db.exists()) {
            db.delete();
        }

        System.out.println("====================================================================");
        System.out.println("   BookStore Enterprise • Sistema de Gestão de Catálogo (Aula 29)");
        System.out.println("====================================================================");

        LivroDAO dao = new LivroDAOSqlite();

        Livro l1 = new Livro("Clean Code", "Robert C. Martin", 89.90);
        Livro l2 = new Livro("Design Patterns", "Gang of Four", 120.00);
        Livro l3 = new Livro("Refactoring", "Martin Fowler", 95.50);

        dao.salvar(l1);
        dao.salvar(l2);
        dao.salvar(l3);

        System.out.println("\nLivros cadastrados:");
        System.out.println("Salvo: " + l1);
        System.out.println("Salvo: " + l2);
        System.out.println("Salvo: " + l3);

        System.out.println("\nConsulta por ID (ID 2):");
        dao.buscarPorId(2L).ifPresentOrElse(
            livro -> System.out.println("Encontrado: " + livro),
            () -> System.out.println("Livro não encontrado.")
        );

        System.out.println("\nConsulta por ID inexistente (ID 999):");
        dao.buscarPorId(999L).ifPresentOrElse(
            livro -> System.out.println("Encontrado: " + livro),
            () -> System.out.println("Livro inexistente (Optional vazio, sem NullPointerException).")
        );

        System.out.println("\n--- LISTAGEM GERAL DE LIVROS ---");
        List<Livro> livros = dao.listarTodos();
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-25s | %-20s | %-10s\n", "ID", "TÍTULO", "AUTOR", "PREÇO");
        System.out.println("-------------------------------------------------------------------------------");
        for (Livro l : livros) {
            System.out.printf("%-6d | %-25s | %-20s | R$ %7.2f\n",
                    l.getId(), l.getTitulo(), l.getAutor(), l.getPreco());
        }
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Total de livros cadastrados: " + livros.size());
    }
}
