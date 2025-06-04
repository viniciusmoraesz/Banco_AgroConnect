package br.org.agroconnect.views;

import br.org.agroconnect.dao.PessoaDao;
import br.org.agroconnect.exception.EntidadeNaoEncontradaException;
import br.org.agroconnect.models.Pessoa;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PessoaView {

    private final Scanner scanner;
    private final PessoaDao pessoaDao;

    public PessoaView() throws SQLException {
        this.scanner = new Scanner(System.in);
        this.pessoaDao = new PessoaDao();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU PESSOA ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Pesquisar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcao) {
                    case 1: cadastrarPessoa(); break;
                    case 2: listarPessoas(); break;
                    case 3: pesquisarPessoa(); break;
                    case 4: atualizarPessoa(); break;
                    case 5: removerPessoa(); break;
                    case 0: sair(); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void cadastrarPessoa() throws SQLException {
        System.out.println("\n--- Cadastrar Pessoa ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("UUID do Usuário: ");
        UUID id = UUID.fromString(scanner.nextLine());
        System.out.print("Tipo de Usuário (agricultor/especialista): ");
        String tipo = scanner.nextLine();

        Pessoa pessoa = new Pessoa(nome, cpf, id, tipo);
        pessoaDao.cadastrar(pessoa);

        System.out.println("Pessoa cadastrada com sucesso.");
    }

    private void listarPessoas() throws SQLException {
        System.out.println("\n--- Lista de Pessoas ---");
        List<Pessoa> pessoas = pessoaDao.listar();

        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada.");
        } else {
            for (Pessoa p : pessoas) {
                System.out.println("Nome: " + p.getNmNome());
                System.out.println("CPF: " + p.getNrCpf());
                System.out.println("UUID do Usuário: " + p.getCdUsuario());
                System.out.println("Tipo de Usuário: " + p.getTpUsuario());
                System.out.println("------------------------");
            }
        }
    }

    private void pesquisarPessoa() throws SQLException, EntidadeNaoEncontradaException {
        System.out.print("\nDigite o UUID do usuário: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Pessoa p = pessoaDao.pesquisar(id);

        System.out.println("Nome: " + p.getNmNome());
        System.out.println("CPF: " + p.getNrCpf());
        System.out.println("UUID do Usuário: " + p.getCdUsuario());
        System.out.println("Tipo de Usuário: " + p.getTpUsuario());
    }

    private void atualizarPessoa() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do usuário a ser atualizado: ");
            UUID id = UUID.fromString(scanner.nextLine());

            Pessoa p = pessoaDao.pesquisar(id);

            System.out.print("Novo nome [" + p.getNmNome() + "]: ");
            String novoNome = scanner.nextLine();
            if (novoNome != null && !novoNome.trim().isEmpty()) p.setNmNome(novoNome);

            System.out.print("Novo CPF [" + p.getNrCpf() + "]: ");
            String novoCpf = scanner.nextLine();
            if (novoCpf!= null && !novoCpf.trim().isEmpty()) p.setNrCpf(novoCpf);

            System.out.print("Novo tipo de usuário [" + p.getTpUsuario() + "]: ");
            String novoTipo = scanner.nextLine();
            if (novoTipo != null && !novoTipo.trim().isEmpty()) p.setTpUsuario(novoTipo);

            pessoaDao.atualizar(p);
            System.out.println("Pessoa atualizada com sucesso.");

        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerPessoa() throws SQLException {
        try {
            System.out.print("\nDigite o UUID do usuário a ser removido: ");
            UUID id = UUID.fromString(scanner.nextLine());

            pessoaDao.remover(id);
            System.out.println("Pessoa removida com sucesso.");
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sair() throws SQLException {
        pessoaDao.fecharConexao();
        System.out.println("Conexão encerrada. Até logo!");
    }

    public static void main(String[] args) {
        try {
            new PessoaView().exibirMenu();
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }
}

